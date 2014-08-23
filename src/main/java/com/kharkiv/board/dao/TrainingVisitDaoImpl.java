package com.kharkiv.board.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.kharkiv.board.dto.schedule.TrainingVisit;
import com.kharkiv.board.util.QueryNamesConstants.TrainingVisitsQueris;


@Repository("trainingVisitDao")
public class TrainingVisitDaoImpl implements TrainingVisitDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TrainingVisit> getAllTrainigVisitsByUserId(Integer userId) {
        TypedQuery<TrainingVisit> query = em.createNamedQuery(TrainingVisitsQueris.GET_4_USER_BY_USER_ID,
                TrainingVisit.class);
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public List<TrainingVisit> getAllTrainigVisitsByScheduleId(Integer scheduleId) {
        TypedQuery<TrainingVisit> query = em.createNamedQuery(TrainingVisitsQueris.GET_4_SCHEDULE_BY_SCHEDULE_ID,
                TrainingVisit.class);
        return query.setParameter("scheduleId", scheduleId).getResultList();
    }

    @Override
    public TrainingVisit addTrainingVisit(TrainingVisit trainingVisit) {
        em.persist(trainingVisit);
        em.flush();
        return trainingVisit;
    }

    @Override
    public void deleteTrainingVisit(TrainingVisit trainingVisit) {
        em.remove(trainingVisit);
    }

    @Override
    public int deleteTrainingVisitById(Integer id) {
        TypedQuery<TrainingVisit> query = em.createNamedQuery(TrainingVisitsQueris.DELETE_BY_ID, TrainingVisit.class);
        return query.setParameter("id", id).executeUpdate();
    }

    @Override
    public TrainingVisit updateTrainingVisit(TrainingVisit trainingVisit) {
        return em.merge(trainingVisit);
    }

}
