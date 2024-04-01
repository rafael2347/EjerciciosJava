package Ejercicio2;

import org.example.Coche;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestLibro {
    public static void main(String[] args) {
        Coche car = new Coche(10, 50);
        Coche car2 = new Coche(12, 60);

        Libro libro = new Libro("Meditaciones", 978849927, "Marco Aurelio", 2024, "edaf", 7.00);
        try (FileWriter salida = new FileWriter("ficheros/FicheroLibro.txt")) {

            // Salida del objeto libro
            System.out.println(libro);

            // Guardar los datos en un archivo XML
            List<Coche> listaCoches = new ArrayList<>();
            listaCoches.add(car);
            listaCoches.add(car2);
            Coche.writeXMLCoches(listaCoches, "ficheros/FicheroCoche.xml");

            // Guardar los datos en un archivo JSON
            Coche.writeJSONCoches(listaCoches, "ficheros/FicheroCoche.json");

            // Guardar los datos en un archivo CSV
            ArrayList<Coche> miListaCoches = new ArrayList<>();
            miListaCoches.add(car);
            miListaCoches.add(car2);
            Coche.writeCSVCoches(miListaCoches, "ficheros/FicheroCoche");

            // Leer desde un archivo CSV
            List<Coche> miListaCoches2 = Coche.readCSVCoches("ficheros/FicheroCoche.csv");
            System.out.println("Hay en la lista: " + miListaCoches2);

            // Leer desde un archivo JSON
            Path jsonFilePath = Paths.get("ficheros/FicheroCoche.json");
            List<Coche> cochesDesdeJSON = Coche.readJSONCoches(jsonFilePath);
            System.out.println("Coches desde JSON: " + cochesDesdeJSON);

            // Leer desde un archivo XML
            List<Coche> cochesDesdeXML = Coche.readCochessXML("ficheros/FicheroCoche.xml");
            System.out.println("Coches desde XML: " + cochesDesdeXML);

        } catch (IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
