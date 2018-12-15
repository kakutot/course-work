package check.controller;

import check.data.ajax.ResponseType;
import check.data.ajax.teacher.TeacherAjaxResponse;
import check.data.db.domain.Teacher;
import check.data.db.domain.Comment;
import check.data.db.domain.TeacherUserRatings;
import check.repos.DepartmentRepo;
import check.repos.TeacherRepo;
import check.service.CommentsService;
import check.service.SecurityService;
import check.service.TeacherUserRatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/teachers")
public class TeacherController {
    @Autowired
    TeacherUserRatingsService teacherUserRatingsService;

    @Autowired
    SecurityService securityService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    TeacherRepo teacherRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @GetMapping()
    public String teachers(@RequestParam(name = "filter",required = false)
                                 String filter, Model model) {

        Iterable teacherResult = teacherRepo.findAll();
        Iterable deptResult = departmentRepo.findAll();

        if(teacherResult.spliterator().getExactSizeIfKnown()==0){
            teacherResult = null;
        }
        if(deptResult.spliterator().getExactSizeIfKnown()==0){
            deptResult = null;
        }

        model.addAttribute("teachers",teacherResult);
        model.addAttribute("departments",deptResult);


        Iterable<Teacher> filteredTeachers;
        if(filter != null && !filter.isEmpty()){
            filteredTeachers = teacherRepo.findByFirstNameContaining(filter);
        } else {
            filteredTeachers = teacherRepo.findAll();
            filter = "";
        }

        if(filteredTeachers.spliterator().getExactSizeIfKnown()==0){
            filteredTeachers = null;
        }

        model.addAttribute("filter",filter);
        model.addAttribute("teachers",filteredTeachers);

        return "teachers";
    }
    @GetMapping("/deleteTeacher")
    public String deleteTeacher(@RequestParam(name="teacherId") String id){
        teacherRepo.deleteById(Integer.valueOf(String.valueOf(id)));

        return "redirect:/teachers";
    }

    @PostMapping()
    public String updateTeacher(Teacher teacher,String deptName){
        if(!deptName.isEmpty()){
            teacher.setDepartment(departmentRepo.findDistinctByDeptName(deptName));
        }
        teacherRepo.save(teacher);

        return "redirect:/teachers";
    }

    @ResponseBody
    @PostMapping("/{id}/rating/posRating/update")
    public TeacherAjaxResponse updatePosRating(@PathVariable String id){
        Teacher teacher = teacherRepo.findById(Integer.valueOf(id)).orElse(null);
        TeacherAjaxResponse response = new TeacherAjaxResponse();
        if(teacher == null){
            response.setResponseType(ResponseType.ERROR);
        }
        Integer curRating = teacher.getPosRating();
        teacher.setPosRating(++curRating);

        teacher = teacherRepo.save(teacher);
        if(teacher == null){
            response.setResponseType(ResponseType.ERROR);
        }
        response.setTeacher(teacher);
        System.out.println("Teacher : " + teacher);
        return response;
    }

    @ResponseBody
    @PostMapping("/{id}/rating/negRating/update")
    public TeacherAjaxResponse updateNegRating(@PathVariable String id){
        Teacher teacher = teacherRepo.findById(Integer.valueOf(id)).orElse(null);
        TeacherAjaxResponse response = new TeacherAjaxResponse();
        if(teacher == null){
            response.setResponseType(ResponseType.ERROR);
        }
        Integer curRating = teacher.getNegRating();
        teacher.setNegRating(++curRating);

        teacher = teacherRepo.save(teacher);
        if(teacher == null){
            response.setResponseType(ResponseType.ERROR);
        }
        response.setTeacher(teacher);
        System.out.println("Teacher : " + teacher);

        return response;
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Teacher findOne(@RequestParam int id) {
        Teacher teacher = teacherRepo.findById(id).orElse(new Teacher("undef"));

        return teacher;
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable String id,Model model) {
        Teacher teacher = teacherRepo.findById(Integer.valueOf(id)).orElse(new Teacher("undef"));
        int lastPage = commentsService.getCurrentPageNumber(teacher);
        List<Comment> comments = commentsService.findPaginated(PageRequest.of(0,
                CommentsService.PAGE_SIZE),teacher,commentsService.constructDescSort());

        comments = commentsService.makeAllPrettyDate(comments);
        model.addAttribute("teacher",teacher);
        model.addAttribute("comments",comments);
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("totalComments",commentsService.
                findByTeacher(teacher,commentsService.constructDescSort()).size());
        model.addAttribute("curUsername",securityService.getCurrentUsername());
        model.addAttribute("teacherUserRating",teacherUserRatingsService.prepareDataForCurrentUser(id));

        return "teacher";
    }



}
