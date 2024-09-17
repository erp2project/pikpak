package kr.co.pikpak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import kr.co.pikpak.dto.deliver_enroll_dto;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.input_request_state_dto;
import kr.co.pikpak.repo.DeliveryRepo;

@Service
public class DeliveryServiceImpl implements DeliveryService{
	
	@Autowired
	DeliveryRepo delrepo;
	
	//납품등록 현황
	@Override
	public List<deliver_enroll_dto> select_deliver_enroll() {
		List<deliver_enroll_dto> deliver_list = delrepo.select_deliver_enroll();
		return deliver_list;
	}
	
	
	//납품등록
	@Override
	public int insert_deliver_enroll(deliver_enroll_dto dto) {
		String supplier_cd = "066570";
		dto.setSupplier_cd(supplier_cd); //업체코드
		
		//납품요청코드 랜덤생성
		String deliver_cd = "3123193";
		dto.setDeliver_cd(deliver_cd);
		
		dto.setDeliver_st("대기");
		
		//세션에서 현재 회원 정보를 가져왔다고 가정
		String deliver_nm = "강감찬";
		dto.setDeliver_nm(deliver_nm);
	
		
		int result = delrepo.insert_deliver_enroll(dto);
		return result;
	}
	
	//현재 입고요청된 리스트 특정 회사에 대해서 가져오기
	@Override
	public List<input_request_state_dto> select_inreq_deliv(String supplier_cd) {
		List<input_request_state_dto> ir_list = delrepo.select_inreq_deliv(supplier_cd);
		return ir_list;
	}

}
