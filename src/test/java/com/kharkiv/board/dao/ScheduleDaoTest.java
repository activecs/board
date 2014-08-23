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

    @Mock
    private EntityManager em;
    @Mock
    private TypedQuery<Schedule> query;

    @InjectMocks
    private ScheduleDao scheduleDao = new ScheduleDaoImpl();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(em.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @Test
    public void shouldReturnAllSchedules_whenCallGetAllSchedules() {
        Schedule schedule = new Schedule();
        when(query.getResultList()).thenReturn(Arrays.asList(schedule));
        List<Schedule> allSchedules = scheduleDao.getAllSchedules();
        verify(em).createNamedQuery(ScheduleQueries.GET_ALL, Schedule.class);
        verify(query).getResultList();
        assertNotNull(allSchedules);
        assertThat(allSchedules).containsOnly(schedule);
    }

    @Test
    public void shouldReturnScheduleAccordingGivenId_whenCallGetScheduleById() {
        Integer id = 1;
        Schedule schedule = new Schedule();
        when(query.getSingleResult()).thenReturn(schedule);
        Schedule scheduleById = scheduleDao.getScheduleById(id);
        verify(em).createNamedQuery(ScheduleQueries.GET_BY_ID, Schedule.class);
        verify(query).setParameter("id", id);
        verify(query).getSingleResult();
        assertSame(schedule, scheduleById);
    }

    @Test
    public void shouldReturnSchedulesForGivenUserId_whenCallGetSchedulesByUserId() {
        Integer userId = 1;
        Schedule schedule = new Schedule();
        when(query.getResultList()).thenReturn(Arrays.asList(schedule));
        List<Schedule> usersSchedule = scheduleDao.getSchedulesByUserId(userId);
        verify(em).createNamedQuery(ScheduleQueries.GET_4_USER_BY_USER_ID, Schedule.class);
        verify(query).setParameter("userId", userId);
        verify(query).getResultList();
        assertThat(usersSchedule).containsOnly(schedule);
    }
    
    @Test
    public void shouldReturnSchedulesForGivenUserLogin_whenCallGetSchedulesByUserLogin() {
        String userLogin = "login";
        Schedule schedule = new Schedule();
        when(query.getResultList()).thenReturn(Arrays.asList(schedule));
        List<Schedule> usersSchedule = scheduleDao.getSchedulesByUserLogin(userLogin);
        verify(em).createNamedQuery(ScheduleQueries.GET_4_USER_BY_USER_LOGIN, Schedule.class);
        verify(query).setParameter("login", userLogin);
        verify(query).getResultList();
        assertThat(usersSchedule).containsOnly(schedule);
    }
    
    @Test
    public void verifyUser_whenDeleteSchedule() {
        Schedule schedule = new Schedule();
        scheduleDao.deleteSchedule(schedule);
        verify(em).remove(schedule);
    }
    
    @Test
    public void shouldDeleteScheduleAccordingGivenId_whenCallDeleteScgeduleById() {
        Integer id = 1;
        when(query.executeUpdate()).thenReturn(1);
        int deleted = scheduleDao.deleteScheduleById(id);
        verify(em).createNamedQuery(ScheduleQueries.DELETE_BY_ID, Schedule.class);
        verify(query).setParameter("id", id);
        verify(query).executeUpdate();
        assertThat(deleted).isEqualTo(1);
    }
    
    @Test
    public void shouldPersistGivenSchedule_whenCallAddSchedule() {
        Schedule toAdd = new Schedule();
        Schedule added = scheduleDao.addSchedule(toAdd);
        verify(em).persist(toAdd);
        verify(em).flush();
        assertThat(added).isEqualTo(toAdd);
    }
    
    @Test
    public void shouldUpdateGivenSchedule_whenCallUpdateSchedule() {
        Schedule toUpdate = new Schedule();
        when(em.merge(any(Schedule.class))).thenReturn(toUpdate);
        Schedule updated = scheduleDao.updateSchedule(toUpdate);
        verify(em).merge(toUpdate);
        assertThat(updated).isSameAs(toUpdate);
    }
}
