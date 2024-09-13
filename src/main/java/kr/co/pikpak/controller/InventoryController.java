package kr.co.pikpak.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.pikpak.dto.WarehouseInventory_dto;
import kr.co.pikpak.mapper.WarehouseInventory_repository;

@Controller
public class InventoryController {
	@Autowired
	private WarehouseInventory_repository wir;
	
	
	//재고 현황 페이지 - 관리 버튼 클릭 시 상세 페이지 데이터 출력
	@GetMapping("/getInventoryDetails")
	@ResponseBody
	public Map<String, Object> getInventoryDetails(@RequestParam("wh_warehouse_idx") Integer wh_warehouse_idx){
		WarehouseInventory_dto getDetailsByIdx = wir.getDetailsByIdx(wh_warehouse_idx);
		//상품코드 별도 추출 후 상품 테이블에서 상품정보 가져오기
		Integer getSafetyInventory_qty = wir.getSafetyInventory_qty(getDetailsByIdx.getProduct_cd());
		Map<String, Object> result = new HashMap<>();
		result.put("details",getDetailsByIdx);
		result.put("safety_inventory", getSafetyInventory_qty);
		
		return result;
	}
	
	
	//회사코드로 데이터 조회
	@PostMapping("/getCompanyByCode")
	@ResponseBody
	public Map<String, Object> getCompanyByCode(@RequestBody Map<String, String> request){
		String supplier_cd = request.get("supplier_cd");
		System.out.println(supplier_cd+"선택한 회사 코드");
		Map<String, Object> getSupplier_nm = wir.findByCompany_cd(supplier_cd);
		return getSupplier_nm;
	}
	
	//회사명으로 데이터 조회
	@PostMapping("/getCompanyByName")
	@ResponseBody
	public Map<String, Object> getCompanyByName(@RequestBody Map<String, String> request){
		String supplier_nm = request.get("supplier_nm");
		System.out.println(supplier_nm+"선택한 회사명");
		Map<String, Object> getSupplier_cd = wir.findByCompany_nm(supplier_nm);
		return getSupplier_cd;
	}
	
	
	//상품코드로 데이터 조회
	@PostMapping("/getpdcode")
	@ResponseBody
	public Map<String, Object> getProductByCode(@RequestBody Map<String, String> request){
		String product_cd = request.get("product_cd");
		WarehouseInventory_dto product = wir.findByProduct_cd(product_cd);
		Map<String, Object> response = new HashMap<>();
		if(product != null) {
			response.put("product_nm", product.getProduct_nm());
		}
		return response;
	}
	
	//상품 명으로 데이터 조회
	@PostMapping("/getProductByName")
	@ResponseBody
	public Map<String, Object> getProductByName(@RequestBody Map<String, String> request){
		String product_nm = request.get("product_nm");
		WarehouseInventory_dto product = wir.findByProduct_nm(product_nm);
		Map<String, Object> response = new HashMap<>();
		if(product != null) {
			response.put("product_cd",product.getProduct_cd());
		}
		return response;
	}	
	
	
	//창고 위치 관리 - warehouse_grid.html
	@PostMapping("/getZoneData")
	@ResponseBody
	public Map<String, Object> getZoneStockData(@RequestBody Map<String, String> request){
		String area_cd = request.get("area_cd");
		List<Map<String, Object>> getAreaStockData = wir.getAreaStockData(area_cd);
		Map<String, Object> response = new HashMap<>();
		response.put("getAreaStockData", getAreaStockData);
		return response;
	}
	
	
	//창고별 재고 현황 페이지 - 구역 선택시 구역 정보 출력 (warehouse_inventory.html)
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
	
	//창고별 재고 현황 페이지 - 구역 선택시 구역+랙의 재고 정보 출력
	@PostMapping("/getReckdata")
	@ResponseBody
	public Map<String, Object> getrackdata(@RequestBody Map<String, String> request){
		String Zone_Rack = request.get("Zone_Rack");
		List<Map<String, Object>> getAreaRackData = wir.getAreaRackData(Zone_Rack);
		
		Map<String, Object> response = new HashMap<>();
		response.put("getAreaRackData", getAreaRackData);
		
		return response;
	}
	
	//창고별 재고 현황 페이지 - 구역 선택시 구역+ 랙 + 단/열의 재고 정보 출력
	@PostMapping("/getLPdata")
	@ResponseBody
	public Map<String, Object> gettotallocationdata(@RequestBody Map<String, String> request){
		String total_location = request.get("total_location");
		System.out.println(total_location);
		List<Map<String, Object>> getTotalLocationData = wir.getTotalLocationData(total_location);
		Map<String, Object> response = new HashMap<>();
		response.put("getTotalLocationData", getTotalLocationData);
		return response;
	}
}
