$ErrorActionPreference = "Stop"

$envFile = "../config/.env.local"
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
Write-Host "Configure environment variables for your setup."
Write-Host ""
Write-Host "If you already have PostgreSQL installed with your own user and password, enter those values when prompted."
Write-Host "Entering custom values will override the default .env.local file."
Write-Host ""
Write-Host "Otherwise, press Enter to accept the default value shown in [brackets]."
Write-Host ""

$envMap["SPRING_DATASOURCE_URL"] = Prompt-WithDefault "SPRING_DATASOURCE_URL" "jdbc:postgresql://localhost:5432/spendeeze"
$envMap["SPRING_DATASOURCE_USERNAME"] = Prompt-WithDefault "SPRING_DATASOURCE_USERNAME" "postgres"
$envMap["SPRING_DATASOURCE_PASSWORD"] = Prompt-WithDefault "SPRING_DATASOURCE_PASSWORD" "admin1234"

$envMap["POSTGRES_USER"] = Prompt-WithDefault "POSTGRES_USER" "postgres"
$envMap["POSTGRES_PASSWORD"] = Prompt-WithDefault "POSTGRES_PASSWORD" "admin1234"

$envMap["JWT_SECRET"] = Prompt-WithDefault "JWT_SECRET" "0c4872abdb41610145e2c6819fc7420b589e37b624dc57eb7c1f212b14017d28"
$envMap["JWT_EXPIRATION_MS"] = Prompt-WithDefault "JWT_EXPIRATION_MS" "86400000"

$envMap.GetEnumerator() | ForEach-Object {
    "$($_.Key)=$($_.Value)"
} | Set-Content -Encoding UTF8 $envFile

Write-Host "`nEnvironment saved to $envFile"

$envMap.GetEnumerator() | ForEach-Object {
    [System.Environment]::SetEnvironmentVariable($_.Key, $_.Value, "Process")
}

Write-Host "`nLoading environment..."

Write-Host "`nStarting backend..."
Start-Process powershell -ArgumentList '-NoExit', '-Command', "cd ../backend; .\mvnw spring-boot:run"

Write-Host "Starting frontend..."
Start-Process powershell -ArgumentList '-NoExit', '-Command', "cd ../frontend; npm run dev"

Write-Host "`nPress Ctrl + C to exit."
