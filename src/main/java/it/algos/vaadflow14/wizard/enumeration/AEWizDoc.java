package it.algos.vaadflow14.wizard.enumeration;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: dom, 16-mag-2021
 * Time: 15:34
 */
public enum AEWizDoc {
    nullo("Nessuno", "Non modifica il file."),
    inizio("Inizio file","Modifica l'header partendo dall'inizio del file e fino al tag @AIScript(sovraScrivibile="),
    revisione("Inizio revisione","Modifica l'header partendo dalla data di revisione (compresa) e fino al tag @AIScript(sovraScrivibile="),
    ;

    private String tag;

    private String descrizione;


    /**
     * Costruttore <br>
     */
    AEWizDoc(String tag, String descrizione) {
        this.tag = tag;
        this.descrizione = descrizione;
    }

    public String getTag() {
        return tag;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
