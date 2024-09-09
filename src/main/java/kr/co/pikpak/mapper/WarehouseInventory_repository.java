package kr.co.pikpak.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.pikpak.dto.WarehouseInventory_dto;
@Repository
@Mapper
public interface WarehouseInventory_repository {
	List<WarehouseInventory_dto> getAllinventory();
}
