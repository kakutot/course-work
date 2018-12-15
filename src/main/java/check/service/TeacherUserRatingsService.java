package check.service;

import check.data.db.domain.RatingsCompositeId;
import check.data.db.domain.TeacherUserRatings;
import check.repos.TeacherUserRatingsRepo;
import check.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherUserRatingsService {
    @Autowired
    TeacherUserRatingsRepo teacherUserRatingsRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SecurityService securityService;

    public TeacherUserRatings findByTeacherAndUser(String teacherId,String userId){
        return teacherUserRatingsRepo.findById(new RatingsCompositeId(teacherId,userId)).orElse(null);
    }

    public TeacherUserRatings prepareDataForCurrentUser(String teacherId){
        TeacherUserRatings teacherUserRatings = null;
        String curUsername = securityService.getCurrentUsername();
        if(!curUsername.equals(SecurityService.ANONYMOUS)){
            teacherUserRatings = ifNotPresentSaveAndGet(teacherId,curUsername);
        }

        return teacherUserRatings;
    }

    public TeacherUserRatings ifNotPresentSaveAndGet(String teacherId,String userId){
        TeacherUserRatings result = findByTeacherAndUser(teacherId,userId);
        if(result == null){
            result = saveEntry(teacherId,userId);
        }

        return result;
    }

    private TeacherUserRatings saveEntry(String teacherId,String userId){
        RatingsCompositeId ratingsCompositeId = new RatingsCompositeId(teacherId,userId);

        return teacherUserRatingsRepo.save(new TeacherUserRatings(ratingsCompositeId,false,false));
    }

    public Boolean isEntryPresented(String teacherId,String userId){
        return teacherUserRatingsRepo.findById(new RatingsCompositeId(teacherId,userId)).get()!=null;
    }
}
