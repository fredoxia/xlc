package com.onlineMIS.ORM.entity.headQ.user;

import java.io.Serializable;

public class SystemLog implements Serializable {
	private String fileName;
	private String filePath;
	private String fileSize;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	

}
