/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminMain;

import courseproject.enterenceWindowController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class MainController implements Initializable {

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LoadAddingDoctor(ActionEvent event) {
         LoadWindow("/addingDoctor/FXML.fxml", "adding doctor window");
        
        
    }

    @FXML
    private void LoadListDoctor(ActionEvent event) {
         LoadWindow("/ListDoctor/listDoctor.fxml", "list doctor window");
    }

    @FXML
    private void LoadAddingNurse(ActionEvent event) {
         LoadWindow("/addingNurse/addingNurse.fxml", "adding nurse window");
    }

    @FXML
    private void LoadListNurse(ActionEvent event) {
        LoadWindow("/ListNurse/ListNurse.fxml", "list nurse window");
        
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
