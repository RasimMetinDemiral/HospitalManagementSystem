/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addingNurse;

import ListDoctor.ListDoctorController;
import ListNurse.ListNurseController;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class AddingNurseController implements Initializable {

    @FXML
    private TextField nurseId;
    @FXML
    private TextField nurseName;
    @FXML
    private TextField nurseSurname;
    @FXML
    private TextField nurseExperience;
    @FXML
    private Button addbutton;
    @FXML
    private Button cancelbutton;
    
    Connection con= null;
    PreparedStatement ps = null; 
    ResultSet rs = null;
    Boolean editMode = false;
    @FXML
    private AnchorPane mainPanel;
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void dataInputControl(KeyEvent event) {
        try{
            int number = Integer.parseInt(nurseId.getText()); // nurse ıd text file ından değeri aldık 
            
            nurseId.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
            
        }
    
        catch(NumberFormatException e){
                
             nurseId.setStyle("-fx-control-inner-background: #ff1a1a;");
             // data input control line if we can wrong type input it will show red line
          } 
    }

    @FXML
    private void dataInputControlNurse(KeyEvent event) {
         try{
             // nurse ıd text file ından değeri aldık 
            int nurseEx = Integer.parseInt(nurseExperience.getText()); // nurse ıd text file ından değeri aldık 
            
             // data input control line , if we can wrong type input it will show white line
            nurseExperience.setStyle("-fx-control-inner-background: #ffffff;");
        }
    
        catch(NumberFormatException e){
                
             
            nurseExperience.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
    }

    @FXML
    private void addNurse(ActionEvent event) throws ClassNotFoundException, SQLException {
        //String nurseIdNumber = (nurseId.getText().toString()); 
         int nurseIdNumber = Integer.parseInt(nurseId.getText());// we took the nurse ıd number from entering value
         String NurseNam= nurseName.getText(); //  we took the nurse name number from entering value , elle girdiğimiz değeri aldık
         String Nursesur= nurseSurname.getText(); // we took the nurse name number from entering value 
         int NurseExp= Integer.parseInt(nurseExperience.getText()); 
         
         if ( NurseNam.isEmpty() || Nursesur.isEmpty()){ // we control the all data enterence 
             
             Alert alert = new  Alert(Alert.AlertType.ERROR); // we catch the error
            
             alert.setHeaderText("catched the error");
             
             alert.setContentText("please enter the all fields");
             
             alert.showAndWait();

      }
         else{
             if(editMode){
             updateNurse();
             }
             else{
                try {
               Class.forName("oracle.jdbc.driver.OracleDriver"); 
               con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       
            String SqlZ = " INSERT INTO NURSES VALUES (?,?,?,?)";
            ps = con.prepareStatement(SqlZ);
            ps.setInt(1,nurseIdNumber); // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
            ps.setString(2,NurseNam);
            ps.setString(3,Nursesur);
            ps.setInt(4,NurseExp);
            rs =ps.executeQuery(); 
           } 
            catch(SQLException s) { 
                       s.getMessage();
           }
              finally {
                           rs.close();
                           ps.close();
                           con.close();
           } 
             
             Alert alert = new  Alert(Alert.AlertType.INFORMATION); // we catch the error
             alert.setHeaderText("The process of adding a nurse is complete");
             alert.setContentText(" please make new operation"); 
             alert.showAndWait();
           
           Stage stage = (Stage) mainPanel.getScene().getWindow();
           stage.close(); // we close the window when we clicked the cancel button 
             }
            
    
        }
    
    }

    @FXML
    private void cancel(ActionEvent event) {
        
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
    }
    
    
    public void getTextForUpdates(ListNurseController.Nurse nurse){// update komutu için açılan ekranda text fielddan girilen değerleri aldık ve methoda yolladık
        nurseId.setText(nurse.getN_id());
        nurseName.setText(nurse.getNname());
        nurseSurname.setText(nurse.getNsurname());
        nurseExperience.setText(nurse.getNexp());
        editMode = true;
    }
    public void updateNurse() throws SQLException, ClassNotFoundException{ // doctor nesnesi oluşturdum txt fiedlddan alınan yeni değeri burda tuttuk
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        ListNurseController.Nurse nurse = new ListNurseController.Nurse(
               nurseId.getText(), 
               nurseName.getText(), 
               nurseSurname.getText(), 
               nurseExperience.getText());
               
      
       String sql = "update NURSES SET NURSENAME=?,NURSESURNAME=?,NURSEEXPERIMENT=? where NURSEID=?";
       
       ps = con.prepareStatement(sql);
       // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
       ps.setString(1,nurse.getNname());
       ps.setString(2,nurse.getNsurname());
       ps.setInt(3,Integer.valueOf(nurse.getNexp()));
       ps.setInt(4,Integer.valueOf(nurse.getN_id()));
       ps.executeUpdate(); 
      
       
       
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
       
   }
    
}
