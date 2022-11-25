package com.example.absar1;

public class recipe {
    private String name;
    private String ingredient;
    private int calories;
    private int Ptime;
    private String instructions;
   // private String image;

    public recipe(String name, String ingredient, String calories, String ptime, String instructions) {
        this.name = name;
        this.ingredient = ingredient;
        this.calories = calories;
        this.Ptime = ptime;
        this.instructions = instructions;
       // this.image = image;
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

    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getPtime() {
        return Ptime;
    }
    public void setPtime(int ptime) {
        Ptime = ptime;
    }

    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "recipe{" + "name='" + name + '\'' + ", calories=" + calories + ", Ptime=" + Ptime + ", instructions='" + instructions + '\''  + ", ingredient='" + ingredient + '\'' + '}';
    }
}
