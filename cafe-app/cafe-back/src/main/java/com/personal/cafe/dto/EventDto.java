package com.personal.cafe.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDto {
	private String evName;
	private String guestName;
	private Integer seats;
	private Boolean isPrivate;
	private Date date;
}
