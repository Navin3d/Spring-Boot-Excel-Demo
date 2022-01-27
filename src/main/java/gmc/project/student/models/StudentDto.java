package gmc.project.student.models;

import java.io.Serializable;


import lombok.Data;

@Data
public class StudentDto implements Serializable {
	
	private static final long serialVersionUID = 934597485724550826L;
	
	private String studentId;
	
	private String name;
	
	private String email;
	
	private String phoneNumber;

}
