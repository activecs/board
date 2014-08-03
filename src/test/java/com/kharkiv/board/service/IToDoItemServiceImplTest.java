package com.kharkiv.board.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;
import com.kharkiv.board.dao.IToDoItemDao;

public class IToDoItemServiceImplTest {
	
	private static final Long ITEM_ID = 2l;
	private static final Priority PRIORITY = Priority.NORMAL;
	
	@InjectMocks
	private IToDoItemService service = new IToDoItemServiceImpl();
	@Mock
	private ToDoItem mockItem;
	@Mock
	private IToDoItemDao mockDao;
	private List<ToDoItem> mockItems;
	
	@Before
	public void setUp() {
		initMocks(this);
		setUpMockBehaviour();
		mockItems = Lists.newArrayList(mockItem);
	}

	private void setUpMockBehaviour() {
		when(mockDao.create(mockItem)).thenReturn(mockItem);
		when(mockDao.update(mockItem)).thenReturn(mockItem);
	}
	
	@Test
	public void verifyServiceHasServiceAnnotation(){
		assertThat(service.getClass().isAnnotationPresent(Service.class)).isTrue();
	}
	
	@Test
	public void shouldCallDaoWithGivenItemID_whenCallFindByIdOnService(){
		service.find(ITEM_ID);
		verify(mockDao).findById(ITEM_ID);
	}
	
	@Test
	public void shouldReturnItemAccordingToId_whenCallFindOnService(){
		when(mockDao.findById(ITEM_ID)).thenReturn(mockItem);
		ToDoItem returnedItem = service.find(ITEM_ID);
		assertThat(mockItem).isSameAs(returnedItem);
	}
	
	@Test
	public void shouldCallDao_whenCallFindAllOnService(){
		service.findAll();
		verify(mockDao).findAll();
	}
	
	@Test
	public void shouldReturnItems_whenCallFindAllOnService(){
		when(mockDao.findAll()).thenReturn(mockItems);
		List<ToDoItem> returnedItems = service.findAll();
		assertThat(mockItems).isSameAs(returnedItems);
	}
	
	@Test
	public void shouldCallDaoWithGivenPriority_whenCallFindByPriorityOnService(){
		service.find(PRIORITY);
		verify(mockDao).findByPriority(PRIORITY);
	}
	
	@Test
	public void shouldReturnItemsAccordingToPriority_whenCallFindByPriorityOnService(){
		when(mockDao.findByPriority(PRIORITY)).thenReturn(mockItems);
		List<ToDoItem> returnedItem = service.find(PRIORITY);
		assertThat(returnedItem).isSameAs(mockItems);
	}
	
	@Test
	public void shouldCallDaoWithGivenItem_whenCallCreateItemOnService(){
		service.create(mockItem);
		verify(mockDao).create(mockItem);
	}
	
	@Test
	public void shouldReturnCreatedItem_whenCallCreateItemOnService(){
		ToDoItem returnedItem = service.create(mockItem);
		assertThat(returnedItem).isSameAs(mockItem);
	}
	
	@Test
	public void shouldCallDaoWithGivenItem_whenCallUpdateOnService(){
		service.update(mockItem);
		verify(mockDao).update(mockItem);
	}
	
	@Test
	public void shouldReturnUpdatedItem_whenCallUpdateItemOnService(){
		ToDoItem returnedItem = service.update(mockItem);
		assertThat(returnedItem).isSameAs(mockItem);
	}
	
	@Test
	public void shouldCallDaoGivenItem_whenCallDeleteItemOnService(){
		service.remove(mockItem);
		verify(mockDao).remove(mockItem);
	}
}