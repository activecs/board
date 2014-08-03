package com.kharkiv.board.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;
import com.kharkiv.board.dao.IToDoItemDao;

@Service("iToDOItemService")
@Transactional
public class IToDoItemServiceImpl implements IToDoItemService {
	
	@Inject
	private IToDoItemDao dao;
	
	@Override
	@Transactional(readOnly=true)
	public ToDoItem find(long itemId) {
		return dao.findById(itemId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ToDoItem> find(Priority priority) {
		return dao.findByPriority(priority);
	}

	@Override
	@Transactional(readOnly=true)
	public List<ToDoItem> findAll() {
		return dao.findAll();
	}

	@Override
	public ToDoItem create(ToDoItem item) {
		item.setCreatedDate(new Date());
		return dao.create(item);
	}

	@Override
	public ToDoItem update(ToDoItem item) {
		item.setUpdatedDate(new Date());
		return dao.update(item);
	}

	@Override
	public boolean remove(ToDoItem item) {
		return dao.remove(item);
	}
}