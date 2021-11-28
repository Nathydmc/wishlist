package com.ecommerce.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TamanhoListaProdutosExcedidoException extends RuntimeException {
	
	private static final long serialVersionUID = -7963046017321467063L;

	public TamanhoListaProdutosExcedidoException(final String idCliente) {
		super("O cliente de id " + idCliente + " está com a quantidade máxima de itens em sua wishlist.");
	}
	
}
