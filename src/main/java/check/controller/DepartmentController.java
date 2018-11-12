package check.controller;

import check.data.db.domain.Department;
import check.repos.DepartmentRepo;
import check.repos.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequestMapping(value="/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private FacultyRepo facultyRepo;

    @GetMapping()
    public String  departments(@RequestParam(name = "filter",required = false)String filter,Model model) {
        Iterable result = departmentRepo.findAll();
        Iterable facultyResult = facultyRepo.findAll();
        model.addAttribute("departments",result);
        model.addAttribute("faculties",facultyResult);
        //model.addAttribute("department",new Department());
        Iterable<Department> departments;
        if(filter!=null && !filter.isEmpty()){
            departments = departmentRepo.findByDeptNameContaining(filter);
        }
        else {
            departments = departmentRepo.findAll();
            filter="";
        }
        model.addAttribute("filter",filter);
        model.addAttribute("departments",departments);
        return "departments";
    }
    /*
    @PostMapping(value = "/add_department")
    public String addDepartment(@ModelAttribute("department") Department department){

        departmentRepo.save(department);

        return "redirect:/departments";
    }
    */
    /*
    @GetMapping(value = "/filter_department")
    public String filterDepartment(@RequestParam(name = "filter") String filter, Model model){
        Iterable<Department> departments;
        if(filter!=null && !filter.isEmpty()){
            departments = departmentRepo.findByDeptNameContaining(filter);
        }
        else {
            departments = departmentRepo.findAll();
        }
        model.addAttribute("departments",departments);

        return "departments";
    }
    */
    @GetMapping(value = "/delete_department")
    public String deleteDepartment(@RequestParam(name="departmentId") String id){

        departmentRepo.deleteById(Integer.valueOf(String.valueOf(id)));
        System.out.print("DELEE " + id);
        return "redirect:/departments";
    }
    @PostMapping(value = "/update_department")
    public String updateDepartment(Department department,String deptId ,String facultyName){

        System.out.println("Dept id : " + deptId);

        department.setId(Integer.valueOf(deptId));

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
}
