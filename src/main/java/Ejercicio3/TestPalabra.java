package Ejercicio3;

public class TestPalabra {
    public static void main(String[] args) {
        Palabra palabra = new Palabra("Fantastico");
        Palabra palabra2 = new Palabra("telefono");

        //Pasar letras para convertirlas a mayúsculas
        Mayusculas(palabra, palabra2);
    }

    private static void Mayusculas(Palabra palabra, Palabra palabra2) {
        String palabro1 = palabra.devolverMayusculas();
        String palabro2 = palabra2.devolverMayusculas();
        System.out.println(palabro1);
        System.out.println(palabro2);
    }
}
