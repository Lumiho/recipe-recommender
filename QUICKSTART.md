# Quick Start Guide

Get the Recipe Recommender app running in 5 minutes!

## Step 1: Set Up Backend (2 minutes)

1. Open a terminal/command prompt

2. Navigate to the backend folder:
```bash
cd recipe-recommender/backend
```

3. Get your Anthropic API key:
   - Go to https://console.anthropic.com/
   - Sign up or log in
   - Go to API Keys section
   - Create a new API key

4. Create `.env` file:
```bash
# Windows:
copy .env .env

# macOS/Linux:
cp .env .env
```

5. Edit `.env` and paste your API key:
```
ANTHROPIC_API_KEY=sk-ant-xxxxxxxxxxxxx
PORT=8000
```

6. Start the server:
```bash
# Windows:
start_server.bat

# macOS/Linux:
chmod +x start_server.sh
./start_server.sh

# Or manually:
python -m venv venv
# Activate venv (see README)
pip install -r requirements.txt
python server.py
```

You should see: "Application startup complete."

## Step 2: Set Up Android App (3 minutes)

1. Open Android Studio

2. Select "Open an Existing Project"

3. Navigate to and open: `recipe-recommender/android-app`

4. Wait for Gradle sync to complete

5. **Important for physical devices**:
   - Find your computer's IP address:
     - Windows: `ipconfig` (look for IPv4)
     - Mac/Linux: `ifconfig` or `ip addr`
   - Open `RecipeApiService.kt`
   - Change BASE_URL to: `"http://YOUR_IP:8000/"`
   - For emulator, keep it as `"http://10.0.2.2:8000/"`

6. Connect device or start emulator

7. Click the green "Run" button

## Step 3: Use the App!

1. Enter ingredients (one per line):
```
chicken
tomato
garlic
onion
```

2. Tap "Get Recipes"

3. Wait 5-10 seconds

4. Browse your personalized recipes!

## Common Issues

**"Connection failed"**
- Make sure backend server is running (check terminal)
- Check BASE_URL in RecipeApiService.kt
- Ensure phone and computer on same Wi-Fi
- Allow port 8000 through Windows Firewall

**"API key error"**
- Verify API key in `.env` file
- Make sure there are no extra spaces
- Restart the backend server

**Gradle sync failed**
- Click "File" > "Invalidate Caches / Restart"
- Check internet connection
- Ensure JDK 17 is installed

## Testing

Try these ingredient combinations:

**Italian:**
- tomato, pasta, garlic, basil, mozzarella

**Asian:**
- rice, soy sauce, ginger, chicken, vegetables

**Breakfast:**
- eggs, bread, milk, butter, cheese

**Dessert:**
- flour, sugar, butter, chocolate, eggs

Enjoy your AI-powered recipe recommendations!
