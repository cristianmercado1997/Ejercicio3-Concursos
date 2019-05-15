package clases;

import java.util.Objects;

public class Envio {
    private final String nomEquip;
    private final int numProblema;
    private final String solucion;
    private final Evaluacion res;

    public Envio(String nombreEquipo, int numProblema, String solucion, Evaluacion resultado) {
        this.nomEquip = nombreEquipo;
        this.numProblema = numProblema;
        this.solucion = solucion;
        this.res = resultado;
    }
    
   
    public String getNomEquip() { return nomEquip; }
    public int getNumProblema() { return numProblema; }
    public String getSolucion() { return solucion; }
    public Evaluacion getRes() { return res; }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Envio other = (Envio) obj;
        if (this.numProblema != other.numProblema) {
            return false;
        }
        return Objects.equals(this.nomEquip, other.nomEquip);
    }
}
