package kr.co.pikpak.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.LoginDTO;
import kr.co.pikpak.repo.LoginRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private LoginRepo lr;
	
	@Override
	public CustomUserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
		List<LoginDTO> user = lr.userDataById(user_id);
		
		if (user.size()==0) {
			throw new UsernameNotFoundException("사용자 정보 찾을 수 없음");
		}
		
		return new CustomUserDetails(user);
	}
	
	public String operatorLvByUserId(String user_id) {
		String result = lr.operatorLvById(user_id);
		return result;
	}
}
