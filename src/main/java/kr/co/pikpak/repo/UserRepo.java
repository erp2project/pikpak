package kr.co.pikpak.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.LoginDTO;

@Mapper
public interface UserRepo {
	List<LoginDTO> userListFromView();
}
