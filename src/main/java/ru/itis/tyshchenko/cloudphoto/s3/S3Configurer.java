package ru.itis.tyshchenko.cloudphoto.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import ru.itis.tyshchenko.cloudphoto.model.config.ConfigModel;

public class S3Configurer {

  public static AmazonS3 getAmazonS3(ConfigModel configModel) {
    AWSCredentials credentials = new BasicAWSCredentials(configModel.awsAccessKeyId,
        configModel.awsSecretAccessKey
    );
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withEndpointConfiguration(
            new AmazonS3ClientBuilder.EndpointConfiguration(configModel.endpoint,
                configModel.region
            ))
        .build();
  }
}
