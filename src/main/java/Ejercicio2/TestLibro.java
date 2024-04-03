package Ejercicio2;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La clase TestLibro se encarga de crear un objeto libro, el cual pasa los datos a la clase Libro y con los getters
 * podemos traer los datos, después guardamos los datos para el fichero txt, pasarle los datos al csv, al xml y al json
 */
public class TestLibro {
    public static void main(String[] args) {
        try (FileWriter salida = new FileWriter("ficheros/FicheroLibro.txt")) {
            List<Libro> listaLibros = new ArrayList<>();

            Libro libro = new Libro("Meditaciones", 978849927, "Marco Aurelio", 2024, "edaf", 7.00);
            Libro libro2 = new Libro("Como poner un 10", 123456789, "Autor Desconocido", 2023, "Editorial XYZ", 9.99);

            //Guardar los datos en un archivo TXT
            salida.write(libro.toString()+"\r");
            salida.write(libro2.toString());

            // Guardar los datos en un archivo XML
            listaLibros.add(libro);
            listaLibros.add(libro2);
            Libro.writeXMLLibros(listaLibros, "ficheros/FicheroLibro.xml");

            // Guardar los datos en un archivo JSON
            Libro.writeJSONLibros(listaLibros, "ficheros/FicheroLibro.json");

            // Guardar los datos en un archivo CSV
            ArrayList<Libro> miListaLibros = new ArrayList<>();
            miListaLibros.add(libro);
            miListaLibros.add(libro2);
            Libro.writeCSVLibros(miListaLibros, "ficheros/FicheroLibro");

              // Leer desde un archivo CSV
              List<Libro> miListaLibros2 = Libro.readCSVLibros("ficheros/FicheroLibro.csv");
              System.out.println("Libros desde CSV:");
              for (Libro l : miListaLibros2) {
                  System.out.println(l);
              }



            // Leer desde un archivo JSON
            Path jsonFilePath = Paths.get("ficheros/FicheroLibro.json");
            List<Libro> librosDesdeJSON = Libro.readJSONLibros(jsonFilePath.toString());
            System.out.println("Libros desde JSON: " + librosDesdeJSON);

            // Leer desde un archivo XML
            List<Libro> librosDesdeXML = Libro.readXMLLibros("ficheros/FicheroLibro.xml");
            System.out.println("Libros desde XML: " + librosDesdeXML);



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
