package com.personal.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.cafe.dto.ProductDto;
import com.personal.cafe.entities.Category;
import com.personal.cafe.entities.Event;
import com.personal.cafe.entities.Product;
import com.personal.cafe.entities.User;
import com.personal.cafe.service.CategoryServiceIMPL;
import com.personal.cafe.service.EventsServiceIMPL;
import com.personal.cafe.service.ProductsServiceIMPL;
import com.personal.cafe.service.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	ProductsServiceIMPL productService;

	@Autowired
	CategoryServiceIMPL categoryService;
	
	@Autowired
	EventsServiceIMPL eventsService;

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = userService.getAllUsers();
		ResponseEntity<List<User>> res = new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		return res;
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> catList = categoryService.findAll();
		ResponseEntity<List<Category>> res = new ResponseEntity<List<Category>>(catList, HttpStatus.OK);
		return res;
	}

	@PostMapping("/categories/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addCategory(@RequestBody Category category) {
		Category cat = categoryService.save(category);
		ResponseEntity<Category> res = new ResponseEntity<Category>(cat, HttpStatus.OK);
		return res;
	}

	@PutMapping("/categories/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> modCategory(@PathVariable Long id, @RequestBody Category category) {
		category = categoryService.findById(id);
		categoryService.mod(category);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> prodList = productService.findAll();
		ResponseEntity<List<Product>> res = new ResponseEntity<List<Product>>(prodList, HttpStatus.OK);
		return res;
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId) {
		Product product = productService.getById(productId);
		if (product == null) {
			return new ResponseEntity<>("Product Not Found!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping("/products/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		Category category = categoryService.findByName(product.getCategory().getName());
		if (category == null) {
			category = new Category();
			category.setName(product.getCategory().getName());
			categoryService.save(category);
		}
		Product prod = new Product();
		prod.setProductName(product.getProductName());
		prod.setDescription(product.getDescription());
		prod.setFullPrice(product.getFullPrice());
		prod.setDiscount(product.getDiscount());
		prod.setQuantity(product.getQuantity());
		prod.setSmallDescription(product.getSmallDescription());
		prod.setDescription(product.getDescription());
		prod.setPicture(product.getPicture());
		prod.setCategory(category);

		Product newProduct = productService.save(product);

		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}

	@PutMapping("products/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product update) {
		Product p = productService.getById(productId);
		if (p == null) {
			return new ResponseEntity<>("Product Not Found!", HttpStatus.NOT_FOUND);
		}
		Category category = categoryService.findByName(update.getCategory().getName());
		if (category == null) {
			category = new Category();
			category.setName(update.getCategory().getName());
			categoryService.save(category);
		}
		p.setProductName(update.getProductName());
		p.setDescription(update.getDescription());
		p.setFullPrice(update.getFullPrice());
		p.setDiscount(update.getDiscount());
		p.setSmallDescription(update.getSmallDescription());
		p.setDescription(update.getDescription());
		p.setQuantity(update.getQuantity());
		p.setPicture(update.getPicture());
		p.setCategory(category);

		Product updatedProduct = productService.save(p);

		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}
	
	@DeleteMapping("/products/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
		Product p = productService.getById(productId);
		productService.deleteById(productId);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@GetMapping("/events")
	public ResponseEntity<List<Event>> getAllEvents() {
		List<Event> evList = eventsService.findAll();
		ResponseEntity<List<Event>> res = new ResponseEntity<List<Event>>(evList, HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/events/{eventId}")
	public ResponseEntity<?> getEventById(@PathVariable Long eventId) {
		Event ev = eventsService.getById(eventId);
		if (ev == null) {
			return new ResponseEntity<>("Event Not Found!", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ev, HttpStatus.OK);
	}	
	
	@PostMapping("/events/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addEvent(@RequestBody Event event) {
		Event ev = new Event();
		ev.setEvName(event.getEvName());
		ev.setGuestName(event.getGuestName());
		ev.setSeats(event.getSeats());
		ev.setDate(event.getDate());
		ev.setIsPrivate(event.getIsPrivate());

		Event newEvent = eventsService.save(ev);

		return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
	}
	
	@PutMapping("events/{eventId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateEvent(@PathVariable Long eventId, @RequestBody Event update) {
		Event e = eventsService.getById(eventId);
		if (e == null) {
			return new ResponseEntity<>("Event Not Found!", HttpStatus.NOT_FOUND);
		}
	
		e.setEvName(update.getEvName());
		e.setGuestName(update.getGuestName());
		e.setSeats(update.getSeats());
		e.setDate(update.getDate());
		e.setIsPrivate(update.getIsPrivate());

		Event updatedEvent = eventsService.save(e);

		return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
	}
	
	@DeleteMapping("/events/{eventId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteEvent(@PathVariable Long eventId){
		Event e = eventsService.getById(eventId);
		eventsService.deleteById(eventId);
		return new ResponseEntity<>(e, HttpStatus.OK);
	}
	
}
