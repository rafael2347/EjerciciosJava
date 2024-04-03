package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 *<p>
 *     En la clase coche como podemos ver nos hemos creado las variables consumo y deposito, la cual nos hemos creado su
 *     constructor, sus getters y setters, para poder calcular el deposito total, le pedimos al usuario en la clase
 *     TestCoche con cuanto va a repostar, una vesz pasado el dato, se le pasa al setDeposito, el cual lo sumará al
 *     deposito que ya había.
 *     Para calcular la autonomia, cogerá los datos tanto ya el deposito total como del consumo que tiene el coche,
 *     hará una regla de tres y le devolverá al usuario la autonomía que tiene los dos coches por separado.
 *     Podemos observar los métodos de writeXMLCoches, se le pasará los datos de deposito, consumo y la autonomía.
 *     Podemos tambien el metodo writeJsonCoches, este método recorrerá la lista, la cual se he guardado la autonomia,
 *     el deposito y el consumo, luego recorrerá la lista y lo guardará en el fichero json, he creado un bucle for
 *     ya que al ser dos coches hay que recorrer la lista, si no hubiera ese bucle solo leeríamos un solo coche.
 *</p>
 */
public class Coche {
    private int consumo;
    private int deposito;

    public Coche() {
        consumo = 7;
        deposito = 5;
    }

    public Coche(int consumo, int deposito) {
        this.consumo = consumo;
        this.deposito = deposito;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public int getDeposito() {
        return deposito;
    }

    public void setDeposito(int litros) {
        this.deposito += litros;
    }

    public float autonomiaKm() {
        float totalAutonomia = (float) (deposito * 100) / consumo;
        return totalAutonomia;
    }

    @Override
    public String toString() {
        return consumo + "," + deposito;
    }


    public static void writeCSVCoches(List<Coche> listaCoches, String fileName) throws IOException {
        final String NOMBRE_FILE = fileName + ".csv";
        Files.writeString(Paths.get(NOMBRE_FILE), "consumo, deposito\n\r", StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        for (Coche coche : listaCoches) {
            Files.writeString(Paths.get(NOMBRE_FILE), coche.toString(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        }

    }

    public static void writeXMLCoches(List<Coche> listaCoches, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Elemento raíz
        Element cochesElement = doc.createElement("Coches");
        doc.appendChild(cochesElement);

        // Para cada coche en la lista
        for (Coche coche : listaCoches) {
            // Elemento coche
            Element cocheElement = doc.createElement("Coche");
            cochesElement.appendChild(cocheElement);

            // Elemento consumo
            Element consumoElement = doc.createElement("Consumo");
            consumoElement.appendChild(doc.createTextNode(String.valueOf(coche.getConsumo())));
            cocheElement.appendChild(consumoElement);

            // Elemento deposito
            Element depositoElement = doc.createElement("Deposito");
            depositoElement.appendChild(doc.createTextNode(String.valueOf(coche.getDeposito())));
            cocheElement.appendChild(depositoElement);

            // Elemento autonomía
            Element autonomiaElement = doc.createElement("Autonomia");
            autonomiaElement.appendChild(doc.createTextNode(String.valueOf(coche.autonomiaKm())));
            cocheElement.appendChild(autonomiaElement);
        }

        // Transformar y escribir en archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    /*Guardar un fichero JSON*/
    public static void writeJSONCoches(List<Coche> listaCoches, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonCoches = new JsonArray();

        for (Coche coche : listaCoches) {
            JsonObject jsonCoche = new JsonObject();
            jsonCoche.addProperty("consumo", coche.getConsumo());
            jsonCoche.addProperty("deposito", coche.getDeposito());
            jsonCoche.addProperty("autonomia", coche.autonomiaKm());
            jsonCoches.add(jsonCoche);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(gson.toJson(jsonCoches));
        }
    }

    public static List<Coche> readCSVCoches(String fileName) {
        List<Coche> coches = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

            // Verificar si la primera línea es un encabezado y omitirla si es necesario
            boolean esEncabezado = true;
            for (String linea : lineas) {
                if (esEncabezado) {
                    esEncabezado = false;
                    continue; // Saltar la primera línea del archivo CSV
                }

                if (!linea.isEmpty()) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 2 && !partes[0].isEmpty() && !partes[1].isEmpty()) { // Verificar que haya al menos dos partes (consumo y depósito) y que no estén vacías
                        int consumo = Integer.parseInt(partes[0].trim());
                        int deposito = Integer.parseInt(partes[1].trim());
                        coches.add(new Coche(consumo, deposito));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coches;
    }


    public static List<Coche> readJSONCoches(Path path) throws IOException {

        try (Reader reader = Files.newBufferedReader(path)) {
            Type tipoLista = new TypeToken<List<Coche>>() {
            }.getType();
            return new Gson().fromJson(reader, tipoLista);
        }
    }

    public static List<Coche> readCochessXML(String file) throws IOException, ParserConfigurationException, SAXException {
        List<Coche> lista = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(file));
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Coche"); // Cambiado a "Coche"
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                Coche coche = new Coche();
                int consumo = Integer.parseInt(eElement.getElementsByTagName("Consumo").item(0).getTextContent());
                int deposito = Integer.parseInt(eElement.getElementsByTagName("Deposito").item(0).getTextContent());
                coche.setConsumo(consumo);
                coche.setDeposito(deposito);
                lista.add(coche);
            }
        }
        return lista;
    }
}
