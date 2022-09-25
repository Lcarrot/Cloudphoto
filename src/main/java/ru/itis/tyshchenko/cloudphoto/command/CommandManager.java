package ru.itis.tyshchenko.cloudphoto.command;

import java.util.Map;

public class CommandManager {

  public void executeCommand(String command, Map<String, String> arguments) {
    switch (command) {
      case "upload" -> new UploadCommand().execute(arguments);
      case "download" -> new DownloadCommand().execute(arguments);
      case "list" -> new ListCommand().execute(arguments);
      case "delete" -> new DeleteCommand().execute(arguments);
      case "init" -> new InitCommand().execute(arguments);
      case "mksite" -> new MksiteCommand().execute(arguments);
      default -> throw new RuntimeException("Can't find command");
    }
  }
}
