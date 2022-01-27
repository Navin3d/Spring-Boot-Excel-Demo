package gmc.project.student.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gmc.project.student.daos.StudentDao;
import gmc.project.student.models.MailingModel;
import gmc.project.student.models.StudentDto;
import gmc.project.student.models.StudentEntity;
import gmc.project.student.services.ExcelAssist;
import gmc.project.student.services.MailingService;
import gmc.project.student.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private MailingService mailingService;

	@Override
	public List<StudentDto> addStudentsThroughExcel(MultipartFile file) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<StudentEntity> savedData = null;

		try {

			List<StudentEntity> students = ExcelAssist.excelToTutorials(file.getInputStream());

			savedData = studentDao.saveAll(students);

			students.stream().iterator().forEachRemaining(student -> {
				MailingModel mail = new MailingModel();
				mail.setTo(student.getEmail());
				mail.setSubject("Thanks For SignIn");
				mail.setBody("Ignore this frends...");

				mailingService.sendMail(mail);
			});

		} catch (IOException e) {

			throw new RuntimeException("fail to store excel data: " + e.getMessage());

		}

		List<StudentDto> returnValue = new ArrayList<>();

		savedData.stream().iterator().forEachRemaining(student -> {
			returnValue.add(modelMapper.map(student, StudentDto.class));
		});

		return returnValue;

	}

	@Override
	public List<StudentDto> getAllStudents() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<StudentEntity> savedData = studentDao.findAll();

		List<StudentDto> returnValue = new ArrayList<>();

		savedData.stream().iterator().forEachRemaining(student -> {
			returnValue.add(modelMapper.map(student, StudentDto.class));
		});

		return returnValue;
	}

}
