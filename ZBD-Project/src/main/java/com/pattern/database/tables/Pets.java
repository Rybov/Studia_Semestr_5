package com.pattern.database.tables;


public class Pets {

  private int idPet;
  private String petName;
  private int attack;
  private int defence;

  public Pets(int idPet, String petName, int attack, int defence) {
    this.idPet = idPet;
    this.petName = petName;
    this.attack = attack;
    this.defence = defence;
  }

  public int getIdPet() {
    return idPet;
  }

  public void setIdPet(int idPet) {
    this.idPet = idPet;
  }

  public String getPetName() {
    return petName;
  }

  public void setPetName(String petName) {
    this.petName = petName;
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
