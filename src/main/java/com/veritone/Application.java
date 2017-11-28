package com.veritone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.veritone.domain.Payload;
import com.veritone.service.VeritoneServices;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired(required=true)
    VeritoneServices veritoneServices;
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
    	log.info("Calling the run method");
    	String filePath = System.getenv("PAYLOAD_FILE"); //"/Users/Viswanath/Desktop/payload.json";
    	log.info("reading the file from the path "+filePath);
		Payload payload = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			payload = new Gson().fromJson(br, Payload.class);
			String recordId = payload.getRecordingId();
			String token = payload.getToken();
			log.info("Recording Id from the input json "+recordId);
			log.info("Token from the input json "+token);
			boolean validRequestId = veritoneServices.validateRecordingId(recordId);
			log.info("Validating the record in the database "+validRequestId);
			if(validRequestId) {
				log.info("Calling the media file method");
				String mediaUrl = veritoneServices.getMediaFile(recordId, token);
				log.info("Retrieved the media url "+mediaUrl);
				log.info("Saving the details to the database");
				veritoneServices.postHybridTranscription(recordId, mediaUrl, token);
				System.exit(0);
			} else {
				log.error("The transcription is completed for the recording id ==> "+recordId);
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
    }
}
