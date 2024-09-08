package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InoutBoundController {
	//입고요청 이동
	@GetMapping("inoutbound/inboundreq")
	public String inboundreq() {
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
