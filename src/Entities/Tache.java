package Entities;

public class Tache {
    private String nomTache;
    private String nomPersonne;
    private boolean termine;

    public Tache(String nomTache, String nomPersonne, boolean termine) {
        this.nomTache = nomTache;
        this.nomPersonne = nomPersonne;
        this.termine = termine;
    }

    public String getNomTache() {
        return nomTache;
    }

    public String getNomPersonne() {
        return nomPersonne;
    }

    public boolean isTermine() {
        return termine;
    }
}
