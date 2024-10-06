package com.vanillwa.sbp.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vanillwa.sbp.dto.JoinDTO;
import com.vanillwa.sbp.entity.UserEntity;
import com.vanillwa.sbp.repository.UserRepository;
import com.vanillwa.sbp.service.JoinService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

	final UserRepository userRepository;
	final PasswordEncoder passwordEncoder;

	@Override
	public void createUser(JoinDTO joinDTO) {
		
		String username = joinDTO.getUsername();
		String nickname = joinDTO.getNickname();
		String password = joinDTO.getPassword();
		
		Boolean isExistUsername = userRepository.existsByUsername(username);
		Boolean isExistNickname = userRepository.existsByNickname(nickname);
		
		if(isExistUsername || isExistNickname ) return;
		
		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setNickname(nickname);
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
	}

	

}
