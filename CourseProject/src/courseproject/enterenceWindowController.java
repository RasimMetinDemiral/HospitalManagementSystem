/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseproject;

import databaseConnection.database;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author khas
 */
public class enterenceWindowController implements Initializable {
    
    private Label label;
    @FXML
    private Button main_admin;
    @FXML
    private Button main_doc;
    @FXML
    private Button main_res;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        try {
            database.connection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(enterenceWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    @FXML
    private void adminPaneliAc(ActionEvent event) {
        LoadWindow("/adminMain/main.fxml", "Window Page");
    }

    @FXML
    private void doctorPaneliAc(ActionEvent event) {
        LoadWindow("/DoctorWindow/DoctorAndPatient.fxml", " doctor and patient information window");
        
        
    }

    @FXML
    private void reservationPanelAc(ActionEvent event) {
          LoadWindow("/reservationMain/reservationPanel.fxml", " patient window");
    }
    
    
     void LoadWindow(String loc,String title){
        
        try{
          
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED );
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            
            
        }
        catch(IOException e){
            Logger.getLogger(enterenceWindowController.class.getName()).log(Level.SEVERE,null,e);
            
        }
    }
    
 }  