# Recipe Recommender App

A mobile application that recommends recipes based on a list of ingredients provided by the user. The app uses a Python backend server that connects to Anthropic's Claude AI to generate personalized recipe recommendations.

## Project Structure

```
recipe-recommender/
├── backend/               # Python FastAPI server
│   ├── server.py         # Main API server
│   ├── requirements.txt  # Python dependencies
│   └── .env.example      # Environment variables template
├── android-app/          # Kotlin Android application
│   ├── app/             # App module
│   │   ├── src/main/
│   │   │   ├── java/com/example/recipeapp/  # Kotlin source files
│   │   │   ├── res/                          # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle
│   ├── build.gradle
│   └── settings.gradle
└── README.md
```

## Features

- Enter multiple ingredients
- Get 3 personalized recipe recommendations
- View detailed recipe information including:
  - Recipe name
  - Complete ingredients list with quantities
  - Step-by-step cooking instructions
  - Preparation time
  - Difficulty level

## Prerequisites

### Backend Requirements
- Python 3.8 or higher
- pip (Python package manager)
- Anthropic API key (get one at https://console.anthropic.com/)

### Android App Requirements
- Android Studio (latest version recommended)
- Android SDK (API level 24 or higher)
- JDK 17
- Android device or emulator

## Setup Instructions

### 1. Backend Setup

1. Navigate to the backend directory:
```bash
cd recipe-recommender/backend
```

2. Create a virtual environment (recommended):
```bash
python -m venv venv

# On Windows:
venv\Scripts\activate

# On macOS/Linux:
source venv/bin/activate
```

3. Install dependencies:
```bash
pip install -r requirements.txt
```

4. Create a `.env` file from the example:
```bash
# On Windows:
copy .env .env

# On macOS/Linux:
cp .env .env
```

5. Edit the `.env` file and add your Anthropic API key:
```
ANTHROPIC_API_KEY=your_actual_api_key_here
PORT=8000
```

6. Run the server:
```bash
python server.py
```

The server will start on `http://localhost:8000`

### 2. Android App Setup

1. Open Android Studio

2. Click "Open an Existing Project"

3. Navigate to and select the `recipe-recommender/android-app` directory

4. Wait for Gradle to sync (this may take a few minutes on first run)

5. **Important**: Update the server URL if needed
   - If testing on a physical device, update the BASE_URL in `RecipeApiService.kt`:
   ```kotlin
   private const val BASE_URL = "http://YOUR_COMPUTER_IP:8000/"
   ```
   - Replace `YOUR_COMPUTER_IP` with your computer's local IP address
   - For emulator, the default `http://10.0.2.2:8000/` should work

6. Connect your Android device or start an emulator:
   - For physical device: Enable USB debugging in Developer Options
   - For emulator: Use Android Studio's AVD Manager

7. Click the "Run" button (green play icon) in Android Studio

## Usage

1. Make sure the Python backend server is running

2. Launch the app on your Android device

3. Enter ingredients in the text field (one per line), for example:
   ```
   tomato
   onion
   chicken
   garlic
   ```

4. Tap "Get Recipes"

5. Wait a few seconds while the AI generates recipe recommendations

6. Scroll through the recommended recipes with full details

## API Endpoints

### GET /
Health check endpoint

**Response:**
```json
{
  "message": "Recipe Recommender API is running"
}
```

### POST /api/recommend
Get recipe recommendations based on ingredients

**Request Body:**
```json
{
  "foods": ["tomato", "chicken", "garlic"]
}
```

**Response:**
```json
{
  "recipes": [
    {
      "name": "Garlic Chicken with Tomatoes",
      "ingredients": ["2 chicken breasts", "4 tomatoes, diced", "4 cloves garlic, minced"],
      "instructions": ["Step 1...", "Step 2...", "Step 3..."],
      "prep_time": "30 minutes",
      "difficulty": "Easy"
    }
  ]
}
```

## Troubleshooting

### Backend Issues

- **Import errors**: Make sure you activated the virtual environment and installed all dependencies
- **API key errors**: Verify your Anthropic API key is correctly set in the `.env` file
- **Port already in use**: Change the PORT value in `.env` to a different port

### Android App Issues

- **Connection failed**:
  - Ensure the backend server is running
  - Check that the BASE_URL in RecipeApiService.kt is correct
  - For physical devices, ensure your phone and computer are on the same network
  - Check Windows Firewall settings to allow connections on port 8000

- **Build errors**:
  - Click "File" > "Invalidate Caches / Restart" in Android Studio
  - Ensure you have the correct JDK version (17)
  - Check that all Gradle dependencies downloaded successfully

- **App crashes**:
  - Check Logcat in Android Studio for error messages
  - Ensure you have INTERNET permission in AndroidManifest.xml (already included)

## Network Configuration for Physical Devices

To test on a physical Android device:

1. Find your computer's local IP address:
   - Windows: Run `ipconfig` in Command Prompt, look for IPv4 Address
   - macOS/Linux: Run `ifconfig` or `ip addr show`

2. Update `RecipeApiService.kt`:
   ```kotlin
   private const val BASE_URL = "http://192.168.1.XXX:8000/"  // Use your IP
   ```

3. Ensure your phone and computer are on the same Wi-Fi network

4. You may need to allow the connection through your firewall

## Technologies Used

### Backend
- FastAPI: Modern Python web framework
- Anthropic Python SDK: Integration with Claude AI
- Uvicorn: ASGI server
- Python-dotenv: Environment variable management

### Android App
- Kotlin: Primary programming language
- Retrofit: HTTP client for API calls
- Gson: JSON serialization/deserialization
- Material Design Components: UI elements
- Coroutines: Asynchronous programming
- RecyclerView: Efficient list display

## License

This project is provided as-is for educational purposes.

## Credits

Built with Anthropic's Claude AI for intelligent recipe recommendations.
