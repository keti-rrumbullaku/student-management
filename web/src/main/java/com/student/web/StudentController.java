package com.student.web;

import com.student.dto.StudentDTO;
import com.student.excel.ExcelExportService;
import com.student.model.Student;
import com.student.repository.StudentRepository;
import com.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;
    private final ExcelExportService excelExportService;
    private final StudentRepository studentRepository;

    // CREATE - Shto student
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO created = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ ALL - Shfaq listën
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // READ ONE - Shfaq studentin me id
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // UPDATE - Edito studentin
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,
                                                     @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    // DELETE - Fshi studentin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // EXCEL EXPORT - Eksporto listën në Excel
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() {
        List<Student> students = studentRepository.findAll();
        byte[] excelData = excelExportService.exportStudentsToExcel(students);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "studentet.xlsx");
        headers.setContentLength(excelData.length);

        return ResponseEntity.ok().headers(headers).body(excelData);
    }

    // HEALTH CHECK
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Student Management System - Running OK");
    }
}
