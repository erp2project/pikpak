package kr.co.pikpak.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.product_dto_lhwtemp;

@Mapper
public interface InoutBoundRepo {
	List<product_dto_lhwtemp> selectProduct();
}
