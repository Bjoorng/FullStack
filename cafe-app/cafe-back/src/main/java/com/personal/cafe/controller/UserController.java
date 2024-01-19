package com.personal.cafe.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.cafe.entities.Event;
import com.personal.cafe.entities.Order;
import com.personal.cafe.entities.Product;
import com.personal.cafe.entities.Reservation;
import com.personal.cafe.entities.ShoppingCart;
import com.personal.cafe.entities.User;
import com.personal.cafe.enums.EOrder;
import com.personal.cafe.enums.EReservation;
import com.personal.cafe.repositories.OrdersRepository;
import com.personal.cafe.repositories.ReservationsRepository;
import com.personal.cafe.repositories.UserRepository;
import com.personal.cafe.service.EventsServiceIMPL;
import com.personal.cafe.service.OrdersServiceIMPL;
import com.personal.cafe.service.ProductsServiceIMPL;
import com.personal.cafe.service.ReservationsServiceIMPL;
import com.personal.cafe.service.ShoppingCartsServiceIMPL;
import com.personal.cafe.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository usersRepository;
	
	@Autowired
	ProductsServiceIMPL productsService;
	
	@Autowired
	ShoppingCartsServiceIMPL shoppingCartsService;
	
	@Autowired
	OrdersServiceIMPL ordersService;
	
	@Autowired
	OrdersRepository ordersRepository;
	
	@Autowired
	EventsServiceIMPL eventsService;
	
	@Autowired
	ReservationsServiceIMPL reservationsService;
	
	@Autowired
	ReservationsRepository reservationsRepository;
	
	@GetMapping("/products/highPrice")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> filterByPriceHigh() {
        List<Product> productDtoList = productsService.filterPriceHigh();
        ResponseEntity<List<Product>> resp = new ResponseEntity<List<Product>>(productDtoList, HttpStatus.OK);
        return resp;
    }

    @GetMapping("/products/lowPrice")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> filterByPriceLow() {
        List<Product> productDtoList = productsService.filterPriceLow();
        ResponseEntity<List<Product>> resp = new ResponseEntity<List<Product>>(productDtoList, HttpStatus.OK);
        return resp;
    }
    
    @GetMapping("/cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ShoppingCart> getShoppingCart() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByEmail(email);
        ShoppingCart shoppingCart = user.getCart();
        ResponseEntity<ShoppingCart> resp = new ResponseEntity<ShoppingCart>(shoppingCart, HttpStatus.OK);
        return resp;
    }
    
    @PostMapping("/addItem/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ShoppingCart> addItem(@PathVariable("id") Long productId, 
    		@RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity){
    	Product product = productsService.getById(productId);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User customer = userService.getByEmail(email);
        ShoppingCart cart = shoppingCartsService.addToCart(product, quantity, customer);
        ResponseEntity<ShoppingCart> res = new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
    	return res;
    }
    
    @PutMapping("/updateCart/{id}/{quantity}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ShoppingCart> updateCart(@PathVariable("id") Long productId,
            @PathVariable("quantity") int quantity) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByEmail(email);
        Product product = productsService.getById(productId);
        ShoppingCart cart = shoppingCartsService.updateCart(product, quantity, user);
        if (quantity == 0) {
            shoppingCartsService.deleteProduct(product, user);
        }
        ResponseEntity<ShoppingCart> resp = new ResponseEntity<>(cart, HttpStatus.OK);
        return resp;
    }
    
    @DeleteMapping("/deleteFromCart/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ShoppingCart> deleteItemFromCart(@PathVariable("id") Long productId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByEmail(email);
        Product product = productsService.getById(productId);
        ShoppingCart cart = shoppingCartsService.deleteProduct(product, user);
        ResponseEntity<ShoppingCart> resp = new ResponseEntity<>(cart, HttpStatus.OK);
        return resp;
    }
    
    @PostMapping("/orders/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> addOrder() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User customer = userService.getByEmail(email);
        ShoppingCart cart = customer.getCart();
        Order order = ordersService.save(cart);
        ResponseEntity<Order> resp = new ResponseEntity<Order>(order, HttpStatus.OK);
        return resp;
    }
    
    @DeleteMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId){
    	Optional<Order> optionalOrder = ordersRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            if (order.getUser().getUsername().equals(currentUser.getUsername())) {
                order.setOrderStatus(EOrder.CANCELED);
                ordersRepository.save(order);
                return ResponseEntity.ok("Order canceled successfully");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to cancel this order");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }
    
    @PostMapping("/reservations/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Reservation> reserve(@RequestParam Long eventId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByEmail(email);
        Event event = eventsService.getById(eventId);
        Optional<Reservation> existingReservation = user.getReservations().stream()
                .filter(reservation -> reservation.getEvent().equals(event))
                .findFirst();
        if (existingReservation.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Reservation newReservation = new Reservation();
        newReservation.setEvent(event);
        user.getReservations().add(newReservation);
        usersRepository.save(user);

        ResponseEntity<Reservation> resp = new ResponseEntity<>(newReservation, HttpStatus.OK);
        return resp;
    }
    
    @DeleteMapping("/user/reservations/{reservationId}")
    @PreAuthorize("hasRole('USER')")
    public void cancelReservation(Long reservationId) {
        Optional<Reservation> optionalReservation = reservationsRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            if (reservation.getReservationStatus() == EReservation.PENDING || reservation.getReservationStatus() == EReservation.CONFIRMED) {
                reservation.setReservationStatus(EReservation.CANCELED);
                reservationsRepository.save(reservation);
                System.out.println("Reservation canceled successfully.");
            } else {
                System.out.println("Reservation cannot be canceled in the current status.");
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }

}
