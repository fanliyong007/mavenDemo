package com.zime.maven.entity;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

public class Student {
    private Integer id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    private Date birth;
    @Email
    private String email;

    private String gender;
    @Pattern(regexp="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,8}$)",message="姓名只能是2~8位的汉字或6~16位的字符")
    private String name;
    @Pattern(regexp="^[1][3,4,5,7,8][0-9]{9}$",message="无效的手机号")
    private String phone;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    private Date regTime;

    private Integer majorId;
    
    private Major major;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Student(Integer id, Date birth, String email, String gender, String name, String phone, Date regTime,
			Integer majorId) {
		super();
		this.id = id;
		this.birth = birth;
		this.email = email;
		this.gender = gender;
		this.name = name;
		this.phone = phone;
		this.regTime = regTime;
		this.majorId = majorId;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", birth=" + birth + ", email=" + email + ", gender=" + gender + ", name=" + name
				+ ", phone=" + phone + ", regTime=" + regTime + ", majorId=" + majorId + ", major=" + major + "]";
	}


    
}