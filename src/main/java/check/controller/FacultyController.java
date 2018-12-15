package check.controller;

import check.data.ajax.ResponseType;
import check.data.ajax.faculty.FacultyAjaxRequest;
import check.data.ajax.faculty.FacultyAjaxResponse;
import check.data.ajax.user.UserAjaxResponse;
import check.data.db.domain.Faculty;
import check.data.db.domain.Role;
import check.data.db.domain.User;
import check.repos.FacultyRepo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value="/faculties")
public class FacultyController {
   @Autowired
   private FacultyRepo facultyRepo;

   @GetMapping()
   public String  faculties(@RequestParam(name = "filter",required = false)String filter,Model model) {


      Iterable<Faculty> faculties;
      if(filter!=null && !filter.isEmpty()){
         faculties = facultyRepo.findByFacultyNameContaining(filter);
      } else {
         faculties = facultyRepo.findAll();
         filter = "";
      }
      System.out.println(faculties);
      if(faculties.spliterator().getExactSizeIfKnown()==0){
           faculties = null;
      }
      model.addAttribute("faculty",new Faculty());
      model.addAttribute("filter",filter);
      model.addAttribute("faculties",faculties);
      return "faculties";
   }


   @GetMapping(value = "/delete_faculty")
   public String deleteFaculty(@RequestParam(name="facultyId") String id, Model model){
      try{
          facultyRepo.deleteById(new Integer(id));
      }
      catch (Exception e){
          model.addAttribute("message","Delete wasn't successfull");
      }
      return "redirect:/faculties";
   }
   @PostMapping(value = "/update_faculty")
   public String updateFaculty(Faculty faculty,Model model){
      Faculty res = facultyRepo.save(faculty);
      if(res == null){
          model.addAttribute("message","Update wasn't successfull");
      }
      return "redirect:/faculties";
   }

   @GetMapping("/findOne")
   @ResponseBody
   public Faculty findOne(@RequestParam int id ) {

       return facultyRepo.findById(id).orElse(new Faculty("undef"));
   }

    @PostMapping("/checkFacultyName")
    @ResponseBody
    public ValidationResponse checkDeptName(@RequestBody FacultyNameRequest request) {
        List<String> names = StreamSupport.stream(facultyRepo.findAll().spliterator(),false)
                .map((f)->f.getFacultyName()).collect(Collectors.toList());
        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setValid(names==null ||!names.contains(request.facultyName)
                ||request.facultyName.equals(request.curFacultyName));

        return validationResponse;
    }

    static class FacultyNameRequest{
        String facultyName;
        String curFacultyName;

        public FacultyNameRequest() {
        }

        public String getFacultyName() {
            return facultyName;
        }

        public void setFacultyName(String facultyName) {
            this.facultyName = facultyName;
        }

        public String getCurFacultyName() {
            return curFacultyName;
        }

        public void setCurFacultyName(String curFacultyName) {
            this.curFacultyName = curFacultyName;
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
