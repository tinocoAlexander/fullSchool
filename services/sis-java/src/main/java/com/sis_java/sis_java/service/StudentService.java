package com.sis_java.sis_java.service;

import com.sis_java.sis_java.model.dto.StudentDTO;
import com.sis_java.sis_java.model.entity.Student;
import com.sis_java.sis_java.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    // Obtener todos los estudiantes
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener estudiante por ID
    @Transactional(readOnly = true)
    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    // Obtener estudiante por email
    @Transactional(readOnly = true)
    public Optional<StudentDTO> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(this::convertToDTO);
    }
    
    // Obtener estudiante por studentId
    @Transactional(readOnly = true)
    public Optional<StudentDTO> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .map(this::convertToDTO);
    }
    
    // Buscar estudiantes
    @Transactional(readOnly = true)
    public List<StudentDTO> searchStudents(String searchTerm) {
        return studentRepository.searchStudents(searchTerm)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Crear nuevo estudiante
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Validar que no exista el email o studentId
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este email");
        }
        
        if (studentRepository.existsByStudentId(studentDTO.getStudentId())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este ID");
        }
        
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }
    
    // Actualizar estudiante
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        
        if (existingStudent.isEmpty()) {
            throw new IllegalArgumentException("Estudiante no encontrado con ID: " + id);
        }
        
        Student student = existingStudent.get();
        
        // Validar email único (si cambió)
        if (!student.getEmail().equals(studentDTO.getEmail()) && 
            studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este email");
        }
        
        // Validar studentId único (si cambió)
        if (!student.getStudentId().equals(studentDTO.getStudentId()) && 
            studentRepository.existsByStudentId(studentDTO.getStudentId())) {
            throw new IllegalArgumentException("Ya existe un estudiante con este ID");
        }
        
        // Actualizar campos
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setStudentId(studentDTO.getStudentId());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setPhone(studentDTO.getPhone());
        student.setAddress(studentDTO.getAddress());
        
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }
    
    // Eliminar estudiante
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Estudiante no encontrado con ID: " + id);
        }
        studentRepository.deleteById(id);
    }
    
    // Obtener estudiantes por estado
    @Transactional(readOnly = true)
    public List<StudentDTO> getStudentsByStatus(String status) {
        return studentRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Contar estudiantes por estado
    @Transactional(readOnly = true)
    public long countStudentsByStatus(String status) {
        return studentRepository.countByStatus(status);
    }
    
    // Métodos de conversión
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setStudentId(student.getStudentId());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setPhone(student.getPhone());
        dto.setAddress(student.getAddress());
        dto.setStatus(student.getStatus() != null ? student.getStatus().name() : null);
        return dto;
    }
    
    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setStudentId(dto.getStudentId());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        return student;
    }
}
