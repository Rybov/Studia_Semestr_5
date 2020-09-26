package com.pattern.database.tables;


public class Npcs {

  private int idNpc;
  private String name;
  private int idMap;
  private int xPosition;
  private int yPosition;
  private int zPosition;

  public Npcs(int idNpc, String name, int idMap, int xPosition, int yPosition, int zPosition) {
    this.idNpc = idNpc;
    this.name = name;
    this.idMap = idMap;
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.zPosition = zPosition;
  }

  public int getIdNpc() {
    return idNpc;
  }

  public void setIdNpc(int idNpc) {
    this.idNpc = idNpc;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getIdMap() {
    return idMap;
  }

  public void setIdMap(int idMap) {
    this.idMap = idMap;
  }

  public int getXPosition() {
    return xPosition;
  }

  public void setXPosition(int xPosition) {
    this.xPosition = xPosition;
  }

  public int getYPosition() {
    return yPosition;
  }

  public void setYPosition(int yPosition) {
    this.yPosition = yPosition;
  }

  public int getZPosition() {
    return zPosition;
  }

  public void setZPosition(int zPosition) {
    this.zPosition = zPosition;
  }
}
