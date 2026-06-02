package com.student.service;

import com.student.dto.StudentDTO;
import com.student.mapper.StudentMapper;
import com.student.model.Student;
import com.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(1L)
                .firstName("Arta")
                .lastName("Hoxha")
                .email("arta.hoxha@student.edu.al")
                .major("Informatikë")
                .enrollmentYear(2022)
                .build();

        studentDTO = StudentDTO.builder()
                .id(1L)
                .firstName("Arta")
                .lastName("Hoxha")
                .email("arta.hoxha@student.edu.al")
                .major("Informatikë")
                .enrollmentYear(2022)
                .build();
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() {
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(false);
        when(studentMapper.toEntity(studentDTO)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        StudentDTO result = studentService.createStudent(studentDTO);

        assertNotNull(result);
        assertEquals("Arta", result.getFirstName());
        assertEquals("arta.hoxha@student.edu.al", result.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void createStudent_WhenEmailExists_ShouldThrowException() {
        when(studentRepository.existsByEmail(studentDTO.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> studentService.createStudent(studentDTO));
        verify(studentRepository, never()).save(any());
    }

    @Test
    void getAllStudents_ShouldReturnList() {
        List<Student> students = Arrays.asList(student);
        List<StudentDTO> dtos = Arrays.asList(studentDTO);

        when(studentRepository.findAll()).thenReturn(students);
        when(studentMapper.toDTOList(students)).thenReturn(dtos);

        List<StudentDTO> result = studentService.getAllStudents();

        assertEquals(1, result.size());
        assertEquals("Arta", result.get(0).getFirstName());
    }

    @Test
    void getStudentById_WhenExists_ShouldReturnStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        StudentDTO result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getStudentById_WhenNotExists_ShouldThrowException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.getStudentById(99L));
    }

    @Test
    void deleteStudent_WhenExists_ShouldDelete() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        assertDoesNotThrow(() -> studentService.deleteStudent(1L));
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_WhenNotExists_ShouldThrowException() {
        when(studentRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> studentService.deleteStudent(99L));
        verify(studentRepository, never()).deleteById(any());
    }
}
