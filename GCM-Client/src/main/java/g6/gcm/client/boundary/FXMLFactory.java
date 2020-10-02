package g6.gcm.client.boundary;

public enum FXMLFactory {

    MAIN_UI {
        @Override
        public String getFXMLView() {
            return "/fxml/MainUI.fxml";
        }
    },
    WELCOME_SCREEN {
        @Override
        public String getFXMLView() {
            return "/fxml/WelcomeScreenUI.fxml";
        }
    },
    SIGN_IN_SCREEN {
        @Override
        public String getFXMLView() {
            return "/fxml/SignInUI.fxml";
        }
    },
    SIGN_UP_SCREEN_1 {
        @Override
        public String getFXMLView() {
            return "/fxml/SignUp1.fxml";
        }
    },
    SIGN_UP_SCREEN_2 {
        @Override
        public String getFXMLView() {
            return "/fxml/SignUp2.fxml";
        }
    },
    SIGN_UP_SCREEN_3 {
        @Override
        public String getFXMLView() {
            return "/fxml/SignUp3.fxml";
        }
    },
    UI_SWITCHEROO {
        @Override
        public String getFXMLView() {
            return "/fxml/UISwitcheroo.fxml";
        }
    },
    CATALOG_BROWSER {
        @Override
        public String getFXMLView() {
            return "/fxml/CatalogBrowserUI.fxml";
        }
    },
    CUSTOMER_SIDE_PANE {
        @Override
        public String getFXMLView() {
            return "/fxml/customer/CustomerSidePaneUI.fxml";
        }
    },
    CUSTOMER_PROFILE_EDITOR {
        @Override
        public String getFXMLView() {
            return "/fxml/customer/CustomerProfileEditorUI.fxml";
        }
    },
    CUSTOMER_PROFILE_EDITOR_2 {
        @Override
        public String getFXMLView() {
            return "/fxml/customer/CustomerProfileEditorUI2.fxml";
        }
    },
    CUSTOMER_ACTIVE_SUBSCRIPTIONS_VIEWER {
        @Override
        public String getFXMLView() {
            return "/fxml/customer/CustomerActiveSubscriptionsViewerUI.fxml";
        }
    },
    CUSTOMER_PURCHASE_HISTORY_VIEWER {
        @Override
        public String getFXMLView() {
            return "/fxml/customer/CustomerPurchaseHistoryViewerUI.fxml";
        }
    },
    CUSTOMER_HELP_SCREEN {
        @Override
        public String getFXMLView() {
            return "/fxml/customer/CustomerHelpScreenUI.fxml";
        }
    },
    CUSTOMER_CITY_EXPLORER {
        @Override
        public String getFXMLView() {
            return "/fxml/customer/CustomerCityExplorerUI.fxml";
        }
    },
    EMPLOYEE_SIDE_PANE {
        @Override
        public String getFXMLView() {
            return "/fxml/employee/EmployeeSidePaneUI.fxml";
        }
    },

    EMPLOYEE_WORK_SPACE {
        @Override
        public String getFXMLView() {
            return "/fxml/employee/EmployeeWorkspaceUI.fxml";
        }
    },
    EMPLOYEE_MAPS_ATTACHER {
        @Override
        public String getFXMLView() {
            return "/fxml/employee/EmployeeMapsAttacherUI.fxml";
        }
    },
    EMPLOYEE_MAPS_EDITOR {
        @Override
        public String getFXMLView() {
            return "/fxml/employee/EmployeeMapsEditorUI.fxml";
        }
    },
    EMPLOYEE_REQUESTS_VIEWER {
        @Override
        public String getFXMLView() {
            return "/fxml/employee/EmployeeRequestsViewerUI.fxml";
        }
    },
    EMPLOYEE_PRICES_EDITOR {
        @Override
        public String getFXMLView() {
            return "/fxml/employee/EmployeePricesEditorUI.fxml";
        }
    },
    EMPLOYEE_REPORTS_GENERATOR {
        @Override
        public String getFXMLView() {
            return "/fxml/employee/EmployeeReportsGeneratorUI.fxml";
        }
    }, BINDING_TEST {
        @Override
        public String getFXMLView() {
            return "/fxml/BindingTest2.fxml";
        }
    }, MAPVIEW {
        @Override
        public String getFXMLView() {
            return "/fxml/MapTests/MapViewOLD.fxml";
        }
    }, SIGN_UP_SCREEN_MANAGER {
        @Override
        public String getFXMLView() {
            return "/fxml/SignUpScreenFormManager.fxml";
        }
    }, BLACK {
        @Override
        public String getFXMLView() {
            return "/fxml/Black.fxml";
        }
    }, BLACK2 {
        @Override
        public String getFXMLView() {
            return "/fxml/CatalogUI.fxml";
        }
    }, JUST_BROWSE_SIDE_PANE {
        @Override
        public String getFXMLView() {
            return "/fxml/JustBrowseSidePaneUI.fxml";
        }
    };


    public abstract String getFXMLView();

}
