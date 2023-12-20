package dev.nichoko.diogenes.service;

import java.util.List;

import dev.nichoko.diogenes.model.LocationSummary;
import dev.nichoko.diogenes.model.domain.Location;

public interface LocationService {
    Location getLocationById(int id);

    List<Location> getAllLocations();

    List<LocationSummary> getLocationsSummary();

    Location createLocation(Location item);

    Location updateLocation(int id, Location item);

    void deleteLocation(int id);
}
