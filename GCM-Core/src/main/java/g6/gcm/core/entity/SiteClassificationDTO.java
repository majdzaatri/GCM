package g6.gcm.core.entity;

import g6.gcm.core.factories.DTOsFactory;
import g6.gcm.core.interfaces.AbstractDTO;
import java.util.Objects;

public class SiteClassificationDTO extends AbstractDTO {

  private String category = "";

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @Override
  protected void setDTOType() {
    this.type = DTOsFactory.SITE_CLASSIFICATION;
  }

  @Override
  public String render() {
    return this.category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SiteClassificationDTO)) {
      return false;
    }
    SiteClassificationDTO that = (SiteClassificationDTO) o;
    return Objects.equals(getCategory(), that.getCategory());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCategory());
  }

  @Override
  public String toString() {
    return category + " category";
  }
}
