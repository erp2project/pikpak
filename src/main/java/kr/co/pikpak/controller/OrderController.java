package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletResponse;
import kr.co.pikpak.dto.order_dto;
import kr.co.pikpak.dto.order_list_dto;
import kr.co.pikpak.dto.product_cd_dto;
import kr.co.pikpak.service.order_service;

@Controller
public class OrderController {
	
	@Autowired
	private order_service order_service;
	
	PrintWriter pw = null;
	
	//주문 승인 리스트 페이지
	@GetMapping("/order_aplist")
	public String order_aplist(Model m) {
		List<order_list_dto> order_alllist = order_service.order_list_all();
		List<order_list_dto> product_list = order_service.product_search();
		m.addAttribute("order_aplist",order_alllist);
		m.addAttribute("product_list",product_list);
		
		return "/order/order_aplist";
	}
	
	//주문 리스트 페이지 - 아이디 값에서 회사명 받아오기
	@GetMapping("/order_list")
	public String order_list(Model m) {
		String user_company = "PikPak";
		List<order_list_dto> order_cklist = order_service.order_list(user_company);
		List<order_list_dto> product_list = order_service.product_search();
		m.addAttribute("order_cklist",order_cklist);
		m.addAttribute("product_list",product_list);
		
		return "/order/order_list";
	}
	
	//주문 승인
	@PostMapping("/order_approval_change")
	public String order_approval_change(@ModelAttribute order_dto order_dto,
			ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		int result = order_service.order_approval(order_dto);
		try {
			this.pw = res.getWriter();
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('주문 승인 정보가 변경되었습니다.');"
						+ "location='/order_aplist';"
						+ "</script>");
			}
			else {
				this.pw.print("<script>"
						+ "alert('오류로 인하여 주문 승인 정보 변경을 실패하였습니다.');"
						+ "location='/order_aplist';"
						+ "</script>");
			}
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('오류로 인하여 주문 승인 정보 변경을 실패하였습니다.');"
					+ "location='/order_aplist';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}
	
	//주문 삭제
	@PostMapping("/order_delete")
	public String order_delete(@RequestParam(value="order_idx") int order_idx,
			ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		int result = order_service.order_delete(order_idx);
		try {
			this.pw = res.getWriter();
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 주문이 취소되었습니다.');"
						+ "location='/order_list';"
						+ "</script>");
			}
			else {
				this.pw.print("<script>"
						+ "alert('오류로 인하여 주문 취소를 실패하였습니다.');"
						+ "location='/order_list';"
						+ "</script>");
			}
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('오류로 인하여 주문 취소를 실패하였습니다.');"
					+ "location='/order_list';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}
	
	//주문 수정
	@PostMapping("/order_modify")
	public String order_modify(@ModelAttribute kr.co.pikpak.dto.order_dto order_dto,
			ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		int result = order_service.order_modify(order_dto);
		try {
			this.pw = res.getWriter();
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('주문 수정이 완료되었습니다.');"
						+ "location='/order_list';"
						+ "</script>");
			}
			else {
				this.pw.print("<script>"
						+ "alert('수정된 부분이 존재하지 않습니다.');"
						+ "location='/order_list';"
						+ "</script>");
			}
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('오류로 인하여 주문 수정을 실패하였습니다.');"
					+ "location='/order_list';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}
	
	//상품 코드 조회
	@PostMapping("/product_cd_searchck")
	@ResponseBody
	public String product_cd_searchck(@RequestParam(value="product_cd")	String product_cd) {
		String result = "";
		List<product_cd_dto> product_list = order_service.product_cd_search(product_cd);
		
		if(product_list.size() > 0) {
			StringBuilder pl = new StringBuilder();
			for(product_cd_dto product : product_list) {
				pl.append(product.getProduct_nm()).append(",");
				pl.append(product.getSupplier_nm()).append(",");
				pl.append(product.getProduct_sz()).append(",");
				pl.append(product.getProduct_wt()).append(",");
				pl.append(product.getPurchase_pr());
			}
			result = pl.toString();
		}
		else if(product_list.size() == 0) {
			result = "no";			
		}
		
		return result;
	}
	
	//주문 등록
	@PostMapping("/order_enroll")
	public String order_enroll(@ModelAttribute kr.co.pikpak.dto.order_dto order_dto,
			ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		//코드 생성
		String for_order_cd = "OD-";
		LocalDate ymd = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
		String formattedDate = ymd.format(formatter);
		for(int f=0; f<4; f++) {
			int ran = (int)(Math.random()*10);
			formattedDate += ran;
		}
		String order_cd = for_order_cd + formattedDate;
		order_dto.setOrder_cd(order_cd);
		
		int result = order_service.order_enroll(order_dto);
		try {
			this.pw = res.getWriter();
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('주문 등록이 완료되었습니다.');"
						+ "location='/order_list';"
						+ "</script>");
			}
			else {
				this.pw.print("<script>"
						+ "alert('오류로 인하여 주문 등록을 실패하였습니다.');"
						+ "location='/order_list';"
						+ "</script>");
			}
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('오류로 인하여 주문 등록을 실패하였습니다.');"
					+ "location='/order_list';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}

}
