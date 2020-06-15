package com.course.pontointeligente.controllers;

import com.course.pontointeligente.entities.Company;
import com.course.pontointeligente.services.CompanyService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    private static final String SEARCH_COMPANY_ID_URL = "/api/company/companyID/";
    private static final Long ID = 1L;
    private static final String COMPANYID = "377152";
    private static final String SOCIAL_REASON = "Company x2";

    @Test
    @WithMockUser
    public void testFindCompanyWithInvalidCompanyID() throws Exception {
        BDDMockito.given(this.companyService.findByCompanyID(Mockito.anyString()))
                .willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get(SEARCH_COMPANY_ID_URL + COMPANYID)
            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Company not found for ID " + COMPANYID));
    }

    @Test
    @WithMockUser
    public void testFindCompanyWithValidCompanyID() throws Exception {
        BDDMockito.given(this.companyService.findByCompanyID(Mockito.anyString()))
                .willReturn(Optional.of(this.getCompanyData()));

        mvc.perform(MockMvcRequestBuilders.get(SEARCH_COMPANY_ID_URL + COMPANYID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.socialReason", equalTo(SOCIAL_REASON)))
                .andExpect(jsonPath("$.data.companyID", equalTo(COMPANYID)))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private Company getCompanyData() {
        Company company = new Company();
        company.setId(ID);
        company.setSocialReason(SOCIAL_REASON);
        company.setCompanyID(COMPANYID);
        return company;
    }
}
