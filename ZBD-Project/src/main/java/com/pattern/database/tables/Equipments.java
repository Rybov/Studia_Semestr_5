package com.pattern.database.tables;


public class Equipments {

  private int idCharItem;
  private int idChar;
  private int idItem;
  private String Eq_name;
  private int levelOfUpgrade;
  private int bonusAttack;
  private int bonusDefence;
  private int weared;

  public Equipments(int idCharItem, int idChar, int idItem,String Eq_name, int levelOfUpgrade, int bonusAttack, int bonusDefence, int weared) {
    this.idCharItem = idCharItem;
    this.idChar = idChar;
    this.idItem = idItem;
    this.Eq_name=Eq_name;
    this.levelOfUpgrade = levelOfUpgrade;
    this.bonusAttack = bonusAttack;
    this.bonusDefence = bonusDefence;
    this.weared = weared;
  }

  public String getEq_name() {
    return Eq_name;
  }

  public void setEq_name(String eq_name) {
    Eq_name = eq_name;
  }

  public int getIdCharItem() {
    return idCharItem;
  }

  public void setIdCharItem(int idCharItem) {
    this.idCharItem = idCharItem;
  }

  public int getIdChar() {
    return idChar;
  }

  public void setIdChar(int idChar) {
    this.idChar = idChar;
  }

  public int getIdItem() {
    return idItem;
  }

  public void setIdItem(int idItem) {
    this.idItem = idItem;
  }

  public int getLevelOfUpgrade() {
    return levelOfUpgrade;
  }

  public void setLevelOfUpgrade(int levelOfUpgrade) {
    this.levelOfUpgrade = levelOfUpgrade;
  }

  public int getBonusDefence() {
    return bonusDefence;
  }

  public void setBonusDefence(int bonusDefence) {
    this.bonusDefence = bonusDefence;
  }

  public int getBonusAttack() {
    return bonusAttack;
  }

  public void setBonusAttack(int bonusAttack) {
    this.bonusAttack = bonusAttack;
  }

  public int getWeared() {
    return weared;
  }

  public void setWeared(int weared) {
    this.weared = weared;
  }
}
