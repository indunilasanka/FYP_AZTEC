package login;

import Dashboard.DashboardController;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
    
    @FXML
    private PasswordField pfUserPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField tfUserName;

    @FXML
    private Button signup;
    private Object stage;
    
    @FXML
    private ProgressIndicator loadingMask;

    @FXML
    void makeLogin(ActionEvent event) throws IOException {
        btnLogin.setDisable(true);
        loadingMask.setProgress(0.0);
        System.out.println("test");
        
        if (loadingMask.isVisible()) {
            return;
        }

        // clears the list items and start displaying the loading indicator at the Application Thread
      
        loadingMask.setVisible(true);

        // loads the items at another thread, asynchronously
        Task listLoader = new Task<Integer>() {
            {
                setOnSucceeded(workerStateEvent -> {   
                    System.out.println("success");
                    loadingMask.setVisible(false); // stop displaying the loading indicator
                    if(getValue() == 1){
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/Dashboard/Dashboard.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Parent parent = loader.getRoot();
                        Scene adminPanelScene = new Scene(parent);
                        Stage adminPanelStage = new Stage();
                        adminPanelStage.setTitle("Admin");
                        adminPanelStage.setMaximized(true);

                        DashboardController apControl = loader.getController();
                        adminPanelStage.setScene(adminPanelScene);
                        adminPanelStage.show();

                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        stage.close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Login Successfull!");
                        alert.initStyle(StageStyle.DECORATED);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.initOwner(adminPanelStage);
                        alert.show();
                        try {
                            Thread.sleep(1500l);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        alert.hide();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error : Username or password incorrect");
                        alert.setContentText("Please check the credintials and re-signin \n");
                        alert.initStyle(StageStyle.UNDECORATED);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.initOwner(btnLogin.getScene().getWindow());
                        alert.showAndWait();
                        btnLogin.setDisable(false);
                    }
                });

                setOnFailed(workerStateEvent -> {
                    getException().printStackTrace();
                    btnLogin.setDisable(false);
                });
            }

            @Override
            protected Integer call() throws Exception {
                
                Thread.sleep(1000l); // just emulates some loading time
                
                String username = tfUserName.getText();
                String pass = pfUserPassword.getText();
                
                if(username.equals("Aztec") && pass.equals("Aztec@123")){
                    Thread.sleep(1000l);
                    return 1;               }
                Thread.sleep(1000l);
                return 0;
            }
        };

        Thread loadingThread = new Thread(listLoader, "list-loader");
        loadingThread.setDaemon(true);
        
        
        Thread progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    loadingMask.setProgress(loadingMask.getProgress()+0.1);
                    try {
                        Thread.sleep(200l);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        loadingThread.start();
        progressThread.start();
        
        System.out.println("try");
        System.out.println("try");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("initialize");
        loadingMask.setProgress(0.0);
        loadingMask.setVisible(false);
    }    
    
}
