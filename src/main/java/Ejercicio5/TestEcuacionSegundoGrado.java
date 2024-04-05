package Ejercicio5;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestEcuacionSegundoGrado {
    public static void main(String[] args) {
        try (FileWriter salida = new FileWriter("ficheros/EcuGrado.txt")) {

            EcuacionSegundoGrado ecu = new EcuacionSegundoGrado(1, -5, 6);

            List<EcuacionSegundoGrado> resultados = Ecua(ecu);

            for (EcuacionSegundoGrado resultado : resultados) {
                double raiz = resultado.Ecuacion();
                salida.write(String.valueOf(raiz));
                salida.write(System.lineSeparator());
            }

            EcuacionSegundoGrado.writeCSV(resultados, "ficheros/EcuGra.csv");
            EcuacionSegundoGrado.writeXML(resultados, "ficheros/EcuGra.xml");
            EcuacionSegundoGrado.writeJSON(resultados, "ficheros/EcuGra.json");

            List<EcuacionSegundoGrado> resultadosFromCSV = EcuacionSegundoGrado.readCSV("ficheros/EcuGra.csv");
            List<EcuacionSegundoGrado> resultadosFromXML = EcuacionSegundoGrado.readXML("ficheros/EcuGra.xml");
            List<EcuacionSegundoGrado> resultadosFromJSON = EcuacionSegundoGrado.readJSON("ficheros/EcuGra.json");

            System.out.println("Resultados desde CSV:");
            for (EcuacionSegundoGrado mat : resultadosFromCSV) {
                System.out.println(mat.Ecuacion());
            }

            System.out.println("Resultados desde XML:");
            for (EcuacionSegundoGrado mat : resultadosFromXML) {
                System.out.println(mat.Ecuacion());
            }

            System.out.println("Resultados desde JSON:");
            for (EcuacionSegundoGrado mat : resultadosFromJSON) {
                System.out.println(mat.Ecuacion());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<EcuacionSegundoGrado> Ecua(EcuacionSegundoGrado ecu) {
        double raiz = ecu.Ecuacion();
        EcuacionSegundoGrado resultado = new EcuacionSegundoGrado(ecu.getA(), ecu.getB(), ecu.getC());
        return List.of(resultado);
    }
}
