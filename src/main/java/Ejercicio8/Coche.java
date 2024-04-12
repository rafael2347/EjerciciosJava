package Ejercicio8;

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
import java.util.List;

/**
 * <p>
 * La clase coche tiene los atributos de motor, marca, modelo, averias y como cosa peculiar, tendrá de la clase Motor
 * los caballos del coche, seguidamente he generado el constructor, los getters y setters y he creado un método el cual
 * irá sumando el número de averias, yo he querido que el número que devuelva sea siempre entero porque no puede existir
 * una averia y media, asi que siempre será entero.
 * </p>
 */
public class Coche {
    private String motor;
    private String marca;
    private String modelo;
    private double averias;
    private int cv;
    private int litrosAceite;
    private String averia;

    public Coche(String marca, String modelo, int cv) {
        this.marca = marca;
        this.modelo = modelo;
        this.cv = cv;
    }

    public String getMotor() {
        return motor;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getAverias() {
        return averias;
    }

    public int getCv() {
        return cv;
    }

    public int getLitrosAceite() {
        return litrosAceite;
    }
    public String getAveria() {
        return averia;
    }

    public void setAveria(String averia) {
        this.averia = averia;
    }

    public void incrementarLitrosAceite(int cantidad) {
        litrosAceite += cantidad;
    }

    public int acumularAveria(double nuevaAveria) {
        averias += nuevaAveria;
        return (int) averias;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cv=" + cv +
                ", litrosAceite=" + litrosAceite +
                '}';
    }

    public static void writeCSV(List<Coche> coches, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Coche coche : coches) {
                writer.write(coche.getMarca() + "," + coche.getModelo() + "," + coche.getCv() + "," + coche.getAverias() + "," + coche.getLitrosAceite() + "," + coche.getAveria());
                writer.newLine();
            }
        }
    }


    public static void readCSV(List<Coche> coches, String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String marca = parts[0];
                String modelo = parts[1];
                int cv = Integer.parseInt(parts[2]);
                double averias = Double.parseDouble(parts[3]);
                int litrosAceite = Integer.parseInt(parts[4]);
                String averia = parts[5];
                Coche coche = new Coche(marca, modelo, cv);
                coche.averias = averias;
                coche.litrosAceite = litrosAceite;
                coche.averia = averia;
                coches.add(coche);
            }
        }
    }

    public static void writeXML(List<Coche> coches, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element cochesElement = doc.createElement("Coches");
        doc.appendChild(cochesElement);

        for (Coche coche : coches) {
            Element cocheElement = doc.createElement("Coche");
            cochesElement.appendChild(cocheElement);

            Element marcaElement = doc.createElement("Marca");
            marcaElement.appendChild(doc.createTextNode(coche.getMarca()));
            cocheElement.appendChild(marcaElement);

            Element modeloElement = doc.createElement("Modelo");
            modeloElement.appendChild(doc.createTextNode(coche.getModelo()));
            cocheElement.appendChild(modeloElement);

            Element cvElement = doc.createElement("CV");
            cvElement.appendChild(doc.createTextNode(String.valueOf(coche.getCv())));
            cocheElement.appendChild(cvElement);

            Element averiasElement = doc.createElement("Averias");
            averiasElement.appendChild(doc.createTextNode(String.valueOf(coche.getAverias())));
            cocheElement.appendChild(averiasElement);

            Element litrosAceiteElement = doc.createElement("LitrosAceite");
            litrosAceiteElement.appendChild(doc.createTextNode(String.valueOf(coche.getLitrosAceite())));
            cocheElement.appendChild(litrosAceiteElement);

            Element averiaElement = doc.createElement("Averia");
            averiaElement.appendChild(doc.createTextNode(coche.getAveria()));
            cocheElement.appendChild(averiaElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    // Método para leer en formato XML
    public static void readXML(List<Coche> coches, String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("Coche");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String marca = element.getElementsByTagName("Marca").item(0).getTextContent();
            String modelo = element.getElementsByTagName("Modelo").item(0).getTextContent();
            int cv = Integer.parseInt(element.getElementsByTagName("CV").item(0).getTextContent());
            double averias = Double.parseDouble(element.getElementsByTagName("Averias").item(0).getTextContent());
            int litrosAceite = Integer.parseInt(element.getElementsByTagName("LitrosAceite").item(0).getTextContent());
            String averia = element.getElementsByTagName("Averia").item(0).getTextContent();
            Coche coche = new Coche(marca, modelo, cv);
            coche.averias = averias;
            coche.litrosAceite = litrosAceite;
            coche.averia = averia;
            coches.add(coche);
        }
    }

    // Método para escribir en formato JSON
    public static void writeJSON(List<Coche> coches, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonCoches = new JsonArray();

        for (Coche coche : coches) {
            JsonObject jsonCoche = new JsonObject();
            jsonCoche.addProperty("marca", coche.getMarca());
            jsonCoche.addProperty("modelo", coche.getModelo());
            jsonCoche.addProperty("cv", coche.getCv());
            jsonCoche.addProperty("averias", coche.getAverias());
            jsonCoche.addProperty("litrosAceite", coche.getLitrosAceite());
            jsonCoche.addProperty("averia", coche.getAveria());
            jsonCoches.add(jsonCoche);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(gson.toJson(jsonCoches));
        }
    }

    // Método para leer en formato JSON
    public static void readJSON(List<Coche> coches, String fileName) throws IOException {
        Gson gson = new Gson();
        JsonArray jsonArray = JsonParser.parseReader(new FileReader(fileName)).getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String marca = jsonObject.has("marca") ? jsonObject.get("marca").getAsString() : "";
            String modelo = jsonObject.has("modelo") ? jsonObject.get("modelo").getAsString() : "";
            int cv = jsonObject.has("cv") ? jsonObject.get("cv").getAsInt() : 0;
            double averias = jsonObject.has("averias") ? jsonObject.get("averias").getAsDouble() : 0.0;
            int litrosAceite = jsonObject.has("litrosAceite") ? jsonObject.get("litrosAceite").getAsInt() : 0;
            String averia = jsonObject.has("averia") ? jsonObject.get("averia").getAsString() : "";
            Coche coche = new Coche(marca, modelo, cv);
            coche.averias = averias;
            coche.litrosAceite = litrosAceite;
            coche.averia = averia;
            coches.add(coche);
        }
    }

}

