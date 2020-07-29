package it.algos.vaadflow14.ui.form;

import it.algos.vaadflow14.backend.entity.AEntity;
import it.algos.vaadflow14.ui.fields.AField;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Project vaadflow15
 * Created by Algos
 * User: gac
 * Date: mer, 10-giu-2020
 * Time: 21:15
 * Wrap di informazioni passato dalla Logic alla creazione del Form <br>
 * La Logic mantiene lo stato ed elabora informazioni che verranno usate dal Form <br>
 */
public class WrapForm {


    private AEntity entityBean = null;

    private Class<? extends AEntity> entityClazz = null;

    private boolean usaTopLayout = true;

    private int stepTopLayout = 1;

    private boolean usaBottomLayout = false;

    private int stepBottomLayout = 1;

    private List<String> fieldsName = null;

    private LinkedHashMap<String, AField> fieldsMap;

    private LinkedHashMap<String, List> enumMap;

    private String minWidthForm = "50em";


    public WrapForm() {
    }


    public WrapForm(AEntity entityBean) {
        this.entityBean = entityBean;
    }


    public WrapForm(AEntity entityBean, Class<? extends AEntity> entityClazz, boolean usaTopLayout, int stepTopLayout, boolean usaBottomLayout, int stepBottomLayout, List<String> fieldsName, LinkedHashMap<String, AField> fieldsMap, String minWidthForm) {
        this.entityBean = entityBean;
        this.entityClazz = entityClazz;
        this.usaTopLayout = usaTopLayout;
        this.stepTopLayout = stepTopLayout;
        this.usaBottomLayout = usaBottomLayout;
        this.stepBottomLayout = stepBottomLayout;
        this.fieldsName = fieldsName;
        this.fieldsMap = fieldsMap;
        this.minWidthForm = minWidthForm;
    }


    public AEntity getEntityBean() {
        return entityBean;
    }


    public void setEntityBean(AEntity entityBean) {
        this.entityBean = entityBean;
    }


    public Class<? extends AEntity> getEntityClazz() {
        return entityClazz;
    }


    public void setEntityClazz(Class<? extends AEntity> entityClazz) {
        this.entityClazz = entityClazz;
    }


    public boolean isUsaTopLayout() {
        return usaTopLayout;
    }


    public void setUsaTopLayout(boolean usaTopLayout) {
        this.usaTopLayout = usaTopLayout;
    }


    public int getStepTopLayout() {
        return stepTopLayout;
    }


    public void setStepTopLayout(int stepTopLayout) {
        this.stepTopLayout = stepTopLayout;
    }


    public boolean isUsaBottomLayout() {
        return usaBottomLayout;
    }


    public void setUsaBottomLayout(boolean usaBottomLayout) {
        this.usaBottomLayout = usaBottomLayout;
    }


    public int getStepBottomLayout() {
        return stepBottomLayout;
    }


    public void setStepBottomLayout(int stepBottomLayout) {
        this.stepBottomLayout = stepBottomLayout;
    }


    public String getMinWidthForm() {
        return minWidthForm;
    }


    public void setMinWidthForm(String minWidthForm) {
        this.minWidthForm = minWidthForm;
    }


    public List<String> getFieldsName() {
        return fieldsName;
    }


    public void setFieldsName(List<String> fieldsName) {
        this.fieldsName = fieldsName;
    }


    public LinkedHashMap<String, AField> getFieldsMap() {
        return fieldsMap;
    }


    public void setFieldsMap(LinkedHashMap<String, AField> fieldsMap) {
        this.fieldsMap = fieldsMap;
    }


    public LinkedHashMap<String, List> getEnumMap() {
        return enumMap;
    }


    public void setEnumMap(LinkedHashMap<String, List> enumMap) {
        this.enumMap = enumMap;
    }

}
