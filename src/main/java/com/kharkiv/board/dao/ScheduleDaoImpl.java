package com.kharkiv.board.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.util.QueryNamesConstants.ScheduleQueries;

@Repository("scheduleDao")
public class ScheduleDaoImpl implements ScheduleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Schedule> getAllSchedules() {
        TypedQuery<Schedule> query = em.createNamedQuery(ScheduleQueries.GET_ALL, Schedule.class);
        return query.getResultList();
    }

    @Override
    public Schedule getScheduleById(Integer id) {
        TypedQuery<Schedule> query = em.createNamedQuery(ScheduleQueries.GET_BY_ID, Schedule.class);
        return query.setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Schedule> getSchedulesByUserId(Integer userId) {
        TypedQuery<Schedule> query = em.createNamedQuery(ScheduleQueries.GET_4_USER_BY_USER_ID, Schedule.class);
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Schedule> getSchedulesByUsername(String username) {
        TypedQuery<Schedule> query = em.createNamedQuery(ScheduleQueries.GET_4_USER_BY_USERNAME, Schedule.class);
        return query.setParameter("username", username).getResultList();
    }

    @Override
    public void deleteSchedule(Schedule schedule) {
        em.remove(schedule);
    }

    @Override
    public int deleteScheduleById(Integer id) {
        TypedQuery<Schedule> query = em.createNamedQuery(ScheduleQueries.DELETE_BY_ID, Schedule.class);
        return query.setParameter("id", id).executeUpdate();
    }

    @Override
    public Schedule addSchedule(Schedule schedule) {
        em.persist(schedule);
        em.flush();
        return schedule;
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {
        return em.merge(schedule);
    }

}
