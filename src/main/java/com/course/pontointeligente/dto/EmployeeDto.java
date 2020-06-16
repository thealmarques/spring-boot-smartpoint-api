package com.course.pontointeligente.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class EmployeeDto {
    private Long id;

    @NotEmpty(message = "Name can't be empty.")
    @Length(min = 3, max = 200, message = "Name should contain between 3 and 200 characters.")
    private String name;

    @NotEmpty(message = "Email can't be empty.")
    @Length(min = 3, max = 200, message = "Email should contain between 3 and 200 characters.")
    @Email(message="Invalid email.")
    private String email;

    private Optional<String> password = Optional.empty();

    private Optional<String> hoursPerDay = Optional.empty();

    private Optional<String> pricePerHour = Optional.empty();

    private Optional<String> lunchHours = Optional.empty();

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

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    public Optional<String> getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(Optional<String> hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public Optional<String> getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Optional<String> pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Optional<String> getLunchHours() {
        return lunchHours;
    }

    public void setLunchHours(Optional<String> lunchHours) {
        this.lunchHours = lunchHours;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", hoursPerDay=" + hoursPerDay +
                ", pricePerHour=" + pricePerHour +
                ", lunchHours=" + lunchHours +
                '}';
    }
}
