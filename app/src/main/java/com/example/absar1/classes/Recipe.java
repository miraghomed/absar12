package com.example.absar1.classes;

public class Recipe {
    private String name;
    private String ingredient;
    private String calories;
    private String Ptime;
    private String instructions;

    private String image;

    public Recipe() {
    }

    public Recipe(String name, String ingredient, String calories, String ptime, String instructions,String image) {
        this.name = name;
        this.ingredient = ingredient;
        this.calories = calories;
        this.Ptime = ptime;
        this.instructions = instructions;
        this.image = image;
    }



    public String getIngredient() {
        return ingredient;
    }
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }
    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getPtime() {
        return Ptime;
    }
    public void setPtime(String ptime) {
        Ptime = ptime;
    }

    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", calories='" + calories + '\'' +
                ", Ptime='" + Ptime + '\'' +
                ", instructions='" + instructions + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
