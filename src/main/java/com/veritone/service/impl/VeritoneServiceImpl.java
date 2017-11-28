package com.veritone.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.veritone.domain.HybridTranscriptionTbl;
import com.veritone.repo.HybridTranscriptionRepo;
import com.veritone.service.VeritoneServices;

@Service
public class VeritoneServiceImpl implements VeritoneServices{
	
	private static final Logger log = LoggerFactory.getLogger(VeritoneServiceImpl.class);
	
	@Autowired
	private HybridTranscriptionRepo hybridTranscriptionRepo;

	private RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Override
	 public String getMediaFile(String recordId, String token) {
		HttpHeaders headers = createHttpHeaders(token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		String url="https://api.veritone.com/api/recording/"+recordId+"/asset";
		log.info("Api Url "+url);
		ResponseEntity<String> response = getRestTemplate().exchange(url, HttpMethod.GET, entity, String.class);
		String str[] = response.getBody().split("_uri\":\"")[1].split("\"");
		log.info("File got successfully downloaded from media assert....."+str[0]);
		return str[0];
	}

	@Override
	public HttpHeaders createHttpHeaders(String token) {
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "Bearer "+token);
		header.set("Cache-Control", "no-cache");
		header.set("Content-Type", "application/json");
		log.info("Headers "+header);
		return header;
	}

	@Override
	public String postHybridTranscription(String recordId, String mediaUrl, String token) {
		//generating a random number
		long first14 = (long) (Math.random() * 100000000000000L);
		String jobId = ""+first14+"-"+recordId;
		log.info("new jobId..."+jobId);
		final HybridTranscriptionTbl hybridTranscription = new HybridTranscriptionTbl();
		hybridTranscription.setRecordingid(recordId);
		hybridTranscription.setRequestid(jobId);
		hybridTranscription.setTitle("audio_to_text_" + recordId +"_"+mediaUrl);
		hybridTranscription.setMediaurl(mediaUrl);
		hybridTranscription.setStatus("submitted");
		hybridTranscription.setInputmimetype("wav file");
		hybridTranscription.setDuration("24 Hours");
		hybridTranscription.setTimecode("Yes");
		hybridTranscription.setMediatype("General");
		hybridTranscription.setPackageid("VERITONE-24HRS");
		hybridTranscription.setMediadifficultylevel("0");
		hybridTranscription.setCallbackurl("/api/job/"+jobId+"/"+recordId);
		hybridTranscription.setTransformat("text");
		hybridTranscription.setTranslanguage("en-US");
		hybridTranscription.setTestjob("YES");
		hybridTranscription.setDailyvolumecap("");
		hybridTranscription.setAccuracypercentage("100");
		hybridTranscription.setOutput(" ");
		HybridTranscriptionTbl saved = hybridTranscriptionRepo.save(hybridTranscription);
		//int status = veritoneDao.insertHybridDetails(hybridTranscription);
		log.info("inserted the details into database "+ saved.getTranscriptionid());
		if(saved.getTranscriptionid() > 0){
		 String jobStatus="",outputUrl="";
		 while(true){
			 HybridTranscriptionTbl statusResult = getStatus(hybridTranscription.getRecordingid());
			 jobStatus = statusResult.getStatus();
			 outputUrl = statusResult.getMediaurl();
			 log.info("Current status is ..."+jobStatus);
			 if (statusResult.getStatus().equalsIgnoreCase("completed")&& outputUrl.trim()!="") {
				 String resultTTML = getTTMLFile(jobStatus, outputUrl.trim());
				 log.info("resultTTML....."+resultTTML);
				 log.info("******************Moving file to veritone media assert******************");
				 String result = postMediaAssert(recordId, token);
				 log.info("File(ttml) uploaded in veritone media assert "+result);
				 break;
			 } else {
				 try {
					 Thread.sleep(300000);
				 } catch (InterruptedException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		}
		return "Some text encrypted....";
	}
	
	private String getTTMLFile(String jobStatus,String outputUrl) {
		String response = getRestTemplate().getForObject(outputUrl, String.class);
		try {
			FileUtils.writeStringToFile(new File("thub.ttml"), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
		
	}

	@Override
	public String postMediaAssert(String recordId, String token) {
		final HttpEntity<Resource> requestEntity = createHttpEntity(recordId, token);
		String url="https://api.veritone.com/api/recording/"+recordId+"/asset";
		ResponseEntity<String> result = getRestTemplate().exchange(url, HttpMethod.POST, requestEntity,String.class);
		log.info("File sent successfully for transcription " + result.getBody()); 
		return result.getBody();
	}
	
	private static HttpEntity<Resource> createHttpEntity(String recordingId, String token) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Content-Type", "application/ttml+xml");
		requestHeaders.add("Authorization", "Bearer "+token);
		requestHeaders.add("x-veritone-asset-type", "transcript");
		requestHeaders.add("Cache-Control", "no-cache");		
		return new HttpEntity<Resource>(new FileSystemResource(new File("thub.ttml")), requestHeaders);
	}

	@Override
	public HybridTranscriptionTbl getStatus(String recordingId) {
		List<HybridTranscriptionTbl> hybridTranscriptionTblList = hybridTranscriptionRepo.findByRecordingid(recordingId);
		HybridTranscriptionTbl hybridTranscriptionTbl = hybridTranscriptionTblList.get(hybridTranscriptionTblList.size() - 1);
		return hybridTranscriptionTbl;
	}
	
	@Override
	public boolean validateRecordingId(String recordingId){
		boolean isValid = true;
		List<HybridTranscriptionTbl> hybridTranscriptionTblList = hybridTranscriptionRepo.findByRecordingid(recordingId);
		if(hybridTranscriptionTblList.size() > 0) {
			HybridTranscriptionTbl hybridTranscriptionTbl = hybridTranscriptionTblList.get(hybridTranscriptionTblList.size() - 1);
			if(hybridTranscriptionTbl != null && hybridTranscriptionTbl.getStatus().equalsIgnoreCase("completed"))
				isValid = false;
		}
		return isValid;
	}
}
