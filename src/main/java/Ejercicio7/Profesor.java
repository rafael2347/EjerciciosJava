package Ejercicio7;

import java.util.Random;

/**
 * <p>
 * La clase Profesorcontendrá tanto la nota minima coomo la nota máxima, podrá poner calificación a la asignatura del
 * alumno aleatoriamente, porá calcular la media tomando los getters de la asignatura y la calificacion y una vez obtenida
 * la suma la dividirá entre el total de asignatura en este caso es tres para la calificacion aleatoria restará la
 * nota minima con la nota máxima, lo multiplicará por un numero aleatorio en este coso con decimales y le sumará
 * la nota minima.
 * </p>.
 */
public class Profesor {
    private static final double NOTA_MINIMA = 5.0;
    private static final double NOTA_MAXIMA = 10.0;


    public void ponerNotas(Alumno alumno) {
        asignarCalificacionAleatoria(alumno.getAsignatura1());
        asignarCalificacionAleatoria(alumno.getAsignatura2());
        asignarCalificacionAleatoria(alumno.getAsignatura3());
    }


    public double calcularMedia(Alumno alumno) {
        double sumaCalificaciones = alumno.getAsignatura1().getCalificacion() +
                alumno.getAsignatura2().getCalificacion() +
                alumno.getAsignatura3().getCalificacion();
        return sumaCalificaciones / 3.0;
    }


    private void asignarCalificacionAleatoria(Asignatura asignatura) {
        Random random = new Random();
        double calificacion = NOTA_MINIMA + (NOTA_MAXIMA - NOTA_MINIMA) * random.nextDouble();
        asignatura.setCalificacion(calificacion);
    }
}
