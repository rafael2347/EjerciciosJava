package Ejercicio4;

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
import java.util.Random;

public class Matematicas {
    private double numero;

    public Matematicas(double numero) {
        this.numero = numero;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Matematicas{" +
                "numero=" + numero +
                '}';
    }

    public double RaizCuadrada(){
        return Math.sqrt(numero);
    }

    public double RaizCubica(){
        return Math.cbrt(numero);
    }

    public double Redondeo(){
        return Math.ceil(numero);
    }

    public int NumAleatorio(){
        int limiteSuperior = (int) Math.ceil(Math.abs(numero));
        Random rand = new Random();
        return rand.nextInt(limiteSuperior + 1);
    }

    public static void writeXMLMatematicas(List<Matematicas> listaNumeros, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element numerosElement = doc.createElement("Numeros");
        doc.appendChild(numerosElement);

        for (Matematicas mat : listaNumeros) {
            Element numeroElement = doc.createElement("Numero");
            numeroElement.appendChild(doc.createTextNode(String.valueOf(mat.getNumero())));
            numerosElement.appendChild(numeroElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    public static List<Matematicas> readXMLMatematicas(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Matematicas> numeros = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("Numero");
        for (int i = 0; i < nList.getLength(); i++) {
            Element element = (Element) nList.item(i);
            double numero = Double.parseDouble(element.getTextContent());
            numeros.add(new Matematicas(numero));
        }
        return numeros;
    }

    public static void writeJSONMatematicas(List<Matematicas> listaNumeros, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonNumeros = new JsonArray();

        for (Matematicas mat : listaNumeros) {
            JsonObject jsonNumero = new JsonObject();
            jsonNumero.addProperty("numero", mat.getNumero());
            jsonNumeros.add(jsonNumero);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(gson.toJson(jsonNumeros));
        }
    }

    public static List<Matematicas> readJSONMatematicas(String fileName) throws IOException {
        Gson gson = new Gson();
        String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();
        List<Matematicas> numeros = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            double numero = jsonObject.get("numero").getAsDouble();
            numeros.add(new Matematicas(numero));
        }
        return numeros;
    }
    public static void writeCSVMatematicas(List<Matematicas> listaNumeros, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Matematicas mat : listaNumeros) {
                writer.write(String.valueOf(mat.getNumero()));
                writer.newLine();
            }
        }
    }

    public static List<Matematicas> readCSVMatematicas(String fileName) throws IOException {
        List<Matematicas> numeros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                double numero = Double.parseDouble(line.trim());
                numeros.add(new Matematicas(numero));
            }
        }
        return numeros;
    }

}
