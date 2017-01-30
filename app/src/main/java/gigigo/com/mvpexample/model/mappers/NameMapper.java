package gigigo.com.mvpexample.model.mappers;

import gigigo.com.mvpexample.model.entities.Name;
import gigigo.com.mvpexample.model.networks.vto.ApiName;

public class NameMapper implements Mapper<ApiName, Name> {

  @Override public Name mapFromDataSourceToDomain(ApiName apiName) {
    Name name = new Name();

    name.setFirst(apiName.getFirst());
    name.setLast(apiName.getLast());
    name.setTitle(apiName.getTitle());

    return name;
  }

}
