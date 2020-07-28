package it.algos.vaadflow14.backend.enumeration;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 03-nov-2018
 * Time: 16:22
 * The operations supported by this dialog.
 * Delete is enabled when editing an already existing item.
 */
public enum AEOperation {
    addNew("New", "add", true, false),
    edit("Edit", "edit", true, true),
    editProfile("Edit", "edit", true, false),
    editNoDelete("Edit", "edit", true, false),
    editDaLink("Edit", "edit", true, true),
    showOnly("Mostra", "mostra", false, false);

    private final String nameInTitle;

    private final String nameInText;

    private final boolean saveEnabled;

    private final boolean deleteEnabled;


    AEOperation(String nameInTitle, String nameInText, boolean saveEnabled, boolean deleteEnabled) {
        this.nameInTitle = nameInTitle;
        this.nameInText = nameInText;
        this.saveEnabled = saveEnabled;
        this.deleteEnabled = deleteEnabled;
    }

    public static boolean contiene(String nome) {
        boolean contiene = false;

        for (AEOperation eaOperation : AEOperation.values()) {
            if (eaOperation.name().equals(nome)) {
                contiene = true;
            }
        }

        return contiene;
    }

    public String getNameInTitle() {
        return nameInTitle;
    }


    public String getNameInText() {
        return nameInText;
    }


    public boolean isSaveEnabled() {
        return saveEnabled;
    }


    public boolean isDeleteEnabled() {
        return deleteEnabled;
    }

}

