package com.authentication_microservice.FeigClients;

import com.authentication_microservice.Models.EnrollmentDto;
import com.authentication_microservice.Models.EnrollmentRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
@FeignClient(name = "enrollment-microservice")
public interface EnrollmentFeignClient {
    @PostMapping("/enrollment/enroll")
    public EnrollmentDto enroll(@RequestBody EnrollmentRequestDto enrollmentRequestDto);
    @PostMapping("/enrollment/student-enrollment")
    EnrollmentDto enrollmentByStudent(@RequestBody UUID idStudent);

}
