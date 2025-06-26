  public byte[] exportCustomersToExcel(List<Customer> customers, List<CustomerDetails> customerDetails) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            createCustomerSheet(workbook, customers, headerStyle);
            createCustomerDetailsSheet(workbook, customerDetails, headerStyle);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void createCustomerSheet(Workbook workbook, List<Customer> customers, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("Customers");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Email", "Phone", "Join Date"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
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
        }
        
        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createCustomerDetailsSheet(Workbook workbook, List<CustomerDetails> details, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("Customer Details");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Detail ID", "Customer ID", "Address", "Membership Level", "Last Purchase"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
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
        }
        
        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
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
}
