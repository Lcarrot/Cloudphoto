package ru.itis.tyshchenko.cloudphoto.model;

import java.util.ArrayList;
import java.util.List;

public class Album {

  public Album() {
  }

  public Album(String sourceAlbumName, String albumName) {
    this.albumName = albumName;
    this.sourceAlbumName = sourceAlbumName;
    photos = new ArrayList<>();
  }

  public String albumName;
  public String sourceAlbumName;
  public List<Photo> photos;

  public void setAlbumName(String albumName) {
    this.albumName = albumName;
  }

  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
  }

  public String getAlbumName() {
    return albumName;
  }

  public List<Photo> getPhotos() {
    return photos;
  }

  public String getSourceAlbumName() {
    return sourceAlbumName;
  }

  public void setSourceAlbumName(String sourceAlbumName) {
    this.sourceAlbumName = sourceAlbumName;
  }
}
