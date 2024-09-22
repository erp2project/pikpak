package kr.co.pikpak.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pikpak.dto.deliver_return_dto;
import kr.co.pikpak.dto.ex_receiving_dto;
import kr.co.pikpak.dto.ex_receiving_joined_dto;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.order_enroll_dto_lhwtemp;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.dto.supplier_info_dto_lhwtemp;
import kr.co.pikpak.repo.InoutBoundRepo;

@Service
public class InoutBoundServiceImpl implements InoutBoundService{
	
	@Autowired
	InoutBoundRepo iorepo;
	
	//출고등록에서 주문현황 보여주기
	@Override
	public List<order_enroll_dto_lhwtemp> select_order_enroll() {
		List<order_enroll_dto_lhwtemp> orderlist = iorepo.select_order_enroll();
		return orderlist;
	}
	
	//가입고 반송
	@Override
	public int insert_deliver_return(deliver_return_dto dto) {
		//고유번호 랜덤 생성
		dto.setD_return_cd(this.make_returncode());
		
		//사용자 세션
		dto.setOperator_id("kang");
		
		//여기서 상태업데이트도 해야해(가입고 테이블 리스트) => 이건 트리거 걸겠음 그냥
		
		int result = iorepo.insert_deliver_return(dto);
		return result;
	}
	
	
	
	//가입고 리스트
	@Override
	public List<ex_receiving_joined_dto> select_ex_receiving() {
		List<ex_receiving_joined_dto> exrecv_list = iorepo.select_ex_receiving();
		return exrecv_list;
	}
	
	
	//입고요청 조회
	@Override
	public List<input_request_dto> select_inreq_search(Map<String, Object> data_arr) {
		if((data_arr.get("start_date") != "") && (data_arr.get("end_date") != "")) {
			String startdt = (String) data_arr.get("start_date");
			startdt += " 00:00:00";
			data_arr.put("start_date", startdt); 
			
			String enddt = (String) data_arr.get("end_date");
			enddt += " 23:59:59";
			data_arr.put("end_date", enddt);
		}
		
		List<input_request_dto> ir_search = iorepo.select_inreq_search(data_arr);
		return ir_search;
	}
	
	//입고요청 수정
	@Override
	public int update_inreq(Map<String, Object> inrequest) {
		int result = iorepo.update_inreq(inrequest);
		return result;
	}
	
	//상품명 페이징 + 검색
	@Override
	public List<product_dto_lhwtemp> select_product_limit(Map<String, Object> product) {
		List<product_dto_lhwtemp> pdlist_part = iorepo.select_product_limit(product);
		return pdlist_part;
	}
	
	//상품명 개수
	@Override
	public Integer select_product_total(String pd_nm, String pd_cd) {
		Integer total = iorepo.select_product_total(pd_nm, pd_cd);
		return total;
	}
	
	
	//매입처 페이징 + 검색
	@Override
	public List<supplier_info_dto_lhwtemp> select_supplier_limit(Map<String, Object> supplier) {
		List<supplier_info_dto_lhwtemp> splist_part = iorepo.select_supplier_limit(supplier);
		return splist_part;
	}
	/*
	//매입처 페이징
	@Override
	public List<supplier_info_dto_lhwtemp> select_supplier_limit(Integer startpg, Integer page_size) {
		List<supplier_info_dto_lhwtemp> sp_list_part = iorepo.select_supplier_limit(startpg, page_size);
		return sp_list_part;
	}
	*/
	//매입처 개수
	@Override
	public Integer select_supplier_total(String comp_nm, String comp_cd) {
		Integer total = iorepo.select_supplier_total(comp_nm, comp_cd);
		return total;
	}
	
	//입고요청 데이터 삭제
	@Override
	public int delete_inreq(String request_idx) {
		int result = iorepo.delete_inreq(request_idx);
		return result;
	}
	
	//입고요청 데이터 리스트
	@Override
	public List<input_request_dto> select_inreq() {
		List<input_request_dto> ir_list = iorepo.select_inreq();
		return ir_list;
	}
	
	//입고요청 데이터 등록
	@Override
	public int input_req_insert(input_request_dto dto) {
		dto.setRequest_cd(this.make_inreqcode());
		dto.setOperator_id("kang1234");
		
		dto.setRequest_st("대기"); //dto에 값이 없으면 오류남
		int result = iorepo.input_req_insert(dto);
		return result;
	}
	

	//상품 정보 가져오기(select 옵션)
	@Override
	public List<Map<String, Object>> select_product() {
		List<Map<String, Object>> select_pd = iorepo.select_product();
		return select_pd;
	}
	
	//데이터베이스 서버시간 불러오기
	@Override
	public String get_time() {
		String time = iorepo.get_time();
		return time;
	}
	
	//운영자 이름 검색하기 => 조회용
	@Override
	public List<String> search_operator_nm(String operator_nm) {
		List<String> op_id = iorepo.search_operator_nm(operator_nm);
		return op_id;
	}
	
	//운영자 이름 검색하기 => 출력용
	@Override
	public String search_one_id(String operator_id) {
		String op_name = iorepo.search_one_id(operator_id);
		return op_name;
	}
	
	//입고요청 데이터 등록시 입고요청코드 랜덤생성
	public String make_inreqcode() {
		//서버 시간
		String server_time = this.get_time();
	
		//랜덤숫자 4개 생성
		int w = 0;
		String randnum = "";
		
		while(w < 4) {
			int pc = (int)(Math.ceil(Math.random()*9));
			randnum += pc;
			w++;
		}
		
		String code = "IR "+ server_time + "-" + randnum;
		
		return code;
	}
	
	//반송 데이터 등록시 입고요청코드 랜덤생성
		public String make_returncode() {
			//서버 시간
			String server_time = this.get_time();
		
			//랜덤숫자 4개 생성
			int w = 0;
			String randnum = "";
			
			while(w < 4) {
				int pc = (int)(Math.ceil(Math.random()*9));
				randnum += pc;
				w++;
			}
			
			String code = "DR "+ server_time + "-" + randnum;
			
			return code;
		}
	
	
	
}
