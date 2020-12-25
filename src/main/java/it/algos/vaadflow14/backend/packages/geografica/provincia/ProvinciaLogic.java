package it.algos.vaadflow14.backend.packages.geografica.provincia;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AEOperation;
import it.algos.vaadflow14.backend.enumeration.AESearch;
import it.algos.vaadflow14.backend.logic.ALogic;
import it.algos.vaadflow14.backend.packages.geografica.regione.RegioneLogic;
import it.algos.vaadflow14.backend.packages.geografica.stato.StatoLogic;
import it.algos.vaadflow14.ui.enumeration.AEVista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 15-set-2020
 * Time: 17:59
 * <p>
 * Classe concreta specifica di gestione della 'business logic' di una Entity e di un Package <br>
 * NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * L' istanza può essere richiamata con: <br>
 * 1) @Autowired private Provincia ; <br>
 * 2) StaticContextAccessor.getBean(Provincia.class) (senza parametri) <br>
 * 3) appContext.getBean(Provincia.class) (preceduto da @Autowired ApplicationContext appContext) <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProvinciaLogic extends ALogic {

    public static final String FIELD_REGIONE = "regione";

    public static final String FIELD_STATO = "stato";

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
    public RegioneLogic regioneLogic;

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
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName)) <br>
     */
    public ProvinciaLogic() {
        this(AEOperation.edit);
    }


    /**
     * Costruttore con parametro <br>
     * Not annotated with @Autowired annotation, per creare l' istanza SOLO come SCOPE_PROTOTYPE <br>
     * Costruttore usato da AFormView <br>
     * L' istanza DEVE essere creata con (ALogic) appContext.getBean(Class.forName(canonicalName), operationForm) <br>
     *
     * @param operationForm tipologia di Form in uso
     */
    public ProvinciaLogic(AEOperation operationForm) {
        super(operationForm);
        super.entityClazz = Provincia.class;
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

        super.usaBottoneDeleteAll = false;
        super.usaBottoneResetList = true;
        super.usaBottoneNew = true;
        super.usaBottonePaginaWiki = true;
        super.searchType = AESearch.editField;
        super.wikiPageTitle = "ISO_3166-2";
        super.formClazz = ProvinciaForm.class;
    }


    /**
     * Costruisce una lista di informazioni per costruire l' istanza di AHeaderList <br>
     * Informazioni (eventuali) specifiche di ogni modulo <br>
     * Deve essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     * Esempio:     return new ArrayList(Arrays.asList("uno", "due", "tre"));
     *
     * @param typeVista in cui inserire gli avvisi
     *
     * @return wrapper per passaggio dati
     */
    @Override
    protected List<String> getAlertList(AEVista typeVista) {
        List<String> lista = super.getAlertList(typeVista);

        if (typeVista == AEVista.list) {
            lista.add("Province italiane. Codifica secondo ISO 3166-2.");
            lista.add("Recuperati dalla pagina wiki: " + wikiPageTitle);
            lista.add("Codice ISO, sigla abituale e 'status' normativo");
            lista.add("Ordinamento alfabetico: prima le città metropolitane e poi le altre");
        }

        return lista;
    }


//    /**
//     * Costruisce una mappa di ComboBox di selezione e filtro <br>
//     * DEVE essere sovrascritto nella sottoclasse <br>
//     */
//    @Override
//    protected void fixMappaComboBox() {
//        ComboBox comboBox;
//
//        //        List<Regione> items = regioneLogic.findAllItalian();
//        //        super.creaComboBox("regione", items, 14, null);
//        //        super.creaComboBox("regione");
//        //        mappaComboBox.put("regione", regioneLogic.creaComboRegioniItaliane());
//        //        super.creaComboBox("stato", AEStato.italia.getStato());//@todo senza bandierine
//        //        super.creaComboBox("status", 16);
//
//        //        //--in realtà il render non è necessario. Basta il toString() nella Enumeration.
//        //        //--rimane come esempio
//        //        comboBox.setRenderer(TemplateRenderer.<AETypeProvincia>of("<div>[[item.tag]]</div>").withProperty("tag", AETypeProvincia::getTag));
//    }


//    /**
//     * Crea e registra una entity solo se non esisteva <br>
//     *
//     * @param ordine  (obbligatorio, unico)
//     * @param nome    (obbligatorio, unico)
//     * @param sigla   (consuetudinaria, obbligatoria)
//     * @param regione (obbligatorio)
//     * @param stato   (obbligatorio)
//     * @param iso     di riferimento (obbligatorio, unico)
//     * @param status  (obbligatorio)
//     *
//     * @return la nuova entity appena creata e salvata
//     */
//    public Provincia creaIfNotExist(int ordine, String nome, String sigla, Regione regione, Stato stato, String iso, AETypeProvincia status) {
//        return (Provincia) checkAndSave(newEntity(ordine, nome, sigla, regione, stato, iso, status));
//    }


//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata <br>
//     * Usa il @Builder di Lombok <br>
//     * Eventuali regolazioni iniziali delle property <br>
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    public Provincia newEntity() {
//        return newEntity(0, VUOTA, VUOTA, (Regione) null, (Stato) null, VUOTA, (AETypeProvincia) null);
//    }


//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata <br>
//     * Usa il @Builder di Lombok <br>
//     * Eventuali regolazioni iniziali delle property <br>
//     *
//     * @param ordine  (obbligatorio, unico)
//     * @param nome    (obbligatorio, unico)
//     * @param sigla   (consuetudinaria, obbligatoria)
//     * @param regione (obbligatorio)
//     * @param stato   (obbligatorio)
//     * @param iso     di riferimento (obbligatorio, unico)
//     * @param status  (obbligatorio)
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    public Provincia newEntity(int ordine, String nome, String sigla, Regione regione, Stato stato, String iso, AETypeProvincia status) {
//        Provincia newEntityBean = Provincia.builderProvincia()
//
//                .ordine(ordine > 0 ? ordine : getNewOrdine())
//
//                .nome(text.isValid(nome) ? nome : null)
//
//                .sigla(text.isValid(sigla) ? sigla : null)
//
//                .regione(regione)
//
//                .stato(stato)
//
//                .iso(text.isValid(iso) ? iso : null)
//
//                .status(status != null ? status : AETypeProvincia.provincia)
//
//                .build();
//
//        return (Provincia) fixKey(newEntityBean);
//    }


    /**
     * Operazioni eseguite PRIMA di save o di insert <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Controllo per la presenza della company se FlowVar.usaCompany=true <br>
     * Controlla se la entity registra le date di creazione e modifica <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
//    @Override
    public AEntity beforeSave2(AEntity entityBean, AEOperation operation) {
        Provincia provincia = (Provincia) super.beforeSave(entityBean, operation);

        //--controllla la congruità dei due comboBox: master e slave
        //--dovrebbe già essere controllato nel Form, ma meglio ricontrollare
        if (provincia.regione.stato.id.equals(provincia.stato.id)) {
            return provincia;
        }
        else {
            Notification.show("La regione " + provincia.regione.divisione + " non appartiene allo stato " + provincia.stato.stato + " e non è stata registrata", 3000, Notification.Position.MIDDLE);
            logger.error("La regione " + provincia.regione.divisione + " non appartiene allo stato " + provincia.stato.stato, this.getClass(), "beforeSave");
            return null;
        }
    }


//    /**
//     * Retrieves an entity by its id.
//     *
//     * @param keyID must not be {@literal null}.
//     *
//     * @return the entity with the given id or {@literal null} if none found
//     *
//     * @throws IllegalArgumentException if {@code id} is {@literal null}
//     */
//    @Override
//    public Provincia findById(String keyID) {
//        return (Provincia) super.findById(keyID);
//    }
//
//
//    /**
//     * Retrieves an entity by its keyProperty.
//     *
//     * @param keyValue must not be {@literal null}.
//     *
//     * @return the entity with the given id or {@literal null} if none found
//     *
//     * @throws IllegalArgumentException if {@code id} is {@literal null}
//     */
//    @Override
//    public Provincia findByKey(String keyValue) {
//        return (Provincia) super.findByKey(keyValue);
//    }



//    public void creaBlocco(int posTabella, int ordine, AETypeProvincia status) {
//        List<WrapTreStringhe> listaWrapTre;
//        String wikiTitle = "ISO 3166-2:IT";
//        String nome;
//        String sigla;
//        Regione regione;
//        Stato stato = AEStato.italia.getStato();
//        String iso;
//
//        listaWrapTre = wiki.getTemplateList(wikiTitle, posTabella, 2, 1, 3);
//        if (listaWrapTre != null && listaWrapTre.size() > 0) {
//            for (WrapTreStringhe wrap : listaWrapTre) {
//                ordine++;
//                nome = wrap.getSeconda();
//                sigla = wrap.getPrima();
//                regione = regioneLogic.findByIsoItalian(wrap.getTerza());
//                iso = "IT-" + sigla;
//                if (status != AETypeProvincia.metropolitana) {
//                    status = AETypeProvincia.findBySigla(regione.sigla);
//                }
//                if (status == null) {
//                    status = AETypeProvincia.provincia;
//                }
//
//                creaIfNotExist(ordine, nome, sigla, regione, stato, iso, status);
//            }
//
//        }
//    }

//    /**
//     * Creazione o ricreazione di alcuni dati iniziali standard <br>
//     * Invocato in fase di 'startup' e dal bottone Reset di alcune liste <br>
//     * <p>
//     * 1) deve esistere lo specifico metodo sovrascritto
//     * 2) deve essere valida la entityClazz
//     * 3) deve esistere la collezione su mongoDB
//     * 4) la collezione non deve essere vuota
//     * <p>
//     * I dati possono essere: <br>
//     * 1) recuperati da una Enumeration interna <br>
//     * 2) letti da un file CSV esterno <br>
//     * 3) letti da Wikipedia <br>
//     * 4) creati direttamente <br>
//     * DEVE essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
//     *
//     * @return wrapper col risultato ed eventuale messaggio di errore
//     */
//    //    @Override
//    public AIResult resetEmptyOnly2() {
//        AIResult result = super.resetEmptyOnly();
//        AIResult resultCollectionPropedeutica;
//        int numRec = 0;
//
//        if (result.isErrato()) {
//            return result;
//        }
//
//        resultCollectionPropedeutica = checkRegione();
//        if (resultCollectionPropedeutica.isValido()) {
//            logger.log(AETypeLog.checkData, resultCollectionPropedeutica.getMessage());
//        }
//        else {
//            return resultCollectionPropedeutica;
//        }
//
//        return creaProvinceItaliane();
//    }
//
//
//    private AIResult checkRegione() {
//        String collection = "regione";
//        RegioneLogic regioneLogic;
//
//        if (mongo.isValid(collection)) {
//            return AResult.valido("La collezione " + collection + " esiste già e non è stata modificata");
//        }
//        else {
//            regioneLogic = appContext.getBean(RegioneLogic.class);
//            if (regioneLogic == null) {
//                return AResult.errato("Manca la classe RegioneLogic");
//            }
//            else {
//                return regioneLogic.resetEmptyOnly();
//            }
//        }
//    }
//
//    private AIResult creaProvinceItaliane() {
//        AIResult result = AResult.errato();
//        List<WrapTreStringhe> listaWrap;
//        Provincia provincia;
//        int ordine = 0;
//        String nome;
//        String sigla = VUOTA;
//        String regioneTxt;
//        Regione regione = null;
//        Stato stato = AEStato.italia.getStato();
//        String iso;
//        AETypeProvincia status = AETypeProvincia.provincia;
//
//        listaWrap = wiki.getProvince();
//        if (listaWrap != null && listaWrap.size() > 0) {
//            for (WrapTreStringhe wrap : listaWrap) {
//                nome = wrap.getSeconda();
//                sigla = wrap.getPrima();
//                iso = sigla;
//                regioneTxt = wrap.getTerza().toLowerCase();
//                regione = regioneLogic.findById(regioneTxt);
//
//                if (text.isValid(nome) && stato != null && text.isValid(iso) && text.isValid(sigla)) {
//                    provincia = creaIfNotExist(ordine, nome, sigla, regione, stato, iso, status);
//                    if (provincia != null) {
//                        ordine++;
//                    }
//                }
//                else {
//                    logger.warn("Mancano dati essenziali", this.getClass(), "creaProvinceItaliane");
//                }
//            }
//            result = AResult.valido("Province italiane: ", ordine);
//        }
//        else {
//            result = AResult.errato("Non sono riuscito a trovare la table nella pagina di wikipedia 'Province d'Italia'");
//        }
//
//        return result;
//    }

}