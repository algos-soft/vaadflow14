package it.algos.vaadflow14.backend.packages.geografica.regione;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.geografica.stato.Stato;
import it.algos.vaadflow14.backend.packages.geografica.stato.StatoLogic;
import it.algos.vaadflow14.backend.wrapper.WrapDueStringhe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 12-set-2020
 * Time: 10:38
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Regione ; <br>
 * 2) StaticContextAccessor.getBean(Regione.class) (senza parametri) <br>
 * 3) appContext.getBean(Regione.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegioneLogic extends ALogic {

    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public StatoLogic statoLogic;


    /**
     * Costruttore senza parametri <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AListView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public RegioneLogic() {
        this(AEOperation.edit);
    }


    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (AILogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public RegioneLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Regione.class;
    }


    /**
     * Preferenze standard <br>
     * Primo metodo chiamato dopo init() (implicito del costruttore) e postConstruct() (facoltativo) <br>
     * Può essere sovrascritto <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.operationForm = AEOperation.edit;
        super.usaBottoneDeleteAll = true;
        super.usaBottoneReset = true;
        super.usaBottoneNew = false;
        this.usaBottonePaginaWiki = true;
        this.searchType = AESearch.editField;
        this.wikiPageTitle = "ISO_3166-2:IT";
    }


    /**
     * Crea e registra una entity solo se non esisteva <br>
     *
     * @param nome    (obbligatorio, unico)
     * @param stato   (obbligatorio)
     * @param iso     di riferimento (obbligatorio, unico)
     * @param sigla   (consuetudinaria, obbligatoria)
     * @param statuto (obbligatorio)
     *
     * @return true se la nuova entity è stata creata e salvata
     */
    public Regione crea(String nome, Stato stato, String iso, String sigla, AEStatuto statuto) {
        return (Regione) checkAndSave(newEntity(nome, stato, iso, sigla, statuto));
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity() {
        return newEntity(VUOTA, (Stato) null, VUOTA, VUOTA, (AEStatuto) null);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param nome    (obbligatorio, unico)
     * @param stato   (obbligatorio)
     * @param iso     di riferimento (obbligatorio, unico)
     * @param sigla   (consuetudinaria, obbligatoria)
     * @param statuto (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity(String nome, Stato stato, String iso, String sigla, AEStatuto statuto) {
        Regione newEntityBean = Regione.builderRegione()

                .nome(text.isValid(nome) ? nome : null)

                .stato(stato)

                .iso(text.isValid(iso) ? iso : null)

                .sigla(text.isValid(sigla) ? sigla : null)

                .statuto(statuto)

                .build();

        return (Regione) fixKey(newEntityBean);
    }


    /**
     * Creazione di alcuni dati iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo in alcuni casi) <br>
     * I dati possono essere presi da una Enumeration o creati direttamente <br>
     * Deve essere sovrascritto <br>
     *
     * @return false se non esiste il metodo sovrascritto
     * ....... true se esiste il metodo sovrascritto è la collection viene ri-creata
     */
    @Override
    public boolean reset() {
        super.deleteAll();

        italia();
        //--aggiunge le province
        //        this.addProvince();

        francia();
        svizzera();
        austria();
        germania();
        spagna();
        portogallo();
//        slovenia(); // sono troppi
        belgio();
        olanda();
        croazia();

        return mongo.isValid(entityClazz);
    }


    /**
     * Regioni italiane <br>
     */
    public void italia() {
        String path = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow14/config/regioni";
        List<LinkedHashMap<String, String>> mappaCSV;
        String nome = VUOTA;
        Stato stato = statoLogic.getItalia();
        String iso = VUOTA;
        String sigla = VUOTA;
        String statutoTxt = VUOTA;
        AEStatuto statuto = null;

        mappaCSV = fileService.leggeMappaCSV(path);
        for (LinkedHashMap<String, String> riga : mappaCSV) {
            nome = riga.get("nome");
            iso = riga.get("iso");
            sigla = riga.get("sigla");
            statutoTxt = riga.get("tipo");
            statuto = AEStatuto.get(statutoTxt);
            crea(nome, stato, iso, sigla, statuto);
        }
    }


    /**
     * Regioni francesi <br>
     */
    public void francia() {
        String paginaWiki = "ISO_3166-2:FR";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getFrancia();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "FR-";

        //--13 regioni metropolitane
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.franciaMetropolitana);
            }
        }

        //--3 regioni d'oltremare
        listaWrap = wiki.getDueColonne(paginaWiki, 3, 2, 2, 4);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.franciaOltremare);
            }
        }

        //--9 collettività d'oltremare
        listaWrap = wiki.getDueColonne(paginaWiki, 4, 2, 1, 3);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.franciaCollettivita);
            }
        }
    }


    /**
     * Cantoni svizzeri <br>
     */
    public void svizzera() {
        String paginaWiki = "ISO_3166-2:CH";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getSvizzera();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "CH-";

        //--26 cantoni
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.svizzera);
            }
        }
    }


    /**
     * Länder austriaci <br>
     */
    public void austria() {
        String paginaWiki = "ISO_3166-2:AT";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getAustria();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "AT-";

        //--9 lander
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                sigla = PUNTO + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.austria);
            }
        }
    }


    /**
     * Länder tedeschi <br>
     */
    public void germania() {
        String paginaWiki = "ISO_3166-2:DE";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getGermania();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "DE-";

        //--16 lander
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.germania);
            }
        }
    }


    /**
     * Comunità spagnole <br>
     */
    public void spagna() {
        String paginaWiki = "ISO_3166-2:ES";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getSpagna();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "ES-";

        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.spagna);
            }
        }
    }


    /**
     * Distretti portoghesi <br>
     */
    public void portogallo() {
        String paginaWiki = "ISO_3166-2:PT";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getPortogallo();
        String iso = VUOTA;
        String sigla = VUOTA;

        //--18 distretti
        listaWrap = wiki.getDueColonne(paginaWiki, 1, 2, 2, 3);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.portogalloDistretto);
            }
        }

        //--2 regioni autonome
        listaWrap = wiki.getDueColonne(paginaWiki, 2, 2, 2, 3);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.portogalloRegione);
            }
        }
    }


    /**
     * Comuni sloveni <br>
     */
    public void slovenia() {
        String paginaWiki = "ISO_3166-2:SI";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getSlovenia();
        String iso = VUOTA;
        String sigla = VUOTA;

        //--212 comuni
        listaWrap = wiki.getDueColonne(paginaWiki, 1, 2, 1, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.slovenia);
            }
        }
    }

    /**
     * Regioni belghe <br>
     */
    public void belgio() {
        String paginaWiki = "ISO_3166-2:BE";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getBelgio();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "BE-";

        //--3 regioni
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                sigla =  sigla;
                crea(nome, stato, iso, sigla, AEStatuto.belgio);
            }
        }
    }


    /**
     * Province olandesi <br>
     */
    public void olanda() {
        String paginaWiki = "ISO_3166-2:NL";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getOlanda();
        String iso = VUOTA;
        String sigla = VUOTA;
        String isoTag = "NL-";

        //--9 province
        listaWrap = wiki.getTemplateList(paginaWiki, 1, 2, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                sigla = wrap.getPrima();
                iso = isoTag + sigla;
                crea(nome, stato, iso, sigla, AEStatuto.olanda);
            }
        }
    }

    /**
     * Regioni croate <br>
     */
    public void croazia() {
        String paginaWiki = "ISO_3166-2:HR";
        List<WrapDueStringhe> listaWrap = null;
        String nome = VUOTA;
        Stato stato = statoLogic.getCroazia();
        String iso = VUOTA;
        String sigla = VUOTA;

        //--21 regioni
        listaWrap = wiki.getDueColonne(paginaWiki, 1, 2, 1, 2);
        if (listaWrap != null && listaWrap.size() > 0) {
            for (WrapDueStringhe wrap : listaWrap) {
                nome = wrap.getSeconda();
                iso = wrap.getPrima();
                sigla = text.levaTestaDa(iso, TRATTINO);
                crea(nome, stato, iso, sigla, AEStatuto.croazia);
            }
        }
    }

}