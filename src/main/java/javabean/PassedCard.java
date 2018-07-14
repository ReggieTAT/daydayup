package javabean;
//通过审核的卡片
public class PassedCard extends Card {
    private int praise;//点赞数
    private String userName;//上传者
    private String adminName;//审核者

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
