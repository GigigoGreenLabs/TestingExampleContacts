package gigigo.com.mvpexample.model.mappers;

import gigigo.com.mvpexample.model.entities.Picture;
import gigigo.com.mvpexample.model.networks.vto.ApiPicture;

public class PictureMapper implements Mapper<ApiPicture, Picture> {

  @Override public Picture mapFromDataSourceToDomain(ApiPicture apiPicture) {
    Picture picture = new Picture();

    picture.setLarge(apiPicture.getLarge());
    picture.setMedium(apiPicture.getMedium());
    picture.setThumbnail(apiPicture.getThumbnail());

    return picture;
  }
}
