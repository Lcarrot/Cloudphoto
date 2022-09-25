package ru.itis.tyshchenko.cloudphoto;

import java.util.HashMap;
import java.util.Map;

import ru.itis.tyshchenko.cloudphoto.command.CommandManager;

public class Main {

  public static void main(String[] args) {
    CommandManager manager = new CommandManager();
    String command = args[0];
    Map<String, String> arguments = new HashMap<>();
    for (int i = 1; i < args.length; i+=2) {
      arguments.put(args[i], args[i+1]);
    }
    manager.executeCommand(command, arguments);
  }
}
