package com.pattern.database.tables;


public class Quests {

  private int idQuest;
  private String name;
  private String txtFile;
  private int minLevel;
  private int idNpc;

  public Quests(int idQuest, String name, String txtFile, int minLevel, int idNpc) {
    this.idQuest = idQuest;
    this.name = name;
    this.txtFile = txtFile;
    this.minLevel = minLevel;
    this.idNpc = idNpc;
  }

  public int getIdQuest() {
    return idQuest;
  }

  public void setIdQuest(int idQuest) {
    this.idQuest = idQuest;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTxtFile() {
    return txtFile;
  }

  public void setTxtFile(String txtFile) {
    this.txtFile = txtFile;
  }

  public int getMinLevel() {
    return minLevel;
  }

  public void setMinLevel(int minLevel) {
    this.minLevel = minLevel;
  }

  public int getIdNpc() {
    return idNpc;
  }

  public void setIdNpc(int idNpc) {
    this.idNpc = idNpc;
  }
}
