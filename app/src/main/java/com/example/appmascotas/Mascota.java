package com.example.appmascotas;

//Mantiene la estructura de la tabla

public class Mascota {
    private int id;
    private String tipo;
    private String nombre;
    private String color;
    private double pesokg;

    public Mascota(){}

    public Mascota(int id, String tipo, String nombre, String color, double pesokg) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.color = color;
        this.pesokg = pesokg;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public double getPesokg() {
        return pesokg;
    }
}
