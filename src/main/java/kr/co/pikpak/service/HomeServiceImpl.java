package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.HomeLoginDTO;
import kr.co.pikpak.repo.HomeRepo;

@Service
public class HomeServiceImpl implements HomeService {
	@Autowired
	private HomeRepo hr;
	
	@Override
	public List<HomeLoginDTO> userAuth(String user_id) {
		List<HomeLoginDTO> result = hr.userAuth(user_id);
		return result;
	}

}
