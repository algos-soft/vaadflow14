package it.algos.simple.backend.packages.alfa;

import com.querydsl.core.annotations.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.*;
import it.algos.vaadflow14.backend.enumeration.*;
import it.algos.vaadflow14.wizard.enumeration.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;


/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 18.19.24 <br>
 * Last doc revision: mer, 19-mag-2021 alle 18:37 <br>
 * <p>
 * Classe (obbligatoria) di un package <br>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Le properties sono PUBLIC per poter usare la Reflection ed i Test <br>
 * Unica classe obbligatoria per un package. <br>
 * Le altre servono solo se si vuole qualcosa in più dello standard minimo. <br>
 * <p>
 * Annotated with Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder, @EqualsAndHashCode <br>
 * Annotated with Algos: @AIScript per controllare il typo di file e la ri-creazione con Wizard <br>
 * Annotated with Algos: @AIEntity per informazioni sulle property per il DB <br>
 * Annotated with Algos: @AIView per info su menu, icon, route, search e sort <br>
 * Annotated with Algos: @AIList per info sulla Grid e sulle colonne <br>
 * Annotated with Algos: @AIForm per info sul Form e sulle properties <br>
 */
//Vaadin spring
@SpringComponent
//querydsl
@QueryEntity
//Spring mongodb
@Document(collection = "alfa")
//Spring data
@TypeAlias("alfa")
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderAlfa")
@EqualsAndHashCode(callSuper = false)
//Algos
@AIScript(sovraScrivibile = false, doc = AEWizDoc.inizioFile)
@AIEntity(recordName = "alfaRecord", usaCompany = true, usaNote = false)
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
    public String code;


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