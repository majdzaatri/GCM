package g6.gcm.client.manager;

import g6.gcm.core.entity.ActivityDTO;
import g6.gcm.core.entity.MapDTO;
import g6.gcm.core.entity.NotificationDTO;
import g6.gcm.core.entity.ReportDTO;
import g6.gcm.core.entity.SiteClassificationDTO;
import g6.gcm.core.entity.SubscriptionDTO;
import g6.gcm.core.entity.TourDTO;
import g6.gcm.core.entity.UserDTO;
import g6.gcm.core.factories.ConsoleTextColorsFactory;
import g6.gcm.core.factories.ServersCommands;
import g6.gcm.core.interfaces.AbstractDTO;
import g6.gcm.core.interfaces.Renderable;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import javax.swing.*;

public class IncomingMessageManager implements Observer {

  GCMClient gcmClient;

  public IncomingMessageManager(GCMClient gcmClient) {
    this.gcmClient = gcmClient;
    gcmClient.addObserver(this);
  }

  @Override
  public void update(Observable o, Object msg) {

    if (msg != null) {

      if (msg instanceof List<?>) {
        handleDTOList((List<? extends AbstractDTO>) msg);
      } else {
        handleDTO((AbstractDTO) msg);
      }
    } else {
      gcmClient.say("WTF bro! Why are you sending me NULLs?");
    }
  }

  /**
   *
   */
  private void handleDTOList(List<? extends AbstractDTO> theList) {

    /*
     * Make new thread to manage processing answer of type List<? extends
     * AbstractDTO>
     */
    Runnable manage = () -> {

      if (!theList.isEmpty()) {

        Boolean wasSet = true;

        switch (theList.get(0).getType()) {

          case USER: {

            Platform.runLater(() -> {
              UsersManager.getInstance()
                  .setBeansList(FXCollections.observableArrayList((List<UserDTO>) theList));
              // MapsManager.setMapDTO(((List<MapDTO>) theList).get(0));

            });

            break;
          }

          case CITY: {

            Platform.runLater(() -> {
              CitiesManager.getInstance()
                  .setBeansList(FXCollections.observableArrayList(theList));
            });

            break;
          }

          case ACTIVITY: {


            Platform.runLater(() -> {
              ActivityManager
                  .setListProperty(FXCollections.observableArrayList((List<ActivityDTO>) theList));
            });

            break;
          }

          case NOTIFICATION: {

            Platform.runLater(() -> {

              NotificationsManager.setNotificationssList(
                  FXCollections
                      .observableArrayList(((List<NotificationDTO>) theList).stream().sorted(
                          Comparator.comparing(NotificationDTO::getNotificationID))
                          .collect(Collectors.toList())));
            });

            break;
          }

          case MAP: {

            Platform.runLater(() -> {
              /*ManagersFactory.getMapsManager()
                  .setBeansList(FXCollections.observableArrayList((List<? extends MapDTO>) theList));*/

              MapsManager.getInstance().setBeansList(
                  FXCollections
                      .observableArrayList(((List<MapDTO>) theList).stream().sorted(
                          Comparator.comparing(MapDTO::getMapID)).collect(Collectors.toList())));

              // MapsManager.getInstance().setBean(((List<MapDTO>) theList).get(0));
            });

            break;
          }

          case SITE: {
            Platform.runLater(() -> {
              SitesManager.getInstance().setBeansList(
                  FXCollections.observableArrayList(theList/*.stream().sorted(
                      Comparator.comparing(SiteDTO::getSiteClassification)).collect(Collectors.toList())*/));
            });
            break;
          }

          case REPORT: {
            Platform.runLater(() -> {
              if (((List<ReportDTO>) theList) == null) {
                JOptionPane.showMessageDialog(null, "OooCmooon man, type an existing Email");
              }
              ReportsManager.setListProperty(
                  FXCollections.observableArrayList(((List<ReportDTO>) theList)/*.stream().sorted(
                      Comparator.comparing(SiteDTO::getCategory)).collect(Collectors.toList())*/));

            });
            break;
          }

          case SITE_CLASSIFICATION: {
            Platform.runLater(() -> {
              SitesClassificationsManager.getInstance().setBeansList(
                  FXCollections
                      .observableArrayList(((List<SiteClassificationDTO>) theList).stream().sorted(
                          Comparator.comparing(SiteClassificationDTO::getCategory))
                          .collect(Collectors.toList())));
            });
            break;
          }

          case TOUR: {
            Platform.runLater(() -> {
              /*ManagersFactory.getSitesManager()*/
              ToursManager.getInstance().setBeansList(
                  FXCollections.observableArrayList(((List<TourDTO>) theList)/*.stream().sorted(
                      Comparator.comparing(SiteDTO::getSiteClassification)).collect(Collectors.toList())*/));
            });
            break;
          }

          case ONE_SHOT_DEAL:
          case SUBSCRIPTION:
          case PURCHASE: {
            Platform.runLater(() -> {
              /*ManagersFactory.getSitesManager()*/
              PurchasesManager.getInstance().setBeansList(
                  FXCollections.observableArrayList(((List<SubscriptionDTO>) theList)/*.stream().sorted(
                      Comparator.comparing(SiteDTO::getCategory)).collect(Collectors.toList())*/));
            });
            break;
          }
          case PRICE_REQUEST: {
            Platform.runLater(() -> {
              /*ManagersFactory.getSitesManager()*/
              PriceRequestManager.getInstance().setBeansList(
                  FXCollections.observableArrayList((theList)/*.stream().sorted(
                      Comparator.comparing(SiteDTO::getCategory)).collect(Collectors.toList())*/));
            });
            break;
          }
          default: {
            gcmClient.say("Oh oh, unknown message has been received");
            wasSet = false;
          }
        }
        if (wasSet) {
          gcmClient.say(ConsoleTextColorsFactory.ANSI_YELLOW
              + theList.get(0).getType() + ConsoleTextColorsFactory.ANSI_RESET
              + " beansList just received an update.");
        }
      }

    };
    new Thread(manage).start();
  }

  /**
   *
   */
  private void handleDTO(AbstractDTO theDTO) {


    /* Make new thread to manage processing answer of type AbstractDTO */
    Runnable manage = () -> {

      Boolean wasSet = true;

      switch (theDTO.getType()) {

        case USER:
        case CONTENT_EDITOR:
        case CONTENT_MANAGER:
        case COMPANY_MANAGER: {
          Platform.runLater(() -> {
            UsersManager.getInstance().setBean((UserDTO) theDTO);
            NotificationListUpdater.setUserProperty((UserDTO)theDTO);
            NotificationsManager.getUserNotifications((UserDTO) theDTO);
            System.out.println("user was updated!!");
          });
        }
        case CREDIT_CARD: {
          Platform.runLater(() -> {
            CreditCardsManager.getInstance().setBean(theDTO);
          });

          break;
        }
        case PRICE_REQUEST: {
          Platform.runLater(() -> {
            PriceRequestManager.getInstance().setBean(theDTO);
          });
          break;

        }

        case CITY: {
          Platform.runLater(() -> {
            CitiesManager.getInstance().setBean(theDTO);
          });
          break;
        }

        case MAP: {
          Platform.runLater(() -> {
            MapsManager.getInstance().setBean(theDTO);
          });
          break;

        }
        case NOTIFICATION: {

          Platform.runLater(() -> {
            NotificationsManager.getNotificationsFromNotificationDTO((NotificationDTO) theDTO);
          });

          break;
        }
        case SITE_CLASSIFICATION: {

          Platform.runLater(() -> {
            SitesClassificationsManager.getInstance().setBean(theDTO);
          });

          break;
        }
        case ONE_SHOT_DEAL:
        case SUBSCRIPTION:
        case PURCHASE: {

          Platform.runLater(() -> {
            PurchasesManager.getInstance().setBean(theDTO);
          });

          break;
        }

        case REPORT: {

          Platform.runLater(() -> {
            ReportsManager.DownloadReport((ReportDTO) theDTO);
          });

          break;
        }

        default: {
          gcmClient.say("Oh oh, unknown message has been received");
          wasSet = false;
        }

      }
      if (wasSet) {
        gcmClient.say(ConsoleTextColorsFactory.ANSI_YELLOW
            + theDTO.getType() + ConsoleTextColorsFactory.ANSI_RESET
            + " bean was just set to: " + theDTO);
      }
    };

    new Thread(manage).start();
  }

}