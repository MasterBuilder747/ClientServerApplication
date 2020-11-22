package BasicClientServer.FrontEnd;

import BasicClientServer.BackEnd.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class ClientGUIController {

    //Connect Window Elements
    public static Client client;
    @FXML private TextField IPTextField;

    //Login Page Elements
    @FXML private TextField passwordShown;
    @FXML private PasswordField passwordHidden;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private TextField LoginPageUsernameTextField;

    //Change Password Elements
    @FXML private TextField oldPasswordShown;
    @FXML private PasswordField oldPasswordHidden;
    @FXML private TextField newPasswordShown;
    @FXML private PasswordField newPasswordHidden;
    @FXML private TextField reEnteredNewPasswordShown;
    @FXML private PasswordField reEnteredNewPasswordHidden;
    @FXML private CheckBox CPShowPasswordCheckBox;

    //Login Page Controller -------------------------------------------------------------------------------------------------------------------------
    //Opens Account Creation window upon pressing 'New Account' Button
    public void createAccountWindow(MouseEvent mouseEvent) {
        try {
            Parent newAccount = FXMLLoader.load(getClass().getResource("/NewAccount.fxml")); //account creation page
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Account");
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); //Setting the window in the middle of main window
            stage.setX((screenBounds.getWidth() - 600) / 2);
            stage.setY((screenBounds.getHeight() - 600) / 2);
            stage.setScene(new Scene(newAccount, 600, 400));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Opens Password Recovery window upon pressing 'Forgot Password' Button
    public void forgotPasswordWindow(MouseEvent mouseEvent) {
        try {
            Parent newAccount = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml")); //account creation page
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Password Recovery");
            stage.setScene(new Scene(newAccount, 484, 299));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Logic for the show password checkbox
    public void togglePasswordVisibility(ActionEvent actionEvent) {
        if(showPasswordCheckBox.isSelected()){
            System.out.println("password is now shown");
            passwordShown.setText(passwordHidden.getText());
            passwordShown.setVisible(true);
            passwordHidden.setVisible(false);
            return;
        } else {
            System.out.println("password is now hidden");
            passwordHidden.setText(passwordShown.getText());
            passwordHidden.setVisible(true);
            passwordShown.setVisible(false);
        }


    }

    //Disconnects from server upon pressing 'Disconnect' Button
    public void disconnectFromServer(MouseEvent mouseEvent) {
        System.out.println("pressed disconnect");
        String commandString = "disconnect";
        client.networkaccess.sendString(commandString, false);

        //close the GUI
        ((Stage)(((Button)mouseEvent.getSource()).getScene().getWindow())).close();
    }

    public void AccountLoginEvent(MouseEvent mouseEvent) {
        // -- send message to server and receive reply.
        String commandString;
        String userInformationString;
        String replyString;

        //If the User presses login button with nothing typed in the fields then popup error message to type in shit
        String username = LoginPageUsernameTextField.getText();
        String password = passwordHidden.getText();
        commandString = "login" + "," + username + "," + password;

        replyString = client.networkaccess.sendString(commandString, true);

        if(replyString.equals("Success")) {
            try {
                ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
                Parent loginPage = FXMLLoader.load(getClass().getResource("/MainApplication.fxml")); //main application page
                Stage stage = new Stage();
                stage.setTitle("Main Application");
                stage.setScene(new Scene(loginPage, 721, 475));
                stage.show();

            }
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("Caught exception");
            }
        } else {
            //display an error message
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Login Error");
            error.setContentText("Please Re-Enter your Username and Password");
            error.setHeaderText(null);
            error.showAndWait();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------

    //Connect Window Controller -----------------------------------------------------------------------------------------------
    public void connectToServer(MouseEvent mouseEvent) {
        String host = IPTextField.getText();
        if(host == "") return;
        int port = 8000;
        // -- instantiate a Client object
        //    the constructor will attempt to connect to the server
        client = new Client(host, port);


        // -- send message to server and receive reply.
        String commandString;
        String replyString;

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            };

        // -- send a String to the server and wait for the response
        commandString = "connect";
        replyString = client.networkaccess.sendString(commandString, true);
        System.out.println(replyString);
        if(replyString.equals("Success")) {
            try {
                ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
                Parent loginPage = FXMLLoader.load(getClass().getResource("/LoginPage.fxml")); //account creation page
                Stage stage = new Stage();
                stage.setTitle("Login Page");
                stage.setScene(new Scene(loginPage, 721, 475));
                stage.show();

            }
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("Caught exception");
            }
        } else {
            //display an error message
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Connection Error");
            error.setContentText("Faulty Internet or Incorrect IP Address - Please Check Your Connection or IP Address");
            error.setHeaderText(null);
            error.showAndWait();
        }


    }


    //-------------------------------------------------------------------------------------------------------------------------

    //Change Password Window Controller ---------------------------------------------------------------------------------------

    //WORK ON THIS IT IS NOT FINISHED -- HAVE TO ADD THE FUNCTION AND IDS TO FXML
//    public void togglePasswordVisibility1(ActionEvent actionEvent) {
//        if(showPasswordCheckBox2.isSelected()){
//            System.out.println("password is now shown");
//            passwordShown2.setText(passwordHidden2.getText());
//            passwordShown2.setVisible(true);
//            passwordHidden2.setVisible(false);
//            return;
//        } else {
//            System.out.println("password is now hidden");
//            passwordHidden2.setText(passwordShown2.getText());
//            passwordHidden2.setVisible(true);
//            passwordShown2.setVisible(false);
//        }
//
//
//    }

    // ------------------------------------------------------------------------------------------------------------------------


    //Forgot Password Window Controller ---------------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------------------------------------------------


    //New Account Window Controller -------------------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------------------------------------------------


    //Main Application Window Controller ---------------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------------------------------------------------
}
