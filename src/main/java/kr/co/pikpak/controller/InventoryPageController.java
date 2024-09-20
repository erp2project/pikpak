package kr.co.pikpak.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.pikpak.dto.WarehouseInspection_dto;
import kr.co.pikpak.dto.WarehouseInventory_dto;
import kr.co.pikpak.service.WarehouseInventoryService;
@Controller
public class InventoryPageController {
	@Autowired
	private WarehouseInventoryService wis;
	
	//재고 현황 페이지 - 재고 리스트 페이지 출력
	@GetMapping("/inventorylist")
	public String loginPage(Model m) {
		//최초 접속 시 재고 리스트 전체 출력
		List<WarehouseInventory_dto> all = wis.getAllinventory();
		
		//검색 옵션 - 회사코드 출력
		List<Map<String, Object>> getAllsupplier_cd = wis.getAllsupplier_cd();
		
		//상품코드 옵션 출력
		Set<String> product_cd = all.stream().map(WarehouseInventory_dto::getProduct_cd).collect(Collectors.toSet());
		//상품명 옵션 출력
		Set<String> product_nm = all.stream().map(WarehouseInventory_dto::getProduct_nm).collect(Collectors.toSet());
		//회사명 옵션 출력
		Set<String> supplier_nm = all.stream().map(WarehouseInventory_dto::getSupplier_nm).collect(Collectors.toSet());
		m.addAttribute("all",all);
		m.addAttribute("product_cd",product_cd);
		m.addAttribute("product_nm",product_nm);
		m.addAttribute("supplier_nm",supplier_nm);
		m.addAttribute("getAllsupplier_cd",getAllsupplier_cd);
		
		return "/Inventory/inventorylist";
	}

	//재고 현황 페이지 - 관리 버튼 팝업창
	@GetMapping("/inventory_popup")
	public String inventory_popup(@RequestParam("wh_warehouse_idx") String wh_warehouse_idx, Model m) {
		System.out.println(wh_warehouse_idx);
		return "/Inventory/inventory_popup";
	}
	
	//창고별 재고 현황 페이지 출력
	@GetMapping("/warehouse_inventory")
	public String warehouse_inventory() {
		return "/Inventory/warehouse_inventory";
	}
	
	//창고 관리 페이지 - 창고 점검 리스트 출력
	@GetMapping("/warehouse_management")
	public String warehouse_management(Model m) {
		List<WarehouseInspection_dto> all = wis.getCheckData();
		Map<String, List<WarehouseInspection_dto>> area =
				all.stream().collect(Collectors.groupingBy(WarehouseInspection_dto::getArea_cd));
		m.addAttribute("area",area);
		return "/Inventory/warehouse_management";
	}
	
	
	//재고 현황 페이지 - 상품 검색 팝업창
	@GetMapping("/product_search") 		
	public String product_search() {
		return "/Inventory/product_search";
	}
	
	//재고 현황 페이지 - 회사 검색 팝업창
	@GetMapping("/corp_search")			
	public String corp_search () {
		return "/Inventory/corp_search";
	}
	
	//재고 창고 위치 관리 페이지
	@GetMapping("/warehouse_location")
	public String warehouse_location(Model m) {
		
		return "/Inventory/warehouse_grid";
	}
}
