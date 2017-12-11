package base;

import java.util.Date;

public class BaseModel {

    protected Byte status;
    protected Date addDttm;
    protected String addUser;
    protected Date lastUpdDttm;
    protected String lastUpdUser;
    protected String remark;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getAddDttm() {
        return addDttm;
    }

    public void setAddDttm(Date addDttm) {
        this.addDttm = addDttm;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Date getLastUpdDttm() {
        return lastUpdDttm;
    }

    public void setLastUpdDttm(Date lastUpdDttm) {
        this.lastUpdDttm = lastUpdDttm;
    }

    public String getLastUpdUser() {
        return lastUpdUser;
    }

    public void setLastUpdUser(String lastUpdUser) {
        this.lastUpdUser = lastUpdUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "status=" + status +
                ", addDttm=" + addDttm +
                ", addUser='" + addUser + '\'' +
                ", lastUpdDttm=" + lastUpdDttm +
                ", lastUpdUser='" + lastUpdUser + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
