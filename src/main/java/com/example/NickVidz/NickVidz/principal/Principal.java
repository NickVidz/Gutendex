package com.example.NickVidz.NickVidz.principal;

import com.example.NickVidz.NickVidz.modelos.*;
import com.example.NickVidz.NickVidz.repositorio.LibroRepository;
import com.example.NickVidz.NickVidz.service.ConsuumoAPI;
import com.example.NickVidz.NickVidz.service.ConvierteDatos;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsuumoAPI consuumoAPI = new ConsuumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<libro> libros;


    public Principal(LibroRepository repository){
        this.repositorio = repository;
    }

    public void Menu(){



        var opcion = 0;

        do{
            System.out.println("*******************************");
            System.out.println("Elija la opcion del menu: ");
            System.out.println("1. - Buscar libro por titulo");
            System.out.println("2. - Mirar libros buscados ");
            System.out.println("3. - Mirar autores registrados (según libros buscados)");
            System.out.println("4. - Buscar autores vivos segun año");
            System.out.println("5. - Mirar libros buscados según idioma");
            System.out.println("6. - Salir");
            System.out.println("*******************************");
            try{
                opcion = teclado.nextInt();
                teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivos();
                    break;
                case 5:
                    mostrarLibrosSegunIdioma();
                    break;
                case 6:
                    System.out.println("Saliendo, muchas gracias por usar el programa.");
                    opcion = -1;
                    break;
                default:
                    System.out.println("Opcion ingresada invalida");
            }



            }catch (InputMismatchException e){
                System.out.println("Debes ingresar numeros no caracteres : " + e);
                teclado.nextLine();
            }
        }while(opcion != -1);
    }

    private List<libro> getDatosLibro(){
        System.out.println("Ingrese el titulo del libro que desee buscar");
        var NombreLibro = teclado.nextLine();
        var json = consuumoAPI.obtenerDatos(URL_BASE +"?search="+ NombreLibro.replace(" ", "+"));
        System.out.println(json);
        teclado.nextLine();
        var datos = convierteDatos.obtenerDatos(json, libros.class);
        Optional<DatosLibros> libroBuuscado = datos.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(NombreLibro.toUpperCase()))
                .findFirst();
        if(libroBuuscado.isPresent()){
            List<libro> datosa = libroBuuscado.stream()
                    .map(this::mapToLibro)
                    .collect(Collectors.toList());
            return datosa;
        }else{
            return null;
        }
        }


    private libro mapToLibro(DatosLibros datosLibros) {
        return new libro(datosLibros);
    }
    private void buscarLibroPorTitulo(){

        List<libro> datosLibros = getDatosLibro();
        if(datosLibros == null || datosLibros.isEmpty()){
            System.out.println("No se encontro el libro");
        }else{
            List<libro> libroNoExistente = datosLibros.stream()
                            .filter(l->!repositorio.existsByTitulo(l.getTitulo()))
                                    .collect(Collectors.toList());

            if (libroNoExistente.isEmpty()) {
                System.out.println("El libro ingresado ya se encuentra en la DB");
                return;
            }
            try{
                System.out.println(datosLibros);
                repositorio.saveAll(datosLibros);
            }catch (DataIntegrityViolationException e){
                throw new IllegalArgumentException("Error al guardar los libros: " + e);
            }
        }

    }

    private void mostrarLibrosBuscados(){
        libros = repositorio.findAll();

        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados(){
        List<autores> autoresBuscados = repositorio.autoresBuscados();

        autoresBuscados.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutoresVivos(){
        System.out.println("Ingrese el año del que necesita saber los autores vivos");
        int añoIngresado = teclado.nextInt();
        List<autores> autoresVivos = repositorio.autoresVivos(añoIngresado);

        if(autoresVivos.isEmpty() || autoresVivos == null){
            System.out.println("No se encontraron autores vivos en esa fecha en la base de datos");
        }else{
            autoresVivos.stream()
                    .forEach(System.out::println);
        }
    }
    private void mostrarLibrosSegunIdioma(){
        System.out.println("Ingrese el diminutivo del idioma, ejemplo : es - en - pt: ");
        var idiomaIngresado = teclado.nextLine();
        idiomaIngresado.toLowerCase();
        if(idiomaIngresado.length() >= 3){
            System.out.println("El diminutivo son unicamente dos letras");
        }else{
        List<String> listaReseteada = new ArrayList<>();
        listaReseteada.add(idiomaIngresado);
            List<libro> librosList = repositorio.librosSegunLengua(listaReseteada);
            librosList.stream()
                    .forEach(System.out::println);
        }

    }
}
