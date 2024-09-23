package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletResponse;
import kr.co.pikpak.dto.outgoing_cd_dto;
import kr.co.pikpak.dto.return_dto;
import kr.co.pikpak.dto.return_list_dto;

@Controller
public class ReturnController {
	
	@Autowired
	private kr.co.pikpak.service.return_service return_service;
	
	PrintWriter pw = null;
	
	@GetMapping("/return")
	public String return_rq() {
		return "/return/return";
	}
	
	//반품 상세 리스트 - 임시(아이디 값을 통해 회사명 받아온 후 동일 회사만)
	@GetMapping("/return_check")
	public String return_check(Model m) {
		String user_company = "PikPak";
		List<return_list_dto> return_cklist = return_service.return_list(user_company);
		m.addAttribute("return_cklist",return_cklist);
		
		return "/return/return_check";
	}
	
	//반품 승인 리스트 - 임시(전부 출력토록)
	@GetMapping("/return_approval")
	public String return_approval(Model m) {
		String user_company = "PikPak";
		List<return_list_dto> return_cklist = return_service.return_list(user_company);
		m.addAttribute("return_cklist",return_cklist);
		
		return "/return/return_approval";
	}
	
	//반품 승인
	@PostMapping("/return_approval_change")
	public String return_approval_change(@ModelAttribute return_dto retrun_dto,
			ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		int result = 0;
		int type = 0;
		
		//대기 상태
		if(retrun_dto.getReturn_st().equals("승인") || retrun_dto.getReturn_st().equals("거절")) {
			type = 1;
			result = return_service.return_approval(retrun_dto, type);
		}
		//승인 상태
		else if(retrun_dto.getReturn_st().equals("완료")){
			type = 2;
			result = return_service.return_approval(retrun_dto, type);
		}
		try {
			this.pw = res.getWriter();
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('반품 승인 정보가 변경되었습니다.');"
						+ "location='/return_approval';"
						+ "</script>");
			}
			else {
				this.pw.print("<script>"
						+ "alert('오류로 인하여 반품 승인 정보 변경을 실패하였습니다.');"
						+ "location='/return_approval';"
						+ "</script>");
			}
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('오류로 인하여 반품 승인 정보 변경을 실패하였습니다.');"
					+ "location='/return_approval';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}
	
	//반품 삭제
	@PostMapping("/return_delete")
	public String return_delete(@RequestParam(value="return_idx") int return_idx,
			ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		int result = return_service.return_delete(return_idx);
		try {
			this.pw = res.getWriter();
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 반품 신청이 취소되었습니다.');"
						+ "location='/return_check';"
						+ "</script>");
			}
			else {
				this.pw.print("<script>"
						+ "alert('오류로 인하여 반품 신청 취소를 실패하였습니다.');"
						+ "location='/return_check';"
						+ "</script>");
			}
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('오류로 인하여 반품 신청 취소를 실패하였습니다.');"
					+ "location='/return_check';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		return null;
	}
	
	
	//출구 코드 조회
	@PostMapping("/outgoing_cd_searchck")
	@ResponseBody
	public String outgoing_cd_searchck(@RequestParam(value="outgoing_cd", required=false)
	String outgoing_cd) {
		String result = "";
		List<outgoing_cd_dto> outgoing_list = return_service.outgoing_cd_search(outgoing_cd);
		
		if(outgoing_list.size() > 0) {
			StringBuilder ol = new StringBuilder();
			for(outgoing_cd_dto outgoing : outgoing_list) {
				ol.append(outgoing.getProduct_cd()).append(",");
				ol.append(outgoing.getProduct_nm()).append(",");
				ol.append(outgoing.getSupplier_nm()).append(",");
				ol.append(outgoing.getOutgoing_dt()).append(",");
				ol.append(outgoing.getProduct_qty()).append(",");
				ol.append(outgoing.getPurchase_pr());
			}
			result = ol.toString();
		}
		else if(outgoing_list.size() == 0) {
			result = "no";			
		}
		
		return result;
	}
	
	//반품등록
	@PostMapping("/return_enroll")
	public String return_enroll(@ModelAttribute kr.co.pikpak.dto.return_dto return_dto,
			ServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		String return_cd = "";
		for(int f=0; f<8; f++) {
			int ran = (int)(Math.random()*10);
			return_cd += ran;
		}
		return_dto.setReturn_cd(return_cd);
		int result = return_service.return_enroll(return_dto);
		try {
			this.pw = res.getWriter();
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('반품 등록이 완료되었습니다.');"
						+ "location='/return';"
						+ "</script>");
			}
			else {
				this.pw.print("<script>"
						+ "alert('오류로 인하여 반품 등록을 실패하였습니다.');"
						+ "location='/return';"
						+ "</script>");
			}
		}catch(Exception e) {
			this.pw.print("<script>"
					+ "alert('오류로 인하여 반품 등록을 실패하였습니다.');"
					+ "location='/return';"
					+ "</script>");
		}
		finally {
			this.pw.close();
		}
		
		
		return null;
	}
	
}
