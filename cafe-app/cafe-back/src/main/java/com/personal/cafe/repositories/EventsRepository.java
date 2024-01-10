package com.personal.cafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.cafe.entities.Event;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long>{
	
	@Query("select e from Event e order by e.date")
	List<Event> filterFurthestEvent();
	
	@Query("select e from Event e order by e.date desc")
	List<Event> filterClosestEvent();
	
}
