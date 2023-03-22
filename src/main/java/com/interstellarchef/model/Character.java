package com.interstellarchef.model;

import java.util.HashMap;
import java.util.List;

public class Character {
  private String name;
  private String description;
  private HashMap<String,String> actionResponse; //action, text printed in response to action
  private List<Item> items;
  private List<String> dialog;


  public void talk(){
    String result = "";
    System.out.println(getActionResponse().get("talk"));
    System.out.println(String.format(dialog.get(0), name));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public HashMap<String, String> getActionResponse() {
    return actionResponse;
  }

  public void setActionResponse(HashMap<String, String> actionResponse) {
    this.actionResponse = actionResponse;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
