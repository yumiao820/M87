package com.cn.muiltmodel.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
//-@ApiModel()用于类 表示对类进行说明，用于参数用实体类接收
@ApiModel(value = "user",description = "用户实体类")
public class User implements Serializable {
//    @ApiModelProperty()用于方法，字段 ;表示对model属性的说明或者数据操作更改
//    required 表示字段是否是必须的
    @ApiModelProperty(value = "id",notes = "用户编号",required =true)
    private Integer id=null;
    @ApiModelProperty(value = "userCode",notes = "用户编码")
    private String userCode=null;
    @ApiModelProperty(value = "userName",notes = "用户姓名",required =true)
    private String userName=null;
    @ApiModelProperty(value = "userPassword",notes = "用户性别")
    private String userPassword=null;
    @ApiModelProperty(value = "gender",notes = "用户性别")
    private Integer gender=null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    @ApiModelProperty(value = "birthday",notes = "用户生日")
    private Date birthday=null;
}
