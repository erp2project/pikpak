package kr.co.pikpak.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.pikpak.mapper.WarehouseInventory_repository;

@Controller
public class InventoryController {
	@Autowired
	private WarehouseInventory_repository wir;
	
	//창고별 재고 현황 페이지 - 구역 선택시 구역 정보 출력
	@PostMapping("/getAreadata")
	@ResponseBody
	public Map<String, Object> getareadata(@RequestBody Map<String, String> request) {
		String area_cd = request.get("area_cd");
		//구역 정보 
		Map<String, Object> getAreaData = wir.getAreaData(area_cd);
		//구역의 재고 정보
		List<Map<String, Object>> getAreaStockData = wir.getAreaStockData(area_cd);
		
		Map<String, Object> response = new HashMap<>();
		response.put("getAreaData", getAreaData);
		response.put("getAreaStockData", getAreaStockData);
		
		return response;
	}
}
