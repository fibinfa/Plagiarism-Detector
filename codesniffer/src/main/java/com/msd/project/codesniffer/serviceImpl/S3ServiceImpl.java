package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin, Hao
   @version 1.0
   @since 06-Apr-2018
*/

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.msd.project.codesniffer.service.S3Service;
import com.msd.project.codesniffer.utils.Constants;

@Service
public class S3ServiceImpl implements S3Service{
	
	private Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.S3Service#uploadReportFolderS3(java.lang.String, java.lang.String)
	 */
	public void uploadReportFolderS3(String folderName, String fullPath){
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJP7LOHUIGACMLXDA", "9ZshvPrbTZmEwwKzdEJAarqdTvt/HoIro+BB4vfS");
		AmazonS3 s3client = new AmazonS3Client(credentials);
		Boolean recursive = true;
		TransferManager xfer_mgr = TransferManagerBuilder.standard()
				.withS3Client(s3client)
				.withDisableParallelDownloads(false)
				.build();
		try {
			xfer_mgr.uploadDirectory(Constants.BUCKET_NAME,
					folderName, new File(fullPath), recursive);
		} catch (AmazonServiceException e) {
			logger.debug("Exception thrown in S3 service");
		}
	}
}
