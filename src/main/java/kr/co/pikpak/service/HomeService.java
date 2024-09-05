package kr.co.pikpak.service;

import java.util.List;

import kr.co.pikpak.dto.HomeLoginDTO;

public interface HomeService {
	public List<HomeLoginDTO> userAuth(String user_id);
}
