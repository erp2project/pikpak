package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletResponse;
import kr.co.pikpak.dto.deliver_enroll_dto;
import kr.co.pikpak.dto.deliver_return_joined_dto;
import kr.co.pikpak.dto.ex_receiving_dto;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.input_request_state_dto;
import kr.co.pikpak.service.DeliveryService;

@Controller
public class DeliveryController {
	
	PrintWriter pw = null;
	
	@Autowired
	DeliveryService delservice;
	
	
	//납품등록에서 배송 버튼 클릭 시 가입고 등록
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/insert_exreceiving")
	public String insert_exreceiving(ServletResponse res,
			@RequestBody ex_receiving_dto dto) {
		//넘어오는 값 : request_cd, deliver_cd, supplier_cd, product_cd, exreceiving_qty, exreceiving_size, exreceiving_area, make_dt
		//만들어야하는 값 : exreceiving_cd, exreceiving_st, exreceiving_id , operator_id, operator_nm
		//DB에서 들어가는 값 or null 값 : exreceiving_idx, exreceiving_dt, update_dt, update_id, update_nm 
		try {
			this.pw = res.getWriter();
			int result = delservice.insert_ex_receiving(dto);
			
			if(result > 0) {
				this.pw.print("ok");
				
				List<String> request_code = delservice.select_delivered_finish();
				if(request_code.contains(dto.getRequest_cd())) {
					String request_cd = dto.getRequest_cd();
					System.out.println(request_cd);
					int update = delservice.update_finished_inreq(request_cd);
					//프론트로 넘겨서 사용자에게 해당 관련 요청사항이 완료되었다고 알려줄지 고민
				}
			}
			
		}
		catch(Exception e) {
			this.pw.print("error");
			e.printStackTrace();
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	//입고요청 거절 => 세션가지고 와
	@PostMapping("/reject_deliverylist")
	public String reject_deliverylist(ServletResponse res, 
			@RequestParam(defaultValue = "", required = false) String request_idx) {
		try {
			this.pw = res.getWriter();
			int result = delservice.update_inreq_reject(request_idx);
			if(result > 0) {
				this.pw.print("ok");
			}
		} 
		catch (Exception e) {
			this.pw.print("error");
		}
		finally {
			this.pw.close();
		}
		return null;
	}
	
	
	//납품등록 삭제 => 세션가지고 와
	@PostMapping("/delivery/delete_deliveryok")
	public String delete_deliveryok(ServletResponse res,
			@RequestParam(defaultValue = "", required = false) String del_each_ck[],
			@RequestParam(defaultValue = "", required = false) String deliver_idx) {
		res.setContentType("text/html;charset=utf-8");
		try {
			this.pw = res.getWriter();
			//System.out.println(del_each_ck.length);
			//System.out.println(deliver_idx);
			String idx_data[] = deliver_idx.split(",");
			if(idx_data.length == del_each_ck.length) {
				int result = delservice.delete_deliver_enroll(deliver_idx);
				
				if(result > 0) {
					this.pw.print("<script>"
							+ "alert('정상적으로 삭제되었습니다.');"
							+ "location.href='./deliveryenroll';"
							+ "</script>");
				}
			}	
		}
		catch(Exception e) {
			System.out.println(e);
			this.pw.print("<script>"
					+ "alert('데이터베이스 문제로 삭제되지 못하였습니다.');"
					+ "location.href='./deliveryenroll';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		return null;
	}
	
	
	
	//납품등록
	@PostMapping("/delivery/delivery_enrollok")
	public String delivery_enrollok(ServletResponse res, @ModelAttribute("deliver") deliver_enroll_dto dto) {
		//프론트에서 넘어오는 값 : request_cd, prodcut_cd, product_nm, deliver_qty, make_dt, expect_dt, deliver_size, deliver_area
		//여기서 넣어야하는 값 : supplier_cd, deliver_cd, deliver_st, operator_nm, operator_id
		//쿼리문에서 넣는 값 or null 값 : deliver_idx, deliver_dt, update_id, update_nm, update_dt
		res.setContentType("text/html;charset=utf-8");
		
		try {
			this.pw = res.getWriter();
			int result = delservice.insert_deliver_enroll(dto);
			
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 등록되었습니다');"
						+ "location.href='./deliveryenroll';"
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
	
	//반송현황
	@GetMapping("delivery/returnstate")
	public String returnstate(Model m) {
		//세션에서 회사 정보 가져왔다고 가정
		String supplier_cd = "066570";
		
		try {
			List<deliver_return_joined_dto> d_return = delservice.select_return_joined(supplier_cd);
			m.addAttribute("d_return",d_return);
		}
		catch(Exception e) {
			System.out.println(e);
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
