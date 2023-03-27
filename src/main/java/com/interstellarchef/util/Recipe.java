package com.interstellarchef.util;

import com.interstellarchef.model.Item;
import java.util.List;

public class Recipe {
  private String name;
  private List<Item> ingredients;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Item> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Item> ingredients) {
    this.ingredients = ingredients;
  }
}
