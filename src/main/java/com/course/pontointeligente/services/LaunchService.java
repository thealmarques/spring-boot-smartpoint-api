package com.course.pontointeligente.services;

import com.course.pontointeligente.entities.Launch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LaunchService {
    /**
     * Returns a paginated list of employee actions given an employee ID.
     *
     * @param employeeId
     * @param pageRequest
     * @return Page<Launch>
     */
    Page<Launch> findByEmployeeID(Long employeeId, PageRequest pageRequest);

    /**
     * Returns launch given an ID
     *
     * @param id
     * @return Optional<Launch>
     */
    Optional<Launch> findById(Long id);

    /**
     * Persists a launch in the DB.
     *
     * @param launch
     * @return a launch
     */
    Launch persist(Launch launch);

    /**
     * Removes a launch from the DB.
     *
     * @param id
     */
    void remove(Long id);
}
