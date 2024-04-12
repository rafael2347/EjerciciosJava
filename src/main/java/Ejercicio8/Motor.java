package Ejercicio8;

/**
 * <p>
 * La clase Motor tiene los atributos de litrosAceite propios de un coche y los caballos del coche, tambien podemos ver
 * tiene por defecto que los caballos son cero y por lo demás tiene sus getters, setters y su toString.
 * </p>
 */
public class Motor {
    private int litrosAceite;
    private int cv;

    public Motor (){
        cv = 0;
    }
    public Motor(int litrosAceite, int cv) {
        this.litrosAceite = litrosAceite;
        this.cv = cv;
    }

    public int getLitrosAceite() {
        return litrosAceite;
    }

    public void setLitrosAceite(int litrosAceite) {
        this.litrosAceite = litrosAceite;
    }

    public int getCv() {
        return cv;
    }

    public void setCv(int cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return "Motor{" +
                "litrosAceite=" + litrosAceite +
                ", cv=" + cv +
                '}';
    }
}
