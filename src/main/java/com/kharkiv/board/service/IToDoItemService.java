package com.kharkiv.board.service;

import java.util.List;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;

public interface IToDoItemService {
	
	ToDoItem find(long itemId);
	List<ToDoItem> find(Priority priority);
	List<ToDoItem> findAll();
	ToDoItem create(ToDoItem item);
	ToDoItem update(ToDoItem item);
	boolean remove(ToDoItem item);
}
