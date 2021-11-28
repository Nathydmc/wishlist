package com.ecommerce.wishlist.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ecommerce.wishlist.model.Wishlist;

@Repository
public class WishlistCustomRepository {
	
	private static final String ID_CLIENTE = "idCliente";
	
	private static final String PRODUTOS = "produtos";

	private final MongoTemplate mongoTemplate;

	public WishlistCustomRepository(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public Wishlist addProduct(final String idCliente, final String idProduto) {
		Query query = new Query(Criteria.where(ID_CLIENTE).is(idCliente));
		Update update = new Update().addToSet(PRODUTOS, idProduto);
		return mongoTemplate.update(Wishlist.class).matching(query).apply(update).findAndModifyValue();
	}
	
	public Wishlist criar(final Wishlist wishlist) {
		return mongoTemplate.insert(wishlist);
	}
	
	public Wishlist removeProduto(final String idCliente, final String idProduto) {
		Query query = new Query(Criteria.where(ID_CLIENTE).is(idCliente));
		Update update = new Update().pull(PRODUTOS, idProduto);
		return mongoTemplate.update(Wishlist.class).matching(query).apply(update).findAndModifyValue();
	}
	
	public Wishlist buscarPorIdCliente(final String idCliente) {
		Query query = new Query(Criteria.where(ID_CLIENTE).is(idCliente));
		return mongoTemplate.findOne(query, Wishlist.class);
	}
	
	public boolean verificarProduto(final String idCliente, final String idProduto) {
		Query query = new Query(Criteria.where(ID_CLIENTE).is(idCliente));
		query.addCriteria(Criteria.where(PRODUTOS).in(idProduto));
		return mongoTemplate.exists(query, Wishlist.class);
	}
	
}
