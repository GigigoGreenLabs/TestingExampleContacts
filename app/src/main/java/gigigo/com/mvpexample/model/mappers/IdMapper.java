package gigigo.com.mvpexample.model.mappers;

import gigigo.com.mvpexample.model.entities.Id;
import gigigo.com.mvpexample.model.networks.vto.ApiId;

public class IdMapper implements Mapper<ApiId, Id> {

  @Override public Id mapFromDataSourceToDomain(ApiId apiId) {
    Id id = new Id();

    id.setName(apiId.getName());
    id.setValue(apiId.getValue());

    return id;
  }
}
