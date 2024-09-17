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
import kr.co.pikpak.dto.deliver_enroll_dto;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.input_request_state_dto;
import kr.co.pikpak.service.DeliveryService;

@Controller
public class DeliveryController {
	
	PrintWriter pw = null;
	
	@Autowired
	DeliveryService delservice;
	
	//납품등록
	@PostMapping("/delivery/delivery_enrollok")
	public String delivery_enrollok(ServletResponse res, @ModelAttribute("deliver") deliver_enroll_dto dto) {
		//프론트에서 넘어오는 값 : request_cd, prodcut_cd, product_nm, deliver_qty, make_dt, expect_dt, deliver_size, deliver_area
		//여기서 넣어야하는 값 : supplier_cd, deliver_cd, deliver_st, deliver_nm
		//쿼리문에서 넣는 값 or null 값 : deliver_idx, deliver_dt, update_nm, update_dt
		res.setContentType("text/html;charset=utf-8");
		
		try {
			this.pw = res.getWriter();
			int result = delservice.insert_deliver_enroll(dto);
			
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 등록되었습니다');"
						+ "location.href='./inreqstate';"
						+ "</script>");
			}
			
		}
		catch(Exception e) {
			System.out.println(dto.getSupplier_cd());
			System.out.println(e);
			this.pw.print("<script>"
					+ "alert('데이터베이스 문제로 등록되지 못하였습니다');"
					+ "location.href='./inreqstate';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	
	//입고요청현황
	@GetMapping("/delivery/inreqstate")
	public String inreqstate(Model m) {
		//세션에서 회사정보를 가져왔다고 가정 -> 지금은 supplier_cd 사용
		//String supplier_nm = "LG전자";
		String supplier_cd = "066570";
		try {
			List<input_request_state_dto> ir_list = delservice.select_inreq_deliv(supplier_cd);
			//System.out.println(ir_list.size());
			m.addAttribute("ir_list", ir_list);
		}	
		catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	//납품 등록
	@GetMapping("/delivery/deliveryenroll")
	public String deliveryenroll(Model m) {
		try {
			List<deliver_enroll_dto> deliver_list = delservice.select_deliver_enroll();
			//System.out.println(deliver_list.get(0).getProduct_nm());
			m.addAttribute("deliver_list",deliver_list);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
}
