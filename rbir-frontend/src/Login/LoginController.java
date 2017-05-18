package Login;

import Dashboard.DashboardController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    void makeLogin(ActionEvent event) throws IOException {
        String username = tfUserName.getText();
        String pass = pfUserPassword.getText();
        
        if(username.equals("Aztec") && pass.equals("Aztec@123")){
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Dashboard/Dashboard.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Scene adminPanelScene = new Scene(parent);
            Stage adminPanelStage = new Stage();
            adminPanelStage.setMaximized(true);
            
            DashboardController apControl = loader.getController();
            adminPanelStage.setScene(adminPanelScene);
            adminPanelStage.show();

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error : Username or password incorrect");
            alert.setContentText("Please check the credintials and re-signin \n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
