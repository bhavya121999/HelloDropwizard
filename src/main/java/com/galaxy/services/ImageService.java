package com.galaxy.services;

import com.galaxy.client.S3Client;

/**
 * <File Description>.
 *
 * @author Amit Khandelwal
 */
public class ImageService {
	private final S3Client s3Client;

	public ImageService(S3Client s3Client) {
		this.s3Client = s3Client;
	}

	public void uploadFile(String uploadedFileLocation, String fileName, String fileType) {
		s3Client.uploadFileToS3(uploadedFileLocation, fileName, fileType);
	}
}
