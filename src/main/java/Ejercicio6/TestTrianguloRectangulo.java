package Ejercicio6;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * La clase test se va a encargar de pasarle los datos a la otra clase a través del nuevo objeto que nos hemos creado, y
 * tambien guardará todos los datos en ficheros txt, csv, xml y json.
 * </p>
 */
public class TestTrianguloRectangulo {
    public static void main(String[] args) {

        TrianguloRectangulo triangu = new TrianguloRectangulo(12.0f, 5.0f);

        System.out.println("La hipotenusa es: " + triangu.hipotenusa());
        System.out.println("El area del triangulo es: " + triangu.trianguloArea());
        System.out.println("El perimetro del triangulo es: " + triangu.perimetroTriangulo());

        try {
            FileWriter salida = new FileWriter("ficheros/Triangulo.txt");
            salida.write("La hipotenusa es: " + triangu.hipotenusa() + "\n");
            salida.write("El area del triangulo es: " + triangu.trianguloArea() + "\n");
            salida.write("El perimetro del triangulo es: " + triangu.perimetroTriangulo() + "\n");
            salida.close();

            TrianguloRectangulo.writeCSV(List.of(triangu), "ficheros/Triangulo.csv");
            TrianguloRectangulo.writeXML(List.of(triangu), "ficheros/Triangulo.xml");
            TrianguloRectangulo.writeJSON(List.of(triangu), "ficheros/Triangulo.json");

            // Leer desde los archivos
            List<TrianguloRectangulo> triangulosFromCSV = TrianguloRectangulo.readCSV("ficheros/Triangulo.csv");
            List<TrianguloRectangulo> triangulosFromXML = TrianguloRectangulo.readXML("ficheros/Triangulo.xml");
            List<TrianguloRectangulo> triangulosFromJSON = TrianguloRectangulo.readJSON("ficheros/Triangulo.json");

            // Mostrar los datos leídos
            System.out.println("Triángulos desde CSV:");
            for (TrianguloRectangulo t : triangulosFromCSV) {
                System.out.println(t);
            }

            System.out.println("Triángulos desde XML:");
            for (TrianguloRectangulo t : triangulosFromXML) {
                System.out.println(t);
            }

            System.out.println("Triángulos desde JSON:");
            for (TrianguloRectangulo t : triangulosFromJSON) {
                System.out.println(t);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


    }
}
