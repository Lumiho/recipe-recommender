@echo off
echo Starting Recipe Recommender Backend Server...
echo.

REM Check if virtual environment exists
if not exist "venv" (
    echo Creating virtual environment...
    python -m venv venv
)

REM Activate virtual environment
call venv\Scripts\activate

REM Check if .env exists
if not exist ".env" (
    echo Error: .env file not found!
    echo Please create a .env file with your ANTHROPIC_API_KEY
    echo You can copy .env.example and add your API key
    pause
    exit /b 1
)

REM Install/update dependencies
echo Installing dependencies...
pip install -r requirements.txt

echo.
echo Starting server on http://localhost:8000
echo Press Ctrl+C to stop the server
echo.

REM Start the server
python server.py
