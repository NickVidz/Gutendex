package com.example.NickVidz.NickVidz.modelos;

import jakarta.persistence.*;
import org.hibernate.dialect.VarcharUUIDJdbcType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;


    @Override
    public String toString() {
        return "\n" + "Nombre: " + nombre + '\n' +
                "Fecha de nacimiento: " + fechaDeNacimiento + "\n"+
                "Fecha de fallecimiento: " + fechaDeFallecimiento + "\n" ;
    }

    public autores(){}
    
    public autores(String nombre, Integer fechaDeNacimiento, Integer fechaDeFallecimiento){
        this.nombre = nombre;
        this.fechaDeFallecimiento = fechaDeFallecimiento;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }



    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String autor) {
        this.nombre = autor;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

}
