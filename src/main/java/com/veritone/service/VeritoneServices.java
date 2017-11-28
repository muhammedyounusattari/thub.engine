package com.veritone.service;

import org.springframework.http.HttpHeaders;

import com.veritone.domain.HybridTranscriptionTbl;

public interface VeritoneServices {
	
	boolean validateRecordingId(String recordingId);
	
	HybridTranscriptionTbl getStatus(String recordingId);
	
	String getMediaFile(String recordId, String token);
	
	HttpHeaders createHttpHeaders(String token);
	
	String postHybridTranscription(String recordId, String mediaUrl, String token);
	
	String postMediaAssert(String recordId, String token);
}
