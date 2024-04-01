package Ejercicio2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

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

    public static void writeCSVLibros(List<Libro> listaLibros, String fileName) throws IOException {
        final String NOMBRE_FILE = fileName + ".csv";
        Files.writeString(Paths.get(NOMBRE_FILE), "titulo, isbn, autor, anio, editorial, precio\n\r", StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        for (Libro libro : listaLibros) {
            Files.writeString(Paths.get(NOMBRE_FILE), libro.toString(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        }
    }

    public static void writeXMLLibros(List<Libro> listaLibros, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Elemento raíz
        Element librosElement = doc.createElement("Libros");
        doc.appendChild(librosElement);

        // Para cada libro en la lista
        for (Libro libro : listaLibros) {
            // Elemento libro
            Element libroElement = doc.createElement("Libro");
            librosElement.appendChild(libroElement);

            // Elemento titulo
            Element tituloElement = doc.createElement("Titulo");
            tituloElement.appendChild(doc.createTextNode(libro.getTitulo()));
            libroElement.appendChild(tituloElement);

            // Elemento isbn
            Element isbnElement = doc.createElement("ISBN");
            isbnElement.appendChild(doc.createTextNode(String.valueOf(libro.getIsbn())));
            libroElement.appendChild(isbnElement);

            // Elemento autor
            Element autorElement = doc.createElement("Autor");
            autorElement.appendChild(doc.createTextNode(libro.getAutor()));
            libroElement.appendChild(autorElement);

            // Elemento anio
            Element anioElement = doc.createElement("Anio");
            anioElement.appendChild(doc.createTextNode(String.valueOf(libro.getAnio())));
            libroElement.appendChild(anioElement);

            // Elemento editorial
            Element editorialElement = doc.createElement("Editorial");
            editorialElement.appendChild(doc.createTextNode(libro.getEditorial()));
            libroElement.appendChild(editorialElement);

            // Elemento precio
            Element precioElement = doc.createElement("Precio");
            precioElement.appendChild(doc.createTextNode(String.valueOf(libro.getPrecio())));
            libroElement.appendChild(precioElement);
        }

        // Transformar y escribir en archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    public static void writeJSONLibros(List<Libro> listaLibros, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonLibros = new JsonArray();

        for (Libro libro : listaLibros) {
            JsonObject jsonLibro = new JsonObject();
            jsonLibro.addProperty("titulo", libro.getTitulo());
            jsonLibro.addProperty("isbn", libro.getIsbn());
            jsonLibro.addProperty("autor", libro.getAutor());
            jsonLibro.addProperty("anio", libro.getAnio());
            jsonLibro.addProperty("editorial", libro.getEditorial());
            jsonLibro.addProperty("precio", libro.getPrecio());
            jsonLibros.add(jsonLibro);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(gson.toJson(jsonLibros));
        }
    }

    public static List<Libro> readCSVLibros(String fileName) throws IOException {
        List<Libro> libros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Ignorar la primera línea (encabezado)
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                if (partes.length == 6) {
                    String titulo = partes[0].trim();
                    long isbn = Long.parseLong(partes[1].trim());
                    String autor = partes[2].trim();
                    int anio = Integer.parseInt(partes[3].trim());
                    String editorial = partes[4].trim();
                    double precio = Double.parseDouble(partes[5].trim());
                    libros.add(new Libro(titulo, isbn, autor, anio, editorial, precio));
                }
            }
        }
        return libros;
    }

    public static List<Libro> readJSONLibros(String fileName) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(fileName))) {
            Type tipoLista = new TypeToken<List<Libro>>() {}.getType();
            return new Gson().fromJson(reader, tipoLista);
        }
    }

    public static List<Libro> readXMLLibros(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Libro> libros = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(Files.newInputStream(Paths.get(fileName)));
        NodeList nodeList = document.getElementsByTagName("Libro");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String titulo = element.getElementsByTagName("Titulo").item(0).getTextContent();
                long isbn = Long.parseLong(element.getElementsByTagName("ISBN").item(0).getTextContent());
                String autor = element.getElementsByTagName("Autor").item(0).getTextContent();
                int anio = Integer.parseInt(element.getElementsByTagName("Anio").item(0).getTextContent());
                String editorial = element.getElementsByTagName("Editorial").item(0).getTextContent();
                double precio = Double.parseDouble(element.getElementsByTagName("Precio").item(0).getTextContent());
                libros.add(new Libro(titulo, isbn, autor, anio, editorial, precio));
            }
        }
        return libros;
    }
}
