package com.pattern.database.tables;


public class CharQuests {
    private int idCharQuest;
    private int idQuest;
    private int idChar;
    private String status;

    public CharQuests(int idCharQuest,int idQuest, int idChar, String status) {
        this.idCharQuest=idCharQuest;
        this.idQuest = idQuest;
        this.idChar = idChar;
        this.status = status;
    }

  public int getIdCharQuest() {
    return idCharQuest;
  }

  public void setIdCharQuest(int idCharQuest) {
    this.idCharQuest = idCharQuest;
  }

  public int getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(int idQuest) {
        this.idQuest = idQuest;
    }

    public int getIdChar() {
        return idChar;
    }

    public void setIdChar(int idChar) {
        this.idChar = idChar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
