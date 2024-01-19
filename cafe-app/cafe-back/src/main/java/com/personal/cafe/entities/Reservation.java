package com.personal.cafe.entities;

import java.sql.Date;

import com.personal.cafe.enums.EReservation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reservations")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private Integer reservedSeats;
	@Column
	private Double reservationCost;
	@Column
	private Date reservationDate;
	@Enumerated(EnumType.STRING)
	private EReservation reservationStatus;
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
}
