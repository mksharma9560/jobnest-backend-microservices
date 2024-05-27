package com.jobnest.searchms.controller;

import com.jobnest.searchms.dto.CompanyDto;
import com.jobnest.searchms.dto.JobDto;
import com.jobnest.searchms.helper.ApiResponse;
import com.jobnest.searchms.helper.ResponseBuilder;
import com.jobnest.searchms.services.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller class for managing search operations.
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;
    private final ResponseBuilder responseBuilder;
    private boolean success;
    private String message;
    private HttpStatus httpStatus;

    // Constructor
    public SearchController(SearchService searchService, ResponseBuilder responseBuilder) {
        this.searchService = searchService;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Searches for jobs based on criteria.
     *
     * @param searchCriteria The search criteria as a map.
     * @return A response entity with a list of job DTOs and HTTP status 200 if successful,
     *         or a 404 status if no jobs match the criteria.
     * @throws Exception If an error occurs during the job search process.
     */
    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<List<JobDto>>> searchJobs(@Valid @RequestParam Map<String, String> searchCriteria) {
        log.info("*** GET request received to search jobs ***");

        List<JobDto> jobDtos = searchService.getJobs(searchCriteria);

        success = !jobDtos.isEmpty();
        message = success ? "Jobs fetched successfully" : "No jobs match the given criteria";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<JobDto>> response = responseBuilder.buildResponseJobDto(jobDtos, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Searches for companies based on criteria.
     *
     * @param searchCriteria The search criteria as a map.
     * @return A response entity with a list of company DTOs and HTTP status 200 if successful,
     *         or a 404 status if no companies match the criteria.
     * @throws Exception If an error occurs during the company search process.
     */
    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<List<CompanyDto>>> searchCompanies(@Valid @RequestParam Map<String, String> searchCriteria) {
        log.info("*** Received GET request to search companies ***");

        List<CompanyDto> companyDtos = searchService.getCompanies(searchCriteria);

        success = !companyDtos.isEmpty();
        message = success ? "Companies fetched successfully" : "No company match the given criteria";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<CompanyDto>> response = responseBuilder.buildResponseCompanyDto(companyDtos, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Searches for jobs by keyword.
     *
     * @param keyword The keyword to search for.
     * @return A response entity with a list of job DTOs and HTTP status 200 if successful,
     *         or a 404 status if no jobs match the keyword.
     * @throws Exception If an error occurs during the job search process.
     */
    @GetMapping("/quick-jobs")
    public ResponseEntity<ApiResponse<List<JobDto>>> searchJobByKeyword(@RequestParam("keyword") String keyword) {
        log.info("*** GET request received to search job by keyword");

        List<JobDto> jobDtos = searchService.getJobsByKeyword(keyword);

        success = !jobDtos.isEmpty();
        message = success ? "Jobs fetched successfully" : "No jobs match the given criteria";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<JobDto>> response = responseBuilder.buildResponseJobDto(jobDtos, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Searches for companies by keyword.
     *
     * @param keyword The keyword to search for.
     * @return A response entity with a list of company DTOs and HTTP status 200 if successful,
     *         or a 404 status if no companies match the keyword.
     * @throws Exception If an error occurs during the company search process.
     */
    @GetMapping("/quick-companies")
    public ResponseEntity<ApiResponse<List<CompanyDto>>> searchCompaniesByKeyword(@RequestParam("keyword") String keyword) {
        log.info("*** GET request received to search companies by keyword");

        List<CompanyDto> companyDtos = searchService.getCompaniesByKeyword(keyword);

        success = !companyDtos.isEmpty();
        message = success ? "Companies fetched successfully" : "No company match the given criteria";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<CompanyDto>> response = responseBuilder.buildResponseCompanyDto(companyDtos, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }
}