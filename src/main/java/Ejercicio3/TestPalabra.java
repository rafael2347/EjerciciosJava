package Ejercicio3;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Esta es la clase test, aquí simplemente probaremos la clase Palabra, le paso a traves de un objeto una palabra en minuscula
 * y en otro objeto la palabra en mayuscula, para poder saber si se están realizando bien los métodos creados anteriormente
 * por ultimo guardamos la informacion en ficheros txt, csv, xml y json para luego leerlos, los métodos que podemos encontrar
 * más a bajo son los métodos que le vamos pasando  a los métodos de la clase Palabra.
 * </p>
 */
public class TestPalabra {
    public static void main(String[] args) {
        try (FileWriter salida = new FileWriter("ficheros/FicheroPalabra.txt")) {
            Palabra palabra = new Palabra("FANTASTICO");
            Palabra palabra2 = new Palabra("telefono");

            List<Palabra> listaPalabras = new ArrayList<>();
            listaPalabras.add(palabra);
            listaPalabras.add(palabra2);

            // Pasar letras para convertirlas a mayúsculas
            System.out.println("------Mayusculas-----");
            Mayusculas(palabra, palabra2);
            System.out.println("------Minusculas-----");
            Minusculas(palabra, palabra2);
            System.out.println("------Contar numeros---");
            Contador(palabra, palabra2);
            System.out.println("------Reemplazar letras---");
            ReemplazarLetras(palabra, palabra2, 'A', 'u');
            ReemplazarLetras(palabra, palabra2, 'e', 'a');
            System.out.println("------Devolver Primera Letra---");
            PrimeraLetra(palabra, palabra2);
            System.out.println("------Devolver Ultima Letra Mayuscula---");
            UltimaLetra(palabra, palabra2);

            // Escribir y leer archivos
            salida.write(listaPalabras+"\n");
            Palabra.writeXMLPalabras(listaPalabras, "ficheros/palabras.xml");
            Palabra.writeJSONPalabras(listaPalabras, "ficheros/palabras.json");
            Palabra.writeCSVPalabras(listaPalabras, "ficheros/palabras.csv");

            List<Palabra> palabrasFromXML = Palabra.readXMLPalabras("ficheros/palabras.xml");
            List<Palabra> palabrasFromJSON = Palabra.readJSONPalabras("ficheros/palabras.json");
            List<Palabra> palabrasFromCSV = Palabra.readCSVPalabras("ficheros/palabras.csv");

            for (Palabra p : palabrasFromXML) {
                System.out.println("Palabra desde XML: " + p.getPalabra());
            }

            for (Palabra p : palabrasFromJSON) {
                System.out.println("Palabra desde JSON: " + p.getPalabra());
            }

            for (Palabra p : palabrasFromCSV) {
                System.out.println("Palabra desde CSV: " + p.getPalabra());
            }

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private static void Mayusculas(Palabra palabra1, Palabra palabra2) {
        String palabro1 = palabra1.devolverMayusculas();
        String palabro2 = palabra2.devolverMayusculas();
        System.out.println(palabro1);
        System.out.println(palabro2);
    }

    private static void Minusculas(Palabra palabra1, Palabra palabra2) {
        String word1 = palabra1.devolverMinusculas();
        String word2 = palabra2.devolverMinusculas();
        System.out.println(word1);
        System.out.println(word2);
    }

    private static void Contador(Palabra palabra1, Palabra palabra2) {
        int palabraa1 = palabra1.ContadorPalabras();
        int palabraa2 = palabra2.ContadorPalabras();
        System.out.println(palabraa1);
        System.out.println(palabraa2);
    }

    private static void ReemplazarLetras(Palabra palabra1, Palabra palabra2, char letraOriginal, char letraReemplazo) {
        String nuevoPalabra1 = palabra1.reemplazarLetras(letraOriginal, letraReemplazo);
        String nuevoPalabra2 = palabra2.reemplazarLetras(letraOriginal, letraReemplazo);

        System.out.println("Palabra original 1: " + palabra1.getPalabra());
        System.out.println("Palabra original 2: " + palabra2.getPalabra());
        System.out.println("Palabra con letras reemplazadas 1: " + nuevoPalabra1);
        System.out.println("Palabra con letras reemplazadas 2: " + nuevoPalabra2);
    }

    private static void PrimeraLetra(Palabra palabra, Palabra palabra2) {
        String primeraLetraA = palabra.devolverPrimeraLetra();
        String primeraLetraB = palabra2.devolverPrimeraLetra();
        System.out.println(primeraLetraA);
        System.out.println(primeraLetraB);
    }

    private static void UltimaLetra(Palabra palabra, Palabra palabra2) {
        String UltimaLetraA = palabra.UltimaLetraMayus();
        String UltimaLetraB = palabra2.UltimaLetraMayus();
        System.out.println(UltimaLetraA);
        System.out.println(UltimaLetraB);
    }
}
