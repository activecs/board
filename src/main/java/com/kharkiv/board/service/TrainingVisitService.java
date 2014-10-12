package com.kharkiv.board.service;

import java.util.List;

import com.kharkiv.board.dto.schedule.TrainingVisit;

public interface TrainingVisitService {

	List<TrainingVisit> getAllTrainigVisitsByUserId(Integer userId);

	List<TrainingVisit> getAllTrainigVisitsByScheduleId(Integer scheduleId);

	TrainingVisit addTrainingVisit(TrainingVisit trainingVisit);

	void deleteTrainingVisit(TrainingVisit trainingVisit);

	void deleteTrainingVisitById(Integer id);

	TrainingVisit updateTrainingVisit(TrainingVisit trainingVisit);
}
