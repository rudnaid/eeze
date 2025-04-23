$ErrorActionPreference = "Stop"

Write-Host "Loading environment..."
Get-Content "../config/.env.docker" | ForEach-Object {
    $var = $_.Split('=')
    if ($var.Length -eq 2) {
        [System.Environment]::SetEnvironmentVariable($var[0].Trim(), $var[1].Trim(), [System.EnvironmentVariableTarget]::Process)
    }
}

docker-compose up -d --build

Write-Host "Waiting for PostgreSQL to be ready..."
$tablesReady = $false

while (-not $tablesReady) {
    $result = docker exec -i db psql -U postgres -d spendeeze -c "SELECT 1 FROM information_schema.tables WHERE table_name IN ('expense', 'income', 'member', 'member_roles', 'transaction_category');" 2>&1
    if ($result -match "1") {
        $tablesReady = $true
    } else {
        Write-Host "Tables not ready yet, waiting..."
        Start-Sleep -Seconds 5
    }
}

Write-Host "Tables are ready. Executing dummy data script..."

docker exec -i db psql -U postgres -d spendeeze < ../backend/db_init/dummyDataGenerator.sql

Write-Host "Dummy data inserted successfully."
Write-Host "Visit http://localhost:3000 in your browser."
