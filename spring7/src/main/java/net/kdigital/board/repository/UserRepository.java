package net.kdigital.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.board.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	UserEntity findByUserId(String userId);
}
