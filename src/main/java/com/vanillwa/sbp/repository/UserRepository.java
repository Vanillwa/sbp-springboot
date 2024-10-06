package com.vanillwa.sbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vanillwa.sbp.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Boolean existsByUsername(String username);
	Boolean existsByNickname(String nickname);

	UserEntity findByUsername(String username);
}
