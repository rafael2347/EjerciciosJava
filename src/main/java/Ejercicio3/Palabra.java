package Ejercicio3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Palabra {
    private String palabra;

    public Palabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    @Override
    public String toString() {
        return "Palabra{" +
                "palabra='" + palabra + '\'' +
                '}';
    }

    public String devolverMayusculas() {
        return palabra.toUpperCase();
    }

    public String devolverMinusculas() {
        return palabra.toLowerCase();
    }

    public int ContadorPalabras() {
        return palabra.length();
    }

    public String reemplazarLetras(char letraOriginal, char letraReemplazo) {
        StringBuilder nuevaPalabra = new StringBuilder();

        for (int i = 0; i < palabra.length(); i++) {
            char caracterActual = palabra.charAt(i);
            if (caracterActual == letraOriginal) {
                nuevaPalabra.append(letraReemplazo);
            } else {
                nuevaPalabra.append(caracterActual);
            }
        }

        return nuevaPalabra.toString();
    }

    public String devolverPrimeraLetra() {
        char letra = palabra.charAt(0);
        return String.valueOf(letra);
    }

    public String UltimaLetraMayus() {
        char letrra = palabra.charAt(palabra.length() - 1);
        String caddena = Character.toString(letrra);
        return caddena.toUpperCase();
    }

    public static void writeXMLPalabras(List<Palabra> listaPalabras, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element palabrasElement = doc.createElement("Palabras");
        doc.appendChild(palabrasElement);

        for (Palabra palabra : listaPalabras) {
            Element palabraElement = doc.createElement("Palabra");
            palabraElement.appendChild(doc.createTextNode(palabra.getPalabra()));
            palabrasElement.appendChild(palabraElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    public static List<Palabra> readXMLPalabras(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Palabra> palabras = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("Palabra");
        for (int i = 0; i < nList.getLength(); i++) {
            Element element = (Element) nList.item(i);
            String palabra = element.getTextContent();
            palabras.add(new Palabra(palabra));
        }
        return palabras;
    }

    public static void writeJSONPalabras(List<Palabra> listaPalabras, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonPalabras = new JsonArray();

        for (Palabra palabra : listaPalabras) {
            JsonObject jsonPalabra = new JsonObject();
            jsonPalabra.addProperty("palabra", palabra.getPalabra());
            jsonPalabras.add(jsonPalabra);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(gson.toJson(jsonPalabras));
        }
    }

    public static List<Palabra> readJSONPalabras(String fileName) throws IOException {
        Gson gson = new Gson();
        String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        TypeToken<List<Palabra>> token = new TypeToken<List<Palabra>>() {
        };
        return gson.fromJson(jsonContent, token.getType());
    }
}
