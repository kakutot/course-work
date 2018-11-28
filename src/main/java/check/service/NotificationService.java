package check.service;

import check.data.db.domain.User;
import check.repos.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    private final static String SUBJECT = "Technology stack of Java EE (LW6, Student : Tupkalenko Roman)";
    private final static String FILE_PATH = "C:\\src\\facultyContent.txt";
    private final static String FROM_EMAIL = "kakutot27@gmail.com";
    private final static String ATTACHMENT_FILE_NAME = "Faculty content";
    private final static String TABLE_NAME = "Faculties";

    @Autowired
    private TableContentFetcherService tableContentFetcherService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private FacultyRepo facultyRepo;

    @Autowired
    public NotificationService (JavaMailSender javaMailSender){
        this.emailSender = javaMailSender;
    }

    public void sendNotification(User user) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmailAddress());
        helper.setFrom(FROM_EMAIL);
        helper.setSubject(SUBJECT);
        helper.setText(constructMessageContent());
        helper.addAttachment(ATTACHMENT_FILE_NAME,
                tableContentFetcherService.fetchTableContentIntoFile(FILE_PATH,facultyRepo));

        emailSender.send(helper.getMimeMessage());
    }

    public void sendNotification(List<User> users){
        for(User user : users){
            try {
                sendNotification(user);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public String constructMessageContent(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("I send you the content of table %s",TABLE_NAME))
               .append(String.format(" as of %s",dateFormat.format(date)));

        return builder.toString();
    }

}
