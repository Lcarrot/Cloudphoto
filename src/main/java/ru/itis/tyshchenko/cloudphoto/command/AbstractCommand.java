package ru.itis.tyshchenko.cloudphoto.command;

import com.amazonaws.services.s3.AmazonS3;
import ru.itis.tyshchenko.cloudphoto.helper.FileHierarchyHelper;
import ru.itis.tyshchenko.cloudphoto.helper.IniFileHelper;
import ru.itis.tyshchenko.cloudphoto.model.config.ConfigModel;
import ru.itis.tyshchenko.cloudphoto.s3.S3Configurer;

public abstract class AbstractCommand implements Command {

  protected final ConfigModel model;
  protected final AmazonS3 s3;
  protected final FileHierarchyHelper hierarchyHelper;

  public AbstractCommand() {
    model = IniFileHelper.readConfigFile();
    s3 = S3Configurer.getAmazonS3(model);
    hierarchyHelper = new FileHierarchyHelper(model.bucket, s3);
  }
}
