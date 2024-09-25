package kr.co.pikpak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.LocationDTO;
import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;
import kr.co.pikpak.repo.WarehouseRepo;

@Service
public class WarehouseInventoryServiceImpl implements WarehouseInventoryService{
	@Autowired
	private WarehouseRepo wr;
	
	//창고 관리 페이지 - 구역 점검 기록 출력
	@Override
	public List<WarehouseInspection_dto> getCheckData() {
		return wr.getCheckData();
	}
	
	//창고별 재고 현황 페이지 - 구역 정보 출력
	@Override
	public Map<String, Object> getAreaData(String area_cd) {
		
		return wr.getAreaData(area_cd);
	}
	
	//창고별 재고 현황 페이지 - 구역의 재고 정보 출력
	@Override
	public List<Map<String, Object>> getAreaStockData(String area_cd) {

		return wr.getAreaStockData(area_cd);
	}
	
	//창고별 재고 현황 페이지 - 구역 + 랙의 재고 정보 출력
	@Override
	public List<Map<String, Object>> getAreaRackData(String Zone_Rack) {
		
		return wr.getAreaRackData(Zone_Rack);
	}
	
	//창고별 재고 현황 페이지 - 구역 + 랙 + 단 + 열의 재고 정보 출력
	@Override
	public List<Map<String, Object>> getTotalLocationData(String total_location) {
		return wr.getTotalLocationData(total_location);
	}
	
	
	
	//재고 현황 페이지 - 재고 전체 리스트 출력
	@Override
	public List<WarehouseInventory_dto> getAllinventory() {
		return wr.getAllinventory();
	}
	
	//재고 현황 페이지 - 조회한 리스트 출력
	@Override
	public List<WarehouseInventory_dto> searchInventory(String area_cd, String rack_number, String level, String part, String product_cd, String product_nm, String supplier_nm) {
		return wr.searchInventory(area_cd, rack_number, level, part, product_cd, product_nm, supplier_nm);
	}
	
	//재고 현황 페이지 - 검색 옵션 / 전체 회사 코드 출력
	@Override
	public List<Map<String, Object>> getAllsupplier_cd() {
		List<Map<String, Object>> result = wr.getAllsupplier_cd();
		return result;
	}
	//재고 현황 페이지 - 관리 버튼 / idx 값으로 재고 데이터 조회
	@Override
	public WarehouseInventory_dto getDetailsByIdx(Integer wh_warehouse_idx) {
		return wr.getDetailsByIdx(wh_warehouse_idx);
	}
	//재고 현황 페이지 - 관리 버튼 팝업 / 상품코드로 상품테이블에서 안전재고 조회
	@Override
	public Integer getSafetyInventory_qty(String product_cd) {
		
		return wr.getSafetyInventory_qty(product_cd);
	}
	
	
	
	
	//재고 현황 페이지 검색 옵션 - 상품코드 선택 시  (상품코드로 상품 조회)
	@Override
	public WarehouseInventory_dto findByProduct_cd(String product_cd) {
		
		return wr.findByProduct_cd(product_cd);
	}
	
	//재고 현황 페이지 검색 옵션 - 상품명 선택 시(상품명으로 상품 조회)
	@Override
	public WarehouseInventory_dto findByProduct_nm(String product_nm) {
		
		return wr.findByProduct_nm(product_nm);
	}
	
	//재고 현황 페이지 검색 옵션 - 회사코드 선택시
	@Override
	public Map<String, Object> findByCompany_cd(String supplier_cd) {
		
		return wr.findByCompany_cd(supplier_cd);
	}
	//재고 현황 페이지 검색 옵션 - 회사명 선택 시
	@Override
	public Map<String, Object> findByCompany_nm(String supplier_nm) {
		
		return wr.findByCompany_nm(supplier_nm);
	}

	
	//창고 위치 관리 페이지
	@Override
	public List<Map<String, Object>> getLocationWithStockData(String area_cd) {
		
		return wr.getLocationWithStockData(area_cd);
	}
	
	//창고 위치 관리 페이지 - 위치 정보 조회
	@Override
	public LocationDTO findLocationByCode(String location_cd) {
		return wr.findLocationByCode(location_cd);
	}
	
	//창고 위치 관리 페이지
	@Override
	public List<Map<String, String>> getAllSupplierInfo() {

		return wr.getAllSupplierInfo();
	}
	
	//창고 위치 관리 페이지 - 위치 지정
	@Override
	public int insertLocation(String location_cd, String supplier_cd,Integer max_capacity) {

		return wr.insertLocation(location_cd, supplier_cd,max_capacity);
	}
	
	//창고 위치 관리 페이지 - 위치 삭제
	@Override
	public int deleteLocationByCode(String location_cd) {
		
		return wr.deleteLocationByCode(location_cd);
	}
	
	
	
	
	
	//창고 관리 페이지 - 점검 등록/ 재고 수량 일치여부 확인
	@Override
	public List<Map<String, Object>> getStockDataByZone(String zoneId) {
		return wr.getStockDataByZone(zoneId);
	}
	
	//창고 관리 페이지 - 점검 등록
	@Override
	public int insertCheckData2(Map<String, String> data) {

		return wr.insertCheckData2(data);
	}
	//창고 관리 페이지 - 상세보기
	@Override
	public Map<String, Object> getCheckRecordDetailsByIdx(Integer a_check_idx) {
		return wr.getCheckRecordDetailsByIdx(a_check_idx);
	}
}
