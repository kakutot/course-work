package check.controller;

import check.data.db.domain.Faculty;
import check.repos.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/faculties")
public class FacultyController {
   @Autowired
   private FacultyRepo facultyRepo;

   @GetMapping()
   public String  faculties(@RequestParam(name = "filter",required = false)String filter,Model model) {
      Iterable result = facultyRepo.findAll();
      model.addAttribute("faculties",result);
      model.addAttribute("faculty",new Faculty());
      Iterable<Faculty> faculties;
      if(filter!=null && !filter.isEmpty()){
         faculties = facultyRepo.findByFacultyNameContaining(filter);
      }
      else {
         faculties = facultyRepo.findAll();
         filter = "";
      }
      model.addAttribute("filter",filter);
      model.addAttribute("faculties",faculties);
      return "faculties";
   }

   /*@PostMapping(value = "/add_faculty")
   public String addFaculty(@ModelAttribute("faculty") Faculty faculty){

      facultyRepo.save(faculty);

      return "redirect:/faculties";
   }
   */
   /*
   @GetMapping(value = "/filter_faculty")
   public String filterFaculty(@RequestParam(name = "filter") String filter, Model model){
      Iterable<Faculty> faculties;
      if(filter!=null && !filter.isEmpty()){
         faculties = facultyRepo.findByFacultyNameContaining(filter);
      }
      else {
         faculties = facultyRepo.findAll();
      }
      model.addAttribute("faculties",faculties);

      return "faculties";
   }
   */
   @GetMapping(value = "/delete_faculty")
   public String deleteFaculty(@RequestParam(name="facultyId") String id){

     facultyRepo.deleteById(new Integer(id));
      return "redirect:/faculties";
   }
   @PostMapping(value = "/update_faculty")
   public String updateFaculty(Faculty faculty){
      facultyRepo.save(faculty);

      return "redirect:/faculties";
   }

   @GetMapping("/findOne")
   @ResponseBody
   public Faculty findOne(@RequestParam int id ) {
     return facultyRepo.findById(id).orElse(new Faculty("undef"));
   }
}
