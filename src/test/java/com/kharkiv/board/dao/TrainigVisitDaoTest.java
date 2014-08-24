package com.kharkiv.board.dao;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kharkiv.board.dto.schedule.TrainingVisit;
import com.kharkiv.board.util.QueryNamesConstants.TrainingVisitsQueris;

public class TrainigVisitDaoTest {

    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<TrainingVisit> query;

    @InjectMocks
    private TrainingVisitDao tvDao = new TrainingVisitDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    public void shouldReturnAllTrainingVisitsForGivenUserId_whenCallGetAllTrainingVisitsByUserId() {
        Integer userId = 1;
        TrainingVisit trainingVisit = new TrainingVisit();
        when(query.getResultList()).thenReturn(Arrays.asList(trainingVisit));
        List<TrainingVisit> usersVisits = tvDao.getAllTrainigVisitsByUserId(userId);
        verify(em).createNamedQuery(TrainingVisitsQueris.GET_4_USER_BY_USER_ID, TrainingVisit.class);
        verify(query).setParameter("userId", userId);
        verify(query).getResultList();
        assertTrue(CollectionUtils.isNotEmpty(usersVisits));
        assertThat(usersVisits).containsOnly(trainingVisit);
    }

    @Test
    public void shouldReturnAllTrainingVisitsForGivenScheduleId_whenCallGetAllTrainingVisitsByScheduleId() {
        Integer scheduleId = 1;
        TrainingVisit trainingVisit = new TrainingVisit();
        when(query.getResultList()).thenReturn(Arrays.asList(trainingVisit));
        List<TrainingVisit> schedulesVisits = tvDao.getAllTrainigVisitsByScheduleId(scheduleId);
        verify(em).createNamedQuery(TrainingVisitsQueris.GET_4_SCHEDULE_BY_SCHEDULE_ID, TrainingVisit.class);
        verify(query).setParameter("scheduleId", scheduleId);
        verify(query).getResultList();
        assertTrue(CollectionUtils.isNotEmpty(schedulesVisits));
        assertThat(schedulesVisits).containsOnly(trainingVisit);
    }

    @Test
    public void shouldPersistGivenTrainingVisit_whenCallAddTrainingVisit() {
        TrainingVisit toAdd = new TrainingVisit();
        TrainingVisit added = tvDao.addTrainingVisit(toAdd);
        verify(em).persist(toAdd);
        verify(em).flush();
        assertThat(added).isSameAs(toAdd);
    }

    @Test
    public void shouldDeleteGivenTrainingVisit_whenCallDeleteTrainingVisit() {
        TrainingVisit toDelete = new TrainingVisit();
        tvDao.deleteTrainingVisit(toDelete);
        verify(em).remove(toDelete);
    }

    @Test
    public void shouldDeleteTrainingVisitByGivenId_whenCallDeleteTrainingVisitById() {
        Integer id = 1;
        when(query.executeUpdate()).thenReturn(1);
        int deleted = tvDao.deleteTrainingVisitById(id);
        verify(em).createNamedQuery(TrainingVisitsQueris.DELETE_BY_ID, TrainingVisit.class);
        verify(query).setParameter("id", id);
        verify(query).executeUpdate();
        assertThat(deleted).isEqualTo(1);
    }

    @Test
    public void shouldUpdateGivenTrainingVisit_whenCallUpdateTrainingVisit() {
        TrainingVisit toUpdate = new TrainingVisit();
        when(em.merge(any(TrainingVisit.class))).thenReturn(toUpdate);
        TrainingVisit updated = tvDao.updateTrainingVisit(toUpdate);
        verify(em).merge(toUpdate);
        assertThat(updated).isSameAs(toUpdate);
    }
}
