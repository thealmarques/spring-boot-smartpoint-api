package com.course.pontointeligente.controllers;

import com.course.pontointeligente.dto.LaunchDto;
import com.course.pontointeligente.entities.Employee;
import com.course.pontointeligente.entities.Launch;
import com.course.pontointeligente.enums.EType;
import com.course.pontointeligente.response.Response;
import com.course.pontointeligente.services.EmployeeService;
import com.course.pontointeligente.services.LaunchService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/launch")
@CrossOrigin(origins = "*")
public class LaunchController {
    private static final Logger log = LoggerFactory.getLogger(LaunchController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LaunchService launchService;

    @Autowired
    private EmployeeService employeeService;

    @Value("${pagination.itemsPerPage}")
    private int itemsPerPage;

    /**
     * Gets list of launches for a given employee ID
     * @param employeeID
     * @param pag
     * @param ord
     * @param dir
     * @return
     */
    @GetMapping(value = "/employee/{employeeID}")
    public ResponseEntity<Response<Page<LaunchDto>>> listByEmployeeID(
            @PathVariable("employeeID") Long employeeID,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
        log.info("Finding launches by employee ID: {}, page: {}", employeeID, pag);
        Response<Page<LaunchDto>> response = new Response<Page<LaunchDto>>();

        PageRequest pageRequest = PageRequest.of(pag, this.itemsPerPage, Sort.Direction.valueOf(dir), ord);
        Page<Launch> launches = this.launchService.findByEmployeeID(employeeID, pageRequest);
        Page<LaunchDto> launchDtos = launches.map(launch -> this.convertLaunchToDto(launch));

        response.setData(launchDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Get launch by ID
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<LaunchDto>> listByID(@PathVariable("id") Long id) {
        log.info("Finding launch by ID: {}", id);
        Response<LaunchDto> response = new Response<LaunchDto>();
        Optional<Launch> launch = this.launchService.findById(id);

        if (!launch.isPresent()) {
            log.info("Launch not found for ID: {}", id);
            response.getErrors().add("Launch not found for id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertLaunchToDto(launch.get()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<LaunchDto>> add(@Valid @RequestBody LaunchDto launchDto,
                                                             BindingResult result) throws ParseException {
        log.info("Adding launch: {}", launchDto.toString());
        Response<LaunchDto> response = new Response<>();
        validateEmployee(launchDto, result);
        Launch launch = this.convertDtoToLaunch(launchDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando lançamento: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        launch = this.launchService.persist(launch);
        response.setData(this.convertLaunchToDto(launch));
        return ResponseEntity.ok(response);
    }

    /**
     * Updates Launch
     * @param id
     * @param launchDto
     * @param result
     * @return
     * @throws ParseException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<LaunchDto>> update(@PathVariable("id") Long id,
                                                             @Valid @RequestBody LaunchDto launchDto, BindingResult result) throws ParseException {
        log.info("Updating launch: {}", launchDto.toString());
        Response<LaunchDto> response = new Response<>();
        validateEmployee(launchDto, result);
        launchDto.setId(Optional.of(id));
        Launch launch = this.convertDtoToLaunch(launchDto, result);

        if (result.hasErrors()) {
            log.error("Error validating launch: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        launch = this.launchService.persist(launch);
        response.setData(this.convertLaunchToDto(launch));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id) {
        log.info("Removing launch: {}", id);
        Response<String> response = new Response<String>();
        Optional<Launch> lancamento = this.launchService.findById(id);

        if (!lancamento.isPresent()) {
            log.info("Error while removing.: Launch {} is invalid.", id);
            response.getErrors().add("Error while removing launch with ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.launchService.remove(id);
        return ResponseEntity.ok(new Response<String>());
    }

    /**
     * Converts dto to launch entity
     * @param launchDto
     * @param result
     * @return
     * @throws ParseException
     */
    private Launch convertDtoToLaunch(LaunchDto launchDto, BindingResult result) throws ParseException {
        Launch launch = new Launch();

        if (launchDto.getId().isPresent()) {
            Optional<Launch> launchOptional = this.launchService.findById(launchDto.getId().get());
            if (launchOptional.isPresent()) {
                launch = launchOptional.get();
            } else {
                result.addError(new ObjectError("launch", "Launch does not exist."));
            }
        } else {
            launch.setEmployee(new Employee());
            launch.getEmployee().setId(launchDto.getEmployeeID());
        }

        launch.setLocation(launchDto.getLocation());
        launch.setDescription(launch.getDescription());
        launch.setDate(this.dateFormat.parse(launchDto.getDate()));

        if (EnumUtils.isValidEnum(EType.class, launchDto.getType())) {
            launch.setType(EType.valueOf(launchDto.getType()));
        } else {
            result.addError(new ObjectError("type", "Invalid type."));
        }

        return launch;
    }

    /**
     * Validates employee
     * @param launchDto
     * @param result
     */
    private void validateEmployee(LaunchDto launchDto, BindingResult result) {
        if (launchDto.getEmployeeID() == null) {
            result.addError(new ObjectError("employee", "Employee not binded."));
            return;
        }

        log.info("Validating employee id {}: ", launchDto.getEmployeeID());
        Optional<Employee> employee = this.employeeService.findById(launchDto.getEmployeeID());
        if (!employee.isPresent()) {
            result.addError(new ObjectError("employee", "Employee not found."));
        }
    }

    /**
     * Converts launch entity to dto
     * @param launch
     * @return
     */
    private LaunchDto convertLaunchToDto(Launch launch) {
        LaunchDto dto = new LaunchDto();
        dto.setId(Optional.of(launch.getId()));
        dto.setDate(this.dateFormat.format(launch.getDate()));
        dto.setDescription(launch.getDescription());
        dto.setEmployeeID(launch.getEmployee().getId());
        dto.setLocation(launch.getLocation());
        dto.setType(launch.getType().toString());

        return dto;
    }
}
