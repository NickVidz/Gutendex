package com.example.NickVidz.NickVidz.repositorio;

import com.example.NickVidz.NickVidz.modelos.autores;
import com.example.NickVidz.NickVidz.modelos.libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<libro, Long> {
    @Query("SELECT a FROM autores a")
    List<autores> autoresBuscados();

    @Query("SELECT a FROM autores a WHERE :añoIngresado > a.fechaDeNacimiento AND :añoIngresado < a.fechaDeFallecimiento")
    List<autores> autoresVivos(int añoIngresado);

    @Query("SELECT l FROM libro l WHERE l.idiomas IN :idioma")
    List<libro> librosSegunLengua(List<String> idioma);

    boolean existsByTitulo(String titulo);

}
