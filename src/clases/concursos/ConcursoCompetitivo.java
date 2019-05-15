package clases.concursos;
import clases.Concurso;
import clases.Envio;
import clases.Evaluacion;
import java.util.ArrayList;

public class ConcursoCompetitivo extends Concurso {
    private ArrayList<Envio> enviosacepted;
    public ConcursoCompetitivo(String nombre, int numProblemas, long tiempoDuracion) {
        super(nombre, numProblemas, tiempoDuracion);
        this.enviosacepted = new ArrayList<>();
    }
    
    public boolean isProblemaResuelto(int numP) {
        for(Envio e : this.enviosacepted) {
            if(e.getNumProblema() == numP) {
                return true;
            }
        }
        
        return false;
    }
    @Override
    public boolean cumpleCondicionesConcurso(Envio e) {
        if(e.getRes().equals(Evaluacion.ACEPTADO)) {
            if(enviosacepted.isEmpty()) {
                enviosacepted.add(e);
                return true;
            }else{
                if(enviosacepted.contains(e)) {
                    return false;
                }else{
                    enviosacepted.add(e);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected ConcursoCompetitivo clone() {
        ConcursoCompetitivo c = (ConcursoCompetitivo) super.clone();
        c.enviosacepted = new ArrayList<>(c.enviosacepted);
        return c;
    }
}