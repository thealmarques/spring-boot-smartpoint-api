package com.course.pontointeligente.entities;

import com.course.pontointeligente.enums.EProfile;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    static final long serialVersionUID = 1L; //assign a long value

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "person_id", nullable = false)
    private String personID;

    @Column(name = "price_hour", nullable = false)
    private BigDecimal pricePerHour;

    @Column(name = "hours_day", nullable = false)
    private Float hoursPerDay;

    @Column(name = "lunch_hours", nullable = false)
    private Float lunchHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile", nullable = false)
    private EProfile profile;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "creation_date", nullable = false)
    private Date modifiedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Launch> launch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    @Transient
    public Optional<BigDecimal> getPricePerHourOpt(BigDecimal priceHour) {
        return Optional.ofNullable(priceHour);
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Float getHoursPerDay() {
        return hoursPerDay;
    }

    @Transient
    public Optional<Float> getHoursPerDay(Float hoursDay) {
        return Optional.ofNullable(hoursDay);
    }

    public void setHoursPerDay(Float hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public Float getLunchHours() {
        return lunchHours;
    }

    @Transient
    public Optional<Float> getLunchHours(Float lunchHours) {
        return Optional.ofNullable(lunchHours);
    }

    public void setLunchHours(Float lunchHours) {
        this.lunchHours = lunchHours;
    }

    public EProfile getProfile() {
        return profile;
    }

    public void setProfile(EProfile profile) {
        this.profile = profile;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Launch> getLaunches() {
        return launch;
    }

    public void setLaunches(List<Launch> launch) {
        this.launch = launch;
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
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", personID='" + personID + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", hoursPerDay=" + hoursPerDay +
                ", lunchHours=" + lunchHours +
                ", profile=" + profile +
                ", creationDate=" + creationDate +
                ", modifiedDate=" + modifiedDate +
                ", company=" + company +
                ", launch=" + launch +
                '}';
    }
}
