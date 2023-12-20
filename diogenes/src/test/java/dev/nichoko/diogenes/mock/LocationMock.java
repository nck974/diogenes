package dev.nichoko.diogenes.mock;

import dev.nichoko.diogenes.model.domain.Location;

public class LocationMock {
        /*
     * Return a mock of an location
     */
    public static Location getMockLocation(Integer number) {
        return new Location(
                number,
                "TestName" + number.toString(),
                "Description" + number.toString(),
                "AB02" + number.toString());
    }

}
