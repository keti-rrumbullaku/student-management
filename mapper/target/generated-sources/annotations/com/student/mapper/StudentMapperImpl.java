package com.student.mapper;

import com.student.dto.StudentDTO;
import com.student.model.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T22:37:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDTO toDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO.StudentDTOBuilder studentDTO = StudentDTO.builder();

        studentDTO.id( student.getId() );
        studentDTO.firstName( student.getFirstName() );
        studentDTO.lastName( student.getLastName() );
        studentDTO.email( student.getEmail() );
        studentDTO.phoneNumber( student.getPhoneNumber() );
        studentDTO.major( student.getMajor() );
        studentDTO.enrollmentYear( student.getEnrollmentYear() );

        return studentDTO.build();
    }

    @Override
    public Student toEntity(StudentDTO studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        Student.StudentBuilder student = Student.builder();

        student.id( studentDTO.getId() );
        student.firstName( studentDTO.getFirstName() );
        student.lastName( studentDTO.getLastName() );
        student.email( studentDTO.getEmail() );
        student.phoneNumber( studentDTO.getPhoneNumber() );
        student.major( studentDTO.getMajor() );
        student.enrollmentYear( studentDTO.getEnrollmentYear() );

        return student.build();
    }

    @Override
    public List<StudentDTO> toDTOList(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<StudentDTO> list = new ArrayList<StudentDTO>( students.size() );
        for ( Student student : students ) {
            list.add( toDTO( student ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDTO(StudentDTO dto, Student student) {
        if ( dto == null ) {
            return;
        }

        student.setId( dto.getId() );
        student.setFirstName( dto.getFirstName() );
        student.setLastName( dto.getLastName() );
        student.setEmail( dto.getEmail() );
        student.setPhoneNumber( dto.getPhoneNumber() );
        student.setMajor( dto.getMajor() );
        student.setEnrollmentYear( dto.getEnrollmentYear() );
    }
}
