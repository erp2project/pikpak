package kr.co.pikpak.service;

import java.util.List;
import java.util.Map;

import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.dto.supplier_info_dto_lhwtemp;

public interface InoutBoundService {
	//상품코드 관련 정보 가져오기
	//public List<product_dto_lhwtemp> select_pdlist();
	public List<Map<String, Object>> select_product();
	
	//입고요청 데이터 insert
	public int input_req_insert(input_request_dto dto);
	
	//mysql 서버시간 불러오기
	public String get_time();
	
	//입고요청 리스트
	public List<input_request_dto> select_inreq();

	//입고요청 삭제
	public int delete_inreq(String request_idx);
	
	//매입처 회사명 검색 리스트 총 개수
	public Integer select_supplier_total(String comp_nm, String comp_cd);
	
	//매입처 페이징
	public List<supplier_info_dto_lhwtemp> select_supplier_limit(Map<String, Object> supplier);

	//매입처 검색
	//public List<supplier_info_dto_lhwtemp> select_supplier_search(String comp_nm, String comp_cd);

	//상품명 검색 리스트 총 개수
	public Integer select_product_total(String pd_nm, String pd_cd);
	
	//상품명 페이징 + 검색
	public List<product_dto_lhwtemp> select_product_limit(Map<String, Object> product);
	
	//입고요청 리스트 수정
	public int update_inreq(Map<String, Object> inrequest);
}
