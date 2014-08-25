package com.kharkiv.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.dao.TrainingVisitDao;
import com.kharkiv.board.dto.schedule.TrainingVisit;

@Service("trainingVisitService")
@Transactional
public class TrainingVisitServiceImpl implements TrainingVisitService {
	
	private static final String ERR_MESSAGE_USER_ID_CANNOT_BE_NULL = "User id cannot be null";
	private static final String ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL = "Schedule id cannot be null";
	private static final String ERR_MESSAGE_TRAINING_VISIT_CANNOT_BE_NULL = "Training visit cannot be null";
	private static final String ERR_MESSAGE_TRAINING_VISIT_ID_CANNOT_BE_NULL = "Training visit id cannot be null";
	
	@Inject
	private TrainingVisitDao trainingVisitDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TrainingVisit> getAllTrainigVisitsByUserId(Integer userId) {
		if(userId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_USER_ID_CANNOT_BE_NULL);
		return trainingVisitDao.getAllTrainigVisitsByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TrainingVisit> getAllTrainigVisitsByScheduleId(Integer scheduleId) {
		if(scheduleId == null)
			throw new IllegalArgumentException(ERR_MESSAGE_SCHEDULE_ID_CANNOT_BE_NULL);
		return trainingVisitDao.getAllTrainigVisitsByScheduleId(scheduleId);
	}

	@Override
	public TrainingVisit addTrainingVisit(TrainingVisit trainingVisit) {
		if(trainingVisit == null)
			throw new IllegalArgumentException(ERR_MESSAGE_TRAINING_VISIT_CANNOT_BE_NULL);
		return trainingVisitDao.addTrainingVisit(trainingVisit);
	}

	@Override
	public void deleteTrainingVisit(TrainingVisit trainingVisit) {
		if(trainingVisit == null)
			throw new IllegalArgumentException(ERR_MESSAGE_TRAINING_VISIT_CANNOT_BE_NULL);
		trainingVisitDao.deleteTrainingVisit(trainingVisit);
	}

	@Override
	public void deleteTrainingVisitById(Integer id) {
		if(id == null)
			throw new IllegalArgumentException(ERR_MESSAGE_TRAINING_VISIT_ID_CANNOT_BE_NULL);
		trainingVisitDao.deleteTrainingVisitById(id);
	}

	@Override
	public TrainingVisit updateTrainingVisit(TrainingVisit trainingVisit) {
		if(trainingVisit == null)
			throw new IllegalArgumentException(ERR_MESSAGE_TRAINING_VISIT_CANNOT_BE_NULL);
		return trainingVisitDao.updateTrainingVisit(trainingVisit);
	}

}
