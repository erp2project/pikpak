package kr.co.pikpak.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.LoginDTO;
import kr.co.pikpak.dto.UserAddDTO;
import kr.co.pikpak.dto.UserOperatorDTO;
import kr.co.pikpak.dto.UserTraderDTO;

@Mapper
public interface UserRepo {
	// 회원 정보 리스트출력
	List<LoginDTO> userListFromView();
	
	// 아이디 조회
	int ctnFromView(String userId);
	
	// 연락처 회원 유형 중복 체크
	int ctnFromViewFilterBy(String userTel, String userType);
	
	// 회원 등록
	int addUserToTable(UserAddDTO dto);
	
	// 회원 유형 조회
	String userTypeFromView(String userId);
	
	// 회원 상세정보 조회
	LoginDTO userDetailsFromView(String userId);
	
	// 회원 등급
	String userLvFromOperator(String userId);

	// 회원 수정
	int modUserInTable(UserAddDTO dto);
}
