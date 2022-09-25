package ru.itis.tyshchenko.cloudphoto.command;

import java.util.Map;

import ru.itis.tyshchenko.cloudphoto.model.Album;
import ru.itis.tyshchenko.cloudphoto.model.Hierarchy;
import ru.itis.tyshchenko.cloudphoto.model.Photo;

public class DeleteCommand extends AbstractCommand {

  @Override
  public void execute(Map<String, String> arguments) {
    String albumName = arguments.get("--album");
    Hierarchy hierarchy = hierarchyHelper.getHierarchy();
    Album album = hierarchy.getAlbum()
        .stream()
        .filter(album1 -> album1.getSourceAlbumName().equals(albumName))
        .findAny()
        .orElseThrow(() -> new RuntimeException("Can't find album with name: " + albumName));
    if (arguments.containsKey("--photo")) {
      String photoName = arguments.get("--photo");
      if (album.photos == null || album.photos.isEmpty()) {
        throw new RuntimeException("Album is empty");
      }
      Photo photo = album.photos.stream()
          .filter(photo1 -> photo1.sourceName.equals(photoName))
          .findAny()
          .orElseThrow(() -> new RuntimeException("Can't find photo with name: " + photoName));
      album.getPhotos().remove(photo);
      s3.deleteObject(model.bucket, photo.getFileName());
    }
    else {
      if (album.photos != null && !album.photos.isEmpty()) {
        for (Photo photo : album.photos) {
          s3.deleteObject(model.bucket, photo.fileName);
        }
      }
      hierarchy.getAlbum().remove(album);
    }
    hierarchyHelper.saveHierarchy();
  }
}
