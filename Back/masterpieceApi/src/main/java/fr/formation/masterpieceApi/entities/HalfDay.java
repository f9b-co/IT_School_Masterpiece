package fr.formation.masterpieceApi.entities;

public enum HalfDay {

    AM("matin"),
    PM("apres-midi");

    public final String label;

    private HalfDay(String label) {
        this.label = label;
    }

}
