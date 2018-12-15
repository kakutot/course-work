package check.service;

import check.data.db.domain.Comment;
import check.data.db.domain.Teacher;
import check.data.db.domain.User;
import check.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentsService {
    public static final int PAGE_SIZE = 5;

    @Autowired
    CommentRepo commentRepo;

    public List<Comment> findByTeacher(Teacher teacher,Sort sort){
        List<Comment> comments = commentRepo.findAllByTeacher(teacher,sort);

        return comments;
    }

    public List<Comment> findByUser(User user,Sort sort){
        List<Comment> comments = commentRepo.findAllByUser(user,sort);

        return comments;
    }

    public List<Comment> findPaginated(Pageable pageable,List<Comment> comments){
        int pageSize = PAGE_SIZE;
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        System.out.println("Current page : " + currentPage + "start item :" + startItem);

        List<Comment> list;
        if (comments.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, comments.size());
            list = comments.subList(startItem, toIndex);
        }

        Page<Comment> commentPage
                = new PageImpl<Comment>(list, PageRequest.of(currentPage, pageSize), comments.size());

        return commentPage.getContent();

    }

    public List<Comment> findPaginated(Pageable pageable, Teacher teacher,Sort sort) {
        List<Comment> comments = findByTeacher(teacher,sort);

        return findPaginated(pageable,comments);
    }

    public List<Comment> findPaginated(Pageable pageable, User user,Sort sort) {
        List<Comment> comments = findByUser(user,sort);

        return findPaginated(pageable,comments);
    }

    public void deleteComment(String id){
        commentRepo.delete(commentRepo.findById(Integer.valueOf(id)).get());
    }

    public Comment addComment(Comment comment){
        return commentRepo.save(comment);
    }

    public Comment findById(String id){
        return commentRepo.findById(Integer.valueOf(id)).get();
    }

    public int getCurrentPageNumber(Teacher teacher){
        return commentRepo.findAllByTeacher(teacher,constructDescSort()).size()/PAGE_SIZE;
    }

    public int getCurrentPageNumber(User user){
        return commentRepo.findAllByUser(user,constructDescSort()).size()/PAGE_SIZE;
    }

    public Comment prettyDate(Comment comment){
        comment.setCommentDate(new SimpleDateFormat
                ("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(comment.commentDate))));
        return comment;
    }

    public List<Comment> makeAllPrettyDate(List<Comment> comments){
        return comments.stream().map(this::prettyDate).collect(Collectors.toList());
    }

    public Sort constructAscSort(){
        return  new Sort(new Sort.Order(Sort.Direction.ASC, "commentDate"));
    }

    public Sort constructDescSort(){
        return  new Sort(new Sort.Order(Sort.Direction.DESC, "commentDate"));
    }

}
