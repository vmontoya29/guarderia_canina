package com.ces2.guarderia_canina.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dueno {
	
	@Id 
    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correoElectronico;
}
