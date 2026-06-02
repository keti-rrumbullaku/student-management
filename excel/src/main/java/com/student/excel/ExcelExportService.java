package com.student.excel;

import com.student.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ExcelExportService {

    public byte[] exportStudentsToExcel(List<Student> students) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Studentët");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Emri", "Mbiemri", "Email", "Telefoni", "Dega", "Viti i Regjistrimit"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            // Data rows
            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(student.getId() != null ? student.getId() : 0);
                row.createCell(1).setCellValue(student.getFirstName());
                row.createCell(2).setCellValue(student.getLastName());
                row.createCell(3).setCellValue(student.getEmail());
                row.createCell(4).setCellValue(student.getPhoneNumber() != null ? student.getPhoneNumber() : "");
                row.createCell(5).setCellValue(student.getMajor() != null ? student.getMajor() : "");
                row.createCell(6).setCellValue(student.getEnrollmentYear() != null ? student.getEnrollmentYear() : 0);
            }

            // Auto size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            log.error("Gabim gjatë gjenerimit të Excel", e);
            throw new RuntimeException("Nuk mund të gjenerohet file Excel", e);
        }
    }
}
