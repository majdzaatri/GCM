package g6.gcm.client.manager;

import g6.gcm.core.factories.ClientsInquiries;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ServersCommands;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import jfxtras.labs.scene.control.BeanPathAdapter;



/**
 * Authenticates login credentials and manages user information.
 */
public class UsersManager extends BeanManager{

  private static UsersManager onlyInstance = new UsersManager();

  private final UserDTO DEFAULT_USER =new UserDTO();


  private UsersManager() {
    super();

    //Initialize default user

    DEFAULT_USER.setUsername("");
    DEFAULT_USER.setEmail("");
    DEFAULT_USER.setPassword("");
    DEFAULT_USER.setLastName("");
    DEFAULT_USER.setFirstName("");
    DEFAULT_USER.setOnline(false);

    // Instantiate & initialize an empty bean as an object property
    bean = new SimpleObjectProperty<>(new UserDTO());

    // Initialize bean's bean adaptor
    superBean = new BeanPathAdapter<>(new UserDTO());

    // Initialize Users list
    beansList = new SimpleListProperty<>(FXCollections.observableArrayList());

    // Initialize Manager's bindings
    initializeInternalBindings();
  }

  public static UsersManager getInstance() {
    return onlyInstance;
  }

    /**
     * Initialize Bindings to manager's bean
     */
  private void initializeInternalBindings() {

    // Bind bean to superBean
    bean.addListener((observable, oldValue, newValue) -> {
      if (newValue != null && oldValue != newValue) {
        getSuperBean().setBean(getBean());
        PurchasesManager.getInstance().requestAllSubscriptionsOfUser();
      }
    });

  }
  /**************************************************************************************************/
/**    _____        _    _                               _____        _    _                     **/
/**   / ____|      | |  | |                     ___     / ____|      | |  | |                    **/
/**  | (___    ___ | |_ | |_  ___  _ __  ___   ( _ )   | |  __   ___ | |_ | |_  ___  _ __  ___   **/
/**   \___ \  / _ \| __|| __|/ _ \| '__|/ __|  / _ \/\ | | |_ | / _ \| __|| __|/ _ \| '__|/ __|  **/
/**   ____) ||  __/| |_ | |_|  __/| |   \__ \ | (_>  < | |__| ||  __/| |_ | |_|  __/| |   \__ \  **/
/**  |_____/  \___| \__| \__|\___||_|   |___/  \___/\/  \_____| \___| \__| \__|\___||_|   |___/  **/
/**                                                                                              **/
  /************************************************************************************************/



/**************************************************************************************************/
/**       ___  _  _               _          ___                       _        _                **/
/**     / ___|| |(_)  ___  _ __  | |_  ___  |_ _| _ __    __ _  _   _ (_) _ __ (_)  ___  ___     **/
/**    | |    | || | / _ \| '_ \ | __|/ __|  | | | '_ \  / _` || | | || || '__|| | / _ \/ __|    **/
/**    | |___ | || ||  __/| | | || |_ \__ \  | | | | | || (_| || |_| || || |   | ||  __/\__ \    **/
/**     \____||_||_| \___||_| |_| \__||___/ |___||_| |_| \__, | \__,_||_||_|   |_| \___||___/    **/
/**                                                         |_|                                  **/
  /************************************************************************************************/


    /**
     * authenticate login credentials
     */
  public static void authenticateLoginCredentials() {

      UserDTO user =new UserDTO();
      user.setRequest(ClientsInquiries.AUTHENTICATE_LOGIN);
      user.setEmail(((UserDTO)UsersManager.getInstance().getBean()).getEmail());
      user.setPassword(((UserDTO)UsersManager.getInstance().getBean()).getPassword());
      GCMClient.send(user);
  }


  public void logoutUser()
  {
      UserDTO userDto = new UserDTO();
      userDto.setRequest(ClientsInquiries.SIGN_OUT_USER);
      userDto.setEmail(((UserDTO)getBean()).getEmail());
      GCMClient.send(userDto);

      //StageManager.getManager()

  }

    /**
     * signs up newly registered user
     */
  public static void createUser() {


      UserDTO userRequest =new UserDTO();
      userRequest.setUsername(((UserDTO)UsersManager.getInstance().getBean()).getUsername());
      userRequest.setEmail(((UserDTO)UsersManager.getInstance().getBean()).getEmail());
      userRequest.setPassword(((UserDTO)UsersManager.getInstance().getBean()).getPassword());
      userRequest.setFirstName(((UserDTO) UsersManager.getInstance().getBean()).getFirstName());
      userRequest.setLastName(((UserDTO) UsersManager.getInstance().getBean()).getLastName());
      userRequest.setPhoneNumber(((UserDTO) UsersManager.getInstance().getBean()).getPhoneNumber());
      userRequest.setRequest(ClientsInquiries.SIGN_UP_USER);

      GCMClient.send(userRequest);

  }

  public static void UpdateUser() {
    UsersManager.getInstance().getBean().setRequest(ClientsInquiries.UPDATE_USER);
    GCMClient.send((UserDTO) UsersManager.getInstance().getBean());
  }

  public static void UpdateUserPassword() {
    UsersManager.getInstance().getBean().setRequest((ClientsInquiries.UPDATE_USER_PASSWORD));
    GCMClient.send((UserDTO) UsersManager.getInstance().getBean());
  }


    /**
     * sign out current online user
     */
  public void signOutUser() {
    UserDTO user = new UserDTO();
    user.setRequest(ClientsInquiries.SIGN_OUT_USER);
    user.setEmail(((UserDTO) UsersManager.getInstance().getBean()).getEmail());
    GCMClient.send(user);
  }
}
