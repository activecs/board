package com.kharkiv.board.controller;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;
import com.kharkiv.board.service.IToDoItemService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml"})
@WebAppConfiguration
public class IToDoItemControllerIntegrationTest {
	
	private static final int ITEM_ID = 123;
	private static final String ITEM_TITLE = "title";
	private static final Priority ITEM_PRIORITY = Priority.NORMAL;
	private static final String CONTROLLER_MAPPING = "/rest/to-do-item";
	
	@Inject
	private WebApplicationContext wac;
	@Inject
	private IToDoItemService service;
	private MockMvc mockMvc;
	private Gson gson = new GsonBuilder().setDateFormat("zzz").create();
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockMvc = webAppContextSetup(wac).build();
	}
	
	@Test
	public void shouldCreateNewItem() throws Exception{
		List<ToDoItem> beforeItems = service.findAll();
		mockMvc.perform(post(CONTROLLER_MAPPING).content(gson.toJson(getToDoItem()))
				.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
		assertThat(service.findAll()).hasSize(beforeItems.size() + 1);
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
		ToDoItem createdItem = service.create(getToDoItem());
		mockMvc.perform(get(CONTROLLER_MAPPING + "/{id}",createdItem.getId())
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
		ToDoItem createdItem = service.create(getToDoItem());
		mockMvc.perform(delete(CONTROLLER_MAPPING + "/{id}",createdItem.getId())
				.contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdItem.getId().intValue()));
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
	public void shouldReturnIsOkStatus_whenCallGetItemsWithGivenPriority() throws Exception{
		mockMvc.perform(get(CONTROLLER_MAPPING+"/priority/"+ITEM_PRIORITY)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
	}
	
	private ToDoItem getToDoItem(){
		ToDoItem toDoItem = new ToDoItem();
		toDoItem.setTitle(ITEM_TITLE);
		return toDoItem;
	}
}