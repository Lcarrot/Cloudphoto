package ru.itis.tyshchenko.cloudphoto.command;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import ru.itis.tyshchenko.cloudphoto.model.Album;
import ru.itis.tyshchenko.cloudphoto.model.Photo;

public class DownloadCommand extends AbstractCommand {

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
      pathDir = Paths.get("");
    }
    for (Photo photo : album.photos) {
      S3Object s3Object = s3.getObject(model.bucket, photo.getFileName());
      Path path = pathDir.resolve(Paths.get(photo.getSourceName()));
      try (BufferedOutputStream outputStream = new BufferedOutputStream(
          new FileOutputStream(path.toFile()));
           S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
        outputStream.write(inputStream.readAllBytes());
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
