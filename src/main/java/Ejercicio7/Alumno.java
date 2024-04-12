package Ejercicio7;

/**
 * <p>
 * La clase Alumno va a contener los atributos de la clase asignatura y su contrucor, getters y setters.
 * </p>
 */
public class Alumno {
    private Asignatura asignatura1;
    private Asignatura asignatura2;
    private Asignatura asignatura3;


    public Alumno(Asignatura asignatura1, Asignatura asignatura2, Asignatura asignatura3) {
        this.asignatura1 = asignatura1;
        this.asignatura2 = asignatura2;
        this.asignatura3 = asignatura3;
    }


    public Alumno(int id1, int id2, int id3) {
        this.asignatura1 = new Asignatura(id1, 0.0);
        this.asignatura2 = new Asignatura(id2, 0.0);
        this.asignatura3 = new Asignatura(id3, 0.0);
    }


    public Asignatura getAsignatura1() {
        return asignatura1;
    }

    public Asignatura getAsignatura2() {
        return asignatura2;
    }

    public Asignatura getAsignatura3() {
        return asignatura3;
    }
}

