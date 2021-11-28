package com.ecommerce.wishlist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.wishlist.model.Wishlist;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String>{

	
}
