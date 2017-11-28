package com.veritone.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hybrid_transcription_tbl", catalog = "dbhybrid_transcription")
public class HybridTranscriptionTbl implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8781789435654842414L;
	private Integer transcriptionid;
	private String requestid;
	private String title;
	private String mediaurl;
	private String inputmimetype;
	private String duration;
	private String packageid;
	private String mediatype;
	private String timecode;
	private String mediadifficultylevel;
	private String callbackurl;
	private String transformat;
	private String translanguage;
	private String testjob;
	private String dailyvolumecap;
	private String accuracypercentage;
	private String output;
	private String status;
	private String recordingid;

	public HybridTranscriptionTbl() {
	}

	public HybridTranscriptionTbl(String requestid, String title, String mediaurl, String inputmimetype,
			String duration, String packageid, String mediatype, String timecode, String mediadifficultylevel,
			String callbackurl, String transformat, String translanguage, String testjob, String dailyvolumecap,
			String accuracypercentage, String output, String status, String recordingid) {
		this.requestid = requestid;
		this.title = title;
		this.mediaurl = mediaurl;
		this.inputmimetype = inputmimetype;
		this.duration = duration;
		this.packageid = packageid;
		this.mediatype = mediatype;
		this.timecode = timecode;
		this.mediadifficultylevel = mediadifficultylevel;
		this.callbackurl = callbackurl;
		this.transformat = transformat;
		this.translanguage = translanguage;
		this.testjob = testjob;
		this.dailyvolumecap = dailyvolumecap;
		this.accuracypercentage = accuracypercentage;
		this.output = output;
		this.status = status;
		this.recordingid = recordingid;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "transcriptionid", unique = true, nullable = false)
	public Integer getTranscriptionid() {
		return this.transcriptionid;
	}

	public void setTranscriptionid(Integer transcriptionid) {
		this.transcriptionid = transcriptionid;
	}

	@Column(name = "requestid", length = 1200)
	public String getRequestid() {
		return this.requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

	@Column(name = "title", length = 1200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "mediaurl", length = 1200)
	public String getMediaurl() {
		return this.mediaurl;
	}

	public void setMediaurl(String mediaurl) {
		this.mediaurl = mediaurl;
	}

	@Column(name = "inputmimetype", length = 1200)
	public String getInputmimetype() {
		return this.inputmimetype;
	}

	public void setInputmimetype(String inputmimetype) {
		this.inputmimetype = inputmimetype;
	}

	@Column(name = "duration", length = 1200)
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Column(name = "packageid", length = 1200)
	public String getPackageid() {
		return this.packageid;
	}

	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}

	@Column(name = "mediatype", length = 1200)
	public String getMediatype() {
		return this.mediatype;
	}

	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}

	@Column(name = "timecode", length = 1200)
	public String getTimecode() {
		return this.timecode;
	}

	public void setTimecode(String timecode) {
		this.timecode = timecode;
	}

	@Column(name = "mediadifficultylevel", length = 1200)
	public String getMediadifficultylevel() {
		return this.mediadifficultylevel;
	}

	public void setMediadifficultylevel(String mediadifficultylevel) {
		this.mediadifficultylevel = mediadifficultylevel;
	}

	@Column(name = "callbackurl", length = 1200)
	public String getCallbackurl() {
		return this.callbackurl;
	}

	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}

	@Column(name = "transformat", length = 1200)
	public String getTransformat() {
		return this.transformat;
	}

	public void setTransformat(String transformat) {
		this.transformat = transformat;
	}

	@Column(name = "translanguage", length = 1200)
	public String getTranslanguage() {
		return this.translanguage;
	}

	public void setTranslanguage(String translanguage) {
		this.translanguage = translanguage;
	}

	@Column(name = "testjob", length = 1200)
	public String getTestjob() {
		return this.testjob;
	}

	public void setTestjob(String testjob) {
		this.testjob = testjob;
	}

	@Column(name = "dailyvolumecap", length = 1200)
	public String getDailyvolumecap() {
		return this.dailyvolumecap;
	}

	public void setDailyvolumecap(String dailyvolumecap) {
		this.dailyvolumecap = dailyvolumecap;
	}

	@Column(name = "accuracypercentage", length = 1200)
	public String getAccuracypercentage() {
		return this.accuracypercentage;
	}

	public void setAccuracypercentage(String accuracypercentage) {
		this.accuracypercentage = accuracypercentage;
	}

	@Column(name = "output", length = 1200)
	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Column(name = "status", length = 1000)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "recordingid", length = 1000)
	public String getRecordingid() {
		return this.recordingid;
	}

	public void setRecordingid(String recordingid) {
		this.recordingid = recordingid;
	}

}
