package gmc.project.student.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gmc.project.student.models.StudentDto;

public interface StudentService {
	List<StudentDto> addStudentsThroughExcel(MultipartFile file);
	List<StudentDto> getAllStudents();
}
