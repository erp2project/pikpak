package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class InventoryPageController {
	@GetMapping("/inventorylist")
	public String loginPage() {
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
