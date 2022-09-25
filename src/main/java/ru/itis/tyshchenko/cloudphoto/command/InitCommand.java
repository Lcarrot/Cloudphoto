package ru.itis.tyshchenko.cloudphoto.command;

import java.util.Map;

import com.amazonaws.services.s3.AmazonS3;
import ru.itis.tyshchenko.cloudphoto.helper.IniFileHelper;
import ru.itis.tyshchenko.cloudphoto.model.config.ConfigModel;
import ru.itis.tyshchenko.cloudphoto.s3.S3Configurer;

public class InitCommand implements Command {

  @Override
  public void execute(Map<String, String> arguments) {
    ConfigModel model = IniFileHelper.writeConfigFile();
    AmazonS3 s3 = S3Configurer.getAmazonS3(model);
    if (s3.listBuckets().stream().noneMatch(bucket -> bucket.getName().equals(model.bucket))) {
      s3.createBucket(model.bucket);
    }
  }
}
