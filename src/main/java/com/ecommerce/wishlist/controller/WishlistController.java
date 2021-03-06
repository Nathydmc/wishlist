package com.ecommerce.wishlist.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wishlist.configuration.ProjectInfo;
import com.ecommerce.wishlist.service.WishlistService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
		@ApiResponse(code = 200, message = ProjectInfo.OK),
		@ApiResponse(code = 400, message = ProjectInfo.BAD_REQUEST),
		@ApiResponse(code = 404, message = ProjectInfo.NOT_FOUND),
		@ApiResponse(code = 500, message = ProjectInfo.SERVER_ERROR),
})
public class WishlistController {
	
	private final WishlistService service;
	
	@ApiOperation(ProjectInfo.ADICIONAR_PRODUTO)
	@PostMapping(value = "/v1/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> adicionar(@PathVariable final String idCliente,
			@RequestBody(required = true) final String idProduto) {
		service.adicionarProduto(idCliente, idProduto);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(ProjectInfo.REMOVER_PRODUTO)
	@DeleteMapping(value = "/v1/{idCliente}/produtos/{idProduto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> remover(@PathVariable final String idCliente, @PathVariable final String idProduto) {
		service.removerProduto(idCliente, idProduto);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(ProjectInfo.LISTAR_PRODUTOS)
	@GetMapping(value = "/v1/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> listarProdutos(@PathVariable final String idCliente) {
		return ResponseEntity.ok(service.listarProdutos(idCliente));
	}
	
	@ApiOperation(ProjectInfo.VERIFICAR_PRODUTO)
	@GetMapping(value = "/v1/{idCliente}/produtos/{idProduto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> verificarProduto(@PathVariable final String idCliente, @PathVariable final String idProduto) {
		return ResponseEntity.ok(service.verificarProduto(idCliente, idProduto));
	}
	
}
