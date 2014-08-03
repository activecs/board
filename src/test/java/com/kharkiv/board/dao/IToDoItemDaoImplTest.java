package com.kharkiv.board.dao;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.stereotype.Repository;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;
import com.kharkiv.board.dao.IToDoItemDao;
import com.kharkiv.board.dao.IToDoItemDaoImpl;
import com.google.common.collect.Lists;

public class IToDoItemDaoImplTest {
	
	private static final Long ITEM_ID = 2l;
	private static final Priority PRIORITY = Priority.NORMAL;
	
	@InjectMocks
	private IToDoItemDao dao = new IToDoItemDaoImpl();
	@Mock
	private SessionFactory mockSf;
	@Mock
	private IdentifierLoadAccess mockLoadAccess;
	@Mock
	private Session mockSession;
	@Mock
	private Criteria mockCriteria;
	@Mock
	private ToDoItem mockItem;
	
	@Before
	public void setUp() {
		initMocks(this);
		setUpMockBehaviour();
	}

	private void setUpMockBehaviour() {
		when(mockSf.getCurrentSession()).thenReturn(mockSession);
		when(mockSession.byId(ToDoItem.class)).thenReturn(mockLoadAccess);
		when(mockSession.createCriteria(ToDoItem.class)).thenReturn(mockCriteria);
	}
	
	@Test
	public void verifyDaoHasRepositoryAnnotation(){
		assertThat(dao.getClass().isAnnotationPresent(Repository.class)).isTrue();
	}
	
	@Test
	public void shouldCallSessionWithToDoItemClassAndCallLoadAccessWithGivenItemId_whenCallFindItemById(){
		dao.findById(ITEM_ID);
		verify(mockSession).byId(ToDoItem.class);
		verify(mockLoadAccess).load(ITEM_ID);
	}
	
	@Test
	public void shouldReturnItemAccordingToId_whenCallFindItemById(){
		when(mockLoadAccess.load(ITEM_ID)).thenReturn(mockItem);
		ToDoItem returnedItem = dao.findById(ITEM_ID);
		assertThat(mockItem).isSameAs(returnedItem);
	}
	
	@Test
	public void shouldCreateCriteria_whenCallFindByPriority(){
		dao.findByPriority(PRIORITY);
		verify(mockSession).createCriteria(ToDoItem.class);
	}
	
	@Test
	public void shouldReturnItemsAccordingToPriority_whenCallFindByPriority(){
		when(mockCriteria.list()).thenReturn(Lists.newArrayList(mockItem));
		List<ToDoItem> returnedItems = dao.findByPriority(PRIORITY);
		assertThat(returnedItems).containsOnly(mockItem);
	}
	
	@Test
	public void shouldCreateCriteria_whenCallFindAll(){
		dao.findAll();
		verify(mockSession).createCriteria(ToDoItem.class);
	}
	
	@Test
	public void shouldReturnAllItems_whenCallFindAll(){
		when(mockCriteria.list()).thenReturn(Lists.newArrayList(mockItem));
		List<ToDoItem> returnedItems = dao.findAll();
		assertThat(returnedItems).containsOnly(mockItem);
	}
	
	@Test
	public void shouldCallPersistOnSessionWithGivenItemAndCallFlush_whenCallCreateItem(){
		dao.create(mockItem);
		verify(mockSession).persist(mockItem);
		verify(mockSession).flush();
	}
	
	@Test
	public void shouldReturnCreatedItem_whenCallCreateItem(){
		ToDoItem returnedItem = dao.create(mockItem);
		assertThat(returnedItem).isSameAs(mockItem);
	}
	
	@Test
	public void shouldCallMergeOnSessionWithGivenItem_whenCallUpdateItem(){
		dao.update(mockItem);
		verify(mockSession).merge(mockItem);
	}
	
	@Test
	public void shouldReturnUpdatedItem_whenCallUpdateItem(){
		when(mockSession.merge(mockItem)).thenReturn(mockItem);
		ToDoItem returnedItem = dao.update(mockItem);
		assertThat(returnedItem).isSameAs(mockItem);
	}
	
	@Test
	public void shouldCallDeleteItem_whenCallDeleteItemAndItemExist(){
		when(mockSession.contains(mockItem)).thenReturn(true);
		dao.remove(mockItem);
		verify(mockSession).delete(mockItem);
	}
	
	@Test
	public void shouldNotCallDeleteWithItem_whenCallDeleteItemAndItemDoesNotExist(){
		when(mockSession.contains(mockItem)).thenReturn(false);
		dao.remove(mockItem);
		verify(mockSession, never()).delete(mockItem);
	}
}