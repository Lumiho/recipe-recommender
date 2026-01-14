import os
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from anthropic import Anthropic
from dotenv import load_dotenv
from typing import List

load_dotenv()

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

client = Anthropic(api_key=os.getenv("ANTHROPIC_API_KEY"))

class FoodListRequest(BaseModel):
    foods: List[str]

class Recipe(BaseModel):
    name: str
    ingredients: List[str]
    instructions: List[str]
    prep_time: str
    difficulty: str

class RecipeResponse(BaseModel):
    recipes: List[Recipe]

@app.get("/")
def read_root():
    return {"message": "Recipe Recommender API is running"}

@app.post("/api/recommend", response_model=RecipeResponse)
async def recommend_recipes(request: FoodListRequest):
    if not request.foods:
        raise HTTPException(status_code=400, detail="Food list cannot be empty")

    foods_str = ", ".join(request.foods)

    prompt = f"""Based on the following ingredients: {foods_str}

Please recommend 3 different recipes that can be made using some or all of these ingredients.
For each recipe, provide:
1. Recipe name
2. Full list of ingredients needed (including quantities)
3. Step-by-step cooking instructions
4. Estimated prep time
5. Difficulty level (Easy, Medium, or Hard)

Format your response as JSON with this structure:
{{
  "recipes": [
    {{
      "name": "Recipe Name",
      "ingredients": ["ingredient 1 with quantity", "ingredient 2 with quantity", ...],
      "instructions": ["step 1", "step 2", ...],
      "prep_time": "30 minutes",
      "difficulty": "Easy"
    }}
  ]
}}

Only return the JSON, no additional text."""

    try:
        message = client.messages.create(
            model="claude-3-5-sonnet-20241022",
            max_tokens=2048,
            messages=[
                {"role": "user", "content": prompt}
            ]
        )

        response_text = message.content[0].text

        import json
        response_data = json.loads(response_text)

        return RecipeResponse(**response_data)

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error generating recipes: {str(e)}")

if __name__ == "__main__":
    import uvicorn
    port = int(os.getenv("PORT", 8000))
    uvicorn.run(app, host="0.0.0.0", port=port)
