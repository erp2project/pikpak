package kr.co.pikpak.repo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.pikpak.dto.LocationDTO;
import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;

@Repository
@Mapper
public interface WarehouseRepo {

	
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
	
	//재고 리스트 출력 페이지 - 조회
	List<WarehouseInventory_dto> searchInventory(String area_cd,String rack_number,String level,String part,String product_cd,String product_nm,String supplier_nm);
	
	//재고 리스트 출력 페이지 - 관리 버튼 클릭 시 idx값으로 재고 상세 조회
	WarehouseInventory_dto getDetailsByIdx(Integer wh_warehouse_idx);
	
	//재고 리스트 출력 페이지 - 관리 버튼 팝업 / 상품코드로 상품테이블에서 안전재고 조회
	Integer getSafetyInventory_qty(String product_cd);
	
	//재고 리스트 출력 페이지 - 검색 옵션 / 전체 회사코드 출력
	List<Map<String, Object>> getAllsupplier_cd();
	
	//재고 리스트 출력 페이지 - 상품코드 선택 (상품코드로 상품 조회)
	WarehouseInventory_dto findByProduct_cd(String product_cd);
	
	//재고 리스트 출력 페이지 - 상품명 선택 (상품명으로 상품 조회)
	WarehouseInventory_dto findByProduct_nm(String product_nm);
	
	//재고 리스트 출력 페이지 - 회사코드 선택
	
	Map<String, Object> findByCompany_cd(String supplier_cd);
	
	//재고 리스트 출력 페이지 - 회사명 선택
	Map<String, Object> findByCompany_nm(String supplier_nm);
	
	
	//창고 위치 관리 페이지 - 
	List<Map<String, Object>> getLocationWithStockData (String area_cd);
	
	//창고 위치 관리 페이지
	LocationDTO findLocationByCode (String location_cd);
	
	//창고 위치 관리 페이지
	List<Map<String, String>> getAllSupplierInfo();
	
	//창고 위치 관리 페이지 - 위치 등록
	int insertLocation(String location_cd, String supplier_cd,Integer max_capacity);
	
	//창고 위치 관리 페이지 - 위치 삭제
	int deleteLocationByCode(String location_cd);
	
	
	
	//창고 관리 페이지 - 구역 점검 기록 리스트
	List<WarehouseInspection_dto> getCheckData();
	
	//창고 관리 페이지 - 점검 등록/ 재고 수량 일치여부 확인
	List<Map<String, Object>> getStockDataByZone(String zoneId);
	
	//창고 관리 페이지 - 점검 등록
	int insertCheckData2(Map<String, String> data);

	//창고 관리 페이지 - 상세 보기
	Map<String, Object> getCheckRecordDetailsByIdx(Integer a_check_idx);
	
}
