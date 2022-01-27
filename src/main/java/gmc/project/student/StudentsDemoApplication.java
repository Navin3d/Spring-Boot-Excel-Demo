package gmc.project.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import gmc.project.student.models.MailingModel;
import gmc.project.student.services.MailingService;

@SpringBootApplication
public class StudentsDemoApplication {

	public static void main(String[] args) {
		
		MailingModel mail = new MailingModel();
        mail.setTo("smnavin65@gmail.com");
        mail.setSubject("Application Started");
        mail.setBody("Spring Application Student Demo Started now...");
 
        ApplicationContext ctx = SpringApplication.run(StudentsDemoApplication.class, args);
        MailingService mailService = (MailingService) ctx.getBean("mailingService");
        mailService.sendMail(mail);
        
    }

}
