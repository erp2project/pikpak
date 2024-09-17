package kr.co.pikpak.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.deliver_enroll_dto;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.input_request_state_dto;

@Mapper
public interface DeliveryRepo {
	//입고요청 현황
	List<input_request_state_dto> select_inreq_deliv(String supplier_cd);
	
	//납품등록
	int insert_deliver_enroll(deliver_enroll_dto dto);
	
	//납품등록 현황
	List<deliver_enroll_dto> select_deliver_enroll();
}
