package check.controller;

import check.data.ajax.ResponseType;
import check.data.ajax.user.UserAjaxRequest;
import check.data.ajax.user.UserAjaxResponse;
import check.data.db.domain.Department;
import check.data.db.domain.Faculty;
import check.data.db.domain.Role;
import check.data.db.domain.User;
import check.repos.DepartmentRepo;
import check.repos.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value="/departments")
public class DepartmentController {
    private final static String FILTER_FACULTY = "faculty";
    private final static String FILTER_NAME = "name";
    private String curFilter = FILTER_NAME;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private FacultyRepo facultyRepo;

    private Map<String,FilterAction<Department>> filterMap;
    @GetMapping()
    public String departments(@RequestParam(value = "filter",required = false) String filter,
                              @RequestParam(value = "value",required = false) String value,
                              Model model) {
        initFilterMap();
        Iterable faculties = facultyRepo.findAll();
        if(faculties.spliterator().getExactSizeIfKnown() == 0){
            faculties = null;
        }
        System.out.println("Cur filter : " + filter);
        List<Department> result = null;

        if((filter!=null  && !filter.isEmpty())&& (value!=null && !value.isEmpty())){
            result = filterMap.get(filter).apply(value);
            curFilter = filter;
        }
        else {
            result = departmentRepo.findAll();
        }

        if(faculties.spliterator().getExactSizeIfKnown()==0){
            faculties = null;
        }
        System.out.println(curFilter);
        model.addAttribute("filter",curFilter);
        model.addAttribute("faculties",faculties);
        model.addAttribute("department",new Department());
        model.addAttribute("departments",result);
        return "departments";
    }


    @GetMapping(value = "/delete_department")
    public String deleteDepartment(@RequestParam(name="departmentId") String id){

        departmentRepo.deleteById(Integer.valueOf(String.valueOf(id)));
        return "redirect:/departments";
    }

    @PostMapping(value = "/update_department")
    public String updateDepartment(Department department,String facultyName){
        System.out.println(department.deptId);
        if(!facultyName.isEmpty()){
            department.setFaculty(facultyRepo.getDistinctByFacultyName(facultyName));
        }

        System.out.println("Department : " + department);
        departmentRepo.save(department);

        return "redirect:/departments";
    }


    @GetMapping("/findOne")
    @ResponseBody
    public Department findOne(@RequestParam int id ) {
        Department dept = departmentRepo.findById(id).orElse(new Department("undef"));
        System.out.println("FindOne res : " + dept);
        return dept;
    }

    private void initFilterMap(){
        filterMap = new HashMap<>();
        filterMap.put(FILTER_FACULTY,(filter)->{
            List<Department> departments = new ArrayList<>();
            Faculty faculty = null;
            if(filter!=null && !filter.isEmpty()) {
                faculty = facultyRepo.getDistinctByFacultyName(filter);
            }
            if(faculty != null){
                departments = departmentRepo.findAllByFaculty(faculty);
            }

            return departments;
        });
        filterMap.put(FILTER_NAME,(filter)->{
            List<Department> departments = new ArrayList<>();
            if(filter != null && !filter.isEmpty()){
                departments = departmentRepo.findByDeptNameContaining(filter);
            } else {
                departments = departmentRepo.findAll();
            }

            return departments;
        });
    }

    @PostMapping("/checkDeptName")
    @ResponseBody
    public ValidationResponse checkDeptName(@RequestBody DeptNameRequest deptNameRequest) {
        List<String> deptNames = StreamSupport.stream(departmentRepo.findAll().spliterator(),false)
                .map((dept)->dept.getDeptName()).collect(Collectors.toList());
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setValid(deptNames==null ||!deptNames.contains(deptNameRequest.departmentName)
                ||deptNameRequest.departmentName.equals(deptNameRequest.curDepartmentName));

        return validationResponse;
    }

    static class DeptNameRequest{
        String departmentName;
        String curDepartmentName;

        public DeptNameRequest() {
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getCurDepartmentName() {
            return curDepartmentName;
        }

        public void setCurDepartmentName(String curDepartmentName) {
            this.curDepartmentName = curDepartmentName;
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
