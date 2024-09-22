package kr.co.pikpak.repo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.pikpak.dto.deliver_return_dto;
import kr.co.pikpak.dto.ex_receiving_dto;
import kr.co.pikpak.dto.ex_receiving_joined_dto;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.order_enroll_dto_lhwtemp;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.dto.supplier_info_dto_lhwtemp;

@Mapper
public interface InoutBoundRepo {
	//서버시간
	String get_time();
	
	//운영자 이름으로 id검색(사용자 조회용)
	List<String> search_operator_nm(String operator_nm);
	
	//운영자 id로 이름 검색(리스트 출력용)
	String search_one_id(String operator_id);
	
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
	
	
	//입고요청 리스트 수정
	int update_inreq(Map<String, Object> inrequest);
	
	//입고요청 리스트 조회
	List<input_request_dto> select_inreq_search(Map<String, Object> data_arr);
	
	//가입고 테이블 select -> 입고등록 페이지
	List<ex_receiving_joined_dto> select_ex_receiving();
	
	//가입고 반송등록
	int insert_deliver_return(deliver_return_dto dto);
	
	//주문현황 확인
	List<order_enroll_dto_lhwtemp> select_order_enroll();
}
