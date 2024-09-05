package kr.co.pikpak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class InventoryPageController {
	@GetMapping("/inventory")
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
}
