package ru.itis.tyshchenko.cloudphoto.model;

public class Photo {

  public String sourceName;
  public String fileName;

  public Photo(String sourceName, String fileName) {
    this.sourceName = sourceName;
    this.fileName = fileName;
  }

  public Photo() {
  }

  public String getSourceName() {
    return sourceName;
  }

  public String getFileName() {
    return fileName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
