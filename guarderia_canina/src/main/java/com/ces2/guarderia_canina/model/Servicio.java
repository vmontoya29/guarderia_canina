package com.ces2.guarderia_canina.model;

import org.springframework.data.relational.core.mapping.Column;

import com.ces2.guarderia_canina.enums.TipoServicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
	
	@Id 
    private Long id;
    private TipoServicio tipoServicio; 
    private double costo;
    private String duracion;
    @Column("perro_id") 
    private Long perroId;
}
