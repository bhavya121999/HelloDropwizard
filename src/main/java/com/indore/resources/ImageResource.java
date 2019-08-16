package com.indore.resources;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URL;

/**
 * Resource for user's album.
 *
 * @author Amit Khandelwal
 */
@Path("/image")
public class ImageResource {
    private static final Logger log = LoggerFactory.getLogger(ImageResource.class);

    String accessKey;
    String secretAccesKey;
    String bucketname;
    String clientregion;
    public ImageResource(String accessKey, String secretAccesKey, String bucketname, String clientregion) {
        this.accessKey=accessKey;
        this.secretAccesKey=secretAccesKey;
        this.bucketname=bucketname;
        this.clientregion=clientregion;


    }
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/upload")
    public Response imageUpload(@FormDataParam("file") final InputStream inputStream) {
       String uploadedFileLocation="";
       String location="/home/bhavya/Desktop/";
        try{
            //Double fileName= Math.floor(Math.random() * 90000000);

            uploadedFileLocation = location + "demo.jpeg";
            writeToFile(inputStream, uploadedFileLocation);
            //TODO: Need to create a unique file name and look into file type.
            uploadFileToS3(uploadedFileLocation, "demo", ".jpeg");
        }
        catch (Exception e){
            log.error(e.toString());
        }
        finally {
            File file=new File(uploadedFileLocation);
            if(file.exists())
                file.delete();
        }
        return Response.ok().build();
    }

    private void uploadFileToS3(String uploadedFileLocation, String fileName, String fileType) {

        // to create a client connection to access Amazon S3 web service.

        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,secretAccesKey
        );


        // configure the S3 client.
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(clientregion)
                .build();


        if(s3client.doesBucketExist(bucketname)) {
            log.info("Bucket name is not available."
                    + " Try again with a different Bucket name.");
            return;
        }

        s3client.createBucket(bucketname);

        // Uploading Objects
        s3client.putObject(
                bucketname,
                fileName+fileType,
                new File(uploadedFileLocation)
        );

        URL url = s3client.getUrl("bucketname",fileName);
        String urllocation=url.toString();
        log.info("s3 uploaded URL location is {}" , url.toString());



    }

    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }


    }}