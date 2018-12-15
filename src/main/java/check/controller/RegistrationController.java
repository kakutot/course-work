package check.controller;

import check.data.db.domain.Role;
import check.data.db.domain.User;
import check.repos.DepartmentRepo;
import check.repos.UserRepo;
import check.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value="/registration")
public class RegistrationController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    NotificationService notificationService;

    @GetMapping()
    public String getRegistration(Model model){
        Iterable departments = departmentRepo.findAll();
        if(departments.spliterator().getExactSizeIfKnown()==0){
            departments = null;
        }
        model.addAttribute("departments",departments);
        return "registration";
    }
    @PostMapping()
    public String addUser(User user, String deptName, Model model){
        User userFromDb = userRepo.findByUsername(user.getUsername());
        List<String> emails = StreamSupport.stream(userRepo.findAll().spliterator(),false)
                                             .map((usr)->usr.getEmailAddress()).collect(Collectors.toList());
        if(userFromDb != null){
            String error = "";
            StringBuilder stringBuilder = new StringBuilder(error);
            stringBuilder.append("User exists!");
            if(emails.contains(user.getEmailAddress())){
                stringBuilder.append("Email exists!");
            }

            model.addAttribute("message",stringBuilder.toString());

            Iterable departments = departmentRepo.findAll();
            if(departments.spliterator().getExactSizeIfKnown()==0){
                departments = null;
            }
            model.addAttribute("departments",departments);

            return "registration";
        }
        else{
            user.setActive(true);
            user.setUserRoles(Collections.singleton(Role.ROLE_USER));
            user.setDepartment(departmentRepo.findDistinctByDeptName(deptName));
            userRepo.save(user);
        }

        return "redirect:/login";
    }
}
