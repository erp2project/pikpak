package kr.co.pikpak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;
import kr.co.pikpak.mapper.WarehouseInventory_repository;

@Service
public class WarehouseInventoryServiceImpl implements WarehouseInventoryService{
	@Autowired
	private WarehouseInventory_repository wir;
	
	//창고 관리 페이지 - 구역 점검 기록 출력
	@Override
	public List<WarehouseInspection_dto> getCheckData() {
		return wir.getCheckData();
	}
	
	//창고별 재고 현황 페이지 - 구역 정보 출력
	@Override
	public Map<String, Object> getAreaData(String area_cd) {
		
		return wir.getAreaData(area_cd);
	}
	
	//재고 현황 페이지 
	@Override
	public List<WarehouseInventory_dto> getAllinventory() {
		
		return wir.getAllinventory();
	}
}
