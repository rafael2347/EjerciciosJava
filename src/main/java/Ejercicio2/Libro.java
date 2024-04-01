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
    private long isbn;
    private String autor;
    private int anio;
    private String editorial;
    private double precio;

    public Libro(String titulo, long isbn, String autor, int anio, String editorial, double precio) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.anio = anio;
        this.editorial = editorial;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
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
