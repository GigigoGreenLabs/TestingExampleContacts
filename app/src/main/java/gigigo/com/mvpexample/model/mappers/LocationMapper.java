package gigigo.com.mvpexample.model.mappers;

import gigigo.com.mvpexample.model.entities.Location;
import gigigo.com.mvpexample.model.networks.vto.ApiLocation;

public class LocationMapper implements Mapper<ApiLocation, Location> {

  @Override public Location mapFromDataSourceToDomain(ApiLocation apiLocation) {
    Location location = new Location();

    location.setCity(apiLocation.getCity());
    location.setPostcode(apiLocation.getPostcode());
    location.setState(apiLocation.getState());
    location.setStreet(apiLocation.getStreet());

    return location;
  }
}
