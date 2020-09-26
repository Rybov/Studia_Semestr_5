package com.pattern.database.tables;


public class CharPet {

  private int idCharPet;
  private int idPet;
  private int idChar;
  private String Char_Pet_name;
  private int petLevel;
  private int experience;
  private int bonusAttack;
  private int bonusDefence;

  public CharPet(int idCharPet, int idPet, int idChar,String Char_Pet_name, int petLevel, int experience, int bonusAttack, int bonusDefence) {
    this.idCharPet = idCharPet;
    this.idPet = idPet;
    this.idChar = idChar;
    this.Char_Pet_name = Char_Pet_name;
    this.petLevel = petLevel;
    this.experience = experience;
    this.bonusAttack = bonusAttack;
    this.bonusDefence = bonusDefence;
  }

  public String getChar_Pet_name() {
    return Char_Pet_name;
  }

  public void setChar_Pet_name(String char_Pet_name) {
    Char_Pet_name = char_Pet_name;
  }

  public int getIdCharPet() {
    return idCharPet;
  }

  public void setIdCharPet(int idCharPet) {
    this.idCharPet = idCharPet;
  }

  public int getIdPet() {
    return idPet;
  }

  public void setIdPet(int idPet) {
    this.idPet = idPet;
  }

  public int getIdChar() {
    return idChar;
  }

  public void setIdChar(int idChar) {
    this.idChar = idChar;
  }

  public int getPetLevel() {
    return petLevel;
  }

  public void setPetLevel(int petLevel) {
    this.petLevel = petLevel;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public int getBonusAttack() {
    return bonusAttack;
  }

  public void setBonusAttack(int bonusAttack) {
    this.bonusAttack = bonusAttack;
  }

  public int getBonusDefence() {
    return bonusDefence;
  }

  public void setBonusDefence(int bonusDefence) {
    this.bonusDefence = bonusDefence;
  }
}
