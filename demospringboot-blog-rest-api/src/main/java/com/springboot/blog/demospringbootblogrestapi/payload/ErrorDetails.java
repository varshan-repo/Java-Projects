package com.springboot.blog.demospringbootblogrestapi.payload;

import java.util.Date;

public class ErrorDetails {

	private Date timeStamp;
	private String message;
	private String details;
	private String error;

	public ErrorDetails(Date timeStamp, String message, String details, String error) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
		this.error = error;
	}
//	public ErrorDetails(Date timeStamp, String message, String details) {
//		super();
//		this.timeStamp = timeStamp;
//		this.message = message;
//		this.details = details;
//	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public String getError() {
		return error;
	}
}
