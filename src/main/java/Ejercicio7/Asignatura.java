package Ejercicio7;

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

public class Asignatura {
    private int id;
    private double calificacion;

    public Asignatura(int id, double calificacion) {
        this.id = id;
        this.calificacion = calificacion;
    }

    public int getId() {
        return id;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public static void writeXML(List<Asignatura> asignaturas, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element asignaturasElement = doc.createElement("Asignaturas");
        doc.appendChild(asignaturasElement);

        for (Asignatura asignatura : asignaturas) {
            Element asignaturaElement = doc.createElement("Asignatura");
            asignaturaElement.setAttribute("id", String.valueOf(asignatura.getId()));
            asignaturaElement.setAttribute("calificacion", String.valueOf(asignatura.getCalificacion()));
            asignaturasElement.appendChild(asignaturaElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    public static List<Asignatura> readXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Asignatura> asignaturas = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("Asignatura");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            int id = Integer.parseInt(element.getAttribute("id"));
            double calificacion = Double.parseDouble(element.getAttribute("calificacion"));
            asignaturas.add(new Asignatura(id, calificacion));
        }
        return asignaturas;
    }

    public static void writeJSON(List<Asignatura> asignaturas, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonAsignaturas = new JsonArray();

        for (Asignatura asignatura : asignaturas) {
            JsonObject jsonAsignatura = new JsonObject();
            jsonAsignatura.addProperty("id", asignatura.getId());
            jsonAsignatura.addProperty("calificacion", asignatura.getCalificacion());
            jsonAsignaturas.add(jsonAsignatura);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(gson.toJson(jsonAsignaturas));
        }
    }

    public static List<Asignatura> readJSON(String fileName) throws IOException {
        Gson gson = new Gson();
        String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();
        List<Asignatura> asignaturas = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            int id = jsonObject.get("id").getAsInt();
            double calificacion = jsonObject.get("calificacion").getAsDouble();
            asignaturas.add(new Asignatura(id, calificacion));
        }
        return asignaturas;
    }

    public static void writeCSV(List<Asignatura> asignaturas, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Asignatura asignatura : asignaturas) {
                writer.write(asignatura.getId() + "," + asignatura.getCalificacion());
                writer.newLine();
            }
        }
    }

    public static List<Asignatura> readCSV(String fileName) throws IOException {
        List<Asignatura> asignaturas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                double calificacion = Double.parseDouble(parts[1]);
                asignaturas.add(new Asignatura(id, calificacion));
            }
        }
        return asignaturas;
    }
}
