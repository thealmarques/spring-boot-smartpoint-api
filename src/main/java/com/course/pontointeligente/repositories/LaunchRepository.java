package com.course.pontointeligente.repositories;

import com.course.pontointeligente.entities.Launch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Transactional(readOnly = true)
    @NamedQueries({
            @NamedQuery(name = "LaunchRepository.findByEmployeeId",
            query = "SELECT lanc FROM launch lanc WHERE lanc.employee.id = :employeeID")
    })
public interface LaunchRepository extends JpaRepository<Launch, Long> {
    List<Launch> findByEmployeeId(@Param("employeeID") Long employeeID);

    Page<Launch> findByEmployeeId(@Param("employeeID") Long employeeID, Pageable pageable);
}
