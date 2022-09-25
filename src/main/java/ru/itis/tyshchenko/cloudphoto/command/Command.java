package ru.itis.tyshchenko.cloudphoto.command;

import java.util.Map;

public interface Command {

  void execute(Map<String, String> arguments);
}
