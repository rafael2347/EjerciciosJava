package Ejercicio2;

import org.example.Coche;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestLibro {
    public static void main(String[] args) {
        Libro libro = new Libro("Meditaciones", 978849927, "Marco Aurelio", 2024, "edaf", 7.00);
        try (FileWriter salida = new FileWriter("ficheros/FicheroLibro.txt") {

            //salida.writeObject(libro);

            System.out.println(libro);
            /**GUARDAR FICHEROS
             * TXT
             * Guardo primero el deposito
             */
            salida.write("Deposito coche A: " + car.getDeposito() + "l\r");
            salida.write("Deposito coche B: " + car2.getDeposito() + "l\r");
            /*Guardo tambien el kilometraje*/
            salida.write(car.autonomiaKm()+ "\r");
            salida.write(car2.autonomiaKm()+ "\r");


            // Guardar los datos en un archivo XML
            listaCoches.add(car);
            listaCoches.add(car2);
            Coche.writeXMLCoches(listaCoches, "ficheros/FicheroCoche.xml");

            //Guardar los datos en un archivo GSON
            Coche.writeJSONCoches(listaCoches, "ficheros/FicheroCoche.json");

            //-----Para leer el csv------------------------------
            ArrayList<Coche> miListaCoches=new ArrayList<>();
            miListaCoches.add(car);
            miListaCoches.add(car2);
            Coche.writeCSVCoches(miListaCoches,"ficheros/coches");

            ArrayList<Coche> miListaCoches2 = new ArrayList<>(Coche.readCSVCoches("ficheros/coches.csv"));

            System.out.println("Hay en la lista: "+miListaCoches2);
            //--------Para leer el json--------------------------------------
            Path jsonFilePath = Paths.get("ficheros/FicheroCoche.json");
            List<Coche> cochesDesdeJSON = Coche.readJSONCoches(jsonFilePath);
            System.out.println("Coches desde JSON: " + cochesDesdeJSON);

            //--------Para leer el xml--------------------------------------
            List<Coche> cochesDesdeXML = Coche.readCochessXML("ficheros/FicheroCoche.xml");
            System.out.println("Coches desde XML: " + cochesDesdeXML);



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException   e) {
            e.printStackTrace();
        }
    }
}
