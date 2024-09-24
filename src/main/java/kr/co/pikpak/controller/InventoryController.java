package kr.co.pikpak.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.pikpak.dto.LocationDTO;
import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;
import kr.co.pikpak.repo.WarehouseRepo;

@Controller
public class InventoryController {
	@Autowired
	private WarehouseRepo wr;
	
	
	//창고 점검 페이지 - 점검 등록 
	@PostMapping("/insertInspectData")
	@ResponseBody
	public ResponseEntity<String> insertInspectData(@RequestParam Map<String, String> request){
		System.out.println(request);
		int result = wr.insertCheckData2(request);
		if(result>0) {
			return ResponseEntity.ok("정상적으로 등록 되었습니다.");
		}else {
			return ResponseEntity.ok("오류가 발생하여 등록에 실패했습니다.");
		}
	}
	
	
	//창고 점검 페이지 - 상세보기
	@GetMapping("/getCheckRecordDetails/{a_check_idx}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getCheckRecordDetails(@PathVariable("a_check_idx")Integer a_check_idx){
		Map<String, Object> recordDetails = wr.getCheckRecordDetailsByIdx(a_check_idx);
		if(recordDetails != null) {
			return ResponseEntity.ok(recordDetails);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	//창고 점검 페이지 - 재고 상태 조회
	@PostMapping("/getZoneStockData")
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getZoneStockData(@RequestBody Map<String, String> request, Model m) {
		String zoneId = request.get("zoneId");
		List<Map<String, Object>> stockData = wr.getStockDataByZone(zoneId);
		return ResponseEntity.ok(stockData);
	}
	
	//창고 위치 관리 페이지 - 위치 지정
	@PostMapping("/assignLocation")
	@ResponseBody
	public Map<String, String> assignLocation(@RequestBody Map<String, Object> data){
		String location_cd = (String) data.get("location_cd");
		String supplier_cd = (String)data.get("supplier_cd");
		Integer max_capacity = 0;
		if(location_cd.contains("L1")) {
			max_capacity = 3;
		}else if(location_cd.contains("L2")||location_cd.contains("L3")) {
			max_capacity = 6;
		}else if(location_cd.contains("L4")) {
			max_capacity = 12;
		}
		
		int result = wr.insertLocation(location_cd, supplier_cd,max_capacity);
		Map<String, String> response = new HashMap<>();
		if(result>0) {
			response.put("status", "success");
			response.put("message", "위치지정이 완료되었습니다.");
		}else {
			response.put("status", "error");
			response.put("message", "위치지정에 실패했습니다.");			
		}
		return response;
	}
	
	//창고 위치 관리 페이지 - 위치 조회 
	@PostMapping("/checkLocation")
	@ResponseBody
	public Map<String, Object> checkLocation(@RequestBody Map<String, String>request){
		String locationCd = request.get("location_cd");
		 LocationDTO location = wr.findLocationByCode(locationCd);
		 Map<String, Object> response = new HashMap<>();
	        if (location != null && location.getIsOccupied()) {
	            response.put("status", "occupied");
	            response.put("has_stock", location.getHasStock());
	        } else {
	            response.put("status", "available");
	        }	 
		return response;
	}
	
	//재고 현황 페이지 - 조회버튼
	@PostMapping("/getSearchData")
	@ResponseBody
	public List<WarehouseInventory_dto> searchInventory(@RequestBody Map<String,String> searchData ){
		System.out.println(searchData);
		String area_cd = searchData.get("area_cd");
		String rack_number = searchData.get("rack_number");
		String level = searchData.get("level");
		String part = searchData.get("part");
		String product_cd = searchData.get("product_cd");
		String product_nm = searchData.get("product_nm");
		String supplier_nm = searchData.get("supplier_nm");
		
		List<WarehouseInventory_dto> searchInventory = wr.searchInventory(area_cd, rack_number, level, part, product_cd, product_nm, supplier_nm);
		
		return  searchInventory;
	}
	
	
	//재고 현황 페이지 - 관리 버튼 클릭 시 상세 페이지 데이터 출력
	@GetMapping("/getInventoryDetails")
	@ResponseBody
	public Map<String, Object> getInventoryDetails(@RequestParam("wh_warehouse_idx") Integer wh_warehouse_idx){
		WarehouseInventory_dto getDetailsByIdx = wr.getDetailsByIdx(wh_warehouse_idx);
		//상품코드 별도 추출 후 상품 테이블에서 상품정보 가져오기
		Integer getSafetyInventory_qty = wr.getSafetyInventory_qty(getDetailsByIdx.getProduct_cd());
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
		Map<String, Object> getSupplier_nm = wr.findByCompany_cd(supplier_cd);
		return getSupplier_nm;
	}
	
	//회사명으로 데이터 조회
	@PostMapping("/getCompanyByName")
	@ResponseBody
	public Map<String, Object> getCompanyByName(@RequestBody Map<String, String> request){
		String supplier_nm = request.get("supplier_nm");
		Map<String, Object> getSupplier_cd = wr.findByCompany_nm(supplier_nm);
		return getSupplier_cd;
	}
	
	
	//상품코드로 데이터 조회
	@PostMapping("/getpdcode")
	@ResponseBody
	public Map<String, Object> getProductByCode(@RequestBody Map<String, String> request){
		String product_cd = request.get("product_cd");
		WarehouseInventory_dto product = wr.findByProduct_cd(product_cd);
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
		WarehouseInventory_dto product = wr.findByProduct_nm(product_nm);
		Map<String, Object> response = new HashMap<>();
		if(product != null) {
			response.put("product_cd",product.getProduct_cd());
		}
		return response;
	}	
	
	
	//창고 위치 관리 - warehouse_grid.html
	@PostMapping("/getZoneData")
	@ResponseBody
	public Map<String, Object> getZoneData(@RequestBody Map<String, String> request){
		String area_cd = request.get("area_cd");
		System.out.println(area_cd);
		List<Map<String, Object>> getAreaStockData = wr.getLocationWithStockData(area_cd);		
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
		Map<String, Object> getAreaData = wr.getAreaData(area_cd);
		//구역의 재고 정보
		List<Map<String, Object>> getAreaStockData = wr.getAreaStockData(area_cd);
		
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
		List<Map<String, Object>> getAreaRackData = wr.getAreaRackData(Zone_Rack);
		
		Map<String, Object> response = new HashMap<>();
		response.put("getAreaRackData", getAreaRackData);
		
		return response;
	}
	
	//창고별 재고 현황 페이지 - 구역 선택시 구역+ 랙 + 단/열의 재고 정보 출력
	@PostMapping("/getLPdata")
	@ResponseBody
	public Map<String, Object> gettotallocationdata(@RequestBody Map<String, String> request){
		String total_location = request.get("total_location");
		List<Map<String, Object>> getTotalLocationData = wr.getTotalLocationData(total_location);
		Map<String, Object> response = new HashMap<>();
		response.put("getTotalLocationData", getTotalLocationData);
		return response;
	}
}
