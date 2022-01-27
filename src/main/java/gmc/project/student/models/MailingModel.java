package gmc.project.student.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class MailingModel implements Serializable {

	private static final long serialVersionUID = 5567980920013646649L;
	
	private String from = "smnavin65@gmail.com";
	
	private String to;
	
	private String subject;
	
	private String body;

}
