package com.msd.project.codesniffer.service;

import org.springframework.stereotype.Service;

/**
   @author fibin
   @version 1.0
   @since 06-Apr-2018
*/


@Service
public interface S3Service {
	/**
	 * upload report to the s3 address
	 * @param folderName
	 * @param fullPath
	 */
	public void uploadReportFolderS3(String folderName, String fullPath);
}
