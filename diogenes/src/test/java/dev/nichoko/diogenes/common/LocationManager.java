package dev.nichoko.diogenes.common;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import dev.nichoko.diogenes.model.domain.Location;
import dev.nichoko.diogenes.utils.JsonProcessor;

public class LocationManager {
    /*
     * Sends the provided location API
     */
    public static ResultActions createLocation(MockMvc mockMvc, Location location) throws Exception {
        return mockMvc.perform(
                post("/api/v1/locations/")
                        .content(JsonProcessor.stringifyClass(location))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    /*
     * Sends the provided location to the API
     */
    public static ResultActions updateLocation(MockMvc mockMvc, Location location, int locationId) throws Exception {
        return mockMvc.perform(
                put("/api/v1/locations/" + Integer.toString(locationId))
                        .content(JsonProcessor.stringifyClass(location))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

}
