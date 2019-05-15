package main;

import clases.Concurso;
import clases.concursos.ConcursoCompetitivo;
import clases.concursos.ConcursoLimitado;
import clases.concursos.ConcursoSecuencial;
import java.util.ArrayList;
import java.util.List;

public class Programa {

  
    public static void main(String[] args) {
     
        ConcursoLimitado cl = new ConcursoLimitado("Sesión 1", 2, 5, 1); 
        cl.establecerSolucionProblema(1, "23");
        cl.establecerSolucionProblema(2, "15");
        
      
        ConcursoCompetitivo cc = new ConcursoCompetitivo("Sesión 2", 3, 15);
        cc.establecerSolucionProblema(1, "AACTG");
        cc.establecerSolucionProblema(2, "null");
        cc.establecerSolucionProblema(3, "[13, 98]");
        
    
        ConcursoSecuencial cs = new ConcursoSecuencial("Sesión 3", 3, 30, 2);
        cs.establecerSolucionProblema(1, "null");
        cs.establecerSolucionProblema(2, "[0, 3]");
        cs.establecerSolucionProblema(3, "AAA");
        
        List<Concurso> concursos = new ArrayList<>();
        concursos.add(cl);
        concursos.add(cc);
        concursos.add(cs);
        
        
        for(Concurso c : concursos) {
            System.out.println("NOMBRE DEL CONCURSO: " + c.getNombre());
            c.añadirEquipos("Equipo 1", "Equipo 2", "Equipo 3");
            c.iniciar();
            for(String str : c.getCojuntoEq()) {
                for(int i = 1; i <= c.getNproblemas(); i++) {
                    c.registrarEnvio(str, i, "null");
                }
            }
            c.clasificaciones();
        }
    }
}
