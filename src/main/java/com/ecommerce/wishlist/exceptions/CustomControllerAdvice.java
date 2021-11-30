package com.ecommerce.wishlist.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {
	
	@ExceptionHandler({ TamanhoListaProdutosExcedidoException.class, IllegalArgumentException.class })
	public ResponseEntity<Map<String, String>> handle(RuntimeException exception) {
		return new ResponseEntity<>(
				Map.of("errorMessage", exception.getMessage(),
						"status", HttpStatus.BAD_REQUEST.toString()), 
				HttpStatus.BAD_REQUEST);	
	}

	@ExceptionHandler({ WishlistNaoEncontradaException.class })
	public ResponseEntity<Map<String, String>> handle(WishlistNaoEncontradaException exception) {
		return new ResponseEntity<>(
				Map.of("errorMessage", exception.getMessage(),
						"status", HttpStatus.NOT_FOUND.toString()), 
				HttpStatus.NOT_FOUND);
	}
	
}
