package Ejercicio3;

public class TestPalabra {
    public static void main(String[] args) {
        Palabra palabra = new Palabra("FANTASTICO");
        Palabra palabra2 = new Palabra("telefono");

        //Pasar letras para convertirlas a mayúsculas
        System.out.println("------Mayusculas-----");
        Mayusculas(palabra, palabra2);
        System.out.println("------Minusculas-----");
        Minusculas(palabra, palabra2);
        System.out.println("------Contar numeros---");
        Contador(palabra, palabra2);

    }

    private static void Mayusculas(Palabra palabra, Palabra palabra2) {
        String palabro1 = palabra.devolverMayusculas();
        String palabro2 = palabra2.devolverMayusculas();
        System.out.println(palabro1);
        System.out.println(palabro2);
    }

    private static void Minusculas(Palabra palabra, Palabra palabra2){
        String word = palabra.devolverMinusculas();
        String word2 = palabra2.devolverMinusculas();
        System.out.println(word);
        System.out.println(word2);

    }

    private static void Contador(Palabra palabra, Palabra palabra2){
        int palabraa = palabra.ContadorPalabras();
        int palabraa2 = palabra2.ContadorPalabras();
        System.out.println(palabraa);
        System.out.println(palabraa2);

    }
}
