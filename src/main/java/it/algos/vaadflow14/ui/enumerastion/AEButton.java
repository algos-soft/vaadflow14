package it.algos.vaadflow14.ui.enumerastion;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.ui.button.AEAction;

import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: gio, 21-mag-2020
 * Time: 06:30
 * KeyModifier.META -> quarto da sinistra, mela, command
 * KeyModifier.CONTROL -> secondo da sinistra, control,  ^
 * KeyModifier.ALT -> boh?
 * KeyModifier.SHIFT -> fila a sinistra, secondo dal basso, freccia in su
 */
public enum AEButton {

    deleteAll("Delete all", VaadinIcon.TRASH, "error", AEAction.deleteAll, true, "Cancella tutta la collezione", "cross", Key.KEY_D, KeyModifier.CONTROL),

    reset("Reset", VaadinIcon.REFRESH, "error", AEAction.reset, true, "Ripristina tutta la collezione", "cross", Key.KEY_R, KeyModifier.CONTROL),

    wiki("Wiki", VaadinIcon.GLOBE_WIRE, "secondary", AEAction.showWiki, true, "Apre la corrispondente pagina di Wikipedia", "cross", Key.KEY_R, KeyModifier.CONTROL),

    nuovo("New", VaadinIcon.PLUS_CIRCLE, "secondary", AEAction.nuovo, true, "Crea una nuova entity", "plus", Key.KEY_N, KeyModifier.CONTROL),

    back("Back", VaadinIcon.ARROW_LEFT, "secondary", AEAction.back, true, "Torna indietro", "arrow-left", Key.ARROW_LEFT, null),

    annulla("Annulla", VaadinIcon.ARROW_LEFT, "secondary", AEAction.annulla, true, "Annulla l' operazione", "arrow-left", Key.KEY_B, KeyModifier.CONTROL),

    conferma("Conferma", VaadinIcon.CHECK, "secondary", AEAction.conferma, true, "Conferma l' operazione", "checkmark", Key.KEY_S, KeyModifier.CONTROL),

    registra("Save", VaadinIcon.CHECK, "primary", AEAction.registra, true, "Registra le modifiche", "download", Key.KEY_S, KeyModifier.CONTROL),

    delete("Delete", VaadinIcon.TRASH, "error", AEAction.delete, true, "Cancella la scheda", "cross", Key.KEY_D, KeyModifier.CONTROL),

    searchDialog("Cerca...", VaadinIcon.SEARCH, "secondary", AEAction.searchDialog, true, "Apre un dialogo di ricerca", "search", Key.KEY_F, KeyModifier.CONTROL),

    ;

    /**
     * Flag di preferenza per il titolo del bottone. <br>
     */
    public String testo;

    /**
     * Flag di preferenza per l'icona (Vaadin) del bottone. <br>
     */
    public VaadinIcon vaadinIcon;

    /**
     * Flag di preferenza per l'attributo 'theme' del bottone. <br>
     */
    public String theme;

    /**
     * Flag di preferenza per l'azione del bottone. <br>
     */
    public AEAction action;

    /**
     * Flag di preferenza per la property 'enabled' del bottone. <br>
     */
    public boolean enabled;

    /**
     * Flag di preferenza per l'attributo 'title' del bottone. <br>
     */
    public String toolTip;

    /**
     * Flag di preferenza per l'icona (Lumo) del bottone. <br>
     */
    public String lumoIcon;

    /**
     * Flag di preferenza per l'attributo 'keyShortCut' del bottone. <br>
     */
    public Key keyShortCut;

    /**
     * Flag di preferenza per l'attributo 'keyModifier' del bottone. <br>
     */
    public KeyModifier keyModifier;


    AEButton(String testo, VaadinIcon vaadinIcon, String theme, AEAction action) {
        this(testo, vaadinIcon, theme, action, true, VUOTA, VUOTA);
    }


    AEButton(String testo, VaadinIcon vaadinIcon, String theme, AEAction action, boolean enabled, String toolTip, String lumoIcon) {
        this(testo, vaadinIcon, theme, action, enabled, toolTip, lumoIcon, (Key) null, (KeyModifier) null);
    }


    AEButton(String testo, VaadinIcon vaadinIcon, String theme, AEAction action, boolean enabled, String toolTip, String lumoIcon, Key keyShortCut, KeyModifier keyModifier) {
        this.testo = testo;
        this.vaadinIcon = vaadinIcon;
        this.theme = theme;
        this.action = action;
        this.enabled = enabled;
        this.toolTip = toolTip;
        this.lumoIcon = lumoIcon;
        this.keyShortCut = keyShortCut;
        this.keyModifier = keyModifier;
    }

}
