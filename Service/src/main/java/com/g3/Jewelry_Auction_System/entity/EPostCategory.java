package com.g3.Jewelry_Auction_System.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum EPostCategory {
    AUCTION_INSIGHTS(1,"Auction Insights"),
    BIDDER_GUIDE(2,"Bidder's Guide"),
    SELLER_GUIDE(3,"Seller's Guide"),
    AUCTION_NEWS(4,"Auction News"),
    TREND(5,"Trending"),
    UPCOMING_AUCTION(6,"Upcoming Auction"),
    JEWELRY_CARE(7,"Jewelry Care"),
    BEHIND_THE_SCENES(8,"Behind the Scenes"),;

    @Getter
    private final int id;
    private final String displayName;

    EPostCategory(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

}
