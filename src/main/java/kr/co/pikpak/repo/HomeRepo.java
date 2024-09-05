package kr.co.pikpak.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.HomeLoginDTO;

@Mapper
public interface HomeRepo {
	List<HomeLoginDTO> userAuth(String user_id);
}
