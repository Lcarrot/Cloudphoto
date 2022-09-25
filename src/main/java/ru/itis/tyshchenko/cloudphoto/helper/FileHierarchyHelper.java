package ru.itis.tyshchenko.cloudphoto.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.tyshchenko.cloudphoto.model.Album;
import ru.itis.tyshchenko.cloudphoto.model.Hierarchy;
import ru.itis.tyshchenko.cloudphoto.model.Photo;

public class FileHierarchyHelper {

  private static final String HIERARCHY_FILE = "hierarchy.json";
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private final String bucket;
  private Hierarchy hierarchy;
  private final AmazonS3 s3;

  public FileHierarchyHelper(String bucket, AmazonS3 s3) {
    this.bucket = bucket;
    this.s3 = s3;
  }

  public Hierarchy getHierarchy() {
    if (hierarchy != null) {
      return hierarchy;
    }
    if (s3.listObjects(bucket)
        .getObjectSummaries()
        .stream()
        .noneMatch(s3ObjectSummary -> s3ObjectSummary.getKey().equals(HIERARCHY_FILE))) {
      hierarchy = new Hierarchy();
      return hierarchy;
    }
    S3Object s3Object = s3.getObject(bucket, HIERARCHY_FILE);
    try {
      hierarchy = objectMapper.readValue(
          new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8),
          Hierarchy.class
      );
      return hierarchy;
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void saveHierarchy() {
    try {
      String value = objectMapper.writeValueAsString(hierarchy);
      s3.putObject(bucket, HIERARCHY_FILE, value);
    }
    catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void saveFileInBucket(Album album, File file) {
    String name = file.getName();
    Optional<Photo> opPhoto = album.photos.stream()
        .filter(photo -> photo.sourceName.equals(name))
        .findAny();
    Photo photo;
    if (opPhoto.isEmpty()) {
      int currentSize = album.photos.size() + 1;
      String photoName = album.albumName + "/" + currentSize + ".jpg";
      photo = new Photo(name, photoName);
      album.photos.add(photo);
    }
    else {
      photo = opPhoto.get();
    }
    s3.putObject(bucket, photo.getFileName(), file);
  }

  public Album getAlbumFromHierarchy(String albumName) {
    getHierarchy().setAlbum(Optional.ofNullable(getHierarchy().album).orElse(new ArrayList<>()));
    return getHierarchy().album
        .stream()
        .filter(album -> album.sourceAlbumName.equals(albumName))
        .findAny().orElseGet(() -> {
          Album album = new Album(albumName, String.valueOf(getHierarchy().album.size() + 1));
          getHierarchy().album.add(album);
          return album;
        });
  }
}

