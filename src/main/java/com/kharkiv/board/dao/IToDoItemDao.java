package com.kharkiv.board.dao;

import java.util.List;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;

public interface IToDoItemDao {
	
	ToDoItem findById(long itemId);
	List<ToDoItem> findByPriority(Priority priority);
	List<ToDoItem> findAll();
	ToDoItem create(ToDoItem item);
	ToDoItem update(ToDoItem item);
	boolean remove(ToDoItem item);
}
