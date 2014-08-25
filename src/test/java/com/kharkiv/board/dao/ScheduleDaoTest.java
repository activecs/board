package com.kharkiv.board.dao;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kharkiv.board.dto.schedule.Schedule;
import com.kharkiv.board.util.QueryNamesConstants.ScheduleQueries;

public class ScheduleDaoTest {

    private static final Integer ID = 1;
    private static final Integer USER_ID = 1;
    private static final String USER_LOGIN = "login";
    
    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<Schedule> query;
    
    private Schedule schedule;

    @InjectMocks
    private ScheduleDao scheduleDao = new ScheduleDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        schedule = new Schedule();
        
        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    public void shouldReturnAllSchedules_whenCallGetAllSchedules() {
        when(query.getResultList()).thenReturn(Arrays.asList(schedule));
        List<Schedule> allSchedules = scheduleDao.getAllSchedules();
        verify(em).createNamedQuery(ScheduleQueries.GET_ALL, Schedule.class);
        verify(query).getResultList();
        assertNotNull(allSchedules);
        assertThat(allSchedules).containsOnly(schedule);
    }

    @Test
    public void shouldReturnScheduleAccordingGivenId_whenCallGetScheduleById() {
        when(query.getSingleResult()).thenReturn(schedule);
        Schedule scheduleById = scheduleDao.getScheduleById(ID);
        verify(em).createNamedQuery(ScheduleQueries.GET_BY_ID, Schedule.class);
        verify(query).setParameter("id", ID);
        verify(query).getSingleResult();
        assertSame(schedule, scheduleById);
    }

    @Test
    public void shouldReturnSchedulesForGivenUserId_whenCallGetSchedulesByUserId() {
        when(query.getResultList()).thenReturn(Arrays.asList(schedule));
        List<Schedule> usersSchedule = scheduleDao.getSchedulesByUserId(USER_ID);
        verify(em).createNamedQuery(ScheduleQueries.GET_4_USER_BY_USER_ID, Schedule.class);
        verify(query).setParameter("userId", USER_ID);
        verify(query).getResultList();
        assertThat(usersSchedule).containsOnly(schedule);
    }
    
    @Test
    public void shouldReturnSchedulesForGivenUserLogin_whenCallGetSchedulesByUserLogin() {
        when(query.getResultList()).thenReturn(Arrays.asList(schedule));
        List<Schedule> usersSchedule = scheduleDao.getSchedulesByUserLogin(USER_LOGIN);
        verify(em).createNamedQuery(ScheduleQueries.GET_4_USER_BY_USER_LOGIN, Schedule.class);
        verify(query).setParameter("login", USER_LOGIN);
        verify(query).getResultList();
        assertThat(usersSchedule).containsOnly(schedule);
    }
    
    @Test
    public void verifyUser_whenDeleteSchedule() {
        scheduleDao.deleteSchedule(schedule);
        verify(em).remove(schedule);
    }
    
    @Test
    public void shouldDeleteScheduleAccordingGivenId_whenCallDeleteScgeduleById() {
        when(query.executeUpdate()).thenReturn(1);
        int deleted = scheduleDao.deleteScheduleById(ID);
        verify(em).createNamedQuery(ScheduleQueries.DELETE_BY_ID, Schedule.class);
        verify(query).setParameter("id", ID);
        verify(query).executeUpdate();
        assertThat(deleted).isEqualTo(1);
    }
    
    @Test
    public void shouldPersistGivenSchedule_whenCallAddSchedule() {
        Schedule added = scheduleDao.addSchedule(schedule);
        verify(em).persist(schedule);
        assertThat(added).isEqualTo(schedule);
    }
    
    public void shouldFlushPersistedSchedule_whenCallAddSchedule() {
        Schedule added = scheduleDao.addSchedule(schedule);
        verify(em).flush();
        assertThat(added).isEqualTo(schedule);
    }
    
    @Test
    public void shouldUpdateGivenSchedule_whenCallUpdateSchedule() {
        when(em.merge(any(Schedule.class))).thenReturn(schedule);
        Schedule updated = scheduleDao.updateSchedule(schedule);
        verify(em).merge(schedule);
        assertThat(updated).isSameAs(schedule);
    }
}
