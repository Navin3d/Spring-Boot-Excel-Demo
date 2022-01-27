package gmc.project.student.services;

import gmc.project.student.models.MailingModel;

public interface MailingService {
	public String sendMail(MailingModel mailingModel);
}
