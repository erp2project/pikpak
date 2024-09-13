//package com.counseling.cms.jwt;
package reference;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import com.counseling.cms.entity.UserInfoEntity;

public class CustomUserDetails implements UserDetails {

	String userId, userPassword, authority;
	
	public CustomUserDetails(UserInfoEntity userInfoEntity) {
		this.userId=userInfoEntity.getUserId();
		this.userPassword=userInfoEntity.getUserPassword();
		this.authority=userInfoEntity.getUserAuthority();
	}
	
	
	public String getUserId() {
		
		return this.userId;
	}
	
	@Override
	public String getPassword() {

		return this.userPassword;
	}
	
	public String getUserAuthority() {
		
		return this.authority;
	}
	
	@Override
	public String getUsername() {
		
		return null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singletonList(new SimpleGrantedAuthority(authority));
	}
	
	@Override
	public boolean isAccountNonExpired() {

		return false;
	}
	
	@Override
	public boolean isAccountNonLocked() {

		return false;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {

		return false;
	}
	
	@Override
	public boolean isEnabled() {

		return false;
	}
}
