package com.galaxy.client;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.galaxy.config.AwsConfig;

/**
 * Exit point to all S3 calls in app.
 *
 * @author Amit Khandelwal
 */
public class S3Client {
    private static final Logger log = LoggerFactory.getLogger(S3Client.class);

    private final AmazonS3 s3client;
    private final String bucketname;

    public S3Client(AwsConfig awsConfig) {
        AWSCredentials credentials = new BasicAWSCredentials(awsConfig.getAccesskey(), awsConfig.getSecretaccesskey());
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsConfig.getClientregion())
                .build();
        this.bucketname = awsConfig.getBucketname();
    }

    public void uploadFileToS3(String uploadedFileLocation, String fileName, String fileType) {
        if (s3client.doesBucketExistV2(bucketname)) {
            log.error("Bucket name is not available. Try again with a different Bucket name.");
            return;
        }

        s3client.createBucket(bucketname);

        // Uploading Objects
        s3client.putObject(bucketname, fileName + fileType, new File(uploadedFileLocation));
        URL url = s3client.getUrl("bucketname", fileName);
        String urllocation = url.toString();
        log.info("s3 uploaded URL location is {}", url.toString());
    }
}
