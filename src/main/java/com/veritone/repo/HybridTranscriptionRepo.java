package com.veritone.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veritone.domain.HybridTranscriptionTbl;

public interface HybridTranscriptionRepo extends JpaRepository<HybridTranscriptionTbl, Integer> {

	List<HybridTranscriptionTbl> findByRecordingid(String recordingid);
}
