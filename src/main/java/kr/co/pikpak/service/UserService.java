package kr.co.pikpak.service;

import java.util.List;

import kr.co.pikpak.dto.LoginDTO;

public interface UserService {
	// 회원 정보 리스트출력
	List<LoginDTO> userListFromView();
	
	// 아이디 조회
	int ctnFromView(String userId);
}
