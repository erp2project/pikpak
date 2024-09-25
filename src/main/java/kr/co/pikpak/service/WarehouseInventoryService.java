package kr.co.pikpak.service;

import java.util.List;
import java.util.Map;

import kr.co.pikpak.dto.LocationDTO;
import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;

public interface WarehouseInventoryService {
	public Map<String, Object> getAreaData(String area_cd);
	public List<Map<String, Object>> getAreaStockData(String area_cd);
	public List<Map<String, Object>> getAreaRackData (String Zone_Rack);
	public List<Map<String, Object>> getTotalLocationData(String total_location);
	
	//재고 리스트 출력 페이지 - 상품코드 선택 (상품코드로 상품 조회)
	public WarehouseInventory_dto findByProduct_cd(String product_cd);
	
	//재고 리스트 출력 페이지 - 상품명 선택 시  (상품명으로 상품 조회)
	public WarehouseInventory_dto findByProduct_nm(String product_nm);	
	
	//재고 리스트 출력 페이지 - 회사코드 선택 시 
	public Map<String, Object> findByCompany_cd(String supplier_cd);
	
	//재고 리스트 출력 페이지 - 회사명 선택 시 
	public Map<String, Object> findByCompany_nm(String supplier_nm);
	
	//재고 리스트 출력 페이지 - 검색 옵션 / 전체 회사 코드 출력
	public List<Map<String, Object>> getAllsupplier_cd();
	
	//재고 리스트 출력 페이지 - 전체 재고 리스트 출력
	public List<WarehouseInventory_dto> getAllinventory();
	
	//재고 리스트 출력 페이지 - 관리 버튼 / idx 값으로 재고 데이터 조회
	public WarehouseInventory_dto getDetailsByIdx(Integer wh_warehouse_idx);
	
	//재고 리스트 출력 페이지 - 관리 버튼 팝업 / 상품코드로 상품테이블에서 안전재고 조회
	public Integer getSafetyInventory_qty(String product_cd);
	
	//재고 리스트 출력 페이지 - 조회
	public List<WarehouseInventory_dto> searchInventory(String area_cd,String rack_number,String level,String part,String product_cd,String product_nm,String supplier_nm);
	
	//창고 위치 관리 페이지 - 
	public List<Map<String, Object>> getLocationWithStockData (String area_cd);
	
	//창고 위치 관리 페이지 - 창고 위치 조회
	public LocationDTO findLocationByCode(String location_cd);
	
	//창고 위치 관리 페이지
	public List<Map<String, String>> getAllSupplierInfo();
	
	//창고 위치 관리 페이지 - 위치 지정
	int insertLocation(String location_cd, String supplier_cd,Integer max_capacity);	
	
	//창고 위치 관리 페이지 - 위치 삭제
	int deleteLocationByCode(String location_cd);	
	
	
	
	//창고 관리 페이지 - 점검 등록/ 재고 수량 일치여부 확인
	public List<WarehouseInspection_dto> getCheckData();
	
	//창고 관리 페이지 - 점검 등록/ 재고 수량 일치여부 확인
	public List<Map<String, Object>> getStockDataByZone(String zoneId);
	
	//창고 관리 페이지 - 점검 등록
	int insertCheckData2(Map<String, String> data);
	
	//창고 관리 페이지 - 상세 보기
	public Map<String, Object> getCheckRecordDetailsByIdx(Integer a_check_idx);
}
