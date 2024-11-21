package com.ces2.guarderia_canina.model;

import com.ces2.guarderia_canina.enums.RazaPerro;
import org.springframework.data.annotation.Id; 
import org.springframework.data.relational.core.mapping.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perro {
    
	@Id 
    private Long id;
    private String nombre;
    private RazaPerro raza; 
    private int edad;
    private double peso;
    @Column("dueno_id") 
    private Long duenoId;
}
