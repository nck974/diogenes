package dev.nichoko.diogenes.controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import dev.nichoko.diogenes.common.LocationManager;
import dev.nichoko.diogenes.common.ItemManager;
import dev.nichoko.diogenes.mock.LocationMock;
import dev.nichoko.diogenes.mock.ItemMock;
import dev.nichoko.diogenes.model.domain.Location;
import dev.nichoko.diogenes.model.domain.Item;
import dev.nichoko.diogenes.utils.JsonProcessor;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class LocationControllerTest {
    @Autowired
    private LocationController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Flyway flyway;

    /*
     * Clean up the database before each test
     */
    @BeforeEach
    public void cleanUp() {
        flyway.clean();
        flyway.migrate();
    }

    /*
     * Test the app can load
     */
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    /**
     * Verify that when an location is not found a
     *
     * @throws Exception
     */
    @Test
    void canGetErrorLocationNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/locations/25"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The following id could not be found: 25"));
    }

    /**
     * Verify that an location can be retrieved with all its parameters
     *
     * @throws Exception
     */
    @Test
    void canSearchLocationById() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        LocationManager.createLocation(this.mockMvc, location);

        this.mockMvc.perform(get("/api/v1/locations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(location.getId()))
                .andExpect(jsonPath("$.name").value(location.getName()))
                .andExpect(jsonPath("$.description").value(location.getDescription()))
                .andExpect(jsonPath("$.icon").value(location.getIcon()));
    }

    /**
     * Can create a new location
     *
     * @throws Exception
     */
    @Test
    void canCreateNewLocation() throws Exception {
        Location location = LocationMock.getMockLocation(1);

        LocationManager.createLocation(this.mockMvc, location)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(location.getName()))
                .andExpect(jsonPath("$.description").value(location.getDescription()))
                .andExpect(jsonPath("$.icon").value(location.getIcon()));
    }

    /**
     * Can not create a new location with a duplicated name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateLocationWithTheSameName() throws Exception {
        Location location = LocationMock.getMockLocation(1);

        LocationManager.createLocation(this.mockMvc, location);
        LocationManager.createLocation(this.mockMvc, location)
                .andExpect(status().isConflict())
                .andExpect(
                        jsonPath("message").value("Location with the name " + location.getName() + " already exists."));
    }

    /**
     * Can not create a update a location with a duplicated name
     *
     * @throws Exception
     */
    @Test
    void canNotUpdateLocationWithTheSameName() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        Location locationUpdate = LocationMock.getMockLocation(2);

        LocationManager.createLocation(this.mockMvc, location);
        LocationManager.createLocation(this.mockMvc, locationUpdate);
        locationUpdate.setName(location.getName());
        LocationManager.updateLocation(this.mockMvc, locationUpdate, locationUpdate.getId())
                .andExpect(status().isConflict())
                .andExpect(
                        jsonPath("message").value("Location with the name " + location.getName() + " already exists."));
    }

    /**
     * Can create a new location
     *
     * @throws Exception
     */
    @Test
    void canCreateLocationWithoutDescription() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        location.setDescription(null);

        LocationManager.createLocation(this.mockMvc, location)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(location.getName()))
                .andExpect(jsonPath("$.description").value(location.getDescription()))
                .andExpect(jsonPath("$.icon").value(location.getIcon()));
    }

    /**
     * Can get all locations
     *
     * @throws Exception
     */
    @Test
    void canGetAllLocations() throws Exception {
        List<Location> locations = List.of(LocationMock.getMockLocation(2), LocationMock.getMockLocation(3),
                LocationMock.getMockLocation(4));
        for (Location location : locations) {
            LocationManager.createLocation(this.mockMvc, location);
        }
        this.mockMvc.perform(get("/api/v1/locations/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(locations.size()))
                .andExpect(jsonPath("$[0].name").value(locations.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(locations.get(1).getName()))
                .andExpect(jsonPath("$[2].name").value(locations.get(2).getName()));
    }

    /**
     * Can get the locations summary
     *
     * @throws Exception
     */
    @Test
    void canGetLocationsSummary() throws Exception {
        List<Location> locations = List.of(LocationMock.getMockLocation(1), LocationMock.getMockLocation(2));
        for (Location location : locations) {
            LocationManager.createLocation(this.mockMvc, location);
        }

        // Add two items
        List<Item> items = List.of(ItemMock.getMockItem(1), ItemMock.getMockItem(2));
        for (Item item : items) {
            item.setLocation(locations.get(0));
            ItemManager.createItem(this.mockMvc, item);
        }

        this.mockMvc.perform(get("/api/v1/locations/summary"))
                .andDo(res -> System.out.println(res))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(locations.size()))
                .andExpect(jsonPath("$[0].location.name").value(locations.get(0).getName()))
                .andExpect(jsonPath("$[0].itemsNumber").value(2))
                .andExpect(jsonPath("$[1].location.name").value(locations.get(1).getName()))
                .andExpect(jsonPath("$[1].itemsNumber").value(0));
    }

    /**
     * Verify create location validation: No name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewLocationWithoutName() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        location.setName(null);
        location.setDescription(null);

        LocationManager.createLocation(this.mockMvc, location)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create location validation: Empty name
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewEmptyName() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        location.setName("");
        location.setDescription("");

        LocationManager.createLocation(this.mockMvc, location)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create location validation: Too long description
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewLocationTooLongDescription() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        location.setDescription("a".repeat(2001));

        LocationManager.createLocation(this.mockMvc, location)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify create location validation: Name too long
     *
     * @throws Exception
     */
    @Test
    void canNotCreateNewWithNameTooLong() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        location.setName("a".repeat(51));

        LocationManager.createLocation(this.mockMvc, location)
                .andExpect(status().isBadRequest());
    }

    /**
     * Verify that a new location can be updated
     *
     * @throws Exception
     */
    @Test
    void canUpdateLocation() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        Location updatedLocation = LocationMock.getMockLocation(2);
        updatedLocation.setId(location.getId());
        LocationManager.createLocation(this.mockMvc, location);

        LocationManager.updateLocation(this.mockMvc, updatedLocation, location.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedLocation.getId()))
                .andExpect(jsonPath("$.name").value(updatedLocation.getName()))
                .andExpect(jsonPath("$.description").value(updatedLocation.getDescription()))
                .andExpect(jsonPath("$.icon").value(updatedLocation.getIcon()));
    }

    /**
     * Verify that a location can be updated and there are no conflicts with the
     * name.
     * 
     * This is added because of the validateName(...) method in item
     *
     * @throws Exception
     */
    @Test
    void canUpdateLocationSameName() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        Location updatedLocation = LocationMock.getMockLocation(2);
        updatedLocation.setId(location.getId());
        updatedLocation.setName(location.getName());
        LocationManager.createLocation(this.mockMvc, location);

        LocationManager.updateLocation(this.mockMvc, updatedLocation, location.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedLocation.getId()))
                .andExpect(jsonPath("$.name").value(updatedLocation.getName()))
                .andExpect(jsonPath("$.description").value(updatedLocation.getDescription()))
                .andExpect(jsonPath("$.icon").value(updatedLocation.getIcon()));
    }

    /**
     * Verify that a non existing location can not be updated
     *
     * @throws Exception
     */
    @Test
    void canNotUpdateNotExistingLocation() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        this.mockMvc.perform(
                put("/api/v1/locations/" + Integer.toString(location.getId()))
                        .content(JsonProcessor.stringifyClass(location))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Verify that an location can be deleted
     *
     * @throws Exception
     */
    @Test
    void canDeleteLocation() throws Exception {
        Location location = LocationMock.getMockLocation(1);
        LocationManager.createLocation(this.mockMvc, location);

        this.mockMvc.perform(
                delete("/api/v1/locations/" + Integer.toString(location.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Verify that a non existing location can not be deleted
     *
     * @throws Exception
     */
    @Test
    void canNotDeleteNonExistingLocation() throws Exception {
        Location location = LocationMock.getMockLocation(1);

        this.mockMvc.perform(
                delete("/api/v1/locations/" + Integer.toString(location.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
