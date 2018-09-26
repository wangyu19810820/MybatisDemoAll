package wangyu1981.model;

import java.util.Date;

public class TbAdmin {

    protected String id;

    protected Date createTime;

    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TbAdmin{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", userName='" + userName + '\'' +
                '}';
    }
}
