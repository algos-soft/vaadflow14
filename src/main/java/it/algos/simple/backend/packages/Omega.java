package it.algos.simple.backend.packages;

import com.querydsl.core.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: sab, 19-set-2020
 * Time: 18:45
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "omega")
@TypeAlias("omega")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderOmega")
@EqualsAndHashCode(callSuper = true)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Omega", keyPropertyName = "testo",  usaCompany = false)
@AIView(menuIcon = VaadinIcon.COG, sortProperty = "testo")
@AIList(fields = "testo,testoObbligatorio,testoObbligatorioMinimo,telefono,telefonoObbligatorio",headerAlert = "Tutti i fields di tipo 'testo'")
@AIForm(fields = "testo,testoObbligatorio,testoObbligatorioMinimo,telefono,telefonoObbligatorio")
public class Omega extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    @AIField(type = AETypeField.text)
    public String testo;

    @NotBlank()
    @AIField(type = AETypeField.text)
    public String testoObbligatorio;

    @NotBlank()
    @Size(min = 3)
    @AIField(type = AETypeField.text)
    public String testoObbligatorioMinimo;

    @AIField(type = AETypeField.phone)
    public String telefono;

    @NotBlank()
    @AIField(type = AETypeField.phone)
    public String telefonoObbligatorio;




    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getTesto();
    }

}