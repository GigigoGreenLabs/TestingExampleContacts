package gigigo.com.mvpexample.model.mappers;

public interface Mapper<DataSource, Domain> {

  Domain mapFromDataSourceToDomain(DataSource dataSource);


}
