package kr.co.pikpak.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.LoginDTO;

@Mapper
public interface UserRepo {
	// 회원 정보 리스트출력
	List<LoginDTO> userListFromView();
	
	// 아이디 조회
	int ctnFromView(String userId);
}
