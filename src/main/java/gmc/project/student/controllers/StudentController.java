package gmc.project.student.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gmc.project.student.models.StudentDto;
import gmc.project.student.services.ExcelAssist;
import gmc.project.student.services.StudentService;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping
	public ResponseEntity<List<StudentDto>> addStudent(@RequestParam("file") MultipartFile file) {

		List<StudentDto> returnValue = null;

		if (ExcelAssist.hasExcelFormat(file))
			returnValue = studentService.addStudentsThroughExcel(file);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@GetMapping
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		List<StudentDto> returnValue = studentService.getAllStudents();

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}

	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=students_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<StudentDto> listStudents = studentService.getAllStudents();

		ExcelAssist excelAssist = new ExcelAssist(listStudents);

		excelAssist.export(response);
	}
}
