package com.example.NickVidz.NickVidz.modelos;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Collections;
import java.util.List;



@Entity
@Table(name="libros")
public class libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Integer IdGutenberg;
    @Column(unique = true)
    private String titulo;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "libro_id")
    @Column(unique = true)
    private List<autores> autor;
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    private List<String> idiomas;
    private Integer descargas;


    @Override
    public String toString() {
        return "******************************** \n"+
                "--------------LIBRO------------ \n" +
                "Titulo: " + titulo +"\n"+
                "IdGutenberg= " + IdGutenberg+ "\n"  +
                "Autor= " + autor + "\n" +
                "idiomas= " + idiomas + "\n" +
                "descargas= " + descargas + "\n";
    }

    public libro(){}


    public libro(DatosLibros datosLibros){
        this.IdGutenberg = datosLibros.id();
        this.titulo = datosLibros.titulo();
        this.autor = datosLibros.autores().stream()
                .map(a -> new autores(a.nombre(), a.fechaDeNacimiento(), a.fechaDeFallecimiento()))
                .toList();
        this.idiomas = datosLibros.idiomas();
        this.descargas = datosLibros.descargas();
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdGutenberg() {
        return IdGutenberg;
    }

    public void setIdGutenberg(Integer idGutenberg) {
        IdGutenberg = idGutenberg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<autores> getAutor() {
        return autor;
    }

    public void setAutor(List<autores> autor) {;
        this.autor = autor;
    }

    public String getIdiomas(){
        return String.valueOf(idiomas);
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }
}
