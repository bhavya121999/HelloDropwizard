package com.indore.resources;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indore.services.ImageService;

/**
 * Resource for user's album.
 *
 * @author Amit Khandelwal
 */
@Path("/image")
public class ImageResource {
    private static final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private final ImageService imageService;

    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/upload/{id}")
    public Response imageUpload(@PathParam("id") @NotEmpty String id, @FormDataParam("file") final InputStream inputStream, @FormDataParam("type") final String fileType) {
        String uploadedFileLocation = "";
        String location = "/home/bhavya/Desktop/";
        try {
            uploadedFileLocation = location + id.trim() + "." + fileType;
            writeToFile(inputStream, uploadedFileLocation);
            //TODO: Need to create a unique file name and look into file type.
            imageService.uploadFile(uploadedFileLocation, "demo", ".jpeg");
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            File file = new File(uploadedFileLocation);
            if (file.exists())
                file.delete();
        }
        return Response.ok().build();
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


    }


}
