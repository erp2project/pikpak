package kr.co.pikpak.repo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.dto.supplier_info_dto_lhwtemp;

@Mapper
public interface InoutBoundRepo {
	//서버시간
	String get_time();
	
	//상품정보
	//List<product_dto_lhwtemp> selectProduct();
	List<Map<String, Object>> select_product();
	
	
	//입고요청 등록 
	int input_req_insert(input_request_dto dto);
	
	//입고요청 리스트
	List<input_request_dto> select_inreq();
	
	//입고요청 삭제
	int delete_inreq(String request_idx);
	
	//매입처 회사명 리스트 개수
	Integer select_supplier_total(String comp_nm, String comp_cd);
	
	//매입처 페이징
	List<supplier_info_dto_lhwtemp> select_supplier_limit(Map<String, Object> supplier);
	
	//매입처 회사 검색
	//List<supplier_info_dto_lhwtemp> select_supplier_search(String comp_nm, String comp_cd);

	//상품명 리스트 개수(검색 포함)
	Integer select_product_total(String pd_nm, String pd_cd);
	
	//상품명 페이징
	List<product_dto_lhwtemp> select_product_limit(Map<String, Object> product);
}
