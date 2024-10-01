package kr.co.pikpak.controller;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class InventoryExcel {
    @GetMapping("/exportExcel")
    public void exportToExcel(HttpServletResponse response) throws IOException, java.io.IOException {
        // 엑셀 파일 응답 헤더 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=inventory.xlsx");

        // 엑셀 워크북 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("재고 목록");

        // 헤더 행 생성
        Row headerRow = sheet.createRow(0);
        String[] headers = {"상품 코드", "상품명", "수량", "위치 코드"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 샘플 데이터 (실제 데이터는 DB 조회 후 처리)
        List<Inventory> inventoryList = getInventoryList();  // DB에서 가져온 재고 리스트
        int rowNum = 1;
        for (Inventory inventory : inventoryList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(inventory.getProductCode());
            row.createCell(1).setCellValue(inventory.getProductName());
            row.createCell(2).setCellValue(inventory.getQuantity());
            row.createCell(3).setCellValue(inventory.getLocationCode());
        }

        // 엑셀 파일을 응답으로 전송
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // 샘플 데이터 (DB 조회 로직을 여기에 추가)
    private List<Inventory> getInventoryList() {
        // DB에서 실제 데이터를 조회하는 로직 추가
        return List.of(
            new Inventory("P001", "상품1", 100, "A01"),
            new Inventory("P002", "상품2", 200, "B01"),
            new Inventory("P003", "상품3", 150, "C01")
        );
    }
}

class Inventory {
    private String productCode;
    private String productName;
    private int quantity;
    private String locationCode;

    public Inventory(String productCode, String productName, int quantity, String locationCode) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.locationCode = locationCode;
    }

    // Getter 메서드들
    public String getProductCode() { return productCode; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getLocationCode() { return locationCode; }
}
