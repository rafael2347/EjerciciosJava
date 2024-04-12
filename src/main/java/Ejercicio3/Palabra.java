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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Esta clase consiste en que el usuario le pasa una palabra a través de un objeto, esta la recibe la clase Palabra,
 *     la cual se encargará de almacenarla y con ella se le pasará a los demás métodos, tenemos el primer método que es
 *     devolver toda la palabra en mayúculas, para ello usamos el método que ya tiene java y se le devolverá al usuario
 *     toda la palabra en mayúscula, luego tenemos el método de devolver toda la palabra en minúscula, la cual usando un
 *     método que tiene java, se le pasará al usuario y convertirá toda la palabra a minuscula, luego tenemos el método de
 *     contador de palabras, que en este caso le devolverá cuantas letras tiene esa palabra, para ello se me ha ocurrido
 *     usar el length, ya que devuelve una longitud de una palabra, pues nos viene perfecto, luego tenemos el método de
 *     remplazar letras el cual remplaza las letras de la palabra con la a y con la u, seguidamente tenemos el metodo de
 *     devolver la primera letra, el cual dividirá a la palabra en varias partes enumeradas para eso sirve el charAt y en
 *     nuestro caso como solo queremos la primera letra, ponemos el uno, luego tenemos el método de escoger la última letra
 *     en mayúscula, la cual la palabra la pasará por un charAt, seguidamente para poder saber que es la ultima, medimos
 *     la palabra y le quitamos uno para poder coger siempre la del final, luego pasamos esa letra por un toString para
 *     convertirla a letra, porque antes era nada más que un número y luego devolvemos la letra en mayúscula con el método
 *     que ya tiene java incorporado, por ultimo tenemos los métodos de leer y escribir en csv, xml y json.
 * </p>
 */
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
    public static void writeCSVPalabras(List<Palabra> listaPalabras, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Palabra palabra : listaPalabras) {
                writer.write(palabra.getPalabra());
                writer.newLine();
            }
        }
    }

    public static List<Palabra> readCSVPalabras(String fileName) throws IOException {
        List<Palabra> palabras = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                palabras.add(new Palabra(line));
            }
        }
        return palabras;
    }

}
