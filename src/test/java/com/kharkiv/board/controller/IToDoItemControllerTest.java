package com.kharkiv.board.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;
import com.kharkiv.board.service.IToDoItemService;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class IToDoItemControllerTest {
	
	private static final int ITEM_ID = 123;
	private static final String ITEM_TITLE = "title";
	private static final Priority ITEM_PRIORITY = Priority.NORMAL;
	private static final String CONTROLLER_MAPPING = "/rest/to-do-item";
	private MockMvc mockMvc;
	
	@Mock
	private IToDoItemService mockService;
	@Mock
	private BindingResult mockBindingResult;
	@Mock
	private ToDoItem mockToDoItem;
	@InjectMocks
	private IToDoItemController controller;
	private Gson gson = new GsonBuilder().setDateFormat("zzz").create();
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}
	
	@Test
	public void shouldCallServiceWithGivenToDoItem_whenCallCreateMethod() {
		when(mockBindingResult.hasErrors()).thenReturn(false);
		controller.create(mockToDoItem, mockBindingResult);
		verify(mockService).create(mockToDoItem);
	}
	
	@Test
	public void shouldNotCallServiceWithGivenToDoItem_whenCallCreateMethodWithInvalidToDoItem() {
		when(mockBindingResult.hasErrors()).thenReturn(true);
		controller.create(mockToDoItem, mockBindingResult);
		verify(mockService, never()).create(mockToDoItem);
	}

	@Test
	public void shouldReturnResponseWithIsCreatedStatus_whenCallCreateMethod() throws Exception{
		mockMvc.perform(post(CONTROLLER_MAPPING).content(gson.toJson(getToDoItem()))
				.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
	}
	
	@Test
	public void shouldCallCreateMethodOnService_whenCallCreateMethodWithToDoItem() throws Exception{
		mockMvc.perform(post(CONTROLLER_MAPPING).content(gson.toJson(getToDoItem())).contentType(APPLICATION_JSON));
		verify(mockService).create(eq(getToDoItem()));
	}
	
	@Test
	public void shouldCallFindAllMethodOnService_whenGetAllItems() {
		controller.getAll();
		verify(mockService).findAll();
	}
	
	@Test
	public void shouldReturnItemsGivenByService_whenGetAllItems() {
		when(mockService.findAll()).thenReturn(Lists.newArrayList(mockToDoItem));
		List<ToDoItem> items = controller.getAll();
		assertThat(items).containsOnly(mockToDoItem);
	}
	
	@Test
	public void shouldReturnIsOkStatus_whenCallGetAllItems() throws Exception{
		mockMvc.perform(get(CONTROLLER_MAPPING)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnOkStatusAndExpectedToDoItem_whenCallGetItemWithExistingId() throws Exception{
		when(mockService.find(ITEM_ID)).thenReturn(getToDoItem());
		mockMvc.perform(get(CONTROLLER_MAPPING + "/{id}",ITEM_ID)
				.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(getToDoItem().getTitle()));
	}
	
	@Test
	public void shouldReturnNotFoundStatus_whenCallGetItemWithNotExistingId() throws Exception{
		mockMvc.perform(get(CONTROLLER_MAPPING + "/{id}",ITEM_ID)
				.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturnOkStatusAndExpectedToDoItem_whenCallRemoveItemWithExistingId() throws Exception{
		when(mockService.find(ITEM_ID)).thenReturn(getToDoItem());
		mockMvc.perform(delete(CONTROLLER_MAPPING + "/{id}",ITEM_ID)
				.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(getToDoItem().getId().intValue()));
	}
	
	@Test
	public void shouldReturnNotFoundStatus_whenCallRemoveItemWithNotExistingId() throws Exception{
		mockMvc.perform(delete(CONTROLLER_MAPPING + "/{id}",ITEM_ID)
				.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldCallFindItemsByPriority() {
		controller.get(ITEM_PRIORITY);
		verify(mockService).find(ITEM_PRIORITY);
	}
	
	@Test
	public void shouldReturnItemsGivenByService_whenGetAllItemsWithGivenPriority() {
		when(mockService.find(ITEM_PRIORITY)).thenReturn(Lists.newArrayList(mockToDoItem));
		List<ToDoItem> items = controller.get(ITEM_PRIORITY);
		assertThat(items).containsOnly(mockToDoItem);
	}
	
	@Test
	public void shouldReturnIsOkStatus_whenCallGetItemsWithGivenPriority() throws Exception{
		mockMvc.perform(get(CONTROLLER_MAPPING+"/priority/"+ITEM_PRIORITY)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
	}
	
	private ToDoItem getToDoItem(){
		ToDoItem toDoItem = new ToDoItem();
		toDoItem.setId(ITEM_ID);
		toDoItem.setTitle(ITEM_TITLE);
		return toDoItem;
	}
}