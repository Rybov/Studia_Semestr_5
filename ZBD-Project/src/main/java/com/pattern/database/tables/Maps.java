package com.pattern.database.tables;


public class Maps {

  private int idMap;
  private String mapName;
  private String mapFile;

  public Maps(int idMap, String mapName, String mapFile) {
    this.idMap = idMap;
    this.mapName = mapName;
    this.mapFile = mapFile;
  }

  public int getIdMap() { return idMap;
  }

  public void setIdMap(int idMap) {
    this.idMap = idMap;
  }


  public String getMapName() {
    return mapName;
  }

  public void setMapName(String mapName) {
    this.mapName = mapName;
  }


  public String getMapFile() {
    return mapFile;
  }

  public void setMapFile(String mapFile) {
    this.mapFile = mapFile;
  }

}
