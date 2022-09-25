package ru.itis.tyshchenko.cloudphoto.command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import ru.itis.tyshchenko.cloudphoto.model.Album;

public class UploadCommand extends AbstractCommand {

  @Override
  public void execute(Map<String, String> arguments) {
    String albumName = arguments.get("--album");
    Album album = hierarchyHelper.getAlbumFromHierarchy(albumName);
    if (album.photos == null) {
      album.photos = new ArrayList<>();
    }
    Path pathDir;
    if (arguments.containsKey("--path")) {
      pathDir = Paths.get(arguments.get(arguments.get("--path")));
    }
    else {
      pathDir = Paths.get(System.getProperty("user.dir"));
    }
    File[] files = pathDir.toFile().listFiles();
    if (files != null) {
      for (File file : files) {
        if (!file.getName().contains(".jpg") && !file.getName().contains(".jpeg")) {
          continue;
        }
        hierarchyHelper.saveFileInBucket(album, file);
      }
    }
    hierarchyHelper.saveHierarchy();
  }
}
