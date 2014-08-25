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

    private static final Integer ID = 1;
    private static final Integer USER_ID = 1;
    private static final Integer SCHEDULE_ID = 1;
    
    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<TrainingVisit> query;

    private TrainingVisit trainingVisit;
    
    @InjectMocks
    private TrainingVisitDao tvDao = new TrainingVisitDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        trainingVisit = new TrainingVisit();
        
        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    public void shouldReturnAllTrainingVisitsForGivenUserId_whenCallGetAllTrainingVisitsByUserId() {
        when(query.getResultList()).thenReturn(Arrays.asList(trainingVisit));
        List<TrainingVisit> usersVisits = tvDao.getAllTrainigVisitsByUserId(USER_ID);
        verify(em).createNamedQuery(TrainingVisitsQueris.GET_4_USER_BY_USER_ID, TrainingVisit.class);
        verify(query).setParameter("userId", USER_ID);
        verify(query).getResultList();
        assertTrue(CollectionUtils.isNotEmpty(usersVisits));
        assertThat(usersVisits).containsOnly(trainingVisit);
    }

    @Test
    public void shouldReturnAllTrainingVisitsForGivenScheduleId_whenCallGetAllTrainingVisitsByScheduleId() {
        when(query.getResultList()).thenReturn(Arrays.asList(trainingVisit));
        List<TrainingVisit> schedulesVisits = tvDao.getAllTrainigVisitsByScheduleId(SCHEDULE_ID);
        verify(em).createNamedQuery(TrainingVisitsQueris.GET_4_SCHEDULE_BY_SCHEDULE_ID, TrainingVisit.class);
        verify(query).setParameter("scheduleId", SCHEDULE_ID);
        verify(query).getResultList();
        assertTrue(CollectionUtils.isNotEmpty(schedulesVisits));
        assertThat(schedulesVisits).containsOnly(trainingVisit);
    }

    @Test
    public void shouldPersistGivenTrainingVisit_whenCallAddTrainingVisit() {
        TrainingVisit added = tvDao.addTrainingVisit(trainingVisit);
        verify(em).persist(trainingVisit);
        verify(em).flush();
        assertThat(added).isSameAs(trainingVisit);
    }
    
    @Test
    public void shouldFlushPersistedTrainingVisit_whenCallAddTrainingVisit() {
        TrainingVisit added = tvDao.addTrainingVisit(trainingVisit);
        verify(em).flush();
        assertThat(added).isSameAs(trainingVisit);
    }

    @Test
    public void shouldDeleteGivenTrainingVisit_whenCallDeleteTrainingVisit() {
        tvDao.deleteTrainingVisit(trainingVisit);
        verify(em).remove(trainingVisit);
    }

    @Test
    public void shouldDeleteTrainingVisitByGivenId_whenCallDeleteTrainingVisitById() {
        when(query.executeUpdate()).thenReturn(1);
        int deleted = tvDao.deleteTrainingVisitById(ID);
        verify(em).createNamedQuery(TrainingVisitsQueris.DELETE_BY_ID, TrainingVisit.class);
        verify(query).setParameter("id", ID);
        verify(query).executeUpdate();
        assertThat(deleted).isEqualTo(1);
    }

    @Test
    public void shouldUpdateGivenTrainingVisit_whenCallUpdateTrainingVisit() {
        when(em.merge(any(TrainingVisit.class))).thenReturn(trainingVisit);
        TrainingVisit updated = tvDao.updateTrainingVisit(trainingVisit);
        verify(em).merge(trainingVisit);
        assertThat(updated).isSameAs(trainingVisit);
    }
}
