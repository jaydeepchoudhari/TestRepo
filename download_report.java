import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class CustomerReportController {

    @GetMapping("/download-customers-excel")
    public ResponseEntity<byte[]> downloadCustomersExcel() throws IOException {
        // Get your data (replace with your actual service calls)
        List<Customer> customers = getCustomers(); // Your method to get customers
        List<CustomerDetails> customerDetails = getCustomerDetails(); // Your method to get details
        
        // Create Excel workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            // Sheet 1: Customers
            Sheet customerSheet = workbook.createSheet("Customers");
            createCustomerSheet(customerSheet, customers, headerStyle);
            
            // Sheet 2: Customer Details
            Sheet detailsSheet = workbook.createSheet("Customer Details");
            createCustomerDetailsSheet(detailsSheet, customerDetails, headerStyle);
            
            // Write workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            
            // Prepare response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "customers_report.xlsx");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        }
    }
    
    private void createCustomerSheet(Sheet sheet, List<Customer> customers, CellStyle headerStyle) {
        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] customerHeaders = {"ID", "Name", "Email", "Phone", "Join Date"}; // Adjust based on your Customer fields
        
        for (int i = 0; i < customerHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(customerHeaders[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Create data rows
        int rowNum = 1;
        for (Customer customer : customers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(customer.getId());
            row.createCell(1).setCellValue(customer.getName());
            row.createCell(2).setCellValue(customer.getEmail());
            row.createCell(3).setCellValue(customer.getPhone());
            row.createCell(4).setCellValue(customer.getJoinDate().toString());
            // Add more fields as needed
        }
        
        // Auto-size columns
        for (int i = 0; i < customerHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private void createCustomerDetailsSheet(Sheet sheet, List<CustomerDetails> details, CellStyle headerStyle) {
        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] detailHeaders = {"Detail ID", "Customer ID", "Address", "Membership Level", "Last Purchase"}; // Adjust based on your CustomerDetails fields
        
        for (int i = 0; i < detailHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(detailHeaders[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Create data rows
        int rowNum = 1;
        for (CustomerDetails detail : details) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(detail.getDetailId());
            row.createCell(1).setCellValue(detail.getCustomerId());
            row.createCell(2).setCellValue(detail.getAddress());
            row.createCell(3).setCellValue(detail.getMembershipLevel());
            row.createCell(4).setCellValue(detail.getLastPurchase().toString());
            // Add more fields as needed
        }
        
        // Auto-size columns
        for (int i = 0; i < detailHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
    
    // Replace these with your actual service methods
    private List<Customer> getCustomers() {
        // Your implementation to get customers
        return customerService.getAllCustomers();
    }
    
    private List<CustomerDetails> getCustomerDetails() {
        // Your implementation to get customer details
        return customerDetailsService.getAllCustomerDetails();
    }

    private List<Customer> getCustomersByIds(short[] customerIds) {
        List<Customer> allCustomers = customerService.getAllCustomers();
        return allCustomers.stream()
                .filter(c -> containsId(customerIds, c.getId()))
                .collect(Collectors.toList());
    }
    
    private List<CustomerDetails> getCustomerDetailsByIds(short[] customerIds) {
        List<CustomerDetails> allDetails = customerDetailsService.getAllCustomerDetails();
        return allDetails.stream()
                .filter(d -> containsId(customerIds, d.getCustomerId()))
                .collect(Collectors.toList());
    }
    
    private boolean containsId(short[] ids, short targetId) {
        for (short id : ids) {
            if (id == targetId) {
                return true;
            }
        }
        return false;
    }
}
