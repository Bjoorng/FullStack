package com.personal.cafe.service;

import java.util.List;

import com.personal.cafe.entities.Event;

public interface EventsService {
	List<Event> findAll();
	Event save(Event event);
	void deleteById(Long id);
	Event getById(Long id);
}
