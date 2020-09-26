package com.pattern.database.tables;


public class Characters {

  private int idChar;
  private String name;
  private int idUser;
  private String clas;
  private String gender;
  private int charlvl;
  private int experience;
  private int idServer;
  private int attack;
  private int defence;
  private int idMap;


  public Characters(int idChar, String name, int idUser, String clas, String gender, int charlvl, int experience, int idServer, int attack, int defence, int idMap) {
    this.idChar = idChar;
    this.name = name;
    this.idUser = idUser;
    this.clas = clas;
    this.gender = gender;
    this.charlvl = charlvl;
    this.experience = experience;
    this.idServer = idServer;
    this.attack = attack;
    this.defence = defence;
    this.idMap = idMap;
  }

  public int getIdChar() { return idChar; }

  public void setIdChar(int idChar) {
    this.idChar = idChar;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public int getIdUser() {
    return idUser;
  }

  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }


  public String getClas() {
    return clas;
  }

  public void setClas(String clas) {
    this.clas = clas;
  }


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }


  public int getCharlvl() {
    return charlvl;
  }

  public void setCharlvl(int charlvl) {
    this.charlvl = charlvl;
  }


  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }


  public int getIdServer() {
    return idServer;
  }

  public void setIdServer(int idServer) {
    this.idServer = idServer;
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


  public int getIdMap() {
    return idMap;
  }

  public void setIdMap(int idMap) {
    this.idMap = idMap;
  }

}
