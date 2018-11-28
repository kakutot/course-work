package check.controller;

import check.data.db.domain.User;
import check.repos.FacultyRepo;
import check.repos.UserRepo;
import check.service.NotificationService;
import check.service.TableContentFetcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/notifications")
public class EMailController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    TableContentFetcherService tableContentFetcherService;

    @Autowired
    UserRepo userRepo;
    @Autowired
    FacultyRepo facultyRepo;

    @GetMapping()
    public String getNotificationsPage(Model model){
        model.addAttribute("users",userRepo.findAll());
        return "notifications";
    }

    @PostMapping()
    public String sendNotifications(@RequestBody MultiValueMap<String, String> formData){
        List<User> users = new ArrayList<>();
        String selectEmailsFormName = "selEmails";
        for(Map.Entry<String,List<String>> entry : formData.entrySet()){
            if(entry.getKey().equals(selectEmailsFormName)){
                users.addAll(entry.getValue()
                        .stream()
                        .map((a)->userRepo.findByEmailAddress(a))
                        .collect(Collectors.toList()));
                break;
            }
        }

        notificationService.sendNotification(users);

        return "redirect:/";
    }
}
