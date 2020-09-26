package com.pattern.database.tables;


public class Items {

  private int idItem;
  private String name;
  private String type;
  private int attack;
  private int defence;

  public Items(int idItem, String name, String type, int attack, int defence) {
    this.idItem = idItem;
    this.name = name;
    this.type = type;
    this.attack = attack;
    this.defence = defence;
  }

  public int getIdItem() {
    return idItem;
  }

  public void setIdItem(int idItem) {
    this.idItem = idItem;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public int getDefence() {
    return defence;
  }

  public void setDefence(int defence) {
    this.defence = defence;
  }
}
