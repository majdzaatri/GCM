package g6.gcm.core.entity;

public class OneShotDealDTO extends PurchaseDTO {

  @Override
  public String render() {
      return "One Shot Deal purchased on " + getPurchaseDate();
  }
}
