package com.authentication_microservice.Controller;

import com.authentication_microservice.Models.*;
import com.authentication_microservice.Persistence.Entity.UserEntity;
import com.authentication_microservice.Service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserEntity>saveUser(@RequestBody UserEntity userEntity){
        return ResponseEntity.ok(userService.saveUser(userEntity));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity>getUserById(@PathVariable("id") UUID id){
        UserEntity user=userService.getByUserId(id);
        if (user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @GetMapping("/teacher/{id}")
    public ResponseEntity<UserEntity>getTeacherById(@PathVariable("id") UUID id){
         UserEntity teacher=userService.getTeacher(id);
        if (teacher==null){
            return ResponseEntity.notFound().build();
        }
        System.out.println(teacher);
        return ResponseEntity.ok(teacher);
    }
    //enrolling courses from user
    @CircuitBreaker(name="enrollmentCb",fallbackMethod = "fallbackEnroll")
    @PostMapping("/enroll-from-user/{idStudent}")
    public ResponseEntity<EnrollmentDto> enroll(@RequestBody List<String>courseNames,@PathVariable("idStudent") UUID idStudent){
       EnrollmentDto enrollmentDto= userService.enrollStudentToCourses(idStudent,courseNames);
       return  ResponseEntity.ok(enrollmentDto);
    }
    @CircuitBreaker(name="enrollmentCb",fallbackMethod = "fallbackEnrollmentByStudent")
    @GetMapping("/enrollment/{id}")
    public ResponseEntity<EnrollmentDto>enrollmentByStudent(@PathVariable("id") UUID id){
        return ResponseEntity.ok(userService.enrollmentByStudent(id));
    }


    //saving notes from user
    @CircuitBreaker(name="notesCb",fallbackMethod = "saveGradesFallBack")
    @PostMapping("/grade/save")
    public ResponseEntity<GradeDto>saveGrades(@RequestBody SaveGradeRequesDto saveGradeRequesDto){
        GradeDto gradeDto= userService.saveGrades(saveGradeRequesDto);
        return ResponseEntity.ok(gradeDto);
    }

    //All FallBacks
    //enrollment fallback for circuit breaker

    // Fallback para POST enroll
    public ResponseEntity<EnrollmentDto> fallbackEnroll(@RequestBody List<String>courseNames,@PathVariable("idStudent") UUID idStudent){
        System.out.println("Error in enrollmentByStudent: ");

        return new ResponseEntity("Fallback activado: enrollment not working",HttpStatus.OK);
    }

    // Fallback  GET enrollmentByStudent
    public ResponseEntity<EnrollmentDto> fallbackEnrollmentByStudent(@PathVariable("id") UUID id, RuntimeException e) {
        System.out.println("Error in enrollmentByStudent: ");

        return new ResponseEntity("Fallback activated: enrollment not working",HttpStatus.OK);
    }
    //fallback saveGrades
    public ResponseEntity<GradeDto>saveGradesFallBack(@RequestBody SaveGradeRequesDto saveGradeRequesDto,RuntimeException e) {

        return new ResponseEntity("Fallback activated:notes microservice is not working", HttpStatus.OK) ;
    }

}
