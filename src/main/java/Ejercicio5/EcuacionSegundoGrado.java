package Ejercicio5;

import com.google.gson.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * La clase EcuacionSegundoGrado la cual tiene los atributos de a, b y c, estos servirán para poder realizar la ecuación,
 * para crear la ecuacion de segundo grado, para ello haremos la ecuacion sin la raiz cuadrada, luego para sacar las dos
 * soluciones cogera del array los valores, si tiene usa solucion solo lo meterá por el else y harña la operación, que
 * no tiene ninguna devolvería vacio, todo esto siendo el discriminante mayor que cero.
 * Por último tenemos los métodos de leer y escribir del csv, xml y json.
 * </p>
 */
public class EcuacionSegundoGrado {
    private double a;
    private double b;
    private double c;

    public EcuacionSegundoGrado(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double[] Ecuacion() {
        double discriminante = Math.pow(b, 2) - (4 * a * c);
        double[] soluciones;

        if (discriminante >= 0) {
            // Dos soluciones
            if (discriminante > 0) {
                soluciones = new double[2];
                soluciones[0] = (-b + Math.sqrt(discriminante)) / (2 * a);
                soluciones[1] = (-b - Math.sqrt(discriminante)) / (2 * a);
            } else { // Una solución
                soluciones = new double[1];
                soluciones[0] = -b / (2 * a);
            }
        } else {
            // Sin solución
            soluciones = new double[0];
        }

        return soluciones;
    }


    public static void writeXML(List<EcuacionSegundoGrado> ecuaciones, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element ecuacionesElement = doc.createElement("Ecuaciones");
        doc.appendChild(ecuacionesElement);

        for (EcuacionSegundoGrado ecuacion : ecuaciones) {
            Element ecuacionElement = doc.createElement("Ecuacion");
            ecuacionElement.setAttribute("a", String.valueOf(ecuacion.getA()));
            ecuacionElement.setAttribute("b", String.valueOf(ecuacion.getB()));
            ecuacionElement.setAttribute("c", String.valueOf(ecuacion.getC()));

            double[] raices = ecuacion.Ecuacion();
            for (int i = 0; i < raices.length; i++) {
                Element raizElement = doc.createElement("Raiz" + (i + 1));
                raizElement.setTextContent(String.valueOf(raices[i]));
                ecuacionElement.appendChild(raizElement);
            }

            ecuacionesElement.appendChild(ecuacionElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    public static List<EcuacionSegundoGrado> readXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<EcuacionSegundoGrado> ecuaciones = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("Ecuacion");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            double a = Double.parseDouble(element.getAttribute("a"));
            double b = Double.parseDouble(element.getAttribute("b"));
            double c = Double.parseDouble(element.getAttribute("c"));
            ecuaciones.add(new EcuacionSegundoGrado(a, b, c));
        }
        return ecuaciones;
    }

    public static void writeJSON(List<EcuacionSegundoGrado> ecuaciones, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonEcuaciones = new JsonArray();

        for (EcuacionSegundoGrado ecuacion : ecuaciones) {
            JsonObject jsonEcuacion = new JsonObject();
            jsonEcuacion.addProperty("a", ecuacion.getA());
            jsonEcuacion.addProperty("b", ecuacion.getB());
            jsonEcuacion.addProperty("c", ecuacion.getC());

            double[] raices = ecuacion.Ecuacion();
            JsonArray jsonRaices = new JsonArray();
            for (double raiz : raices) {
                jsonRaices.add(raiz);
            }

            jsonEcuacion.add("raices", jsonRaices);
            jsonEcuaciones.add(jsonEcuacion);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(gson.toJson(jsonEcuaciones));
        }
    }


    public static List<EcuacionSegundoGrado> readJSON(String fileName) throws IOException {
        Gson gson = new Gson();
        String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();
        List<EcuacionSegundoGrado> ecuaciones = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            double a = jsonObject.get("a").getAsDouble();
            double b = jsonObject.get("b").getAsDouble();
            double c = jsonObject.get("c").getAsDouble();
            ecuaciones.add(new EcuacionSegundoGrado(a, b, c));
        }
        return ecuaciones;
    }

    public static void writeCSV(List<EcuacionSegundoGrado> ecuaciones, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (EcuacionSegundoGrado ecuacion : ecuaciones) {
                double[] raices = ecuacion.Ecuacion();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < raices.length; i++) {
                    sb.append(raices[i]);
                    if (i < raices.length - 1) {
                        sb.append(", ");
                    }
                }
                writer.write(ecuacion.getA() + "," + ecuacion.getB() + "," + ecuacion.getC() + "," + sb.toString());
                writer.newLine();
            }
        }
    }


    public static List<EcuacionSegundoGrado> readCSV(String fileName) throws IOException {
        List<EcuacionSegundoGrado> ecuaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                double a = Double.parseDouble(parts[0]);
                double b = Double.parseDouble(parts[1]);
                double c = Double.parseDouble(parts[2]);
                ecuaciones.add(new EcuacionSegundoGrado(a, b, c));
            }
        }
        return ecuaciones;
    }
}
