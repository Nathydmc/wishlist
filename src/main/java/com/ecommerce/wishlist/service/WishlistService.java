package com.ecommerce.wishlist.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.ecommerce.wishlist.exceptions.TamanhoListaProdutosExcedidoException;
import com.ecommerce.wishlist.exceptions.WishlistNaoEncontradaException;
import com.ecommerce.wishlist.model.Wishlist;
import com.ecommerce.wishlist.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistService {
	
	private static final int TAMANHO_MAXIMO_LISTA_PRODUTOS = 20;
	
	private final WishlistRepository repository;

	
	public void removerProduto(final String idCliente, final String idProduto) {
		repository.removerProduto(idCliente, idProduto);
	}
	
	public void adicionarProduto(final String idCliente, final String idProduto) {
		validarAdicionar(idCliente, idProduto);
		Wishlist wishlist = repository.adicionarProduto(idCliente, idProduto);
		if (Objects.isNull(wishlist)) {
			criarWishlist(idCliente, idProduto);
		}
	}
	
	private Wishlist criarWishlist(final String idCliente, final String idProduto) {
		return repository.criar(Wishlist.builder()
				.idCliente(idCliente)
				.produtos(Arrays.asList(idProduto))
				.build());
	}

	public List<String> listarProdutos(final String idCliente) {
		return Optional.ofNullable(repository.buscarPorIdCliente(idCliente))
				.orElseThrow(() -> new WishlistNaoEncontradaException(idCliente))
				.getProdutos();
	}
	
	public boolean verificarProduto(final String idCliente, final String idProduto) {
		return repository.verificarProduto(idCliente, idProduto);
	}

	private void validarAdicionar(final String idCliente, final String idProduto) {
		if (Strings.isBlank(idProduto)) {
			throw new IllegalArgumentException("IdProduto deve ser informado");
		}
		validarTamanhoListaProdutos(idCliente);
	}
	
	private void validarTamanhoListaProdutos(final String idCliente) {
		Wishlist wishlist = repository.buscarPorIdCliente(idCliente);
		if (Objects.nonNull(wishlist) && 
				wishlist.getProdutos().size() >= TAMANHO_MAXIMO_LISTA_PRODUTOS) {
			throw new TamanhoListaProdutosExcedidoException(idCliente);
		}
	}

}
