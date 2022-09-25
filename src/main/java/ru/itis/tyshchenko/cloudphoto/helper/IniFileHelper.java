package ru.itis.tyshchenko.cloudphoto.helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.ini4j.Wini;
import ru.itis.tyshchenko.cloudphoto.annotation.EnterByUser;
import ru.itis.tyshchenko.cloudphoto.model.config.ConfigModel;

import static ru.itis.tyshchenko.cloudphoto.constants.ConfigFileConstants.CATEGORY;
import static ru.itis.tyshchenko.cloudphoto.constants.ConfigFileConstants.ENDPOINT_URL_KEY;
import static ru.itis.tyshchenko.cloudphoto.constants.ConfigFileConstants.REGION_KEY;

public class IniFileHelper {

  private static final String[] FULL_FILE_PATH_DIR = new String[] {".config", "cloudphoto"};
  private static final String FILE_NAME = "cloudphotorc";

  public static ConfigModel writeConfigFile() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      Path path = getConfigFilePath();
      ConfigModel configModel = new ConfigModel();
      Wini ini = new Wini(path.toFile());
      ini.put(CATEGORY, ENDPOINT_URL_KEY, configModel.endpoint);
      ini.put(CATEGORY, REGION_KEY, configModel.region);
      Arrays.stream(configModel.getClass().getFields()).forEach(field ->
          Optional.ofNullable(field.getAnnotation(EnterByUser.class))
              .ifPresent(annotation -> {
                try {
                  System.out.println("Enter your " + annotation.nameForUser() + " : ");
                  String line = reader.readLine().trim();
                  ini.put(CATEGORY, annotation.fileKey(), line);
                  field.set(configModel, line);
                }
                catch (IOException | IllegalAccessException e) {
                  throw new RuntimeException("Can't write config file");
                }
              }));
      ini.store();
      return configModel;
    }
    catch (IOException e) {
      throw new RuntimeException("Can't write config file", e);
    }
  }

  private static Path getConfigFilePath() throws IOException {
    Path path = Paths.get(System.getProperty("user.home"), FULL_FILE_PATH_DIR);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
    path = path.resolve(FILE_NAME);
    if (!Files.exists(path)) {
      Files.createFile(path);
    }
    return path;
  }

  public static ConfigModel readConfigFile() {
    try {
      Path path = getConfigFilePath();
      ConfigModel configModel = new ConfigModel();
      Wini ini = new Wini(path.toFile());
      Arrays.stream(configModel.getClass().getFields()).forEach(field ->
          Optional.ofNullable(field.getAnnotation(EnterByUser.class))
              .ifPresent(annotation -> {
                try {
                  field.set(configModel, ini.get(CATEGORY, annotation.fileKey()));
                }
                catch (IllegalAccessException e) {
                  throw new RuntimeException("Can't write config file");
                }
              }));
      return configModel;
    }
    catch (IOException e) {
      throw new RuntimeException("Can't read config file", e);
    }
  }
}
