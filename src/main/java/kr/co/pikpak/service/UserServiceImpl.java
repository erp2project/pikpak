package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.LoginDTO;
import kr.co.pikpak.dto.UserAddDTO;
import kr.co.pikpak.dto.UserOperatorDTO;
import kr.co.pikpak.dto.UserTraderDTO;
import kr.co.pikpak.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo ur;
	
	@Override
	public List<LoginDTO> userListFromView() {
		List<LoginDTO> result = ur.userListFromView();
		return result;
	}
	
	@Override
	public int ctnFromView(String userId) {
		int result = ur.ctnFromView(userId);
		return result;
	}
	
	@Override
	public int ctnFromViewFilterBy(String userTel, String userType) {
		int result = ur.ctnFromViewFilterBy(userTel, userType);
		return result;
	}
	
	@Override
	public int addUserToTable(UserAddDTO dto) {
		int result = ur.addUserToTable(dto);
		return result;
	}
	
	@Override
	public String userTypeFromView(String userId) {
		String result = ur.userTypeFromView(userId);
		return result;
	}
	
	@Override
	public LoginDTO userDetailsFromView(String userId) {
		LoginDTO result = ur.userDetailsFromView(userId);
		return result;
	}
	
	@Override
	public String userLvFromOperator(String userId) {
		String result = ur.userLvFromOperator(userId);
		return result;
	}
	
	@Override
	public int modUserInTable(UserAddDTO dto) {
		int result = ur.modUserInTable(dto);
		return result;
	}
	
}
