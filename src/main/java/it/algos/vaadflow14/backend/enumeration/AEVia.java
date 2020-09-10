package it.algos.vaadflow14.backend.enumeration;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 10-set-2020
 * Time: 11:16
 */
public enum AEVia {
    via(1),

    piazza(2),

    largo(3),

    corso(4),

    viale(5),
    ;

    private int pos;


    AEVia(int pos) {
        this.pos = pos;
    }


    public int getPos() {
        return pos;
    }


    public String getNome() {
        return this.name();
    }

}
