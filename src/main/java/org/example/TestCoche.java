package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *<p>
 *     Este programa realizado en Java realiza operaciones sobre el comustible y la autonomia de un coche, como podemos ver
 *     tenemos dos objetos los cuales le pasaremos a la clase coche los datos de dos vehículos, luego nos pedirá con cuanto
 *     queremos repostar, una vez introducido el dato se nos sumará  al combustible que teniamos antes, luego con el
 *     combustible añadido y el consumo nos hará una regla de tres para poder calcular la autonomia del coche
 *     todos los datos los pasamos a un método que previamente está inicializado en la parte de a bajo, cabe tambien
 *     decir que los datos de consumo, combustible y autonomia se guardan en ficheros de txt, json y xml
 *</p>
 */
public class TestCoche {
    public static void main(String[] args) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("ficheros/FicheroCoche.txt"))) {
            List<Coche> listaCoches = new ArrayList<>();

            Coche car = new Coche();
            Coche car2 = new Coche(15, 5);

            Scanner src = new Scanner(System.in);

            /*Calcular el repostaje*/
            repostar(src, car);
            repostar(src, car2);

            /*Total de combustible echado*/
            System.out.println("Combustible actual A: " + car.getDeposito());
            System.out.println("Combustible actual B: " + car2.getDeposito());

            /*Vamos a calcular la autonomía*/
            System.out.println("Autonomía del coche A:");
            calcularCombus(car);
            System.out.println("Autonomía del coche B:");
            calcularCombus(car2);
            /**GUARDAR FICHEROS
             * TXT
             * Guardo primero el deposito
             */
            salida.write(car.getDeposito());
            salida.write(car2.getDeposito());
            /*Guardo tambien el kilometraje*/
            salida.writeObject(car.autonomiaKm());
            salida.writeObject(car2.autonomiaKm());

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
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static void repostar(Scanner src, Coche car) {
        System.out.println("¿Con cuántos litros quieres repostar?");
        int litros = src.nextInt();
        car.setDeposito(litros);
        System.out.println("Has repostado con: " + car.getDeposito() + "l");
    }

    private static void calcularCombus(Coche car) {
        System.out.println("La autonomía que tiene el coche es de:");
        float autonomia = car.autonomiaKm();
        System.out.println(autonomia + " KM");
    }
}
