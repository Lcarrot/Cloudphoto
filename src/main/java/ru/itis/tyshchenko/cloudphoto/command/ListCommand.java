package ru.itis.tyshchenko.cloudphoto.command;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import ru.itis.tyshchenko.cloudphoto.model.Album;
import ru.itis.tyshchenko.cloudphoto.model.Hierarchy;
import ru.itis.tyshchenko.cloudphoto.model.Photo;

public class ListCommand extends AbstractCommand {

  @Override
  public void execute(Map<String, String> arguments) {
    Hierarchy hierarchy = hierarchyHelper.getHierarchy();
    if (arguments.containsKey("--album")) {
      String albumName = arguments.get("--album");
      Album album = hierarchy.album.stream()
          .filter(current -> current.sourceAlbumName.equals(albumName))
          .findAny()
          .orElseThrow(() -> new RuntimeException("Can't find album with name: " + albumName));
      try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
        writer.write("List of files from album: " + albumName + " \n");
        if (album.photos == null || album.photos.isEmpty()) {
          throw new RuntimeException("Can't find photos in album");
        }
        for (Photo photo: album.getPhotos()) {
          writer.write(photo.sourceName + "\n");
        }
      }
      catch (IOException e) {
       throw new RuntimeException(e);
      }
    }
    else {
      try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
        writer.write("List of albums: \n");
        if (hierarchy.album == null || hierarchy.album.isEmpty()) {
          throw new RuntimeException("Can't find any album");
        }
        for (Album album: hierarchy.album) {
          writer.write(album.sourceAlbumName + "\n");
        }
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
