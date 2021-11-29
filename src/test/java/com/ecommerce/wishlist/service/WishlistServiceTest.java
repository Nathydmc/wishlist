package com.ecommerce.wishlist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.wishlist.exceptions.TamanhoListaProdutosExcedidoException;
import com.ecommerce.wishlist.exceptions.WishlistNaoEncontradaException;
import com.ecommerce.wishlist.model.Wishlist;
import com.ecommerce.wishlist.repository.WishlistRepository;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {
	
	private static final String ID_CLIENTE = "123";
	
	private static final String ID_PRODUTO = "222";

	@InjectMocks
	private WishlistService service;
	
	@Mock
	private WishlistRepository repository;

	@Test
	@DisplayName("deve remover produto da lista do cliente.")
	public void removerProduto() {
		service.removerProduto(ID_CLIENTE, ID_PRODUTO);
		
		verify(repository, times(1)).removerProduto(ID_CLIENTE, ID_PRODUTO);
	}
	
	@Test
	@DisplayName("deve adicionar produto a wishlist do cliente quando possuir menos de 20 produtos.")
	public void adicionarProdutoComSucesso() {
		when(repository.buscarPorIdCliente(ID_CLIENTE)).thenReturn(mockWishlist());
		when(repository.adicionarProduto(ID_CLIENTE, ID_PRODUTO)).thenReturn(mockWishlist());
		
		service.adicionarProduto(ID_CLIENTE, ID_PRODUTO);
		
		verify(repository, times(1)).buscarPorIdCliente(ID_CLIENTE);
		verify(repository, times(1)).adicionarProduto(ID_CLIENTE, ID_PRODUTO);
		verify(repository, never()).criar(any(Wishlist.class));
	}
	
	@Test
	@DisplayName("deve lançar exception quando tentar adicionar um produto e a lista possuir 20 produtos.")
	public void lancarExceptionAoAdicionarProduto() {
		when(repository.buscarPorIdCliente(ID_CLIENTE)).thenReturn(mockFullWishlist());
		
		TamanhoListaProdutosExcedidoException exception = 
				Assertions.assertThrows(TamanhoListaProdutosExcedidoException.class, () -> {
					service.adicionarProduto(ID_CLIENTE, ID_PRODUTO);
					});
		
		Assertions.assertEquals("O cliente de id 123 está com a quantidade máxima de itens em sua wishlist.", exception.getMessage());		
		verify(repository, times(1)).buscarPorIdCliente(ID_CLIENTE);
		verify(repository, never()).adicionarProduto(ID_CLIENTE, ID_PRODUTO);
		verify(repository, never()).criar(any(Wishlist.class));
	}
	
	@Test
	@DisplayName("deve criar uma wishlist quando tentar adicionar um produto e o cliente não possuir uma wishlist.")
	public void criarWishlist() {
		when(repository.buscarPorIdCliente(ID_CLIENTE)).thenReturn(null);
		
		service.adicionarProduto(ID_CLIENTE, ID_PRODUTO);
		
		verify(repository, times(1)).buscarPorIdCliente(ID_CLIENTE);
		verify(repository, times(1)).adicionarProduto(ID_CLIENTE, ID_PRODUTO);
		verify(repository, times(1)).criar(any(Wishlist.class));
	}
	
	@Test
	@DisplayName("deve listar produtos da wishlist do cliente.")
	public void listarProdutos() {
		List<String> expectedProdutos = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
				"11", "12", "13", "14", "15", "16", "17", "18", "19");
		
		when(repository.buscarPorIdCliente(ID_CLIENTE)).thenReturn(mockWishlist());
		
		List<String> actualProdutos = service.listarProdutos(ID_CLIENTE);
		
		assertThat(actualProdutos.containsAll(expectedProdutos));
		verify(repository, times(1)).buscarPorIdCliente(ID_CLIENTE);
	}
	
	@Test
	@DisplayName("deve lançar exceção quando wishlist não encontrada.")
	public void lançarExceptionAolistarProdutos() {
		when(repository.buscarPorIdCliente(ID_CLIENTE)).thenReturn(null);
		
		WishlistNaoEncontradaException exception = 
				Assertions.assertThrows(WishlistNaoEncontradaException.class, () -> {
					service.listarProdutos(ID_CLIENTE);
					});
		
		Assertions.assertEquals("Wishlist não encontrada para o cliente de id 123", exception.getMessage());	
		verify(repository, times(1)).buscarPorIdCliente(ID_CLIENTE);
	}
	
	@Test
	@DisplayName("deve verificar se produto existe na wishlist do cliente.")
	public void verificarProduto() {
		when(repository.verificarProduto(ID_CLIENTE, ID_PRODUTO)).thenReturn(true);
		
		Assertions.assertTrue(service.verificarProduto(ID_CLIENTE, ID_PRODUTO));
		verify(repository, times(1)).verificarProduto(ID_CLIENTE, ID_PRODUTO);
	}
	
	private Wishlist mockWishlist() {
		return Wishlist
				.builder()
				.id("abcde123")
				.idCliente(ID_CLIENTE)
				.produtos(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
						"11", "12", "13", "14", "15", "16", "17", "18", "19"))
				.build();		
	}
	
	private Wishlist mockFullWishlist() {
		return Wishlist
				.builder()
				.id("abcde123")
				.idCliente(ID_CLIENTE)
				.produtos(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
						"11", "12", "13", "14", "15", "16", "17", "18", "19", "20"))
				.build();		
	}
	
}
