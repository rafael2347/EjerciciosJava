package Ejercicio7;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * La clase test se encargará de pasarle a los datos a la clase Asignatura para poder trabajar con ellos y así para poder
 * sacar la media, nota final, también tiene los métodos de leer y escribir en txt, csv, xml y json.
 * </p>
 */
public class TestEjercicio7 {
    public static void main(String[] args) {
        // Crear instancias de Asignatura
        Asignatura asignatura1 = new Asignatura(1, 3.0);
        Asignatura asignatura2 = new Asignatura(2, 7.0);
        Asignatura asignatura3 = new Asignatura(3, 10.0);

        // Crear instancia de Alumno con las asignaturas
        Alumno alumno = new Alumno(asignatura1, asignatura2, asignatura3);

        // Crear instancia de Profesor
        Profesor profesor = new Profesor();

        // Poner calificaciones aleatorias al Alumno
        profesor.ponerNotas(alumno);

        // Calcular la media del Alumno
        double media = profesor.calcularMedia(alumno);
        System.out.println("La media del Alumno es: " + media);

        // TXT
        try (FileWriter salida = new FileWriter("ficheros/Escuela.txt")) {
            List<Asignatura> asignaturas = List.of(asignatura1, asignatura2, asignatura3);
            for (Asignatura asignatura : asignaturas) {
                salida.write("ID: " + asignatura.getId() + ", Calificación: " + asignatura.getCalificacion() + "\n");
            }

            // CSV
            Asignatura.writeCSV(asignaturas, "ficheros/Asignaturas.csv");

            // XML
            Asignatura.writeXML(asignaturas, "ficheros/Asignaturas.xml");

            // JSON
            Asignatura.writeJSON(asignaturas, "ficheros/Asignaturas.json");

            // Leer CSV
            List<Asignatura> asignaturasLeidasCSV = Asignatura.readCSV("ficheros/Asignaturas.csv");
            System.out.println("Asignaturas leídas desde el archivo CSV:");
            for (Asignatura asignatura : asignaturasLeidasCSV) {
                System.out.println("ID: " + asignatura.getId() + ", Calificación: " + asignatura.getCalificacion());
            }

            // Leer XML
            List<Asignatura> asignaturasLeidasXML = Asignatura.readXML("ficheros/Asignaturas.xml");
            System.out.println("Asignaturas leídas desde el archivo XML:");
            for (Asignatura asignatura : asignaturasLeidasXML) {
                System.out.println("ID: " + asignatura.getId() + ", Calificación: " + asignatura.getCalificacion());
            }

            // Leer JSON
            List<Asignatura> asignaturasLeidasJSON = Asignatura.readJSON("ficheros/Asignaturas.json");
            System.out.println("Asignaturas leídas desde el archivo JSON:");
            for (Asignatura asignatura : asignaturasLeidasJSON) {
                System.out.println("ID: " + asignatura.getId() + ", Calificación: " + asignatura.getCalificacion());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException | TransformerException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
