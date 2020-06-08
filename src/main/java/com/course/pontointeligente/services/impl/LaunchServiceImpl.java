package com.course.pontointeligente.services.impl;

import com.course.pontointeligente.entities.Launch;
import com.course.pontointeligente.repositories.LaunchRepository;
import com.course.pontointeligente.services.LaunchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaunchServiceImpl implements LaunchService {
    private static final Logger log = LoggerFactory.getLogger(LaunchServiceImpl.class);

    @Autowired
    private LaunchRepository launchRepository;

    @Override
    public Page<Launch> findByEmployeeID(Long employeeId, PageRequest pageRequest) {
        log.info("Searching launches by employee ID {}", employeeId);
        return this.launchRepository.findByEmployeeId(employeeId, pageRequest);
    }

    @Override
    @Cacheable("findById")
    public Optional<Launch> findById(Long id) {
        log.info("Searching launch by ID {}", id);
        return Optional.ofNullable(this.launchRepository.findById(id).orElse(null));
    }

    @Override
    public Launch persist(Launch launch) {
        log.info("Persisting launch {}", launch);
        return this.launchRepository.save(launch);
    }

    @Override
    public void remove(Long id) {
        log.info("Removing launch with id {}", id);
        this.launchRepository.deleteById(id);
    }
}
