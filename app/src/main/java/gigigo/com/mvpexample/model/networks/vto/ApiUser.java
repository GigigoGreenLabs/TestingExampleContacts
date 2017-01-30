package gigigo.com.mvpexample.model.networks.vto;

public class ApiUser {

  private String gender;
  private ApiName name;
  private ApiLocation location;
  private String registered;
  private String phone;
  private ApiId id;
  private ApiPicture picture;

  public String getGender() {
    return gender;
  }

  public ApiName getName() {
    return name;
  }

  public ApiLocation getLocation() {
    return location;
  }

  public String getRegistered() {
    return registered;
  }

  public String getPhone() {
    return phone;
  }

  public ApiId getId() {
    return id;
  }

  public ApiPicture getPicture() {
    return picture;
  }
}
