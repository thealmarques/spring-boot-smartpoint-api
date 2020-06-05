package com.course.pontointeligente.entities;

import com.course.pontointeligente.enums.EType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "launch")
public class Launch implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "modified_date", nullable = false)
    private Date modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EType type;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public EType getType() {
        return type;
    }

    public void setType(EType type) {
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
}
