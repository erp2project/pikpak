package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.WarehouseInventory_dto;
import kr.co.pikpak.mapper.WarehouseInventory_repository;

@Service
public class WarehouseInventoryServiceImpl implements WarehouseInventoryService{
	@Autowired
	private WarehouseInventory_repository wir;
	
	@Override
	public List<WarehouseInventory_dto> getAllinventory() {
		
		return wir.getAllinventory();
	}
}
