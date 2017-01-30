package gigigo.com.mvpexample.model.mappers;

import gigigo.com.mvpexample.model.entities.User;
import gigigo.com.mvpexample.model.networks.vto.ApiUser;

public class UserMapper implements Mapper<ApiUser, User> {

  private final IdMapper idMapper;
  private final LocationMapper locationMapper;
  private final NameMapper nameMapper;
  private final PictureMapper pictureMapper;

  public UserMapper() {
    idMapper = new IdMapper();
    locationMapper = new LocationMapper();
    nameMapper = new NameMapper();
    pictureMapper = new PictureMapper();
  }

  @Override public User mapFromDataSourceToDomain(ApiUser apiUser) {
    User user = new User();

    user.setGender(apiUser.getGender());
    user.setPhone(apiUser.getPhone());
    user.setRegistered(apiUser.getRegistered());

    user.setId(idMapper.mapFromDataSourceToDomain(apiUser.getId()));
    user.setLocation(locationMapper.mapFromDataSourceToDomain(apiUser.getLocation()));
    user.setName(nameMapper.mapFromDataSourceToDomain(apiUser.getName()));
    user.setPicture(pictureMapper.mapFromDataSourceToDomain(apiUser.getPicture()));

    return user;
  }
}
