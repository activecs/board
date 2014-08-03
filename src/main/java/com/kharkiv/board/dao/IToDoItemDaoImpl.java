package com.kharkiv.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.kharkiv.board.bean.ToDoItem;
import com.kharkiv.board.bean.ToDoItem.Priority;

@Repository("iToDoItemDao")
public class IToDoItemDaoImpl implements IToDoItemDao {
	
	private static final String FIELD_PRIORITY = "priority";
	
	@Inject
	private SessionFactory sf;
	
	private Session getCurrentSession(){
		return sf.getCurrentSession();
	}
	
	@Override
	public ToDoItem findById(long itemId) {
		Session session = getCurrentSession();
		return (ToDoItem)session.byId(ToDoItem.class).load(itemId);
	}

	@Override
	public List<ToDoItem> findByPriority(Priority priority) {
		Criteria criteria = getCurrentSession().createCriteria(ToDoItem.class);
		criteria.add(Restrictions.eq(FIELD_PRIORITY, priority));
		return criteria.list();
	}

	@Override
	public List<ToDoItem> findAll() {
		Criteria criteria = getCurrentSession().createCriteria(ToDoItem.class);
		return criteria.list();
	}

	@Override
	public ToDoItem create(ToDoItem item) {
		Session session = getCurrentSession(); 
		session.persist(item);
		session.flush();
		return item;
	}

	@Override
	public ToDoItem update(ToDoItem item) {
		Session session = getCurrentSession(); 
		return (ToDoItem)session.merge(item);
	}

	@Override
	public boolean remove(ToDoItem item) {
		Session session = getCurrentSession();
		if(session.contains(item)){
			session.delete(item);
			return true;			
		}
		return false;
	}

}
