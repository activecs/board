package com.kharkiv.board.dao;

import java.util.List;

import com.kharkiv.board.dto.schedule.TrainingVisit;

public interface TrainingVisitDao {

	List<TrainingVisit> getAllTrainigVisitsByUserId(Integer userId);

	List<TrainingVisit> getAllTrainigVisitsByScheduleId(Integer scheduleId);

	TrainingVisit addTrainingVisit(TrainingVisit trainingVisit);

	void deleteTrainingVisit(TrainingVisit trainingVisit);

	int deleteTrainingVisitById(Integer id);

	TrainingVisit updateTrainingVisit(TrainingVisit trainingVisit);
}
