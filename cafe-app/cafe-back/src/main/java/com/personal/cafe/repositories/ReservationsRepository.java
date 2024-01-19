package com.personal.cafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.cafe.entities.Reservation;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservation, Long>{

	@Query("select r from Reservation r order by r.reservationDate")
	List<Reservation> filterfurthestReservationDate();
	
	@Query("select r from Reservation r order by r.reservationDate desc")
	List<Reservation> filterClosestReservationDate();
	
	@Query("select r from Reservation r order by r.event.date")
	List<Reservation> filterFurthestEventDate();
	
	@Query("select r from Reservation r order by r.event.date desc")
	List<Reservation> filterClosestEventDate();
	
}
