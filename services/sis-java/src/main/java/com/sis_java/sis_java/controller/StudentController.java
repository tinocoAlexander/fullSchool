package com.sis_java.sis_java.controller;

import com.sis_java.sis_java.model.dto.StudentDTO;
import com.sis_java.sis_java.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students") // Ruta base para todos los endpoints de estudiantes
@Validated
@CrossOrigin(origins = "*") // Este es el CORS que permite las solicitudes desde algun dominio en especifico, es importante cambiarlo en produccion
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Obtener todos los estudiantes
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Obtener estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Optional<StudentDTO> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // Buscar estudiantes
    @GetMapping("/search")
    public ResponseEntity<List<StudentDTO>> searchStudents(@RequestParam String query) {
        List<StudentDTO> students = studentService.searchStudents(query);
        return ResponseEntity.ok(students);
    }

    // Obtener estudiantes por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<StudentDTO>> getStudentsByStatus(@PathVariable String status) {
        List<StudentDTO> students = studentService.getStudentsByStatus(status.toUpperCase());
        return ResponseEntity.ok(students);
    }

    // Crear nuevo estudiante
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO createdStudent = studentService.createStudent(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Actualizar estudiante
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, 
                                         @Valid @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Eliminar estudiante
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Estudiante eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Endpoints de utilidad
    @GetMapping("/hello")
    public String hello() {
        return "¡Hola! El SIS (Student Information System) está funcionando correctamente.";
    }

    @GetMapping("/status")
    public String status() {
        return "El servidor SIS está activo y corriendo.";
    }

    @GetMapping("/count/{status}")
    public ResponseEntity<Long> countStudentsByStatus(@PathVariable String status) {
        long count = studentService.countStudentsByStatus(status.toUpperCase());
        return ResponseEntity.ok(count);
    }
}
