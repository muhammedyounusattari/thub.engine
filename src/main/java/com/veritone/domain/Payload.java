package com.veritone.domain;

public class Payload {

	private String applicationId;
	private String jobId;
	private String taskId;
	private String recordingId;
	private String outputLanguage;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getRecordingId() {
		return recordingId;
	}

	public void setRecordingId(String recordingId) {
		this.recordingId = recordingId;
	}

	public String getOutputLanguage() {
		return outputLanguage;
	}

	public void setOutputLanguage(String outputLanguage) {
		this.outputLanguage = outputLanguage;
	}

}