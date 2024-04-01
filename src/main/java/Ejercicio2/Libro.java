package Ejercicio2;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.example.Coche;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Nota del programador: un libro que tiene?
 * titulo
 * isbn
 * autor
 * año de publicación
 * editorial
 * precio
 */

public class Libro {
    private String titulo;
    private long isbn;
    private String autor;
    private int anio;
    private String editorial;
    private double precio;

    public Libro(String titulo, long isbn, String autor, int anio, String editorial, double precio) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.anio = anio;
        this.editorial = editorial;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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

                if (!linea.isEmpty()) { // Verificar que la línea no esté vacía
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
        // create a reader
        try (Reader reader = Files.newBufferedReader(path)) {
            //Type, esta es una clase del propio lenguaje de programación Java que nos permite
            // representar cualquier tipo que el lenguaje soporte,
            // en nuestro caso una List de un tipo en especifico User
            Type tipoLista = new TypeToken<List<Coche>>() {
            }.getType();
            // convert JSON array to list of Coches
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
                Coche coche = new Coche(); // Cambiado el nombre a minúsculas
                // Parsing de los datos y conversión a Integer
                int consumo = Integer.parseInt(eElement.getElementsByTagName("Consumo").item(0).getTextContent());
                int deposito = Integer.parseInt(eElement.getElementsByTagName("Deposito").item(0).getTextContent());
                coche.setConsumo(consumo); // Actualizado a Integer
                coche.setDeposito(deposito); // Actualizado a Integer
                lista.add(coche);
            }
        }
        return lista;
    }
}
