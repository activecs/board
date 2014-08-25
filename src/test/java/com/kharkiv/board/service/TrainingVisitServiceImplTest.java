package com.kharkiv.board.service;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.kharkiv.board.dao.TrainingVisitDao;
import com.kharkiv.board.dto.schedule.TrainingVisit;

public class TrainingVisitServiceImplTest {
	
	private static final Integer USER_ID = 15;
	private static final Integer SCHEDULE_ID = 16;
	private static final Integer TRAINING_VISIT_ID = 16;
	
	@InjectMocks
	private TrainingVisitService service = new TrainingVisitServiceImpl();
	@Mock
	private TrainingVisitDao mockTrainingVisitDao;
	@Mock
	private TrainingVisit mockTrainingVisit;
	
	private List<TrainingVisit> trainingVisits;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		trainingVisits = newArrayList(mockTrainingVisit);
		initMockBehaviour();
	}

	private void initMockBehaviour() {
		when(mockTrainingVisitDao.getAllTrainigVisitsByUserId(USER_ID)).thenReturn(trainingVisits);
		when(mockTrainingVisitDao.getAllTrainigVisitsByScheduleId(SCHEDULE_ID)).thenReturn(trainingVisits);
		when(mockTrainingVisitDao.addTrainingVisit(mockTrainingVisit)).thenReturn(mockTrainingVisit);
		when(mockTrainingVisitDao.updateTrainingVisit(mockTrainingVisit)).thenReturn(mockTrainingVisit);
	}

	@Test
	public void shouldCallGetTrainingVisitsByUserIdOnDaoWithGivenUserId_whenCallGetAllTrainigVisitsByUserId() {
		service.getAllTrainigVisitsByUserId(USER_ID);
		verify(mockTrainingVisitDao).getAllTrainigVisitsByUserId(USER_ID);
	}
	
	@Test
	public void shouldReturnTrainingVisitReturnedByDao_whenCallGetAllTrainigVisitsByUserId() {
		List<TrainingVisit> actualSchedules = service.getAllTrainigVisitsByUserId(USER_ID);
		assertThat(actualSchedules).isEqualTo(trainingVisits);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenUserIdIsNullAndCallGetAllTrainigVisitsByUserId() {
		service.getAllTrainigVisitsByUserId(null);
	}
	
	@Test
	public void shouldCallGetTrainingVisitsByScheduleIdOnDaoWithGivenScheduleId_whenCallGetAllTrainigVisitsByScheduleId() {
		service.getAllTrainigVisitsByScheduleId(SCHEDULE_ID);
		verify(mockTrainingVisitDao).getAllTrainigVisitsByScheduleId(SCHEDULE_ID);
	}
	
	@Test
	public void shouldReturnTrainingVisitReturnedByDao_whenCallGetAllTrainigVisitsByScheduleId() {
		List<TrainingVisit> actualSchedules = service.getAllTrainigVisitsByScheduleId(SCHEDULE_ID);
		assertThat(actualSchedules).isEqualTo(trainingVisits);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenScheduleIdIsNullAndCallGetAllTrainigVisitsByScheduleId() {
		service.getAllTrainigVisitsByScheduleId(null);
	}
	
	@Test
	public void shouldCallAddScheduleWithGivenTrainigVisit_whenCallAddTrainigVisit() {
		service.addTrainingVisit(mockTrainingVisit);
		verify(mockTrainingVisitDao).addTrainingVisit(mockTrainingVisit);
	}
	
	@Test
	public void shouldReturnScheduleReturnedByDao_whenCallAddSchedule() {
		TrainingVisit actualTrainingVisit = service.addTrainingVisit(mockTrainingVisit);
		assertThat(actualTrainingVisit).isEqualTo(mockTrainingVisit);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenTrainigVisitIsNullAndCallAddTrainigVisit() {
		service.addTrainingVisit(null);
	}
	
	@Test
	public void shouldCallUpdateTrainigVisitWithGivenTrainigVisit_whenCallUpdateTrainigVisit() {
		service.updateTrainingVisit(mockTrainingVisit);
		verify(mockTrainingVisitDao).updateTrainingVisit(mockTrainingVisit);
	}
	
	@Test
	public void shouldReturnScheduleReturnedByDao_whenCallUpdateTrainigVisit() {
		TrainingVisit actualTrainingVisit = service.updateTrainingVisit(mockTrainingVisit);
		assertThat(actualTrainingVisit).isEqualTo(mockTrainingVisit);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenTrainigVisitIsNullAndCallUpdateTrainigVisit() {
		service.updateTrainingVisit(null);
	}
	
	@Test
	public void shouldCallDeleteTrainigVisitOnDaoWithGivenTrainigVisit_whenCallDeleteTrainigVisit() {
		service.deleteTrainingVisit(mockTrainingVisit);
		verify(mockTrainingVisitDao).deleteTrainingVisit(mockTrainingVisit);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenTrainigVisitIsNullAndCallDeleteTrainigVisit() {
		service.deleteTrainingVisit(null);
	}
	
	@Test
	public void shouldCallDeleteTrainigVisitByIdOnDaoWithGivenTrainigVisitId_whenCallDeleteTrainigVisitById() {
		service.deleteTrainingVisitById(TRAINING_VISIT_ID);
		verify(mockTrainingVisitDao).deleteTrainingVisitById(TRAINING_VISIT_ID);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException_whenTrainigVisitIdIsNullAndCallDeleteTrainigVisitById() {
		service.deleteTrainingVisitById(null);
	}
}
