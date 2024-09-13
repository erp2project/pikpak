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
	
	//창고별 재고 현황 페이지 - 구역의 재고 정보 출력
	@Override
	public List<Map<String, Object>> getAreaStockData(String area_cd) {

		return wir.getAreaStockData(area_cd);
	}
	
	//창고별 재고 현황 페이지 - 구역 + 랙의 재고 정보 출력
	@Override
	public List<Map<String, Object>> getAreaRackData(String Zone_Rack) {
		
		return wir.getAreaRackData(Zone_Rack);
	}
	
	//창고별 재고 현황 페이지 - 구역 + 랙 + 단 + 열의 재고 정보 출력
	@Override
	public List<Map<String, Object>> getTotalLocationData(String total_location) {
		return wir.getTotalLocationData(total_location);
	}
	
	//재고 현황 페이지 - 재고 전체 리스트 출력
	@Override
	public List<WarehouseInventory_dto> getAllinventory() {
		
		return wir.getAllinventory();
	}
	//재고 현황 페이지 - 검색 옵션 / 전체 회사 코드 출력
	@Override
	public List<Map<String, Object>> getAllsupplier_cd() {
		List<Map<String, Object>> result = wir.getAllsupplier_cd();
		return result;
	}
	//재고 현황 페이지 - 관리 버튼 / idx 값으로 재고 데이터 조회
	@Override
	public WarehouseInventory_dto getDetailsByIdx(Integer wh_warehouse_idx) {
	
		return wir.getDetailsByIdx(wh_warehouse_idx);
	}
	//재고 현황 페이지 - 관리 버튼 팝업 / 상품코드로 상품테이블에서 안전재고 조회
	@Override
	public Integer getSafetyInventory_qty(String product_cd) {
		
		return wir.getSafetyInventory_qty(product_cd);
	}
	
	
	
	
	//재고 현황 페이지 검색 옵션 - 상품코드 선택 시  (상품코드로 상품 조회)
	@Override
	public WarehouseInventory_dto findByProduct_cd(String product_cd) {
		
		return wir.findByProduct_cd(product_cd);
	}
	
	//재고 현황 페이지 검색 옵션 - 상품명 선택 시(상품명으로 상품 조회)
	@Override
	public WarehouseInventory_dto findByProduct_nm(String product_nm) {
		
		return wir.findByProduct_nm(product_nm);
	}
	
	//재고 현황 페이지 검색 옵션 - 회사코드 선택시
	@Override
	public Map<String, Object> findByCompany_cd(String supplier_cd) {
		
		return wir.findByCompany_cd(supplier_cd);
	}
	//재고 현황 페이지 검색 옵션 - 회사명 선택 시
	@Override
	public Map<String, Object> findByCompany_nm(String supplier_nm) {
		
		return wir.findByCompany_nm(supplier_nm);
	}

}
