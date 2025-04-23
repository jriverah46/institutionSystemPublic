package com.notes_microservice.Controller;

import com.notes_microservice.Persistence.Entity.GradeEntity;
import com.notes_microservice.Service.GradeService;
import com.notes_microservice.models.SaveGradesRequesDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @PostMapping("/save")
    @CircuitBreaker(name = "enrollmentCb", fallbackMethod = "")
    public ResponseEntity<GradeEntity> saveGrade(@RequestBody SaveGradesRequesDto saveGradesRequesDto) {
        GradeEntity grade = gradeService.saveGrades(saveGradesRequesDto);
        return ResponseEntity.ok(grade);

    }

    @GetMapping("/by-student")
    public ResponseEntity<List<GradeEntity>> getGradeByStudent(@RequestBody UUID idStudent) {
        return ResponseEntity.ok(gradeService.getGradesByIdStudent(idStudent));

    }

    //fallbacks

    public ResponseEntity<GradeEntity> saveGradeFallBack(@RequestBody SaveGradesRequesDto saveGradesRequesDto,RuntimeException e) {
        return new ResponseEntity("fallback activated: we could not verify if student is in the course", HttpStatus.OK);
    }
}