package com.student.mapper;

import com.student.dto.StudentDTO;
import com.student.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toDTO(Student student);

    Student toEntity(StudentDTO studentDTO);

    List<StudentDTO> toDTOList(List<Student> students);

    void updateEntityFromDTO(StudentDTO dto, @MappingTarget Student student);
}
