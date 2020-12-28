package com.models;

import java.io.InputStream;

public class AttachmentModel {
	private String realName;
	private String attachExt;
	
	private String attachRealName;
	private InputStream attachStream;
	private double attachSize;
	public String getAttachRealName() {
		return attachRealName;
	}
	public void setAttachRealName(String attachRealName) {
		this.attachRealName = attachRealName;
	}
	public InputStream getAttachStream() {
		return attachStream;
	}
	public void setAttachStream(InputStream attachStream) {
		this.attachStream = attachStream;
	}
	public double getAttachSize() {
		return attachSize;
	}
	public void setAttachSize(double attachSize) {
		this.attachSize = attachSize;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAttachExt() {
		return attachExt;
	}
	public void setAttachExt(String attachExt) {
		this.attachExt = attachExt;
	}


}
