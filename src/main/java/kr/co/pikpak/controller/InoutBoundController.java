package kr.co.pikpak.controller;

import java.io.PrintWriter;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletResponse;
import kr.co.pikpak.dto.input_request_dto;
import kr.co.pikpak.dto.product_dto_lhwtemp;
import kr.co.pikpak.dto.supplier_info_dto_lhwtemp;
import kr.co.pikpak.service.InoutBoundService;

@Controller
public class InoutBoundController {
	
	PrintWriter pw = null;
	
	@Autowired
	InoutBoundService ioservice;
	
	//상품 칮기 페이징 + 검색
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/product_paging")
	@ResponseBody
	public Map<String, Object> product_paging(
			@RequestParam(defaultValue = "", required = false) int page,
			@RequestParam(defaultValue = "", required = false) String pd_nm,
			@RequestParam(defaultValue = "", required = false) String pd_cd){
		//System.out.println(page);
		//System.out.println(pd_nm);
		//System.out.println(pd_cd);
		
		int page_size = 10; //한 페이지당 보여줄 리스트 개수
		int startpg = (page - 1) * page_size;
		
		Map<String, Object> product = new HashMap<>();
		product.put("startpg", startpg); //시작 페이지
		product.put("page_size", page_size); //보여줄 리스트 개수
		product.put("pd_nm", pd_nm);
		product.put("pd_cd", pd_cd);
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<product_dto_lhwtemp> pdlist_part = ioservice.select_product_limit(product);
			Integer pd_total = ioservice.select_product_total(pd_nm, pd_cd);
			
			response.put("pdlist_part", pdlist_part); //페이징 리스트
			response.put("pd_total", pd_total); //총 리스트 개수
			response.put("page_size", page_size); //페이징 사이즈
		}
		catch(Exception e) {	
			System.out.println(e);
		}
		
		
		return response;
	}
	
	
	//매입처 페이징 + 검색
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/company_paging") //여기서는 그냥 그대로 경로 써주면 되는구나 ajax니까
	@ResponseBody
	public Map<String, Object> company_paging(@RequestParam(defaultValue = "", required = false) int page,
			@RequestParam(defaultValue = "", required = false) String comp_nm,
			@RequestParam(defaultValue = "", required = false) String comp_cd) {
		//System.out.println(page);
		//System.out.println(comp_nm);
		//System.out.println(comp_cd);
		
		int page_size = 7; //한 페이지당 보여줄 리스트 개수
		int startpg = (page - 1) * page_size;
		
		Map<String, Object> supplier = new HashMap<>();
		supplier.put("startpg", startpg);
		supplier.put("page_size", page_size);
		supplier.put("comp_nm",comp_nm);
		supplier.put("comp_cd", comp_cd);
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<supplier_info_dto_lhwtemp> splist_part = 
					ioservice.select_supplier_limit(supplier);
			
			Integer sp_total = ioservice.select_supplier_total(comp_nm, comp_cd);
			
			response.put("splist_part", splist_part); //페이징 리스트
			response.put("sp_total", sp_total); //총 리스트 개수
			response.put("page_size", page_size); //페이징 사이즈
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return response;
	}
	
	
	//입고요청 삭제
	@PostMapping("inoutbound/delete_inreqok")
	public String delete_inreqok(ServletResponse res,
			@RequestParam(defaultValue = "", required = false) String each_ck[],
			@RequestParam(defaultValue = "", required = false) String request_idx) {
		res.setContentType("text/html;charset=utf-8");
		String idx_datas[] = request_idx.split(",");
		
		if(idx_datas.length == each_ck.length) {
			
			try {
				this.pw = res.getWriter();				
				int result = ioservice.delete_inreq(request_idx);
				if(result == idx_datas.length) {
					this.pw.print("<script>"
							+ "alert('정상적으로 삭제되었습니다.');"
							+ "location.href = './inboundreq';"
							+ "</script>");
				}
			}
			catch(Exception e) {
				this.pw.print("<script>"
						+ "alert('데이터베이스 문제로 삭제되지 못하였습니다.');"
						+ "location.href = './inboundreq';"
						+ "</script>");
			}
			finally {
				this.pw.close();
			}
		}
		return null;
	}
	
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
	
	//입고요청 리스트 검색
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/inboundreq_search")
	public String inboundreq_search(@RequestBody String data_arr) {
		System.out.println(data_arr);
		return null;
	}
	
	
	//입고요청 이동
	@GetMapping("inoutbound/inboundreq")
	public String inboundreq(Model m) {
		//등록 모달에 상품리스트 출력
		//List<product_dto_lhwtemp> pdlist = ioservice.select_pdlist();
		List<Map<String, Object>> pdlist = ioservice.select_product();
		m.addAttribute("pdlist",pdlist);
		
		//입고요청 리스트 출력
		List<input_request_dto> ir_list = ioservice.select_inreq();
		m.addAttribute("ir_list", ir_list);
		
		/*
		//매입처 모달에 회사 리스트 출력
		List<supplier_info_dto_lhwtemp> splist = ioservice.select_supplier();
		int sp_total = splist.size();
		m.addAttribute("splist",splist);
		m.addAttribute("sp_total", sp_total);
		*/
		
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
