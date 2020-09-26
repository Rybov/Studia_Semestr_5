package com.pattern.database.tables;


public class Servers {

  private String name;
  private int idServer;
  private int amountOfCharacter;

  public Servers(String name, int idServer, int amountOfCharacter) {
    this.name = name;
    this.idServer = idServer;
    this.amountOfCharacter = amountOfCharacter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getIdServer() {
    return idServer;
  }

  public void setIdServer(int idServer) {
    this.idServer = idServer;
  }

  public int getAmountOfCharacter() {
    return amountOfCharacter;
  }

  public void setAmountOfCharacter(int amountOfCharacter) {
    this.amountOfCharacter = amountOfCharacter;
  }
}
