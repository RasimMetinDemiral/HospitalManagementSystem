

package addingDoctor;

import ListDoctor.ListDoctorController;
import ListNurse.ListNurseController;
import databaseConnection.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class FXMLController implements Initializable {

    @FXML
    private AnchorPane mainPanel;
    @FXML
    private TextField docId;
    @FXML
    private TextField docName;
    @FXML
    private TextField docSurname;
    @FXML
    private TextField docDepartment;
    @FXML
    private Button addbutton;
    @FXML
    private Button cancelbutton;

        Connection con= null;
        PreparedStatement ps = null; 
        ResultSet rs = null;
        
        Boolean editMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        
    }    

    @FXML
    private void dataInputControl(KeyEvent event) {
        
         try{
            int number = Integer.parseInt(docId.getText()); // doctor ıd text file ından değeri aldık 
            docId.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
   
        }
    
        catch(NumberFormatException e){
                
           
            docId.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
    
    

    
    // key realesed methodunu kullandık çünkü klavyeden girilen değere göre hata verecek yani keyboard takibi yapacaüız
    
    }

    @FXML
    private void addDoctor(ActionEvent event) throws SQLException, ClassNotFoundException {
        
        int doctor_ID = Integer.parseInt(docId.getText());
        String doctor_name = docName.getText();
        String doctor_surname = docSurname.getText();
        String doctor_dept = docDepartment.getText();
        
        
        if (doctor_name.isEmpty() || doctor_surname.isEmpty()|| doctor_dept.isEmpty()){ // we control the all data enterence 
        
             Alert alert = new  Alert(Alert.AlertType.ERROR); // we catch the error
             alert.setHeaderText("catched the error"); 
             alert.setContentText("please enter the all fields");
             alert.showAndWait();
        }
        else{
             if(editMode){ // edit butonunun tıklanıp tıklanmadığını kontorl ettik 
             updateDoctor();
             }
             else{
                 try {
               Class.forName("oracle.jdbc.driver.OracleDriver"); 
               con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       
            String SqlZ = " INSERT INTO DOCTORS VALUES (?,?,?,?)";
            ps = con.prepareStatement(SqlZ);
            ps.setInt(1,doctor_ID); // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
            ps.setString(2,doctor_name);
            ps.setString(3,doctor_surname);
            ps.setString(4,doctor_dept);
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
             alert.setHeaderText("The process of adding a doctor is complete");
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
    
    public void getTextForUpdate(ListDoctorController.Doctor doctor){// update komutu için açılan ekranda text fielddan girilen değerleri aldık ve methoda yolladık
        docId.setText(doctor.getDocId());
        docName.setText(doctor.getDocName());
        docSurname.setText(doctor.getDocSurname());
        docDepartment.setText(doctor.getDocDept());
        editMode = true;
    }
   public void updateDoctor() throws SQLException, ClassNotFoundException{ // doctor nesnesi oluşturdum txt fiedlddan alınan yeni değeri burda tuttuk
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       ListDoctorController.Doctor doctor = new ListDoctorController.Doctor(
               docId.getText(), 
               docName.getText(), 
               docSurname.getText(), 
               docDepartment.getText());
               
      
       String sql = "update DOCTORS SET DOCTORNAME=?,DOCTORSURNAME=?,DOCTORDEPARTMENT=? where DOCTORID=?";
       
       ps = con.prepareStatement(sql);
       // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
       ps.setString(1,doctor.getDocName());
       ps.setString(2,doctor.getDocSurname());
       ps.setString(3,doctor.getDocDept());
       ps.setInt(4,Integer.valueOf(doctor.getDocId()));
       ps.executeUpdate(); 
      
       
       
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
       
   }


    
    
}
