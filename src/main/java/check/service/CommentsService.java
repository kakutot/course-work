package check.service;

import check.data.db.domain.Comment;
import check.data.db.domain.Teacher;
import check.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import check.data.db.domain.Comment;

import java.util.Collections;
import java.util.List;

@Service
public class CommentsService {
    @Autowired
    CommentRepo commentRepo;

    public Page<Comment> findPaginated(Pageable pageable, Teacher teacher) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Comment> comments = commentRepo.findByTeacher(teacher);
        List<Comment> list;
        if (comments.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, comments.size());
            list = comments.subList(startItem, toIndex);
        }

        Page<Comment> commentPage
                = new PageImpl<Comment>(list, PageRequest.of(currentPage, pageSize), comments.size());


        return commentPage;
    }
}
