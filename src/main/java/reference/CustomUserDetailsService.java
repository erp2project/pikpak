//package com.counseling.cms.jwt;
package reference;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.counseling.cms.entity.UserInfoEntity;
import com.counseling.cms.mapper.LoginMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private LoginMapper loginMapper;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserInfoEntity userInfoEntity = loginMapper.findByUserId(userId);

		if (userInfoEntity == null) {
			throw new UsernameNotFoundException("사용자 정보 찾을 수 없음");
		}

		return new CustomUserDetails(userInfoEntity);
	}

}
