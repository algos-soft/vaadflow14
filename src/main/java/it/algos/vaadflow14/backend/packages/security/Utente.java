package it.algos.vaadflow14.backend.packages.security;

import com.mysema.query.annotations.QueryEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow14.backend.annotation.*;
import it.algos.vaadflow14.backend.entity.ACEntity;
import it.algos.vaadflow14.backend.enumeration.AETypeField;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: gio, 20-ago-2020
 * Time: 07:09
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@QueryEntity
@Document(collection = "utente")
@TypeAlias("utente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderUtente")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "Utente", keyPropertyName = "username", usaCreazioneModifica = true)
@AIView(menuIcon = VaadinIcon.USERS, sortProperty = "username")
@AIList(fields = "username,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled")
@AIForm(fields = "username,password,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled")
public class Utente extends ACEntity implements UserDetails {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    //    /**
    //     * Riferimento dinamico alla company CON @DBRef <br>
    //     * Nullo se il flag FlowVar.usaCompany=false <br>
    //     * Obbligatorio se il flag FlowVar.usaCompany=true <br>
    //     */
    //    @DBRef
    //    @AIField(type = AETypeField.combo)
    //    @AIColumn()
    //    public Company company;


    /**
     * username o nickName (obbligatorio, unico)
     */
    @NotBlank()
    @Size(min = 3)
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @Field("user")
    @AIField(type = AETypeField.text, focus = true, caption = "userName")
    @AIColumn(header = "username", widthEM = 12)
    public String username;


    /**
     * password in chiaro (obbligatoria, non unica)
     * con inserimento automatico (prima del 'save') se è nulla
     */
    @NotBlank()
    @Field("pass")
    @AIField(type = AETypeField.password)
    @AIColumn(header = "pass")
    public String password;


    /**
     * flag account valido (facoltativo, di default true)
     */
    @AIField(type = AETypeField.booleano)
    @AIColumn(header = "ane")
    public boolean accountNonExpired;


    /**
     * flag account non bloccato (facoltativo, di default true)
     */
    @AIField(type = AETypeField.booleano)
    @AIColumn(header = "anl")
    public boolean accountNonLocked;


    /**
     * flag credenziali non scadute (facoltativo, di default true)
     */
    @AIField(type = AETypeField.booleano)
    @AIColumn(header = "cne")
    public boolean credentialsNonExpired;


    /**
     * flag abilitato (facoltativo, di default true)
     */
    @AIField(type = AETypeField.booleano)
    @AIColumn(header = "ok")
    public boolean enabled;


    /**
     * GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> listAuthority = new ArrayList<>();
        GrantedAuthority authority;

        authority = new SimpleGrantedAuthority("user");
        listAuthority.add(authority);

        return listAuthority;
    }


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getUsername();
    }

}