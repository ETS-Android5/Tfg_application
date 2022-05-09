package com.example.tfg_plication.entity;

public class Ingredient {
    private long id;
    private String name;

    public Ingredient(){}
    public Ingredient(long id, String name)
    {
        this.id=id;
        this.name=name;
    }
    public void setId(long id)
    {
        this.id=id;
    }
    public long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }
}
