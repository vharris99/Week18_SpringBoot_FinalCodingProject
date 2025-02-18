package ecomm.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ecomm.store.entity.EcommStore;

public interface EcommStoreDao extends JpaRepository<EcommStore, Long>{
	// default findAll() method works as intended
}
