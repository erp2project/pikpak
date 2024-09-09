package kr.co.pikpak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.pikpak.dto.WarehouseInventory_dto;
import kr.co.pikpak.service.WarehouseInventoryService;
@Controller
public class InventoryPageController {
	@Autowired
	private WarehouseInventoryService wis;
	
	//재고 리스트 출력
	@GetMapping("/inventorylist")
	public String loginPage(Model m) {
		List<WarehouseInventory_dto> all = wis.getAllinventory();
		m.addAttribute("all",all);
		return "/Inventory/inventorylist";
	}
	@GetMapping("/warehouse_inventory")
	public String warehouse_inventory() {
		return "/Inventory/warehouse_inventory";
	}
	@GetMapping("/warehouse_management")
	public String warehouse_management() {
		return "/Inventory/warehouse_management";
	}
	@GetMapping("/inventory_popup")
	public String inventory_popup() {
		return "/Inventory/inventory_popup";
	}
	@GetMapping("/product_search") 		//재고 현황 페이지 - 상품 검색 팝업창
	public String product_search() {
		return "/Inventory/product_search";
	}
	@GetMapping("/corp_search")			//재고 현황 페이지 - 회사 검색 팝업창
	public String corp_search () {
		return "/Inventory/corp_search";
	}
}
