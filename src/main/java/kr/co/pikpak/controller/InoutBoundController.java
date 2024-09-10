package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.service.InoutBoundService;

@Controller
public class InoutBoundController {
	
	PrintWriter pw = null;
	
	@Autowired
	InoutBoundService ioservice;
	
	
	//선택할 상품 리스트 가져오기 -> 입고요청 등록위함
	
	
	
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
