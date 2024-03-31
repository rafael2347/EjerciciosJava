package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        Gson gson = new Gson();
        JsonObject jsonCoches = new JsonObject();

        for (int i = 0; i < listaCoches.size(); i++) {
            Coche coche = listaCoches.get(i);
            JsonObject jsonCoche = new JsonObject();
            jsonCoche.addProperty("consumo", coche.getConsumo());
            jsonCoche.addProperty("deposito", coche.getDeposito());
            jsonCoche.addProperty("autonomia", coche.autonomiaKm());
            jsonCoches.add("coche_" + i, jsonCoche);
        }

        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName))) {
            gson.toJson(jsonCoches, writer);
        }
    }


}
