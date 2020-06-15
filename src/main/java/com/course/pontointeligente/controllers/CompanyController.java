package com.course.pontointeligente.controllers;

import com.course.pontointeligente.dto.CompanyDto;
import com.course.pontointeligente.entities.Company;
import com.course.pontointeligente.response.Response;
import com.course.pontointeligente.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*")
public class CompanyController {
    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    /**
     * Returns a company given an companyID.
     *
     * @param companyID
     * @return ResponseEntity<Response<CompanyDto>>
     */
    @GetMapping(value = "/companyID/{companyID}")
    public ResponseEntity<Response<CompanyDto>> buscarPorCnpj(@PathVariable("companyID") String companyID) {
        log.info("Finding company by companyID: {}", companyID);
        Response<CompanyDto> response = new Response<CompanyDto>();
        Optional<Company> company = this.companyService.findByCompanyID(companyID);

        if (!company.isPresent()) {
            log.info("Company not found: {}", companyID);
            response.getErrors().add("Company not found for ID " + companyID);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertCompanyToDto(company.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Converts company entity to DTO
     * @param company
     * @return company dto
     */
    private CompanyDto convertCompanyToDto(Company company) {
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setCompanyID(company.getCompanyID());
        dto.setSocialReason(company.getSocialReason());
        return dto;
    }
}
