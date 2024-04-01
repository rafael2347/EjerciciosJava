package Ejercicio2;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestLibro {
    public static void main(String[] args) {
        Libro libro = new Libro("Meditaciones", 978849927, "Marco Aurelio", 2024, "edaf", 7.00);
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("ficheros/FicheroLibro.txt"))) {

            //salida.writeObject(libro);

            System.out.println(libro);



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException   e) {
            e.printStackTrace();
        }
    }
}
