package it.algos.vaadflow14.backend.packages.anagrafica.via;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 10-set-2020
 * Time: 11:16
 */
public enum AEVia {
    via(2),

    piazza(1),

    largo(3),

    corso(4),

    viale(5),

    vicolo(6),

    piazzale(7),

    stradone(8),

    galleria(9),

    quartiere(10),

    fondamenta(11),

    campo(12),

    calle(13),

    campiello(14),

    lungomare(15),

    bastioni(16),

    porta(17),

    giardino(18),

    sentiero(19),

    stazione(20),

    rione(21),

    banchi(22),

    costa(23),

    corte(24),

    circonvallazione(25),

    vico(26),
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
