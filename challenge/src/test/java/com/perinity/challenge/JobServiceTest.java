package com.perinity.challenge;

import com.perinity.challenge.entities.*;
import com.perinity.challenge.entities.dtos.JobDto;
import com.perinity.challenge.repositories.DepartmentRepository;
import com.perinity.challenge.repositories.JobRepository;
import com.perinity.challenge.repositories.UserRepository;
import com.perinity.challenge.services.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddJob() throws Exception {

        JobDto jobDto = new JobDto();
        jobDto.setDuration(10);
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(departmentRepository.findById(any())).thenReturn(Optional.of(new Department()));
        when(jobRepository.save(any())).thenReturn(new Job());

        assertDoesNotThrow(() -> jobService.addJob(jobDto));
        verify(jobRepository, times(1)).save(any());
    }

    @Test
    void testAddJobWithUserNotFound() {
        JobDto jobDto = new JobDto();
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> jobService.addJob(jobDto));
        verify(jobRepository, never()).save(any());
    }

    @Test
    void testAddJobWithDepartmentNotFound() {
        JobDto jobDto = new JobDto();
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(departmentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> jobService.addJob(jobDto));
        verify(jobRepository, never()).save(any());
    }

    @Test
    void testFinalizeJob() throws Exception {
        Long jobId = 1L;
        Job job = new Job();
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(jobRepository.save(any())).thenReturn(new Job());

        assertTrue(jobService.finalizeJob(jobId));
        assertTrue(job.isFinished());
        verify(jobRepository, times(1)).save(any());
    }

    @Test
    void testFinalizeJobNotFound() throws Exception {
        Long jobId = 1L;
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        assertFalse(jobService.finalizeJob(jobId));
        verify(jobRepository, never()).save(any());
    }

    @Test
    void testAllocateJob() throws Exception {
        Long departmentId = 1L;
        Department department = new Department();
        User user = new User();
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(userRepository.findAllByDepartment(department)).thenReturn(Optional.ofNullable(user));
        when(jobRepository.alocarPessoaNaTarefa(departmentId, user, department)).thenReturn(1);

        int result = jobService.allocateJob(departmentId);
        assertEquals(1, result);
        verify(jobRepository, times(1)).alocarPessoaNaTarefa(departmentId, user, department);
    }

    @Test
    void testAllocateJobWithDepartmentNotFound() {
        Long departmentId = 1L;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> jobService.allocateJob(departmentId));
        verify(jobRepository, never()).alocarPessoaNaTarefa(any(), any(), any());
    }

    @Test
    void testAllocateJobWithUserNotFound() {
        Long departmentId = 1L;
        Department department = new Department();
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(userRepository.findAllByDepartment(department)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> jobService.allocateJob(departmentId));
        verify(jobRepository, never()).alocarPessoaNaTarefa(any(), any(), any());
    }

    @Test
    void testGetIncompleteJobs() {
        Department department = new Department();
        department.setId(1L);
        Job job = new Job();
        job.setDepartment(department);


        when(jobRepository.listarTarefasPendentesComPrazosMaisAntigosLimit3()).thenReturn(Collections.singletonList(job));

        List<JobDto> result = jobService.getIncompleteJobs();
        assertEquals(1, result.size());
        verify(jobRepository, times(1)).listarTarefasPendentesComPrazosMaisAntigosLimit3();
    }
}
