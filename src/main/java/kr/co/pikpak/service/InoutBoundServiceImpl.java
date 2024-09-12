package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.repo.InoutBoundRepo;

@Service
public class InoutBoundServiceImpl implements InoutBoundService{
	
	@Autowired
	InoutBoundRepo iorepo;
	
	//입고요청 데이터 등록
	@Override
	public int input_req_insert(input_request_dto dto) {
		int result = iorepo.input_req_insert(dto);
		return result;
	}
	

	//상품 정보 가져오기(select 옵션)
	@Override
	public List<product_dto_lhwtemp> select_pdlist() {
		List<product_dto_lhwtemp> all = iorepo.selectProduct();
		return all;
	}
	
	//입고요청 데이터 등록시 입고요청코드 랜덤생성
	
	//입고요청 데이터 등록시 사용자 정보 가져오기
	
}
