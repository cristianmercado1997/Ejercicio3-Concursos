package clases;

import clases.clasificacion.PuntuacionEquipo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Concurso implements Cloneable {

    protected String nombre;
    protected final int nproblemas;
    protected Set<String> CojuntoEq;
    protected final long TiemD;
    protected ArrayList<String> Snes;
    protected List<Envio> envios;
    protected boolean iniciado;

    
    protected long fin;
    protected List<PuntuacionEquipo> puntos;

    protected Concurso(String nombre, int numProblemas, long tiempoDuracion) {
        this.nombre = nombre;
        this.nproblemas = numProblemas;
        this.TiemD = tiempoDuracion;
        this.CojuntoEq = new HashSet<>();
        this.Snes = new ArrayList<>();
        this.iniciado = false;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<String> getCojuntoEq() {
        return CojuntoEq;
    }

    public int getNproblemas() {
        return nproblemas;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    public List<String> getSoluciones() {
        return new ArrayList<>(Snes);
    }

    public List<Envio> getEnvios() {
        return new ArrayList<>(envios);
    }

    
    public void añadirEquipos(String... nombres) {
        Collections.addAll(CojuntoEq, nombres);
    }

    public boolean eliminarEquipos(String nombre) {
        return CojuntoEq.remove(nombre);
    }

    
    public String getSolucion(int numProblema) {
        return Snes.get(numProblema - 1);
    }

    public void establecerSolucionProblema(int numProblema, String solucion) {
        this.Snes.add(numProblema - 1, solucion);
    }

    public boolean isPreparado() {
        return (Snes.size() == this.nproblemas);
    }

    public boolean iniciar() {
        if (isPreparado()) {
            setIniciado(true);
            this.envios = new ArrayList<>();
            this.puntos = new ArrayList<>();
            fin = System.currentTimeMillis() + (this.TiemD * 60000);
            return true;
        }

        return false;
    }

    public boolean enMarcha() {
        if (iniciado) {
            long actual = System.currentTimeMillis();
            return (actual < fin);
        }
        return false;
    }

    
    public abstract boolean cumpleCondicionesConcurso(Envio e);
    public Envio registrarEnvio(String equipo, int numP, String respuesta) {
        if (cumpleCondicionesGenerales(equipo, numP, respuesta)) {
            String str = getSolucion(numP);
            PuntuacionEquipo pq = new PuntuacionEquipo(equipo);
            Envio e;
            if (str.equals(respuesta)) {
                e = new Envio(equipo, numP, respuesta, Evaluacion.ACEPTADO);
                if (puntos.contains(pq)) {
                    for (PuntuacionEquipo p : puntos) {
                        if (p.getEquipo().equals(equipo)) {
                            p.incrementarPuntos(3);
                        }
                    }
                } else {
                    pq.incrementarPuntos(3);
                    puntos.add(pq);
                }
            } else {
                e = new Envio(equipo, numP, respuesta, Evaluacion.RECHAZADO);
                if (puntos.contains(pq)) {
                    for (PuntuacionEquipo p : puntos) {
                        if (p.getEquipo().equals(equipo)) {
                            p.decrementarPuntos(1);
                        }
                    }
                } else {
                    pq.decrementarPuntos(1);
                    puntos.add(pq);
                }
            }

            if (cumpleCondicionesConcurso(e)) {
                this.envios.add(e);
                return e;
            }
        }

        return null;
    }

    protected boolean cumpleCondicionesGenerales(String equipo, int numP, String respuesta) {
        if (this.CojuntoEq.contains(equipo)) {
            if (numP > 0 && numP <= this.nproblemas && respuesta != null && !respuesta.isEmpty()) {
                if (enMarcha()) {
                    for (Envio e : this.envios) {
                        String str = e.getNomEquip();
                        if (equipo.equals(str) && numP == e.getNumProblema()) {
                            if (e.getRes().equals(Evaluacion.ACEPTADO)) {
                                return false;
                            }
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    
    
    public void clasificaciones() {
        Collections.sort(puntos);
        System.out.println(".:CLASIFICACION:.");
        for (PuntuacionEquipo pq : puntos) {
            System.out.println(pq.getEquipo() + " -> " + pq.getPuntos());
        }
        System.out.println();
    }

    
    
    @Override
    protected Concurso clone() {
        try {
            Concurso c = (Concurso) super.clone();
            c.envios = new ArrayList<>();
            c.puntos = new ArrayList<>();
            c.CojuntoEq = new HashSet<>(c.CojuntoEq);
            c.Snes = new ArrayList<>(c.Snes);
            return c;
        } catch (CloneNotSupportedException e) {
            System.err.println("ERROR EN LA CLONACION: " + e.getMessage());
        }

        return null;
    }
}
