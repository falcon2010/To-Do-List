package com.mohamedibrahim.ToDoList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoItem {

	String task;
	Date created;

	public Date getCreated() {
		return created;
	}

	public String getTask() {
		return this.task;
	}

	public ToDoItem(String task) {
		this(task, new Date(System.currentTimeMillis()));

	}

	public ToDoItem(String t, Date d) {
		this.task = t;
		this.created = d;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(created);
		return "(" + dateString + ") " + task;
	}

}
