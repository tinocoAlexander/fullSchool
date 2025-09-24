package com.sis_java.sis_java.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import org.springframework.format.annotation.NumberFormat;

public class StudentDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")

    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 60, message = "El apellido no puede tener más de 60 caracteres")
    private String lastName;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
    private String email;
    
    @NotBlank(message = "El ID de estudiante es obligatorio")
    @Size(max = 20, message = "El ID de estudiante no puede tener más de 20 caracteres")
    private String studentId;
    
    private LocalDate dateOfBirth;
    
    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    private String phone;
    
    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    private String address;
    
    private String status;
    
    // Constructores
    public StudentDTO() {}
    
    public StudentDTO(String firstName, String lastName, String email, String studentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentId = studentId;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Método de utilidad
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
