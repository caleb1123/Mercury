package com.g3.Jewelry_Auction_System.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EJewelCategory {
    RINGS("Rings"),
    BRACELETS("Bracelets"),
    BROOCHES_PINS("Brooches Pins"),
    CUFFLINKS_TIEPINS_TIECLIPS("Cufflinks Tiepins Tieclips"),
    EARRINGS("Earrings"),
    LOOSESTONES_BEADS("Loose Stones Beads"),
    NECKLACES_PENDANTS("Necklaces Pendants"),
    WATCHES("Watches");

    private final String categoryName;

    EJewelCategory(String displayName) {
        this.categoryName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return categoryName;
    }

    public int getId() {
        return this.ordinal()+1; // or use a custom ID if preferred
    }
}
