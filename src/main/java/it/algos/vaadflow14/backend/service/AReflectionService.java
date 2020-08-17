package it.algos.vaadflow14.backend.service;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.application.FlowCost;
import it.algos.vaadflow14.backend.entity.AEntity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.algos.vaadflow14.backend.application.FlowCost.PROPERTY_SERIAL;
import static it.algos.vaadflow14.backend.application.FlowCost.VUOTA;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 07-dic-2017
 * Time: 22:11
 * <p>
 * Utility per recuperare property e metodi da altre classi
 * Fields e values da properties statiche di ogni classe <br>
 * Fields e values da properties NON statiche di una entity <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti Spring non la costruisce <br>
 * Implementa il 'pattern' SINGLETON; l'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AReflectionService.class); <br>
 * 2) AReflectionService.getInstance(); <br>
 * 3) @Autowired private AReflectionService reflectionService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, basta il 'pattern') <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AReflectionService extends AAbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Private constructor to avoid client applications to use constructor <br>
     * In alcune circostanze SpringBoot non riesce a costruire l'istanza <br>
     * Rimesso 'public' al posto del precedente 'private' <br>
     */
    public AReflectionService() {
    }// end of constructor


    /**
     * Fields statici dichiarati in una classe generica. <br>
     * Solo la classe indicata. Non ricorsivo. Niente fields delle superClassi. <br>
     * Esclusa la property: PROPERTY_SERIAL
     * Fields non ordinati <br>
     *
     * @param genericClazz da cui estrarre i fields statici
     *
     * @return lista di static fields della classe generica
     */
    public List<Field> getFields(Class<?> genericClazz) {
        List<Field> listaFields = null;
        List<Field> listaFieldsAll = genericClazz != null ? Arrays.asList(genericClazz.getDeclaredFields()) : null;

        if (listaFieldsAll != null) {
            listaFields = new ArrayList<>();
            for (Field field : listaFieldsAll) {
                if (!field.getName().equals(PROPERTY_SERIAL)) {
                    listaFields.add(field);
                }
            }
        }

        return listaFields;
    }


    /**
     * Fields statici dichiarati in una classe di tipo AEntity. <br>
     * 1) Controlla che il parametro in ingresso non sia nullo <br>
     * 2) Controlla che il parametro in ingresso sia della classe prevista <br>
     * Ricorsivo. Comprende la entity e tutte le sue superclassi (fino a ACEntity e AEntity) <br>
     * Escluse le properties: PROPERTY_SERIAL, PROPERTY_CREAZIONE, PROPERTY_MODIFICA
     * Fields non ordinati <br>
     *
     * @param entityClazz da cui estrarre i fields statici
     *
     * @return lista di static fields della Entity e di tutte le sue superclassi
     */
    public List<Field> getAllFields(Class<? extends AEntity> entityClazz) {
        List<Field> listaFields = new ArrayList<>();
        Class<?> clazz = entityClazz;
        Field[] fieldsArray = null;

        // Controlla che il parametro in ingresso non sia nullo
        if (entityClazz == null) {
            return null;
        }

        // Controlla che il parametro in ingresso sia della classe prevista
        if (!AEntity.class.isAssignableFrom(entityClazz)) {
            return null;
        }

        //--recupera tutti i fields della entity e di tutte le superclassi
        while (clazz != Object.class) {
            try {
                fieldsArray = clazz.getDeclaredFields();
                for (Field field : fieldsArray) {
                    if (!FlowCost.ESCLUSI_ALL.contains(field.getName())) {
                        listaFields.add(field);
                    }
                }
            } catch (Exception unErrore) {
                //                logger.error(unErrore.toString());//@todo Funzionalità ancora da implementare
            }
            clazz = clazz.getSuperclass();
        }

        return listaFields;
    }


    /**
     * Nomi dei fields statici dichiarati in una classe generica. <br>
     * Solo la classe indicata. Non ricorsivo. Niente fields delle superclassi. <br>
     * Escluse le properties: PROPERTY_SERIAL, PROPERTY_CREAZIONE, PROPERTY_MODIFICA
     * Fields non ordinati <br>
     *
     * @param genericClazz da cui estrarre i fields statici
     *
     * @return lista di nomi di static fields della classe generica
     */
    public List<String> getFieldsName(Class<?> genericClazz) {
        List<String> listaNomi = new ArrayList<>();
        List<Field> listaFields = getFields(genericClazz);

        if (array.isValid(listaFields)) {
            for (Field field : listaFields) {
                listaNomi.add(field.getName());
            }
        }

        return listaNomi;
    }


    /**
     * Nomi dei fields statici dichiarati in una classe di tipo AEntity. <br>
     * Ricorsivo. Comprende la entity e tutte le sue superclassi (fino a ACEntity e AEntity) <br>
     * Escluse le properties: PROPERTY_SERIAL, PROPERTY_CREAZIONE, PROPERTY_MODIFICA
     * Nomi non ordinati
     *
     * @param entityClazz da cui estrarre i fields statici
     *
     * @return lista di nomi dei fields della Entity e di tutte le supeclassi
     */
    public List<String> getAllFieldsName(Class<? extends AEntity> entityClazz) {
        List<String> listaNomi = null;
        List<Field> listaFields = getAllFields(entityClazz);

        if (listaFields != null) {
            listaNomi = new ArrayList<>();
            for (Field field : listaFields) {
                listaNomi.add(field.getName());
            }
        }

        return listaNomi;
    }


    /**
     * Field statico di una classe generica. <br>
     *
     * @param genericClazz    da cui estrarre il field statico
     * @param publicFieldName property statica e pubblica
     *
     * @return the field
     */
    public Field getField(final Class<?> genericClazz, final String publicFieldName) {
        Field field = null;
        List<Field> listaFields = getFields(genericClazz);

        if (listaFields != null) {
            for (Field fieldTmp : listaFields) {
                if (fieldTmp.getName().equals(publicFieldName)) {
                    field = fieldTmp;
                    break;
                }
            }
        }

        if (field == null && AEntity.class.isAssignableFrom(genericClazz)) {
            listaFields = getAllFields(AEntity.class);
            if (listaFields != null) {
                for (Field fieldTmp : listaFields) {
                    if (fieldTmp.getName().equals(publicFieldName)) {
                        field = fieldTmp;
                        break;
                    }
                }
            }
        }

        return field;
    }


    /**
     * Controlla l'esistenza di un field statico di una classe generica. <br>
     *
     * @param genericClazz    da cui estrarre il field statico da controllare
     * @param publicFieldName property statica e pubblica
     *
     * @return true se esiste
     */
    public boolean hasField(final Class<?> genericClazz, final String publicFieldName) {
        return getField(genericClazz, publicFieldName) != null;
    }


    /**
     * Controlla la mancanza di un field statico di una classe generica. <br>
     *
     * @param genericClazz    da cui estrarre il field statico da controllare
     * @param publicFieldName property statica e pubblica
     *
     * @return true se NON esiste
     */
    public boolean hasNotField(final Class<?> genericClazz, final String publicFieldName) {
        return getField(genericClazz, publicFieldName) == null;
    }


    /**
     * Valore della property statica di una classe. <br>
     *
     * @param genericClazz    da cui estrarre il field statico
     * @param publicFieldName property statica e pubblica
     *
     * @return the property value
     */
    public Object getStaticPropertyValue(final Class<?> genericClazz, final String publicFieldName) {
        Object value = null;
        Field field = null;

        if (genericClazz == null || text.isEmpty(publicFieldName)) {
            return null;
        }

        field = getField(genericClazz, publicFieldName);
        if (field != null) {
            try {
                value = field.get(null);
            } catch (Exception unErrore) {
                logger.error(unErrore);
            }
        }

        return value;
    }


    /**
     * Valore stringa della property statica di una classe. <br>
     *
     * @param genericClazz    da cui estrarre il field statico
     * @param publicFieldName property statica e pubblica
     *
     * @return the string value
     */
    public String getStaticPropertyValueStr(final Class<?> genericClazz, final String publicFieldName) {
        String value = VUOTA;
        Object objValue = getStaticPropertyValue(genericClazz, publicFieldName);

        if (objValue instanceof String) {
            value = (String) objValue;
        }

        return value;
    }


    /**
     * Valore della property statica VaadinIcon di una view. <br>
     *
     * @param genericClazz su cui operare la riflessione
     *
     * @return the vaadin icon
     */
    public VaadinIcon getVaadinIcon(final Class<?> genericClazz) {
        Object objValue = getStaticPropertyValue(genericClazz, "VAADIN_ICON");
        return (objValue instanceof VaadinIcon) ? (VaadinIcon) objValue : null;
    }


    /**
     * Valore della property statica IronIcon di una view. <br>
     *
     * @param genericClazz su cui operare la riflessione
     *
     * @return the iron icon
     */
    public String getIronIcon(final Class<?> genericClazz) {
        return getStaticPropertyValueStr(genericClazz, "IRON_ICON");
    }


    /**
     * Valore della property statica menu di una view. <br>
     *
     * @param genericClazz su cui operare la riflessione
     *
     * @return the menu name
     */
    public String getMenuName(final Class<?> genericClazz) {
        return getStaticPropertyValueStr(genericClazz, "MENU_NAME");
    }


    /**
     * Valore della property corrente di una entity. <br>
     *
     * @param entityBean      oggetto su cui operare la riflessione
     * @param publicFieldName property statica e pubblica
     *
     * @return the property value
     */
    public Object getPropertyValue(final AEntity entityBean, final String publicFieldName) {
        Object value = null;
        List<Field> fieldsList = null;

        if (entityBean == null || text.isEmpty(publicFieldName)) {
            return null;
        }

        fieldsList = getAllFields(entityBean.getClass());
        try {
            for (Field field : fieldsList) {
                if (field.getName().equals(publicFieldName)) {
                    field.setAccessible(true);
                    value = field.get(entityBean);
                }
            }
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

        return value;
    }


    /**
     * Valore stringa della property corrente di una entity. <br>
     *
     * @param entityBean      oggetto su cui operare la riflessione
     * @param publicFieldName property statica e pubblica
     *
     * @return the string value
     */
    public String getPropertyValueStr(final AEntity entityBean, final String publicFieldName) {
        String value = VUOTA;
        Object objValue = getPropertyValue(entityBean, publicFieldName);

        if (objValue instanceof String) {
            value = (String) objValue;
        }

        return value;
    }


    //    /**
    //     * Mappa di tutti i valori delle properties di una classe
    //     *
    //     * @param entityBean oggetto su cui operare la riflessione
    //     */
    //    public Map<String, Object> getPropertyMap(final AEntity entityBean) {
    //        Map<String, Object> mappa = null;
    //        ArrayList<String> listaNomi = getAllFieldsName(entityBean.getClass());
    //        Object value;
    //
    //        if (array.isValid(listaNomi)) {
    //            mappa = new LinkedHashMap<>();
    //            for (String publicFieldName : listaNomi) {
    //                value = getPropertyValue(entityBean, publicFieldName);
    //                mappa.put(publicFieldName, value);
    //            }// end of for cycle
    //        }// end of if cycle
    //
    //        return mappa;
    //    }// end of method


    //    /**
    //     * Valore della property di una classe
    //     *
    //     * @param entityBean      oggetto su cui operare la riflessione
    //     * @param publicFieldName property statica e pubblica
    //     * @param value           da inserire nella property
    //     */
    //    public boolean setPropertyValue(final AEntity entityBean, final String publicFieldName, Object value) {
    //        boolean status = false;
    //        Field field = getField(entityBean.getClass(), publicFieldName);
    //
    //        if (field != null) {
    //            try { // prova ad eseguire il codice
    //                field.set(entityBean, value);
    //                status = true;
    //            } catch (Exception unErrore) { // intercetta l'errore
    //                log.error(unErrore.toString());
    //            }// fine del blocco try-catch
    //        }// end of if cycle
    //
    //        return status;
    //    }// end of method


    //    /**
    //     * Fields dichiarati nella View
    //     * Non ordinati
    //     *
    //     * @param viewClazz da cui estrarre i fields
    //     *
    //     * @return lista di fields della View
    //     */
    //    public ArrayList<Field> getAllFieldsView(Class<? extends IAView> viewClazz) {
    //        ArrayList<Field> listaFields = new ArrayList<>();
    //        Class<?> clazz = viewClazz;
    //        Field[] fieldsArray = null;
    //
    //        //--recupera tutti i fields della view
    //        try { // prova ad eseguire il codice
    //            fieldsArray = clazz.getDeclaredFields();
    //            for (Field field : fieldsArray) {
    //                listaFields.add(field);
    //            }// end of for cycle
    //        } catch (Exception unErrore) { // intercetta l'errore
    //            log.error(unErrore.toString());
    //        }// fine del blocco try-catch
    //
    //        return listaFields;
    //    }// end of method


    //    /**
    //     * Fields dichiarati nella Entity
    //     * Comprende la entity e tutte le superclassi (fino a ACEntity e AEntity)
    //     * Esclusa le properties: PROPERTY_SERIAL, PROPERTY_CREAZIONE, PROPERTY_MODIFICA
    //     * Non ordinati
    //     *
    //     * @param entityClazz da cui estrarre i fields
    //     *
    //     * @return lista di fields della Entity e di tutte le supeclassi
    //     */
    //    public ArrayList<Field> getAllFieldsNoCrono(Class<? extends AEntity> entityClazz) {
    //        ArrayList<Field> listaFields = new ArrayList<>();
    //        Class<?> clazz = entityClazz;
    //        Field[] fieldsArray = null;
    //
    //        //--recupera tutti i fields della entity e di tutte le superclassi
    //        while (clazz != Object.class) {
    //            try { // prova ad eseguire il codice
    //                fieldsArray = clazz.getDeclaredFields();
    //                for (Field field : fieldsArray) {
    //                    if (!FlowCost.ESCLUSI_ALL.contains(field.getName())) {
    //                        listaFields.add(field);
    //                    }// end of if cycle
    //                }// end of for cycle
    //            } catch (Exception unErrore) { // intercetta l'errore
    //                log.error(unErrore.toString());
    //            }// fine del blocco try-catch
    //            clazz = clazz.getSuperclass();
    //        }// end of while cycle
    //
    //        return listaFields;
    //    }// end of method


    //    /**
    //     * Nomi dei fields dichiarati nella Entity
    //     * Comprende la entity e tutte le superclassi (fino a ACEntity e AEntity)
    //     * Esclusa le properties: PROPERTY_SERIAL, PROPERTY_CREAZIONE, PROPERTY_MODIFICA
    //     * Non ordinati
    //     *
    //     * @param entityClazz da cui estrarre i fields
    //     *
    //     * @return lista di fields della Entity e di tutte le supeclassi
    //     */
    //    public ArrayList<String> getAllFieldsNameNoCrono(Class<? extends AEntity> entityClazz) {
    //        ArrayList<String> listaNomi = new ArrayList<>();
    //        Class<?> clazz = entityClazz;
    //        Field[] fieldsArray = null;
    //
    //        //--recupera tutti i fields della entity e di tutte le superclassi
    //        while (clazz != Object.class) {
    //            try { // prova ad eseguire il codice
    //                fieldsArray = clazz.getDeclaredFields();
    //                for (Field field : fieldsArray) {
    //                    if (!FlowCost.ESCLUSI_FORM.contains(field.getName())) {
    //                        listaNomi.add(field.getName());
    //                    }// end of if cycle
    //                }// end of for cycle
    //            } catch (Exception unErrore) { // intercetta l'errore
    //                log.error(unErrore.toString());
    //            }// fine del blocco try-catch
    //            clazz = clazz.getSuperclass();
    //        }// end of while cycle
    //
    //        return listaNomi;
    //    }// end of method


    //    /**
    //     * Fields dichiarati nella Entity, da usare come columns della Grid (List)
    //     * Se listaNomi è nulla, usa tutti i campi (senza ID, senza note, senza creazione, senza modifica)
    //     * Comprende la entity e tutte le superclassi (fino a ACEntity e AEntity)
    //     * Se la company2 è prevista (AlgosApp.USE_MULTI_COMPANY, login.isDeveloper() e entityClazz derivata da ACEntity),
    //     * la posiziona come prima colonna a sinistra
    //     *
    //     * @param entityClazz da cui estrarre i fields
    //     * @param listaNomi   dei fields da considerare. Tutti, se listaNomi=null
    //     *
    //     * @return lista di fields visibili nella Grid
    //     */
    //    public ArrayList<Field> getListFields(Class<? extends AEntity> entityClazz, List<String> listaNomi) {
    //        ArrayList<Field> fieldsList = new ArrayList<>();
    //        Class<?> clazz = entityClazz;
    //        ArrayList<Field> fieldsTmp = new ArrayList<>();
    //        Field[] fieldsArray = null;
    //        Field fieldCompany = null;
    //        String fieldName;
    ////        boolean useCompany = displayCompany(entityClazz); //@todo rimettere flag
    //        boolean useCompany = false;
    //
    //        //--recupera tutti i fields della entity e di tutte le superclassi
    //        while (clazz != Object.class) {
    //            try { // prova ad eseguire il codice
    //                fieldsArray = clazz.getDeclaredFields();
    //                for (Field field : fieldsArray) {
    //                    if (field.getName().equals(FlowCost.PROPERTY_COMPANY)) {
    //                        fieldCompany = field;
    //                    } else {
    //                        fieldsTmp.add(field);
    //                    }// end of if/else cycle
    //                }// end of for cycle
    //            } catch (Exception unErrore) { // intercetta l'errore
    //                log.error(unErrore.toString());
    //            }// fine del blocco try-catch
    //            clazz = clazz.getSuperclass();
    //        }// end of while cycle
    //
    //        //--controlla che i fields siano quelli richiesti
    //        //--se la lista dei nomi dei fields è nulla, li prende tutti
    //        if (array.isValid(fieldsTmp)) {
    //            if (array.isValid(listaNomi)) {
    //                for (String nome : listaNomi) {
    //                    for (Field field : fieldsTmp) {
    //                        fieldName = field.getName();
    //                        if (text.isValid(fieldName) && fieldName.equals(nome)) {
    //                            fieldsList.add(field);
    //                        }// end of if cycle
    //                    }// end of for cycle
    //                }// end of for cycle
    //            } else {
    //                for (Field field : fieldsTmp) {
    //                    fieldName = field.getName();
    //                    if (text.isValid(fieldName) && !FlowCost.ESCLUSI_LIST.contains(fieldName)) {
    //                        fieldsList.add(field);
    //                    }// end of if cycle
    //                }// end of for cycle
    //            }// end of if/else cycle
    //        }// end of if cycle
    //
    //
    //        //--se la entity è di tipo ACEntity, aggiunge (all'inizio) il field di riferimento
    //        if (useCompany && fieldCompany != null) {
    //            fieldsList.add(0, fieldCompany);
    //        }// end of if cycle
    //
    //        return fieldsList;
    //    }// end of method


    //    /**
    //     * Fields dichiarati nella Entity, da usare come campi del Form
    //     * Se listaNomi è nulla, usa tutti i campi:
    //     * user = senza ID, senza note, senza creazione, senza modifica
    //     * developer = con ID, con note, con creazione, con modifica
    //     * Comprende la entity e tutte le superclassi (fino a ACEntity e AEntity)
    //     * Se la company2 è prevista (AlgosApp.USE_MULTI_COMPANY, login.isDeveloper() e entityClazz derivata da ACEntity),
    //     * la posiziona come secondo campo in alto, dopo la keyID
    //     *
    //     * @param entityClazz da cui estrarre i fields
    //     * @param listaNomi   dei fields da considerare. Tutti, se listaNomi=null
    //     *
    //     * @return lista di fields visibili nel Form
    //     */
    //    public List<Field> getFormFields(Class<? extends AEntity> entityClazz, List<String> listaNomi) {
    //        ArrayList<Field> fieldsList = new ArrayList<>();
    //        Class<?> clazz = entityClazz;
    //        ArrayList<Field> fieldsTmp = new ArrayList<>();
    //        Field[] fieldsArray = null;
    //        Field fieldKeyId = null;
    //        Field fieldCompany = null;
    //        String fieldName;
    //        boolean isDeveloper = login.isDeveloper();
    //        boolean useCompany = displayCompany(entityClazz);
    //
    //        //--recupera tutti i fields della entity e di tutte le superclassi
    //        while (clazz != Object.class) {
    //            try { // prova ad eseguire il codice
    //                fieldsArray = clazz.getDeclaredFields();
    //                for (Field field : fieldsArray) {
    //                    if (field.getName().equals(BaseCost.PROPERTY_ID)) {
    //                        fieldKeyId = field;
    //                    }// end of if cycle
    //                    if (field.getName().equals(BaseCost.PROPERTY_COMPANY)) {
    //                        fieldCompany = field;
    //                    }// end of if cycle
    //                    fieldsTmp.add(field);
    //                }// end of for cycle
    //            } catch (Exception unErrore) { // intercetta l'errore
    //                log.error(unErrore.toString());
    //            }// fine del blocco try-catch
    //            clazz = clazz.getSuperclass();
    //        }// end of while cycle
    //
    //        //--controlla che i fields siano quelli richiesti
    //        //--se la lista dei nomi dei fields è nulla, li prende tutti
    //        if (array.isValid(fieldsTmp)) {
    //            if (array.isValid(listaNomi) || text.isValid(listaNomi) && !isDeveloper) {
    //                for (String nome : listaNomi) {
    //                    for (Field field : fieldsTmp) {
    //                        fieldName = field.getName();
    //                        if (text.isValid(fieldName) && fieldName.equals(nome)) {
    //                            fieldsList.add(field);
    //                        }// end of if cycle
    //                    }// end of for cycle
    //                }// end of for cycle
    //            } else {
    //                for (Field field : fieldsTmp) {
    //                    fieldName = field.getName();
    //                    if (isDeveloper) {
    //                        if (text.isValid(fieldName) && !fieldName.equals(BaseCost.PROPERTY_SERIAL)) {
    //                            fieldsList.add(field);
    //                        }// end of if cycle
    //                    } else {
    //                        if (text.isValid(fieldName) && !BaseCost.ESCLUSI_FORM.contains(fieldName)) {
    //                            fieldsList.add(field);
    //                        }// end of if cycle
    //                    }// end of if/else cycle
    //                }// end of for cycle
    //            }// end of if/else cycle
    //        }// end of if cycle
    //
    //        //--se la entity è di tipo ACEntity, aggiunge (all'inizio) il field di riferimento
    //        if (useCompany && fieldCompany != null) {
    //            if (fieldsList.contains(fieldCompany)) {
    //                fieldsList.remove(fieldCompany);
    //            }// end of if cycle
    //            fieldsList.add(0, fieldCompany);
    //        }// end of if cycle
    //
    //        //--se il flag booleano addKeyID è true, aggiunge (all'inizio) il field keyId
    //        if (isDeveloper && fieldKeyId != null) {
    //            if (fieldsList.contains(fieldKeyId)) {
    //                fieldsList.remove(fieldKeyId);
    //            }// end of if cycle
    //            fieldsList.add(0, fieldKeyId);
    //        }// end of if cycle
    //
    //        return fieldsList;
    //    }// end of method


    //    /**
    //     * Flag.
    //     * Deve essere true il flag useMultiCompany
    //     * La Entity deve estendere ACompanyEntity
    //     * L'user collegato deve essere developer
    //     *
    //     * @param entityClazz da cui estrarre i fields
    //     */
    //    public boolean displayCompany(Class<? extends AEntity> entityClazz) {
    //
    //        //--Deve essere true il flag useMultiCompany
    //        if (!AlgosApp.USE_MULTI_COMPANY) {
    //            return false;
    //        }// end of if cycle
    //
    //        //--La Entity deve estendere ACompanyEntity
    //        if (!ACEntity.class.isAssignableFrom(entityClazz)) {
    //            return false;
    //        }// end of if cycle
    //
    //        //--L'User collegato deve essere developer
    //        if (!login.isDeveloper()) {
    //            return false;
    //        }// end of if cycle
    //
    //        return true;
    //    }// end of method

}// end of service class
