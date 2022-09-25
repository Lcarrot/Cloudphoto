package ru.itis.tyshchenko.cloudphoto.model;

import java.util.List;

public class Hierarchy {

  public Hierarchy(List<Album> album) {
    this.album = album;
  }

  public Hierarchy() {
  }

  public List<Album> album;

  public List<Album> getAlbum() {
    return album;
  }

  public void setAlbum(List<Album> album) {
    this.album = album;
  }
}
