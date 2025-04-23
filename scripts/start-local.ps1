Write-Host "Loading environment..."
Get-Content "../config/.env.local" | ForEach-Object {
    if ($_ -match "^\s*([^#][^=]+)=(.+)$") {
        [System.Environment]::SetEnvironmentVariable($matches[1].Trim(), $matches[2].Trim(), "Process")
    }
}

Write-Host "Starting backend..."
Start-Process powershell -ArgumentList '-NoExit', '-Command', "cd ../backend; .\mvnw spring-boot:run"

Write-Host "Starting frontend..."
Start-Process powershell -ArgumentList '-NoExit', '-Command', "cd ../frontend; npm run dev"
