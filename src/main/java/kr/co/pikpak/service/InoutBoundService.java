package kr.co.pikpak.service;

import java.util.List;

import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.product_dto_lhwtemp;

public interface InoutBoundService {
	//상품코드 관련 정보 가져오기
	List<product_dto_lhwtemp> select_pdlist();
	
	//입고요청 데이터 insert
	int input_req_insert(input_request_dto dto);
}
