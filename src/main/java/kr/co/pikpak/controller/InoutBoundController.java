package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.ServletResponse;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.service.InoutBoundService;

@Controller
public class InoutBoundController {
	
	PrintWriter pw = null;
	
	@Autowired
	InoutBoundService ioservice;
	
	
	//입고요청 등록
	@PostMapping("inoutbound/inreq_enrollok")
	public String inreq_enrollok(@ModelAttribute("ir") input_request_dto dto, ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		//System.out.println(dto.getProduct_nm());
		//넘어온 값 : supplier_cd, supplier_nm, product_cd, product_nm, product_qty, add_req, hope_dt
		//안 넘어온 값(따로 처리해야하는 값) : request_cd, operator_id, operator_nm, request_st
		//안 넘어와도 되는 값(쿼리문 처리 or null값): request_idx, request_dt, update_dt
		try {
			this.pw = res.getWriter();
			dto.setRequest_cd("03123123");
			dto.setOperator_id("hong");
			dto.setOperator_nm("홍길동");
			dto.setRequest_st("대기"); //이걸 어디서 한담...default값이 있는데 걍 안되나, 일단 dto에 값이 없으면 오류남
			
			int result = ioservice.input_req_insert(dto);
			
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 등록되었습니다.');"
						+ "location.href='./inboundreq';"
						+ "</script>");
			}
		}
		catch(Exception e) {
			System.out.println(e);
			this.pw.print("<script>"
					+ "alert('데이터베이스 문제로 인해 등록되지 못하였습니다.');"
					+ "location.href='./inboundreq';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		return null;
	}
	
	//입고요청 이동
	@GetMapping("inoutbound/inboundreq")
	public String inboundreq(Model m) {
		//등록 모달에 상품리스트 출력
		List<product_dto_lhwtemp> pdlist = ioservice.select_pdlist();
		m.addAttribute("pdlist",pdlist);
		return null;
	}
	
	//가입고 등록 이동
	@GetMapping("inoutbound/exreceiving")
	public String exreceiving() {
		return null;
	}
	
	//입고 등록 이동
	@GetMapping("inoutbound/recvenroll")
	public String recvenroll() {
		return null;
	}
	
	/*
	// 상품코드/상품명 찾기 팝업
	@GetMapping("/inoutbound/pd_search_popup")
	public String pd_search_popup() {
	    return "inoutbound/pd_search_popup";  // Thymeleaf 템플릿 경로
	}
	
	// 매입처 회사명 찾기 팝업
	@GetMapping("/inoutbound/cp_search_popup")
	public String cp_search_popup() {
	    return "inoutbound/cp_search_popup";
	}
	*/
}
