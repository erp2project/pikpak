package kr.co.pikpak.service;

import java.util.List;

import kr.co.pikpak.dto.LoginDTO;

public interface UserService {
	List<LoginDTO> userListFromView();
}
