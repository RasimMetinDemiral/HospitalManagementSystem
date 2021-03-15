/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listnurseRelation;

import ListDoctor.ListDoctorController;
import ListPatient.ListPatientController;
import courseproject.enterenceWindowController;
import java.io.IOException;
import java.net.URL;
import static java.nio.file.Files.delete;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import listReservertionIdvalue.ListreservationıdvalueController;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class ListnurseRelationController implements Initializable {
         String selectionNurseIdNumber;
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;      
        Statement st=null;
   
    @FXML
    private TextField nurserId;
    
    @FXML
    private AnchorPane mainPanel;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    } 
     @FXML
    private void writerbuttonaction(ActionEvent event) throws ClassNotFoundException, SQLException {
        LoadWindow("/listReservertionIdvalue/listreservationıdvalue.fxml", "list window");
    
         Class.forName("oracle.jdbc.driver.OracleDriver"); 
         con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
                                                     
       selectionNurseIdNumber = nurserId.getText(); 
       
       if (selectionNurseIdNumber== null){
            System.out.println(selectionNurseIdNumber);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select reservation ");
            alert.setContentText("choose reservation for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "select RESERVATIONID from RESERVATIONS where NURSEID="+selectionNurseIdNumber;
             
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery();
          
           
        } 
    } 
    public Integer getNurserId() {
        return Integer.valueOf(nurserId.getText());
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
