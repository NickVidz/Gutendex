package com.example.NickVidz.NickVidz.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
