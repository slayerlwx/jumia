package DO.jumia;

/**
 * @program: myselenium
 * @description: selenium  实体类
 * @author: lwx
 * @create: 2018-04-25 17:12
 **/

public class SeleniumDo {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 评论标题
     */
    private String reviewTitle;
    /**
     * 评论内容
     */
    private String reviewContent;
    /**
     * 发布标题
     */
    private String publishTitle;

    public SeleniumDo(String account, String password, String nickName, String reviewTitle, String reviewContent, String publishTitle) {
        this.account = account;
        this.password = password;
        this.nickName = nickName;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.publishTitle = publishTitle;
    }

    public SeleniumDo() {

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getPublishTitle() {
        return publishTitle;
    }

    public void setPublishTitle(String publishTitle) {
        this.publishTitle = publishTitle;
    }

    @Override
    public String toString() {
        return "JumiaDO{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", publishTitle='" + publishTitle + '\'' +
                '}';
    }
}
