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
$pgReady = $false

while (-not $pgReady) {
    $result = docker exec db pg_isready -U postgres 2>&1
    if ($result -match "accepting connections") {
        $pgReady = $true
    } else {
        Write-Host "PostgreSQL not ready yet, waiting..."
        Start-Sleep -Seconds 5
    }
}


Write-Host "Tables are ready. Executing dummy data script..."

Get-Content ../backend/db_init/dummyDataGenerator.sql | docker exec -i db psql -U postgres -d spendeeze

Write-Host "Dummy data inserted successfully."
Write-Host "Visit http://localhost:3000 in your browser."

