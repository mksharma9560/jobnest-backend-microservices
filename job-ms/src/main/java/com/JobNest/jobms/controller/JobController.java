package com.JobNest.jobms.controller;

import com.JobNest.jobms.dto.JobDto;
import com.JobNest.jobms.entities.Job;
import com.JobNest.jobms.helper.ApiResponse;
import com.JobNest.jobms.helper.ResponseBuilder;
import com.JobNest.jobms.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller class for managing job-related operations.
 */
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    private final JobService jobService;
    private final ResponseBuilder responseBuilder;
    private boolean success;
    private String message;
    private HttpStatus httpStatus;

    // Constructor Injection
    public JobController(JobService jobService, ResponseBuilder responseBuilder) {
        this.jobService = jobService;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Creates a new job.
     *
     * @param job The job data to be created.
     * @return A response entity with the created job DTO and HTTP status 201 if successful.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<JobDto>> createJob(@Valid @RequestBody Job job) {
        log.info("Received POST request to create a job");
        JobDto jobDto = jobService.createJob(job);

        success = jobDto != null;
        message = "Job saved successfully";
        httpStatus = HttpStatus.CREATED;

        ApiResponse<JobDto> response = responseBuilder.buildResponseWithSingleData(jobDto, success, message, httpStatus);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Updates an existing job by its ID.
     *
     * @param jobId  The ID of the job to be updated.
     * @param newJob The updated job data.
     * @return A response entity with a success message and HTTP status 200 if successful,
     *         or a 404 status if the job with the given ID is not found.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @PutMapping("/{jobId}")
    public ResponseEntity<ApiResponse<String>> updateJobById(@Valid @PathVariable Long jobId, @RequestBody Job newJob) {
        log.info("Received PUT request to update job with ID: {}", jobId);
        success = jobService.updateJobById(jobId, newJob);

        message = success ? "Job Updated" : "Job with ID: " + jobId + " not found";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<String> response = responseBuilder.buildResponseWithoutData(success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Retrieves all jobs.
     *
     * @return A response entity with a list of job DTOs and HTTP status 200 if successful,
     *         or a 404 status if no jobs are found.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobDto>>> getAllJobs() {
        log.info("GET request received to fetch all job");
        List<JobDto> jobDtos = jobService.getJobs();

        success = !jobDtos.isEmpty();
        message = success ? "Jobs fetched successfully" : "Jobs data not available";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<JobDto>> response = responseBuilder.buildResponseWithData(jobDtos, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Retrieves all jobs belonging to a company specified by its ID.
     *
     * @param companyId The ID of the company to retrieve jobs for.
     * @return A response entity with a list of job DTOs and HTTP status 200 if successful,
     *         or a 404 status if no jobs are found for the specified company ID.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<JobDto>>> getJobsByCompId(@Valid @PathVariable Long companyId) {
        log.info("Received GET request to fetch all job");
        List<JobDto> jobDtos = jobService.findJobsByCompId(companyId);

        success = !jobDtos.isEmpty();
        message = success ? "Jobs fetched successfully" : "No jobs found for company ID: " + companyId;
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<JobDto>> response = responseBuilder.buildResponseWithData(jobDtos, success, message, httpStatus);
        log.info("Returning {} jobs.", jobDtos.size());
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Retrieves a job by its ID.
     *
     * @param id The ID of the job to retrieve.
     * @return A response entity with the job DTO and HTTP status 200 if successful,
     *         or a 404 status if the job with the given ID is not found.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getJobById(@Valid @PathVariable Long id) {
        log.info("Received GET request to fetch job with ID: {}", id);
        JobDto jobDto = jobService.getJobById(id);

        success = jobDto != null;
        message = success ? "Job fetched successfully" : "Job with ID: " + id + " not found";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<JobDto> response = responseBuilder.buildResponseWithSingleData(jobDto, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Deletes a job by its ID.
     *
     * @param jobId The ID of the job to delete.
     * @return A response entity with a success message and HTTP status 200 if successful,
     *         or a 404 status if the job with the given ID is not found.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @DeleteMapping("/{jobId}")
    public ResponseEntity<ApiResponse<String>> deleteByIdJob(@Valid @PathVariable Long jobId) {
        log.info("Received DELETE request for Job ID: {}", jobId);
        success = jobService.deleteJobById(jobId);

        message = success ? "Job deleted successfully." : "Job ID: " + jobId + " not found.";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<String> response = responseBuilder.buildResponseWithoutData(success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Deletes all jobs belonging to a company specified by its ID.
     *
     * @param companyId The ID of the company to delete jobs for.
     * @return A response entity with a success message and HTTP status 200 if successful,
     *         or a 404 status if no jobs are found for the specified company ID.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<String>> deleteJobByCompId(@Valid @PathVariable Long companyId) {
        log.info("Received DELETE request for Job with company ID: {}", companyId);
        success = jobService.deleteJobByCompId(companyId);

        message = success ? "Jobs deleted successfully" : "Jobs with company ID: " + companyId + " not found.";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<String> response = responseBuilder.buildResponseWithoutData(success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Searches for jobs based on advanced search criteria.
     *
     * @param searchCriteria The advanced search criteria as a map.
     * @return A response entity with a list of job DTOs and HTTP status 200 if successful,
     *         or a 404 status if no jobs match the criteria.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @GetMapping("/advSearch")
    public ResponseEntity<ApiResponse<List<JobDto>>> searchJobs(@Valid @RequestParam Map<String, String> searchCriteria) {
        log.info("Received GET request to search job based on criteria");
        List<JobDto> jobDtos = jobService.searchJobs(searchCriteria);

        success = !jobDtos.isEmpty();
        message = success ? "Jobs fetched successfully" : "No jobs match the given criteria";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<JobDto>> response = responseBuilder.buildResponseWithData(jobDtos, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Searches for jobs by keyword.
     *
     * @param keyword The keyword to search for.
     * @return A response entity with a list of job DTOs and HTTP status 200 if successful,
     *         or a 404 status if no jobs match the keyword.
     * @throws Exception If an error occurs, which will be handled by GlobalExceptionHandler class.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<JobDto>>> searchJobByKeyword(@Valid @RequestParam("keyword") String keyword) {
        log.info("GET request received to search jobs by keyword");
        List<JobDto> jobDtos = jobService.getJobByKeyword(keyword);

        success = !jobDtos.isEmpty();
        message = success ? "Jobs fetched successfully" : "No jobs match the given keyword";
        httpStatus = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        ApiResponse<List<JobDto>> response = responseBuilder.buildResponseWithData(jobDtos, success, message, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }
}