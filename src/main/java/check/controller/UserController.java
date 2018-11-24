package check.controller;

import check.data.db.domain.Department;
import check.data.db.domain.Role;
import check.data.db.domain.User;
import check.repos.DepartmentRepo;
import check.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @GetMapping()
    public String  users(@RequestParam(name = "filter",required = false)
                         String filter, Model model) {

        Iterable userResult = userRepo.findAll();
        Iterable deptResult = departmentRepo.findAll();

        model.addAttribute("users",userResult);
        model.addAttribute("departments",deptResult);

        Iterable<User> filteredUsers;
        if(filter!=null && !filter.isEmpty()){
            filteredUsers = userRepo.findByUsernameContaining(filter);
        }
        else {
            filteredUsers = userRepo.findAll();
            filter = "";
        }

        model.addAttribute("filter",filter);
        model.addAttribute("users",filteredUsers);
        List<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toList());

        model.addAttribute("roles", roles);

        return "users";
    }
    @GetMapping(value = "/delete_user")
    public String deleteUser(@RequestParam(name="userId") String id){
        userRepo.deleteById(Integer.valueOf(String.valueOf(id)));
        System.out.print("DELETE " + id);

        return "redirect:/users";
    }
    @PostMapping(value = "/update_user")
    public String updateUser(User user,String userId,String deptName,
                             @RequestParam() Map<String,String> form){

        User userInDb = userRepo.findById(Integer.valueOf(userId)).orElse(null);
        if(userInDb==null){
            user.setUserRoles(Collections.singleton(Role.USER));
            user.setActive(false);
        }

        if(!deptName.isEmpty()){
            user.setDepartment(departmentRepo.findDistinctByDeptName(deptName));
        }

        Set<Role> userRoles;
        if(form!=null && !form.isEmpty()){
            userRoles = new HashSet<>();
            for(Map.Entry<String,String> entry : form.entrySet()){
                if(entry.getKey().equals("USER")||entry.getKey().equals("ADMIN")){
                    userRoles.add(Role.valueOf(entry.getKey()));
                }
            }
            user.setUserRoles(userRoles);
        }

        System.out.println("User : " + user);
        userRepo.save(user);

        return "redirect:/users";
    }
    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(@RequestParam int id) {
        User user = userRepo.findById(id).orElse(new User("undef"));
        System.out.println("FindOne res : " + user);

        return user;
    }

}
