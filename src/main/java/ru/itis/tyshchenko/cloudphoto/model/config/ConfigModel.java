package ru.itis.tyshchenko.cloudphoto.model.config;

import ru.itis.tyshchenko.cloudphoto.annotation.EnterByUser;
import ru.itis.tyshchenko.cloudphoto.constants.ConfigFileConstants;

public class ConfigModel {

  public final String region = "ru-central1";
  public final String endpoint = "https://storage.yandexcloud.net";
  @EnterByUser(nameForUser = "bucket name", fileKey = ConfigFileConstants.BUCKET_KEY)
  public String bucket;
  @EnterByUser(nameForUser = "aws_access_key_id", fileKey = ConfigFileConstants.AWS_ACCESS_KEY_ID)
  public String awsAccessKeyId;
  @EnterByUser(nameForUser = "aws_secret_access_key",
      fileKey = ConfigFileConstants.AWS_SECRET_ACCESS_KEY)
  public String awsSecretAccessKey;
}
