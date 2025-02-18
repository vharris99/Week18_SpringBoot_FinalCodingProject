package ecomm.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ecomm.store.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

}
