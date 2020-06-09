package com.course.pontointeligente.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RegisterCompanyAdminDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String personID;
    private String socialReason;
    private String companyID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Name can't be empty.")
    @Length(min = 3, max = 200, message = "Name should contain between 3 and 200 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Email can't be empty.")
    @Length(min = 5, max = 200, message = "Email should contain between 5 and 200 characters.")
    @Email(message="Invalid email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password can't be empty.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "PersonID cant' be empty.")
    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @NotEmpty(message = "Social reason can't be empty.")
    @Length(min = 5, max = 200, message = "Social reason should contain between 5 and 200 characters.")
    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    @NotEmpty(message = "CompanyID can't be empty.")
    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    @Override
    public String toString() {
        return "RegisterCompanyAdminDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", personID='" + personID + '\'' +
                ", socialReason='" + socialReason + '\'' +
                ", companyID='" + companyID + '\'' +
                '}';
    }
}
