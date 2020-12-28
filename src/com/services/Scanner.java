package com.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.models.AttachmentModel;

public class Scanner{
	private UploadedFile file;
	private List<AttachmentModel> attachList;
	
	
	public void handleFileUpload(UploadedFile file) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(file.getFileName());
			attach.setAttachSize(file.getSize());
			attach.setAttachStream(file.getInputstream());
			
			attachList.add(attach);

		} catch (Exception e) {

		}
	}
	
	



	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
		if(this.file.getFileName().length()>0)
		handleFileUpload(file);

	}
	
	
	
}
