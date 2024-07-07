package com.g3.Jewelry_Auction_System.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EJewelCategory {
    RINGS("Rings"), //RNG01
    BRACELETS("Bracelets"), //BRCLT01
    BROOCHES_PINS("Brooches Pins"), //BRCHPN01
    CUFFLINKS_TIEPINS_TIECLIPS("Cufflinks Tiepins Tieclips"), //LNKPNCLP01
    EARRINGS("Earrings"), //EARNG01
    LOOSESTONES_BEADS("Loose Stones Beads"), //STNBD01
    NECKLACES_PENDANTS("Necklaces Pendants"), //NCKPDNT
    WATCHES("Watches"); //WATCH01

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
