package kr.co.pikpak.service;

import java.util.List;

import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.dto.supplier_info_dto_lhwtemp;

public interface InoutBoundService {
	//상품코드 관련 정보 가져오기
	public List<product_dto_lhwtemp> select_pdlist();
	
	//입고요청 데이터 insert
	public int input_req_insert(input_request_dto dto);
	
	//mysql 서버시간 불러오기
	public String get_time();
	
	//입고요청 리스트
	public List<input_request_dto> select_inreq();

	//입고요청 삭제
	public int delete_inreq(String request_idx);
	
	//매입처 회사명 검색 리스트
	public Integer select_supplier_total();
	
	//매입처 페이징
	public List<supplier_info_dto_lhwtemp> select_supplier_limit(Integer startpg, Integer page_size);
}
