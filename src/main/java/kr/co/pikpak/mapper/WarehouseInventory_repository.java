package kr.co.pikpak.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.pikpak.dto.Stock_dto;
import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;
@Repository
@Mapper
public interface WarehouseInventory_repository {
	
	//창고별 재고 현황 페이지 - 구역 정보 출력
	Map<String, Object> getAreaData(String area_cd);
	
	//창고별 재고 현황 페이지 - 각 구역의 재고 리스트 출력
	List<Map<String, Object>> getAreaStockData(String area_cd);
	
	//창고별 재고 현황 페이지 - 각 구역 + 랙의 재고 리스트 출력
	List<Map<String, Object>> getAreaRackData(String Zone_Rack);
	
	//창고별 재고 현황 페이지 - 각 구역 + 랙 + 단 + 열 선택 시 리스트 출력
	List<Map<String, Object>> getTotalLocationData(String total_location);
	
	//재고 리스트 출력 페이지 - 전체 재고 리스트 출력
	List<WarehouseInventory_dto> getAllinventory();
	
	//창고 관리 페이지 - 구역 점검 기록 리스트
	List<WarehouseInspection_dto> getCheckData();
	
	
}
