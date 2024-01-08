package com.personal.cafe.dto;

import com.personal.cafe.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
	
	private Long id;
	private String productName;
	private Double fullPrice;
	private Double discount;
	private Integer quantity;
	private String description;
	private String picture;
	private Boolean visible;
	private Category category;
	
}
