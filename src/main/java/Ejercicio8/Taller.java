package Ejercicio8;

/**
 * <p>
 * Para la clase Taller tendrá los atributos de cocheAntendido, averia y cochesAtendidos, con ellos el hombre del taller
 * ira aceptando los coches que le entra, para ello he creado el método de aceptar coche, si el coche es nulo porque en
 * ese momento no esté arreglando coches pues atenderá el coche, se registra la avería asociada con ese coche en el atributo
 * averia, se incrementa el contador de coches atendidos (cochesAtendidos) y se devuelve true para indicar que el coche fue
 * aceptado con éxito, si está arreglando un coche devolverá falso y no lo atenderá.
 *
 * Por ultimo tenemos el método de devolverCoche, se obtiene el total acumulado de la avería del coche que está siendo
 * atendido en el taller. Esto se hace llamando al método getAverias() del objeto cocheAtendido, que devuelve el total
 * acumulado de averías asociadas a ese coche.
 * Luego, se asigna este valor a la variable totalAveria.
 *
 * Después de obtener el total de la avería, se restablecen los atributos cocheAtendido y averia del taller. Se establecen
 * en null para indicar que el taller está listo para atender a un nuevo coche y no tiene avería asociada en este momento.
 *
 * Finalmente, el método devuelve el valor de totalAveria, que representa el total acumulado de la avería asociada al
 * coche que fue atendido en el taller.
 * </p>
 */
public class Taller {
    private Coche cocheAtendido;
    private String averia;
    private int cochesAtendidos;

    public boolean aceptarCoche(Coche coche, String averia) {
        if (cocheAtendido == null) {
            cocheAtendido = coche;
            this.averia = averia;
            cochesAtendidos++;
            return true;
        } else {
            return false;
        }
    }

    public double devolverCoche() {
        double totalAveria = cocheAtendido.getAverias();
        cocheAtendido = null;
        averia = null;
        return totalAveria;
    }

}
