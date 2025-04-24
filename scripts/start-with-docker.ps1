$ErrorActionPreference = "Stop"

$envFile = "../config/.env.docker"
New-Item -ItemType Directory -Force -Path (Split-Path $envFile) | Out-Null
if (!(Test-Path $envFile)) {
    New-Item -Path $envFile -ItemType File -Force | Out-Null
}

$envMap = @{}
if (Test-Path $envFile) {
    Get-Content $envFile | ForEach-Object {
        if ($_ -match "^\s*([^#][^=]+)=(.*)$") {
            $envMap[$matches[1].Trim()] = $matches[2].Trim()
        }
    }
}

function Prompt-WithDefault($key, $default) {
    $current = if ($envMap.ContainsKey($key)) { $envMap[$key] } else { $null }
    $shownDefault = if ($current) { $current } else { $default }
    $input = Read-Host "$key [default: $shownDefault]"
    if ([string]::IsNullOrWhiteSpace($input)) {
        return $shownDefault
    }
    return $input
}

Write-Host ""
Write-Host "Configure environment variables for Docker setup."
Write-Host ""
Write-Host "Entering custom values override the default .env.docker file."
Write-Host ""
Write-Host "If you have an existing Docker volume for this project with custom PostgreSQL credentials, please enter those values when prompted."
Write-Host ""
Write-Host "If the default values shown in [brackets] do not match your existing setup, enter the values you set up previously when prompted."
Write-Host ""
Write-Host "Otherwise, press Enter to accept the default value shown in [brackets]."
Write-Host ""

$envMap["SPRING_DATASOURCE_URL"] = Prompt-WithDefault "SPRING_DATASOURCE_URL" "jdbc:postgresql://eeze_db:5432/eeze"
$envMap["SPRING_DATASOURCE_USERNAME"] = Prompt-WithDefault "SPRING_DATASOURCE_USERNAME" "postgres"
$envMap["SPRING_DATASOURCE_PASSWORD"] = Prompt-WithDefault "SPRING_DATASOURCE_PASSWORD" "admin1234"

$envMap["POSTGRES_USER"] = Prompt-WithDefault "POSTGRES_USER" "postgres"
$envMap["POSTGRES_PASSWORD"] = Prompt-WithDefault "POSTGRES_PASSWORD" "admin1234"
$envMap["POSTGRES_DB"] = Prompt-WithDefault "POSTGRES_DB" "eeze"

$envMap["JWT_SECRET"] = Prompt-WithDefault "JWT_SECRET" "0c4872abdb41610145e2c6819fc7420b589e37b624dc57eb7c1f212b14017d28"
$envMap["JWT_EXPIRATION_MS"] = Prompt-WithDefault "JWT_EXPIRATION_MS" "86400000"

$envMap.GetEnumerator() | ForEach-Object {
    "$($_.Key)=$($_.Value)"
} | Set-Content -Encoding UTF8 $envFile

Write-Host "`nEnvironment saved to $envFile"

$envMap.GetEnumerator() | ForEach-Object {
    [System.Environment]::SetEnvironmentVariable($_.Key, $_.Value, "Process")
}

Write-Host "`nStarting Docker containers..."
docker-compose up -d --build

Write-Host "`nWaiting for PostgreSQL to be ready..."

$dbReady = $false
while (-not $dbReady) {
    try {
        docker exec -it db_eeze psql -U "$envMap['POSTGRES_USER']" -d "$envMap['POSTGRES_DB']" -c "SELECT 1 FROM information_schema.tables WHERE table_name IN ('expense', 'income', 'member', 'member_roles', 'transaction_category');" | Out-Null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "Tables are ready. Executing dummy data script..."
            docker exec -i eeze_db psql -U "$envMap['POSTGRES_USER']" -d "$envMap['POSTGRES_DB']" < ../backend/db_init/dummyDataGenerator.sql
            Write-Host "Dummy data inserted successfully."
            $dbReady = $true
        } else {
            Write-Host "Tables not ready yet, waiting..."
            Start-Sleep -Seconds 5
        }
    } catch {
        Write-Host "Error connecting to the database, waiting..."
        Start-Sleep -Seconds 5
    }
}

Write-Host "`nVisit http://localhost:3000 in your browser."
Write-Host "`nTo stop running the containers enter: docker compose down, to also remove associated volumes enter: docker compose down -v"
