package it.algos.vaadflow14.backend.entity;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import it.algos.vaadflow14.backend.enumeration.AETypeNum;
import it.algos.vaadflow14.backend.packages.company.Company;
import it.algos.vaadflow14.backend.packages.company.CompanyLogic;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 25-ago-2020
 * Time: 11:26
 * <p>
 * Supoerclasse di tutte le entities che usano la company <br>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class ACEntity extends AEntity {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * Riferimento dinamico alla company con @DBRef (per le sottoclassi che usano questa classe)
     * - Nullo se FlowVar.usaCompany=false
     * - Facoltativo od obbligatorio a seconda della sottoclasse, se FlowVar.usaCompany=true
     */
    @DBRef
    @AIField(type = AETypeField.combo, logicClazz = CompanyLogic.class)
    @AIColumn()
    public Company company;



}