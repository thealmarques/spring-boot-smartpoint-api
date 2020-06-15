package com.course.pontointeligente.dto;

public class CompanyDto {
    private Long id;
    private String socialReason;
    private String companyID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", socialReason='" + socialReason + '\'' +
                ", companyID='" + companyID + '\'' +
                '}';
    }
}
