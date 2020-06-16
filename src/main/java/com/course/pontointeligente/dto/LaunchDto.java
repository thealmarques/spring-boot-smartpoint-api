package com.course.pontointeligente.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class LaunchDto {
    private Optional<Long> id = Optional.empty();

    @NotEmpty(message = "Date can't be empty")
    private String date;

    private String type;

    private String description;

    private String location;

    private Long employeeID;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public String toString() {
        return "LaunchDto{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", employeeID=" + employeeID +
                '}';
    }
}
