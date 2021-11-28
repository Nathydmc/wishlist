package com.ecommerce.wishlist.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ecommerce.wishlist.exceptions.TamanhoListaProdutosExcedidoException;
import com.ecommerce.wishlist.exceptions.WishlistNaoEncontradaException;
import com.ecommerce.wishlist.model.Wishlist;
import com.ecommerce.wishlist.repository.WishlistCustomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistService {
	
	private static final int TAMANHO_MAXIMO_LISTA_PRODUTOS = 20;
	
	private final WishlistCustomRepository repositoryC;

	
	public void removerProduto(final String idCliente, final String idProduto) {
		repositoryC.removeProduto(idCliente, idProduto);
	}
	
	public void adicionarProduto(final String idCliente, final String idProduto) {
		validarTamanhoListaProdutos(idCliente);
		Wishlist wishlist = repositoryC.addProduct(idCliente, idProduto);
		if (Objects.isNull(wishlist)) {
			criarWishlist(idCliente, idProduto);
		}
	}
	
	private void criarWishlist(final String idCliente, final String idProduto) {
		repositoryC.criar(
				Wishlist.builder()
				.idCliente(idCliente)
				.produtos(Arrays.asList(idProduto))
				.build());
	}

	public List<String> listarProdutos(final String idCliente) {
		Wishlist wishlist = repositoryC.buscarPorIdCliente(idCliente);
		if (Objects.isNull(wishlist)) {
			throw new WishlistNaoEncontradaException(idCliente);
		}
		return wishlist.getProdutos();
	}
	
	public boolean verificarProduto(final String idCliente, final String idProduto) {
		return repositoryC.verificarProduto(idCliente, idProduto);
	}
	
	private void validarTamanhoListaProdutos(final String idCliente) {
		Wishlist wishlist = repositoryC.buscarPorIdCliente(idCliente);
		if (Objects.nonNull(wishlist) && 
				wishlist.getProdutos().size() >= TAMANHO_MAXIMO_LISTA_PRODUTOS) {
			throw new TamanhoListaProdutosExcedidoException(idCliente);
		}
	}

}
