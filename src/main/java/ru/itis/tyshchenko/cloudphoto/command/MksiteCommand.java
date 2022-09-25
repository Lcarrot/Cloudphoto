package ru.itis.tyshchenko.cloudphoto.command;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import ru.itis.tyshchenko.cloudphoto.model.Album;
import ru.itis.tyshchenko.cloudphoto.model.Hierarchy;
import ru.itis.tyshchenko.cloudphoto.model.Photo;

public class MksiteCommand extends AbstractCommand {

  private static final String TEMPLATE_DIR = "templates";
  private static final String ERROR_PAGE = "error.html";
  private static final String INDEX_PAGE = "index.html";
  private static final String ALBUM_TEMPLATE = "album.html";

  @Override
  public void execute(Map<String, String> arguments) {
    createErrorPage();
    Hierarchy hierarchy = hierarchyHelper.getHierarchy();
    for (Album album : hierarchy.album) {
      createAlbumPage(album);
    }
    createIndexPage();
    System.out.println("https://" + model.bucket  + ".website.yandexcloud.net/");
  }

  private void createErrorPage() {
    String page = readTemplate(Paths.get(TEMPLATE_DIR, ERROR_PAGE));
    uploadPage(ERROR_PAGE, page);
  }

  private void createIndexPage() {
    String page = readTemplate(Paths.get(TEMPLATE_DIR, INDEX_PAGE));
    StringBuilder albums = new StringBuilder();
    for (Album album : hierarchyHelper.getHierarchy().album) {
      albums.append("<li><a href=\"album")
          .append(album.albumName)
          .append(".html\">")
          .append(album.sourceAlbumName)
          .append("</a></li>");
    }
    page = page.replaceAll("\\$\\{albums}", albums.toString());
    uploadPage(INDEX_PAGE, page);
  }

  private void createAlbumPage(Album album) {
    StringBuilder photos = new StringBuilder();
    String page = readTemplate(Paths.get(TEMPLATE_DIR, ALBUM_TEMPLATE));
    for (Photo photo : album.photos) {
      photos.append("<img src=\"")
          .append(photo.getFileName())
          .append("\" data-title=\"")
          .append(photo.getSourceName())
          .append("\">");
    }
    page = page.replaceAll("\\$\\{images}", photos.toString());
    String fileName = "album" + album.getAlbumName() + ".html";
    uploadPage(fileName, page);
  }

  private void uploadPage(String fileName, String page) {
    byte[] bytes = page.getBytes(StandardCharsets.UTF_8);
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("text/html; charset=\"UTF-8\"");
    metadata.setContentLength(bytes.length);
    PutObjectRequest request = new PutObjectRequest(model.bucket, fileName,
        new ByteArrayInputStream(bytes), metadata
    );
    s3.putObject(request);
  }

  private String readTemplate(Path path) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        Objects.requireNonNull(
            getClass().getClassLoader().getResourceAsStream(path.toString())),
        StandardCharsets.UTF_8
    ))) {
      StringBuilder page = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        page.append(line);
        line = reader.readLine();
      }
      return page.toString();
    }
    catch (IOException e) {
      throw new RuntimeException("Can't read template", e);
    }
  }
}
