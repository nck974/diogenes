package dev.nichoko.diogenes.model;

import dev.nichoko.diogenes.model.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LocationSummary {
    private Location location;
    private int itemsNumber;

}
