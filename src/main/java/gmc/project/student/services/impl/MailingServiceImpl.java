package gmc.project.student.services.impl;

import org.springframework.stereotype.Service;

import gmc.project.student.models.MailingModel;
import gmc.project.student.services.MailingService;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service(value = "mailingService")
public class MailingServiceImpl implements MailingService {

	@Autowired
	JavaMailSender mailSender;

	@Override
	public String sendMail(MailingModel mailingModel) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(mailingModel.getSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mailingModel.getFrom(), "GMC@3d"));
			mimeMessageHelper.setTo(mailingModel.getTo());
			mimeMessageHelper.setText(mailingModel.getBody());

			mailSender.send(mimeMessageHelper.getMimeMessage());

		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error Sending Mail...";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "Error Sending Mail...";
		}

		return "Mail Sent Success Fully...";
	}

}
