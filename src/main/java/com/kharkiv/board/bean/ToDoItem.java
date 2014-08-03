package com.kharkiv.board.bean;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="to_do_items")
public class ToDoItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=1, max=255)
	private String title;
	private String description;
	
	@Future
	@DateTimeFormat(pattern=DATE_FORMAT)
	private Date dueDate;
	
	@DateTimeFormat(pattern=DATE_FORMAT)
	private Date createdDate;
	
	@DateTimeFormat(pattern=DATE_FORMAT)
	private Date updatedDate;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	public enum Priority {
		CRITICAL,RUSH,NORMAL,LOW;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ToDoItem))
			return false;
		ToDoItem other = (ToDoItem)obj;
		return equal(other.getId(), id) 
				&& equal(other.getTitle(), title) 
				&& equal(other.getDescription(), description)
				&& equal(other.getDueDate(), dueDate)
				&& equal(other.getCreatedDate(), createdDate)
				&& equal(other.getUpdatedDate(), updatedDate)
				&& equal(other.getPriority(), priority);
	}

	@Override
	public String toString() {
		return toStringHelper(this)
				.add("id", id)
				.add("title", title)
				.add("description", description)
				.add("dueDate", dueDate)
				.add("createdDate", createdDate)
				.add("updatedDate", updatedDate)
				.add("priority", priority)
				.toString();
	}
}