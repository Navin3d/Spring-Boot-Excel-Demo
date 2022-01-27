package gmc.project.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.student.models.MailingModel;
import gmc.project.student.services.MailingService;

@RestController
@RequestMapping(path = "/navin3d-mailer")
public class MailingController {
	
	@Autowired
	private MailingService mailingService;
	
	public ResponseEntity<String> sendMail(@RequestBody MailingModel mailingModel) {
		String returnValue = mailingService.sendMail(mailingModel);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(returnValue);
	}
}
