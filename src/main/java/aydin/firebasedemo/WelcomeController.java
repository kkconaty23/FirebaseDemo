package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WelcomeController {

    @FXML
    void registerButtonClicked(ActionEvent event) {
        registerUser();
    }


    @FXML
    void signInBtnClicked(ActionEvent event) throws IOException {
        addData();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Homepage");
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML
    private TextField emailID;

    @FXML
    private PasswordField passwordID;

    @FXML
    private Button registerBtn;

    @FXML
    private Button signInBtn;

    @FXML
    private TextField phoneNum;


    public boolean registerUser() {

        String email = emailID.getText();
        String password = passwordID.getText();

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);

        UserRecord userRecord;
        try {
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            return false;
        }

    }



    public void addData() {

        DocumentReference docRef = DemoApp.fstore.collection("Persons").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Email", emailID.getText());
        data.put("Password", passwordID.getText());


        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }


}
