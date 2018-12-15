package check.controller;

import check.data.ajax.ResponseType;
import check.data.ajax.user.UserAjaxRequest;
import check.data.ajax.user.UserAjaxResponse;
import check.data.db.domain.*;
import check.repos.DepartmentRepo;
import check.repos.FacultyRepo;
import check.repos.UserRepo;
import check.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    private final static String FILTER_DEPARTMENT = "department";
    private final static String FILTER_FACULTY = "faculty";
    private final static String FILTER_NAME = "name";

    @Autowired
    CommentsService commentsService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    FacultyRepo facultyRepo;

    private Map<String,FilterAction<User>> filterMap;

    @GetMapping()
    public String users(@RequestParam(name = "filter",required = false)
                         String filter, Model model) {

        Iterable userResult = userRepo.findAll();
        Iterable deptResult = departmentRepo.findAll();

        if(userResult.spliterator().getExactSizeIfKnown()==0){
            userResult = null;
        }

        if(deptResult.spliterator().getExactSizeIfKnown()==0){
            deptResult = null;
        }
        model.addAttribute("users",userResult);
        model.addAttribute("departments",deptResult);

        Iterable<User> filteredUsers;

        if(filter!=null && !filter.isEmpty()){
            filteredUsers = userRepo.findByUsernameContaining(filter);
        } else {
            filteredUsers = userRepo.findAll();

            filter = "";
        }

        if(filteredUsers.spliterator().getExactSizeIfKnown()==0){
            filteredUsers = null;
        }

        model.addAttribute("filter",filter);
        model.addAttribute("users",filteredUsers);

        List<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toList());

        model.addAttribute("roles", roles);

        return "users";
    }

    private void initFilterMap(){
        filterMap = new HashMap<>();
        filterMap.put(FILTER_FACULTY,(filter)-> {
            List<User> users = new ArrayList<>();
            Faculty faculty = null;
            if (filter != null && !filter.isEmpty()) {
                faculty = facultyRepo.getDistinctByFacultyName(filter);
            }
            if (faculty != null) {
                List<Department> departments = departmentRepo.findAllByFaculty(faculty);
                if (departments != null) {
                    for (Department department : departments) {
                        List<User> currUsers = null;
                        if ((currUsers = userRepo.findByDepartment(department)) != null) {
                            users.addAll(currUsers);
                        }
                    }

                }
            }
            return users;
        });
        filterMap.put(FILTER_DEPARTMENT,(filter)-> {
            List<User> users = new ArrayList<>();
            Department department = null;
            if (filter != null && !filter.isEmpty()) {
                department = departmentRepo.findDistinctByDeptName(filter);
            }

            if (department != null) {
                 users = userRepo.findByDepartment(department);
            }else{
                users = StreamSupport.stream(userRepo.findAll().spliterator(),false).collect(Collectors.toList());
            }

            return users;
        });
        filterMap.put(FILTER_NAME,(filter)->{
            List<User> users = new ArrayList<>();
            if(filter != null && !filter.isEmpty()){
                users = userRepo.findByUsernameContaining(filter);
            } else {
               users = StreamSupport.stream(userRepo.findAll().spliterator(),false).collect(Collectors.toList());
            }

            return users;
        });
    }

    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam(name="userId") String id,Model model){
        try{
            userRepo.deleteById(Integer.valueOf(String.valueOf(id)));
        } catch (Exception e){
            model.addAttribute("message","Error during delete");
        }

        return "redirect:/users";
    }

    @PostMapping("/update")
    public String updateUser(User user,String deptName,
                             @RequestParam() Map<String,String> form,Model model){
        User userInDb = userRepo.findByUserId(user.getUserId());
        if(userInDb == null){
            user.setUserRoles(Collections.singleton(Role.ROLE_USER));
            user.setActive(false);
        }

        if(!deptName.isEmpty()){
            user.setDepartment(departmentRepo.findDistinctByDeptName(deptName));
        }

        Set<Role> userRoles;
        if(form!=null && !form.isEmpty()){
            System.out.println("Form is not null");
            userRoles = new HashSet<>();
            for(Map.Entry<String,String> entry : form.entrySet()){
                if(entry.getKey().equals("ADMIN")){
                    userRoles.add(Role.valueOf("ROLE_ADMIN"));
                }
            }
            user.setUserRoles(userRoles);
        }
        user.getUserRoles().add(Role.ROLE_USER);
        user.setActive(true);
        try{
            userRepo.save(user);
        }
        catch (Exception e){
            model.addAttribute("message","Error during update");
        }

        return "redirect:/users";
    }

    @ResponseBody
    @PostMapping("/ajax/update")
    public UserAjaxResponse updateUser(@RequestBody UserAjaxRequest userRequest){
        User temp = userRequest.getUserRequest();
        Set userRoles = new HashSet();
        userRoles.add(Role.ROLE_USER);
        if(userRequest.isAdmin()){
            userRoles.add(Role.ROLE_ADMIN);
        }
        temp.setUserRoles(userRoles);
        temp.setDepartment(departmentRepo.findDistinctByDeptName(userRequest.getDeptName()));

        User user = userRepo.save(userRequest.getUserRequest());
        UserAjaxResponse response = new UserAjaxResponse();

        if(user == null){
            response.setResponseType(ResponseType.ERROR);
        }

        response.setUserResponse(user);

        return response;
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable int id,Model model) {
        User user = userRepo.findByUserId(id);

        int lastPage = commentsService.getCurrentPageNumber(user);
        List<Comment> comments = commentsService.findPaginated(PageRequest.of(0,
                CommentsService.PAGE_SIZE),user,commentsService.constructDescSort());

        comments = commentsService.makeAllPrettyDate(comments);
        List<Department> departments = departmentRepo.findAll();

        model.addAttribute("user",user);
        model.addAttribute("comments",comments);
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("totalComments",commentsService.
                findByUser(user,commentsService.constructDescSort()).size());
        model.addAttribute("departments",departments);

        return "user";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(@RequestParam int id) {
        User user = userRepo.findByUserId(id);
        if(user == null){
            user = new User("undefined");
        }
        System.out.println(user);
        return user;
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public ValidationResponse checkEmail(@RequestBody EmailRequest emailRequest) {
        List<String> emails = StreamSupport.stream(userRepo.findAll().spliterator(),false)
                .map((usr)->usr.getEmailAddress()).collect(Collectors.toList());
        System.out.println(emailRequest.getEmail());
        ValidationResponse validationResponse = new ValidationResponse();
        System.out.println(!emails.contains(emailRequest.email));
        validationResponse.setValid(emails==null ||!emails.contains(emailRequest.email)
                ||emailRequest.email.equals(emailRequest.curEmail));
        return validationResponse;
    }

    @PostMapping("/checkUsername")
    @ResponseBody
    public ValidationResponse checkUsername(@RequestBody UsernameRequest usernameRequest) {
        List<String> usernames = StreamSupport.stream(userRepo.findAll().spliterator(),false)
                .map((usr)->usr.getUsername()).collect(Collectors.toList());
        ValidationResponse validationResponse = new ValidationResponse();
        System.out.println(!usernames.contains(usernameRequest.username));
        validationResponse.setValid(usernames==null ||!usernames.contains(usernameRequest.username)
                ||usernameRequest.username.equals(usernameRequest.curUsername));
        return validationResponse;
    }

    static class EmailRequest{
        String email;
        String curEmail;

        public EmailRequest(){
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCurEmail() {
            return curEmail;
        }

        public void setCurEmail(String curEmail) {
            this.curEmail = curEmail;
        }
    }

    static class UsernameRequest{
        String username;
        String curUsername;

        public UsernameRequest() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCurUsername() {
            return curUsername;
        }

        public void setCurUsername(String curUsername) {
            this.curUsername = curUsername;
        }
    }

    static class ValidationResponse{
        Boolean valid;

        public ValidationResponse() {
        }

        public Boolean getValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }
    }
}
