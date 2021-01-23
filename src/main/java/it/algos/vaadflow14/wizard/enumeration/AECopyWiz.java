package it.algos.vaadflow14.wizard.enumeration;

/**
 * Project vaadwiki14
 * Created by Algos
 * User: gac
 * Date: sab, 23-gen-2021
 * Time: 17:08
 */
public enum AECopyWiz {
    dirDeletingAll(AECopyType.directory),
    dirAddingOnly(AECopyType.directory),
    dirSoloSeNonEsiste(AECopyType.directory),
    fileSovrascriveSempreAncheSeEsiste(AECopyType.file),
    fileSoloSeNonEsiste(AECopyType.file),
    fileCheckFlagSeEsiste(AECopyType.file),
    sourceSovrascriveSempreAncheSeEsiste(AECopyType.source),
    sourceSoloSeNonEsiste(AECopyType.source),
    sourceCheckFlagSeEsiste(AECopyType.source),

    ;

    private AECopyType type;

    /**
     * Costruttore <br>
     */
    AECopyWiz(AECopyType type) {
        this.type = type;
    }

    public AECopyType getType() {
        return type;
    }
}
