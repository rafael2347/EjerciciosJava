package Ejercicio8;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *     Nos hemos creado un objeto taller y dos coches con su información al respecto, luego los coches entrarán al taller
 *     por lo menos dos veces, generará una averia, luego aceptará o no según si está arreglando otro coche, y se gernerará
 *     el importe de la averia, luego comprobamos si la averia que tiene el coche es de aceite, si es así incrementaremos
 *     los nuevos litros de aceite en diez y luego el taller devolverá el coche y hacemos lo mismo para el segundo coche,
 *     luego nos dará la información del coche y si le ha añadido aceite o no, por último tenemos los métodos de escritura
 *     y lectura.
 * </p>
 */
public class TestActividad8 {
    public static void main(String[] args) {
        Taller taller = new Taller();

        Coche coche1 = new Coche("Opel", "Corsa", 100);
        Coche coche2 = new Coche("Renault", "Twingo", 150);

        
        for (int i = 0; i < 2; i++) {
            // Aceptar coche 1
            String averiaCoche1 = generarAveria();
            taller.aceptarCoche(coche1, averiaCoche1);
            coche1.acumularAveria(generarImporteAveria());

            if (averiaCoche1 != null && averiaCoche1.equals("aceite")) {
                incrementarLitrosAceite(coche1, 10);
            }
            taller.devolverCoche();

            // Aceptar coche 2
            String averiaCoche2 = generarAveria();
            taller.aceptarCoche(coche2, averiaCoche2);
            coche2.acumularAveria(generarImporteAveria());

            if (averiaCoche2 != null && averiaCoche2.equals("aceite")) {
                incrementarLitrosAceite(coche2, 10);
            }
            taller.devolverCoche();
        }

        // Mostrar información coches
        System.out.println("Información del coche 1:");
        System.out.println(coche1);
        System.out.println("Información del coche 2:");
        System.out.println(coche2);


        List<Coche> coches = new ArrayList<>();
        coches.add(coche1);
        coches.add(coche2);

        try (FileWriter salida = new FileWriter("ficheros/car.txt")) {
            //TXT
            for (Coche coche : coches) {
                salida.write(coche.toString()+"\n");
            }
            // CSV
            Coche.writeCSV(coches, "ficheros/car.csv");
            List<Coche> cochesLeidosCSV = new ArrayList<>();
            Coche.readCSV(cochesLeidosCSV, "ficheros/car.csv");
            System.out.println("Coches leídos desde CSV:");
            for (Coche coche : cochesLeidosCSV) {
                System.out.println(coche);
            }

            // XML
            Coche.writeXML(coches, "ficheros/car.xml");
            List<Coche> cochesLeidosXML = new ArrayList<>();
            Coche.readXML(cochesLeidosXML, "ficheros/car.xml");
            System.out.println("Coches leídos desde XML:");
            for (Coche coche : cochesLeidosXML) {
                System.out.println(coche);
            }

            //JSON
            Coche.writeJSON(coches, "ficheros/car.json");
            List<Coche> cochesLeidosJSON = new ArrayList<>();
            Coche.readJSON(cochesLeidosJSON, "ficheros/car.json");
            System.out.println("Coches leídos desde JSON:");
            for (Coche coche : cochesLeidosJSON) {
                System.out.println(coche);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generarAveria() {
        String[] averias = { "aceite", "frenos", "neumáticos", "transmisión" };
        Random random = new Random();
        int averiaAleatoria = random.nextInt(averias.length);
        return averias[averiaAleatoria];
    }

    private static double generarImporteAveria() {
        return Math.random() * 500;
    }

    private static void incrementarLitrosAceite(Coche coche, int cantidad) {
        coche.incrementarLitrosAceite(cantidad);
    }
}
