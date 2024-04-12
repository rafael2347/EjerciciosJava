package Ejercicio6;

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
 * La clase TrianguloRectangulo definorá los atributos de los catetos, así para poder calcular más tarde la hipotenusa,
 * vamos a empezar a hablar de la clase hipotenusa, para calcular normalmente un triangulo rectangulo su hipotenusa es
 * la suma de sus dos catetos y cada uno de ellos está elevado a dos, bueno en mi caso le he echo un pow a los catetos
 * le he puesto dos porque es elevado a dos, luego hace la suma y luego la raiz cuadrada de dicho resultado y con eso
 * obtendríamos la hipotenusa, para poder calcular el área he cogido y he multiplicado los dos catetos y dividido entre
 * dos, para calcular el perimetro, he tomado el valor de la hipotenusa ya que para ello nos hace falta, bueno para ello
 * he sumado los dos catetos y la hipotenusa y por último tenemos los métodos de leer y escribir del csv, xml y json.
 * </p>
 */
public class TrianguloRectangulo {
    private double catetoA;
    private double catetoB;

    public TrianguloRectangulo(double catetoA, double catetoB) {
        this.catetoA = catetoA;
        this.catetoB = catetoB;
    }

    public double getCatetoA() {
        return catetoA;
    }

    public void setCatetoA(double catetoA) {
        this.catetoA = catetoA;
    }

    public double getCatetoB() {
        return catetoB;
    }

    public void setCatetoB(double catetoB) {
        this.catetoB = catetoB;
    }

    @Override
    public String toString() {
        return "TrianguloRectangulo{" +
                "catetoA=" + catetoA +
                ", catetoB=" + catetoB +
                '}';
    }

    public double hipotenusa() {
        return Math.sqrt(Math.pow(catetoA, 2) + Math.pow(catetoB, 2));
    }

    public double trianguloArea() {
        return (catetoA * catetoB) / 2;
    }

    public double perimetroTriangulo() {
        double hipotenusa = hipotenusa();
        return catetoA + catetoB + hipotenusa;
    }

    public static void writeXML(List<TrianguloRectangulo> triangulos, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element triangulosElement = doc.createElement("Triangulos");
        doc.appendChild(triangulosElement);

        for (TrianguloRectangulo triangulo : triangulos) {
            Element trianguloElement = doc.createElement("Triangulo");
            trianguloElement.setAttribute("catetoA", String.valueOf(triangulo.getCatetoA()));
            trianguloElement.setAttribute("catetoB", String.valueOf(triangulo.getCatetoB()));
            triangulosElement.appendChild(trianguloElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }

    public static List<TrianguloRectangulo> readXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<TrianguloRectangulo> triangulos = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("Triangulo");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            double catetoA = Double.parseDouble(element.getAttribute("catetoA"));
            double catetoB = Double.parseDouble(element.getAttribute("catetoB"));
            triangulos.add(new TrianguloRectangulo(catetoA, catetoB));
        }
        return triangulos;
    }

    public static void writeJSON(List<TrianguloRectangulo> triangulos, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonTriangulos = new JsonArray();

        for (TrianguloRectangulo triangulo : triangulos) {
            JsonObject jsonTriangulo = new JsonObject();
            jsonTriangulo.addProperty("catetoA", triangulo.getCatetoA());
            jsonTriangulo.addProperty("catetoB", triangulo.getCatetoB());
            jsonTriangulos.add(jsonTriangulo);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(gson.toJson(jsonTriangulos));
        }
    }

    public static List<TrianguloRectangulo> readJSON(String fileName) throws IOException {
        Gson gson = new Gson();
        String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();
        List<TrianguloRectangulo> triangulos = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            double catetoA = jsonObject.get("catetoA").getAsDouble();
            double catetoB = jsonObject.get("catetoB").getAsDouble();
            triangulos.add(new TrianguloRectangulo(catetoA, catetoB));
        }
        return triangulos;
    }

    public static void writeCSV(List<TrianguloRectangulo> triangulos, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (TrianguloRectangulo triangulo : triangulos) {
                writer.write(triangulo.getCatetoA() + "," + triangulo.getCatetoB());
                writer.newLine();
            }
        }
    }

    public static List<TrianguloRectangulo> readCSV(String fileName) throws IOException {
        List<TrianguloRectangulo> triangulos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                double catetoA = Double.parseDouble(parts[0]);
                double catetoB = Double.parseDouble(parts[1]);
                triangulos.add(new TrianguloRectangulo(catetoA, catetoB));
            }
        }
        return triangulos;
    }
}
