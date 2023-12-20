package dev.nichoko.diogenes.model;

import dev.nichoko.diogenes.model.domain.Location;

public class LocationSummary {
    private Location location;
    private int itemsNumber;

    public LocationSummary(Location location, int itemsNumber) {
        this.location = location;
        this.itemsNumber = itemsNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public void setItemsNumber(int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }
}
