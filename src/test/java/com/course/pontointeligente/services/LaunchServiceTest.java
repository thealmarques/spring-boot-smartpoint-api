package com.course.pontointeligente.services;

import com.course.pontointeligente.entities.Launch;
import com.course.pontointeligente.repositories.LaunchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LaunchServiceTest {
    @MockBean
    LaunchRepository launchRepository;

    @Autowired
    LaunchService launchService;

    @BeforeEach
    public void setUp() throws Exception{
        BDDMockito.given(this.launchRepository.findByEmployeeId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
                .willReturn(new PageImpl<Launch>(new ArrayList<Launch>()));
        BDDMockito.given(this.launchRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(new Launch()));
        BDDMockito.given(this.launchRepository.save(Mockito.any(Launch.class))).willReturn(new Launch());
    }

    @Test
    public void testFindLaunchByEmployeeId() {
        Page<Launch> launch = this.launchService.findByEmployeeID(1L, PageRequest.of(0, 10));

        assertNotNull(launch);
    }

    @Test
    public void testFindLaunchById() {
        Optional<Launch> launch = this.launchService.findById(1L);

        assertTrue(launch.isPresent());
    }

    @Test
    public void testPersistLaunch() {
        Launch launch = this.launchService.persist(new Launch());

        assertNotNull(launch);
    }

    @Test
    public void testRemoveLaunch() {
        Launch launch = this.launchService.persist(new Launch());

        this.launchService.remove(launch.getId());
        Optional<Launch> find = this.launchService.findById(launch.getId());

        assertTrue(!find.isPresent());
    }
}
