package Ejercicio4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestMatematicas {
    public static void main(String[] args) {
        try (FileWriter salida = new FileWriter("ficheros/numero.txt")) {

            Matematicas numero = new Matematicas(33.33);
            Matematicas numero2 = new Matematicas(-22.52);

            System.out.println("------Raiz Cuadrada------");
            RaizCuadra(numero, numero2);
            System.out.println("------Raiz Cubica------");
            RaizCubicaa(numero, numero2);
            System.out.println("------Redondeo------");
            Redondeooo(numero, numero2);
            System.out.println("------Numero Aleatorio------");
            NumAleatorioo(numero, numero2);

            // Guardar en txt, xml, json y csv
            List<Matematicas> listaNumeros = List.of(numero, numero2);
            Matematicas.writeCSVMatematicas(listaNumeros, "ficheros/numeros.csv");
            Matematicas.writeXMLMatematicas(listaNumeros, "ficheros/numeros.xml");
            Matematicas.writeJSONMatematicas(listaNumeros, "ficheros/numeros.json");

            // Leer desde csv
            List<Matematicas> numerosLeidosCSV = Matematicas.readCSVMatematicas("ficheros/numeros.csv");
            System.out.println("Numeros leídos desde el archivo CSV:");
            for (Matematicas mat : numerosLeidosCSV) {
                System.out.println(mat.getNumero());
            }

            // Leer desde xml
            List<Matematicas> numerosLeidosXML = Matematicas.readXMLMatematicas("ficheros/numeros.xml");
            System.out.println("Numeros leídos desde el archivo XML:");
            for (Matematicas mat : numerosLeidosXML) {
                System.out.println(mat.getNumero());
            }

            // Leer desde json
            List<Matematicas> numerosLeidosJSON = Matematicas.readJSONMatematicas("ficheros/numeros.json");
            System.out.println("Numeros leídos desde el archivo JSON:");
            for (Matematicas mat : numerosLeidosJSON) {
                System.out.println(mat.getNumero());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void RaizCuadra(Matematicas numero, Matematicas numero2) {
        System.out.println(numero.RaizCuadrada());
        System.out.println(numero2.RaizCuadrada());
    }

    private static void RaizCubicaa(Matematicas numero, Matematicas numero2) {
        System.out.println(numero.RaizCubica());
        System.out.println(numero2.RaizCubica());
    }

    private static void Redondeooo(Matematicas numero, Matematicas numero2) {
        System.out.println(numero.Redondeo());
        System.out.println(numero2.Redondeo());
    }

    private static void NumAleatorioo(Matematicas numero, Matematicas numero2) {
        System.out.println(numero.NumAleatorio());
        System.out.println(numero2.NumAleatorio());
    }
}
