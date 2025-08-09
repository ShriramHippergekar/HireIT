package com.hireit.service;

import com.hireit.domain.Job;
import com.hireit.repository.JobRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobRepository jobRepository;
    public JobService(JobRepository jobRepository) { this.jobRepository = jobRepository; }

    public List<Job> findAll() { return jobRepository.findAll(); }
    public Optional<Job> findById(Long id) { return jobRepository.findById(id); }
    public Job save(Job job) { return jobRepository.save(job); }
    public void delete(Long id) { jobRepository.deleteById(id); }
}
