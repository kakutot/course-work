package check.controller;


import check.data.ajax.*;
import check.data.ajax.comment.*;
import check.data.db.domain.*;
import check.repos.TeacherRepo;
import check.repos.UserRepo;
import check.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CommentController {
    @Autowired
    CommentsService commentsService;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    UserRepo userRepo;

    public  MoreCommentAjaxResponse getComments( MoreCommentAjaxRequest request, List<Comment> comments){
        MoreCommentAjaxResponse result = new MoreCommentAjaxResponse();

        if (comments.isEmpty()) {
            result.setMsg("no comments found!");
        } else {
            result.setMsg("success");
        }
        result.setComments(commentsService.makeAllPrettyDate(comments));

        return result;
    }

    @ResponseBody
    @PostMapping(value = "/comments/teacher/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public MoreCommentAjaxResponse getTeacherComments(@PathVariable String id, @RequestBody MoreCommentAjaxRequest request) {
        Optional<Integer> optPage = Optional.of(request.getPageNumber());
        Optional<Integer> optSize = Optional.of(request.getPageSize());

        int currentPage = optPage.orElse(1);
        int pageSize = optSize.orElse(5);
        Teacher teacher = teacherRepo.findById(Integer.valueOf(id)).orElse(new Teacher("undef"));
        List<Comment> comments = commentsService.
                findPaginated(PageRequest.of(currentPage, pageSize),teacher,commentsService.constructDescSort());


        return getComments(request,comments);
    }

    @ResponseBody
    @PostMapping(value = "/comments/user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public MoreCommentAjaxResponse getUserComments(@PathVariable String id, @RequestBody MoreCommentAjaxRequest request) {
        Optional<Integer> optPage = Optional.of(request.getPageNumber());
        Optional<Integer> optSize = Optional.of(request.getPageSize());

        int currentPage = optPage.orElse(1);
        int pageSize = optSize.orElse(5);
        User user = userRepo.findByUserId(Integer.valueOf(id));
        List<Comment> comments = commentsService.
                findPaginated(PageRequest.of(currentPage, pageSize),user,commentsService.constructDescSort());


        return getComments(request,comments);
    }

    @ResponseBody
    @PostMapping(value = "/comments/teacher/{id}/add",produces = { MediaType.APPLICATION_JSON_VALUE })
    public AddCommentAjaxResponse addTeacherComment(@PathVariable String id,
                                                    @RequestBody AddCommentAjaxRequest request) {
        AddCommentAjaxResponse response = new AddCommentAjaxResponse();


        try{
            Comment comment = new Comment();
            comment.setCommentDate(String.valueOf(System.currentTimeMillis()));
            comment.setMessage(request.getMsg());
            comment.setTeacher( teacherRepo.findById(Integer.valueOf(id)).get());
            User user = userRepo.findByUsername(request.getUsername());
            comment.setUser(user);

            comment = commentsService.addComment(comment);

            response.setResponseType(ResponseType.OK);
            response.setComments(Collections.singletonList(commentsService.prettyDate(comment)));
        }
        catch (EmptyResultDataAccessException exception)
        {
            response.setResponseType(ResponseType.ERROR);
        }
        return response;
    }

    @ResponseBody
    @PostMapping(value = "/comments/teacher/{id}/update/{commentId}",produces = { MediaType.APPLICATION_JSON_VALUE })
    public UpdateCommentAjaxResponse updateTeacherComment(@PathVariable String id, @PathVariable String commentId,
                                                          @RequestBody UpdateCommentAjaxRequest request) {
        UpdateCommentAjaxResponse response = new UpdateCommentAjaxResponse();
        try{
            Comment comment = commentsService.findById(commentId);
            comment.setMessage(request.getInnerContent());
            commentsService.addComment(comment);
            response.setResponseType(ResponseType.OK);
            response.setMsg(commentsService.findById(commentId).getMessage());
        }
        catch (EmptyResultDataAccessException exception)
        {
            response.setResponseType(ResponseType.ERROR);
        }
        return response;
    }

    @ResponseBody
    @PostMapping(value = "/comments/teacher/{id}/delete/{commentId}",produces = { MediaType.APPLICATION_JSON_VALUE })
    public DeleteCommentAjaxResponse deleteTeacherComment(@PathVariable String id,
                                                          @PathVariable String commentId) {
        DeleteCommentAjaxResponse response = new DeleteCommentAjaxResponse();
        try{
            commentsService.deleteComment(commentId);
            response.setResponseType(ResponseType.OK);
        }
        catch (EmptyResultDataAccessException exception)
        {
            response.setResponseType(ResponseType.ERROR);
        }
        return response;
    }
}
