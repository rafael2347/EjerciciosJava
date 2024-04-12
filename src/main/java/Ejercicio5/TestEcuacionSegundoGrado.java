package Ejercicio5;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * La clase test se encargará de pasarle los datos a la clase objeto para que trabaje con ellos y devuelva los datos
 * correspondientes, la lista está creada para que se pueda escribir más de un valor que salga de las operaciones en los
 * distintos archivos de ecritura y lectura.
 * </p>
 */
public class TestEcuacionSegundoGrado {
    public static void main(String[] args) {
        try {
            FileWriter salida = new FileWriter("ficheros/EcuGrado.txt");

            EcuacionSegundoGrado ecu = new EcuacionSegundoGrado(1, -5, -6);

            List<EcuacionSegundoGrado> resultados = Ecua(ecu); //Es una lista de objetos

            for (EcuacionSegundoGrado resultado : resultados) {
                double[] raiz = resultado.Ecuacion();
                for (double valor : raiz) {
                    System.out.println("resultado de la ecu: "+valor);
                    salida.write("Resultado de la ecuación: " + valor + "\n");
                }
            }

            EcuacionSegundoGrado.writeCSV(resultados, "ficheros/EcuGra.csv");
            EcuacionSegundoGrado.writeXML(resultados, "ficheros/EcuGra.xml");
            EcuacionSegundoGrado.writeJSON(resultados, "ficheros/EcuGra.json");

            List<EcuacionSegundoGrado> resultadosFromCSV = EcuacionSegundoGrado.readCSV("ficheros/EcuGra.csv");
            List<EcuacionSegundoGrado> resultadosFromXML = EcuacionSegundoGrado.readXML("ficheros/EcuGra.xml");
            List<EcuacionSegundoGrado> resultadosFromJSON = EcuacionSegundoGrado.readJSON("ficheros/EcuGra.json");

            System.out.println("Resultados desde CSV:");
            for (EcuacionSegundoGrado mat : resultadosFromCSV) {
                double[] raiz = mat.Ecuacion();
                for (double valor : raiz) {
                    System.out.println(valor);
                }
            }

            System.out.println("Resultados desde XML:");
            for (EcuacionSegundoGrado mat : resultadosFromXML) {
                double[] raiz = mat.Ecuacion();
                for (double valor : raiz) {
                    System.out.println(valor);
                }
            }

            System.out.println("Resultados desde JSON:");
            for (EcuacionSegundoGrado mat : resultadosFromJSON) {
                double[] raiz = mat.Ecuacion();
                for (double valor : raiz) {
                    System.out.println(valor);
                }
            }

            salida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static List<EcuacionSegundoGrado> Ecua(EcuacionSegundoGrado ecu) {
        double[] raiz = ecu.Ecuacion();
        EcuacionSegundoGrado resultado = new EcuacionSegundoGrado(ecu.getA(), ecu.getB(), ecu.getC());
        return List.of(resultado);
    }
}
