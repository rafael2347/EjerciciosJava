package Ejercicio2;


/**
 * Nota del programador: un libro que tiene?
 * titulo
 * isbn
 * autor
 * año de publicación
 * editorial
 * precio
 */

public class Libro {
    private String titulo;
    private int isbn;
    private String autor;
    private int añoPublicacion;
    private String editorial;
    private double precio;

    public Libro(String titulo, int isbn, String autor, int añoPublicacion, String editorial, double precio) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.añoPublicacion = añoPublicacion;
        this.editorial = editorial;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    public void setAñoPublicacion(int añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
