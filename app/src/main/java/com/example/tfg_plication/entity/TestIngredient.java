package com.example.tfg_plication.entity;

public class TestIngredient {
    private long id;
    private String ingredient;
    private String amount;

    public TestIngredient(){}

    public TestIngredient(long id, String ingredient, String amount) {
        this.id = id;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String  getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
