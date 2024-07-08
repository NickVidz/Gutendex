package com.example.NickVidz.NickVidz.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record libros (

        @JsonAlias("results")List<DatosLibros> libros

        ){
}
