package Ejercicio3;


import java.util.StringTokenizer;

public class Palabra {
    private String palabra;

    public Palabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    @Override
    public String toString() {
        return "Palabra{" +
                "palabra='" + palabra + '\'' +
                '}';
    }
    public String devolverMayusculas() {
        return palabra.toUpperCase();
    }
    public String devolverMinusculas(){
        return palabra.toLowerCase();
    }
    public int ContadorPalabras() {
        if (palabra == null || palabra.isEmpty()) {
            return 0; // Si la cadena está vacía, no hay palabras
        }

        int contador = 0;
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            if (Character.isLetter(c)) {
                contador++;
            }
        }

        return contador;
    }


}
