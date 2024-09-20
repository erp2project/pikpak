package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.LoginDTO;
import kr.co.pikpak.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo ur;
	
	// 회원 정보 리스트출력
	@Override
	public List<LoginDTO> userListFromView() {
		List<LoginDTO> result = ur.userListFromView();
		return result;
	}
	
	// 아이디 조회
	@Override
	public int ctnFromView(String userId) {
		int result = ur.ctnFromView(userId);
		return result;
	}
}
