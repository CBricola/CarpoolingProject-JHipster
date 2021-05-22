package com.bricola.cocovoit.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumération types de trajet
 */
public enum PathType {
    ALLER("Aller"),
    RETOUR("Retour");

    public final String label;

    private PathType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
