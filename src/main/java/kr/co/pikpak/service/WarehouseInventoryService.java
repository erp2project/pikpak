package kr.co.pikpak.service;

import java.util.List;
import java.util.Map;

import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;

public interface WarehouseInventoryService {
	public Map<String, Object> getAreaData(String area_cd);
	public List<WarehouseInventory_dto> getAllinventory();
	public List<WarehouseInspection_dto> getCheckData();
}
