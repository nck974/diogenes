package dev.nichoko.diogenes.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.nichoko.diogenes.exception.NameAlreadyExistsException;
import dev.nichoko.diogenes.exception.ResourceNotFoundException;
import dev.nichoko.diogenes.model.LocationSummary;
import dev.nichoko.diogenes.model.domain.Location;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;
    private ItemService itemService;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, ItemService itemService) {
        this.locationRepository = locationRepository;
        this.itemService = itemService;
    }

    public Location getLocationById(int id) {
        return locationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(id));

    }

    /**
     * Check if another item with the same name already exists in the database
     * 
     * @param location
     */
    private void validateName(Location location) {
        String locationName = location.getName();
        if (locationRepository.existsByName(locationName)) {
            throw new NameAlreadyExistsException(
                    "Location with the name " + locationName + " already exists.");
        }
    }

    /*
     * Return all locations
     */
    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll(Sort.by("name"));
    }

    /*
     * Create a new location
     */
    @Override
    public Location createLocation(Location location) {
        validateName(location);

        return locationRepository.save(location);
    }

    /*
     * Update an existing location or throw a not found exception
     */
    @Override
    public Location updateLocation(int id, Location location) {
        return locationRepository.findById(id)
                .map(existingLocation -> {
                    location.setId(existingLocation.getId());
                    location.setCreatedOn(existingLocation.getCreatedOn());
                    if (!location.getName().equals(existingLocation.getName())) {
                        validateName(location);
                    }
                    return locationRepository.save(location);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /*
     * Delete an existing location or throw a not found exception
     */
    @Override
    public void deleteLocation(int id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(id));
        locationRepository.delete(location);
    }

    /*
     * Get all locations and items and count the number of items in each location
     */
    @Override
    public List<LocationSummary> getLocationsSummary() {

        // Count the locations from the items
        List<Item> items = itemService.getAllItems();
        Map<Integer, Integer> locationItemCountMap = items.stream()
                .collect(
                        Collectors.groupingBy(
                                Item::getLocationId,
                                Collectors.reducing(0, e -> 1, (a, b) -> a + b)));

        // Return the locations with the value
        List<Location> locations = getAllLocations();
        return locations.stream()
                .map(location -> new LocationSummary(location, locationItemCountMap.getOrDefault(location.getId(), 0)))
                .collect(Collectors.toList());
    }

}
