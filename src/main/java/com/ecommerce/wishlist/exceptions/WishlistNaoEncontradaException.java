package com.ecommerce.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WishlistNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 8375272886439874711L;

	public WishlistNaoEncontradaException(final String idCliente) {
		super("Wishlist não encontrada para o cliente de id" + idCliente);
	}

}
