package com.sis_java.sis_java.repository;

import com.sis_java.sis_java.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Métodos de consulta derivados
    Optional<Student> findByEmail(String email);
    
    Optional<Student> findByStudentId(String studentId);
    
    List<Student> findByFirstNameContainingIgnoreCase(String firstName);
    
    List<Student> findByLastNameContainingIgnoreCase(String lastName);
    
    List<Student> findByStatus(String status);
    
    // Consultas personalizadas con @Query
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.studentId) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Student> searchStudents(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT COUNT(s) FROM Student s WHERE s.status = :status")
    long countByStatus(@Param("status") String status);
    
    // Verificar si existe un estudiante con email o studentId
    boolean existsByEmail(String email);
    
    boolean existsByStudentId(String studentId);
}
