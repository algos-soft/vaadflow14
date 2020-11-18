package it.algos.simple.backend.packages;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 18.19.24 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * <p>
 * Not annotated with @SpringComponent (inutile).  <br>
 * Not annotated with @Scope (inutile). Le istanze 'prototype' vengono generate da xxxService.newEntity() <br>
 * Not annotated with @Qualifier (inutile) <br>
 * Annotated with @QueryEntity (facoltativo) per specificare che si tratta di una collection (DB Mongo) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity <br>
 * Annotated with @TypeAlias (facoltativo) to replace the fully qualified class name with a different value. <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (Lombok) con un metodo specifico, per usare quello standard nella (eventuale) sottoclasse <br>
 * - lets you automatically produce the code required to have your class be instantiable with code such as:
 * - Person.builder().name("Adam Savage").city("San Francisco").build(); <br>
 * Annotated with @EqualsAndHashCode (Lombok) per l'uguaglianza di due istanze della classe <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Grid nella lista <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici nel dialogo del Form <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * In ogni caso la ri-creazione del file header avviene sempre FINO alla Annotation @AIScript <br>
 * <p>
 * Inserisce SEMPRE la versione di serializzazione <br>
 * Le singole property sono pubbliche in modo da poterne leggere il valore tramite 'reflection' <br>
 * Le singole property sono annotate con @AIField (obbligatorio Algos) per il tipo di fields nel dialogo del Form <br>
 * Le singole property sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 * Le singole property sono annotate con @Field("xxx") (facoltativo)
 * -which gives a name to the key to be used to store the field inside the document.
 * -The property name (i.e. 'descrizione') would be used as the field key if this annotation was not included.
 * -Remember that field keys are repeated for every document so using a smaller key name will reduce the required space.
 * Le property non primitive, di default sono EMBEDDED con un riferimento statico
 * (EAFieldType.link e XxxPresenter.class)
 * Le singole property possono essere annotate con @DBRef per un riferimento DINAMICO (not embedded)
 * (EAFieldType.combo e XXService.class, con inserimento automatico nel ViewDialog)
 * Una (e una sola) property deve avere @AIColumn(flexGrow = true) per fissare la larghezza della Grid <br>
 */
@QueryEntity
@Document(collection = "alfa")
@TypeAlias("alfa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderAlfa")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "alfaRecord")
@AIView(menuIcon = VaadinIcon.BOAT)
@AIList(fields = "ordine,code,descrizione")
public class Alfa extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * ordine di presentazione (obbligatorio, unico) <br>
     * il più importante per primo <br>
     */
    @NotNull
    @Indexed()
    @Field("ord")
    @AIField(type = AETypeField.integer, widthEM = 3)
    @AIColumn(header = "#", widthEM = 3)
    public int ordine;

    /**
     * codice di riferimento (obbligatorio)
     */
    @Size(min = 2, max = 20)
    @Indexed()
    @AIField(type = AETypeField.text, required = true, focus = true, caption = "Codice", widthEM = 9)
    @AIColumn(header = "sigla", widthEM = 8)
    private String code;


    /**
     * descrizione (facoltativa)
     */
    @AIField(type = AETypeField.text, required = true, caption = "Descrizione completa", widthEM = 26)
    @AIColumn(widthEM = 20, flexGrow = true)
    private String descrizione;

    @AIField(type = AETypeField.textArea)
    private String onlyField;

    @AIColumn(type = AETypeField.integer)
    private String onlyColumn;

    @AIField(type = AETypeField.integer)
    @AIColumn(type = AETypeField.textArea)
    private String fieldAndColumn;

    @AIField()
    @AIColumn(type = AETypeField.enumeration)
    private String fieldEmptyAndColumn;

    @AIField(type = AETypeField.textArea)
    @AIColumn()
    private String fieldAndColumnEmpty;

    @AIField()
    @AIColumn()
    private String fieldEmptyAndColumnEmpty;

    @AIField(type = AETypeField.combo)
    @AIColumn(type = AETypeField.ugualeAlForm)
    private String columnComeField;

    @AIField()
    @AIColumn(type = AETypeField.ugualeAlForm)
    private String columnComeField2;

    @AIColumn(type = AETypeField.ugualeAlForm)
    private String columnComeField3;

    @AIColumn(type = AETypeField.text)
    private String columnComeField4;

    private String nessuno;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }

}