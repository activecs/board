package com.kharkiv.board.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;
import com.kharkiv.board.service.IToDoItemService;

@Controller
@RequestMapping("/rest/to-do-item")
public class IToDoItemController {
	
	@Inject
	private IToDoItemService iToDoItemService;
	
	@RequestMapping(method=POST)
	public ResponseEntity<ToDoItem> create(@Valid @RequestBody ToDoItem toDoItem,BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return new ResponseEntity<>(UNPROCESSABLE_ENTITY);
		iToDoItemService.create(toDoItem);
		return new ResponseEntity<>(CREATED);
	}
	
	@ResponseBody
	@ResponseStatus(OK)
	@RequestMapping(method=GET)
	public List<ToDoItem> getAll() {
		return iToDoItemService.findAll();
	}
	
	@RequestMapping(value="/{id}",method=GET)
	public ResponseEntity<ToDoItem> get(@PathVariable long id) {
		ToDoItem item = iToDoItemService.find(id);
		if(item == null)
			return new ResponseEntity<>(NOT_FOUND);
		return new ResponseEntity<>(item, OK);
	}
	
	@RequestMapping(value="/priority/{priority}",method=GET)
	@ResponseBody
	@ResponseStatus(OK)
	public List<ToDoItem> get(@PathVariable Priority priority) {
		return iToDoItemService.find(priority);
	}
	
	@RequestMapping(value="/{id}",method=DELETE)
	public ResponseEntity<ToDoItem> remove(@PathVariable long id) {
		ToDoItem item = iToDoItemService.find(id);
		if (item == null)
            return new ResponseEntity<ToDoItem>(NOT_FOUND);
		iToDoItemService.remove(item);
        return new ResponseEntity<ToDoItem>(item, OK);
	}
	
	@RequestMapping(value="/",method=PUT)
	public ResponseEntity<ToDoItem> update(@Validated @RequestBody ToDoItem item,BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return new ResponseEntity<>(UNPROCESSABLE_ENTITY);
		
		iToDoItemService.update(item);
		return new ResponseEntity<>(item, OK);
	}
}
