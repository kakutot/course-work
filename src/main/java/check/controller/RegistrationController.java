package check.controller;

import check.data.db.domain.Role;
import check.data.db.domain.User;
import check.repos.DepartmentRepo;
import check.repos.UserRepo;
import check.service.NotificationService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Map;

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
        model.addAttribute("departments",departments);
        return "registration";
    }
    @PostMapping()
    public String addUser(User user, String deptName, Model model){
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if(userFromDb !=null){
            model.addAttribute("message","User exists!");
        }
        else{
            user.setActive(true);
            user.setUserRoles(Collections.singleton(Role.USER));
            //System.out.println("Given dept name"+deptName);
           // System.out.println("Found dept"+departmentRepo.findDistinctByDeptName(deptName));
            user.setDepartment(departmentRepo.findDistinctByDeptName(deptName));
            //System.out.println("Add user / user dept id : " + user.getDepartment());
            userRepo.save(user);
        }

        return "redirect:/login";
    }
}
