package com.ecommerce.wishlist.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.wishlist.service.WishlistService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WishlistService service;

	@Test
	@DisplayName("deve retornar 200 ao adicionar produto.")
	public void adicionar() throws Exception {
		this.mockMvc.perform(post("/v1/123")
				.content("456"))
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("deve retornar 200 ao remover produto.")
	public void remover() throws Exception {
		this.mockMvc.perform(delete("/v1/123/produtos/456"))
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("deve retornar 200 ao listar produtos.")
	public void listar() throws Exception {
		List<String> produtos = List.of("456", "789");
		when(service.listarProdutos("123")).thenReturn(produtos);
		
		this.mockMvc.perform(get("/v1/123"))
		.andExpect(status().isOk())
		.andExpect(content().string(new ObjectMapper().writeValueAsString(produtos)));
	}
	
	@Test
	@DisplayName("deve retornar 200 ao verificar produto.")
	public void verificarProduto() throws Exception {
		when(service.verificarProduto("123", "456")).thenReturn(true);
		
		this.mockMvc.perform(get("/v1/123/produtos/456"))
		.andExpect(status().isOk())
		.andExpect(content().string("true"));
	}

}
