package kr.co.pikpak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;
@Repository
@Mapper
public interface WarehouseInventory_repository {
	
	//창고별 재고 현황 페이지 - 구역 정보 출력
	Map<String, Object> getAreaData(String area_cd);
	
	//재고 리스트 출력 페이지 - 전체 재고 리스트 출력
	List<WarehouseInventory_dto> getAllinventory();
	
	//창고 관리 페이지 - 구역 점검 기록 리스트
	List<WarehouseInspection_dto> getCheckData();
}
