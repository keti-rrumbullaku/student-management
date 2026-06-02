package com.student.service;

import com.student.dto.StudentDTO;
import com.student.mapper.StudentMapper;
import com.student.model.Student;
import com.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new RuntimeException("Studenti me email " + studentDTO.getEmail() + " ekziston tashmë.");
        }
        Student student = studentMapper.toEntity(studentDTO);
        Student saved = studentRepository.save(student);
        return studentMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        return studentMapper.toDTOList(studentRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Studenti me id " + id + " nuk u gjet."));
        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Studenti me id " + id + " nuk u gjet."));
        studentMapper.updateEntityFromDTO(studentDTO, existing);
        Student updated = studentRepository.save(existing);
        return studentMapper.toDTO(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Studenti me id " + id + " nuk u gjet.");
        }
        studentRepository.deleteById(id);
    }
}
