package check.data.ajax.comment;

public class MoreCommentAjaxRequest {
    private Integer pageNumber;
    private Integer pageSize;

    public MoreCommentAjaxRequest(){

    }

    public MoreCommentAjaxRequest(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
