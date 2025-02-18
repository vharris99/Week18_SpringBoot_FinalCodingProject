package ecomm.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ecomm.store.entity.User;

public interface UserDao extends JpaRepository <User, Long> {

}
