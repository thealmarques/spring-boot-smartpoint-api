package com.course.pontointeligente.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.course.pontointeligente.dto.LaunchDto;
import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.entities.Launch;
import com.course.pontointeligente.enums.EType;
import com.course.pontointeligente.services.EmployeeService;
import com.course.pontointeligente.services.LaunchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LaunchControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LaunchService launchService;

    @MockBean
    private EmployeeService employeeService;

    private static final String URL = "/api/launch/";
    private static final Long ID_EMPLOYEE = 1L;
    private static final Long ID_LAUNCH = 1L;
    private static final String TYPE = EType.START_WORK.name();
    private static final Date DATE = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    @WithMockUser
    public void testRegisterLaunch() throws Exception {
        Launch launch = getLaunchData();
        BDDMockito.given(this.employeeService.findById(Mockito.anyLong())).willReturn(Optional.of(new Employee()));
        BDDMockito.given(this.launchService.persist(Mockito.any(Launch.class))).willReturn(launch);

        mvc.perform(MockMvcRequestBuilders.post(URL)
                .content(this.getPostJSON())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID_LAUNCH))
                .andExpect(jsonPath("$.data.type").value(TYPE))
                .andExpect(jsonPath("$.data.date").value(this.dateFormat.format(DATE)))
                .andExpect(jsonPath("$.data.employeeID").value(ID_EMPLOYEE))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testRegisterLaunchWhenIdIsInvalid() throws Exception {
        BDDMockito.given(this.employeeService.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(URL)
                .content(this.getPostJSON())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Employee not found."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void restRemoveLaunch() throws Exception {
        BDDMockito.given(this.launchService.findById(Mockito.anyLong())).willReturn(Optional.of(new Launch()));

        mvc.perform(MockMvcRequestBuilders.delete(URL + ID_LAUNCH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testRemoveLaunchWhenAccessIsDenied() throws Exception {
        BDDMockito.given(this.launchService.findById(Mockito.anyLong())).willReturn(Optional.of(new Launch()));

        mvc.perform(MockMvcRequestBuilders.delete(URL + ID_LAUNCH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private String getPostJSON() throws JsonProcessingException {
        LaunchDto lancamentoDto = new LaunchDto();
        lancamentoDto.setId(null);
        lancamentoDto.setDate(this.dateFormat.format(DATE));
        lancamentoDto.setType(TYPE);
        lancamentoDto.setEmployeeID(ID_EMPLOYEE);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(lancamentoDto);
    }

    private Launch getLaunchData() {
        Launch lancamento = new Launch();
        lancamento.setId(ID_LAUNCH);
        lancamento.setDate(DATE);
        lancamento.setType(EType.valueOf(TYPE));
        lancamento.setEmployee(new Employee());
        lancamento.getEmployee().setId(ID_EMPLOYEE);
        return lancamento;
    }
}
