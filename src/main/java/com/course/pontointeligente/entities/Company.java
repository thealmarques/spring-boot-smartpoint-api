package com.course.pontointeligente.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "company")
public class Company implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "social_reason", nullable = false)
    private String socialReason;

    @Column(name = "company_id", nullable = false)
    private String companyID;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "modified_date", nullable = false)
    private Date modifiedDate;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Company() {}

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date now = new Date();
        this.creationDate = now;
        this.modifiedDate = now;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", socialReason='" + socialReason + '\'' +
                ", companyID='" + companyID + '\'' +
                ", creationDate=" + creationDate +
                ", modifiedDate=" + modifiedDate +
                ", employees=" + employees +
                '}';
    }
}
