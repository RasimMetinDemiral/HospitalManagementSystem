/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patient;

import ListDoctor.ListDoctorController;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class PatientController implements Initializable {
    Connection con=null; 
    Statement st=null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    int doctorıd;
    int NURSEıd ;
    ObservableList<String> doctordepartmentList = FXCollections.observableArrayList("emergency","urology","romology","norology"); // combobox ın içine yazdırdığımız departmentleri array listte tutuyoruz
    ObservableList<String> List = FXCollections.observableArrayList();//DOCTOR ADLARINI TUTTUK
    ObservableList<String> NURSEList = FXCollections.observableArrayList(); // NURSE ADLARINI TUTTUK
    @FXML
    private TextField patientId;
    @FXML
    private TextField patientName;
    @FXML
    private TextField patientAge;
    @FXML
    private TextField patientSurname;
    @FXML
    private RadioButton womenToggleButton;
    @FXML
    private ToggleGroup genderButton;
    @FXML
    private RadioButton manToggleButton;
    @FXML
    private ComboBox<String> doctorDepartmentsBox;
    @FXML
    private ComboBox<String> doctorsBox;
    @FXML
    private DatePicker reservationDate;
    @FXML
    private Button recordPatientButton;
    @FXML
    private Button cancelButton;
    
    @FXML
    private AnchorPane mainPanel;
    String gender ;
    @FXML
    private TextField reservationIdNumber;
    @FXML
    private TextField wardIdNumber;
    @FXML
    private ComboBox<String> nursesBox;

    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        
          doctorDepartmentsBox.setItems(doctordepartmentList); // doctor depertment isimlerini tuttuğumuz array listten değerleri alıp combo box a yolladık ve tıklandığında değerleri gördük 
        try {
            loadNurseList();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          nursesBox.setItems(NURSEList);
        
    }    

    @FXML
    private void RecordPatient(ActionEvent event) throws ClassNotFoundException, SQLException, ParseException {
        //SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/YYYY");
        //String dateInString = reservationDate.getValue();
        //java.util.Date parsed = formatter.parse(dateInString);
        //Date date = new Date(parsed.getTime());
        try {
            
            
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        int patientIdnumber = Integer.parseInt(patientId.getText());
        int reservationIdnumber = Integer.parseInt(reservationIdNumber.getText());
        int wardIdnumber = Integer.parseInt(wardIdNumber.getText());
        
        String patientNam= patientName.getText(); 
        String patientsur= patientSurname.getText();  
        int patientage = Integer.parseInt(patientAge.getText());
        
        if (manToggleButton.isSelected()){
            gender = manToggleButton.getText();
        }
        else {
            
           gender = womenToggleButton.getText();
       
        }
        String deparment= String.valueOf(doctorDepartmentsBox.getSelectionModel().getSelectedItem()); 
        reservationDate.getValue(); // we took the date value from adding patient panel
         
        
       String Sql =  " insert into PATIENTS values (?,?,?,?,?,?)";
       ps = con.prepareStatement(Sql);
       ps.setInt(1,patientIdnumber);
       ps.setString(2,patientNam);
       ps.setString(3,patientsur);
       ps.setInt(4,patientage);
       ps.setString(5,gender);
       ps.setDate(6,Date.valueOf(reservationDate.getValue()));
       ps.executeQuery(); 
       
       
       String Sql2 =  " insert into RESERVATIONS values (?,?,?,?,?,?)";
       ps = con.prepareStatement(Sql2);
       ps.setInt(1,reservationIdnumber);
       ps.setInt(2,doctorıd);
       ps.setInt(3,patientIdnumber);
       ps.setInt(4,NURSEıd);
       ps.setDate(5,Date.valueOf(reservationDate.getValue()));
       ps.setInt(6,wardIdnumber);
       
       ps.executeQuery(); 
       
       
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        
              Alert alert = new  Alert(Alert.AlertType.INFORMATION); // we catch the error
             alert.setHeaderText("The process of adding a patient is complete");
             alert.setContentText(" please make new operation"); 
             alert.showAndWait();
            Stage stage = (Stage) mainPanel.getScene().getWindow();
           stage.close(); // we close the window when we clicked the cancel button  
        
    }

    @FXML
    private void cancelButton(ActionEvent event) {
         Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
    }

    @FXML
    private void doctorSelection(ActionEvent event) throws ClassNotFoundException, SQLException { // combobox ın içine databaseden değerleri yazdırmak için kullandık
       Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       String Sql = " select * from DOCTORS WHERE DOCTORDEPARTMENT = '"+doctorDepartmentsBox.getSelectionModel().getSelectedItem()+"'";
      //System.out.println(Sql);
       ps = con.prepareStatement(Sql);
       rs =ps.executeQuery(); 
       doctorsBox.getItems().removeAll(List); // combo box ın içindeki değerleri sildik eğer tekrar department seçmek istiyorsak 
       while(rs.next()) {
                  
                  String doctorName = rs.getString("DOCTORNAME"); // result setten dönen değeri aldık ve combo box ın içine yolladık
                  List.add(doctorName);
                  
                  doctorsBox.setItems(List);
                }
       
       
        
        
    }

    @FXML
    private void patientIdControl(KeyEvent event) {
         try{
            int IDnumber = Integer.parseInt(patientId.getText()); // nurse ıd text file ından değeri aldık 
            
            patientId.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
            
        }
    
        catch(NumberFormatException e){
                
             patientId.setStyle("-fx-control-inner-background: #ff1a1a;");
             // data input control line if we can wrong type input it will show red line
          } 
        
        
    }

    @FXML
    private void patientAgeControl(KeyEvent event) {
         try{
             // nurse ıd text file ından değeri aldık 
            int patAge = Integer.parseInt(patientAge.getText()); // nurse ıd text file ından değeri aldık 
            
             // data input control line , if we can wrong type input it will show white line
            patientAge.setStyle("-fx-control-inner-background: #ffffff;");
        }
    
        catch(NumberFormatException e){
                
             
            patientAge.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
    }

    @FXML
    private void reservationIdControl(KeyEvent event) {
        try{
             // nurse ıd text file ından değeri aldık 
            int reservationIdcontrol = Integer.parseInt(reservationIdNumber.getText()); // nurse ıd text file ından değeri aldık 
            
             // data input control line , if we can wrong type input it will show white line
            reservationIdNumber.setStyle("-fx-control-inner-background: #ffffff;");
        }
        catch(NumberFormatException e){
                
             
            reservationIdNumber.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
    }

    @FXML
    private void wardIdControl(KeyEvent event) {
        
         try{
             // nurse ıd text file ından değeri aldık 
            int wardIdcontrol = Integer.parseInt(wardIdNumber.getText()); // nurse ıd text file ından değeri aldık 
            
             // data input control line , if we can wrong type input it will show white line
            wardIdNumber.setStyle("-fx-control-inner-background: #ffffff;");
        }
        catch(NumberFormatException e){
                
             
            wardIdNumber.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
        
        
    }

    @FXML
    private void nurseSelection(ActionEvent event) throws ClassNotFoundException, SQLException { //reservatsyon listelemede nurse ıdyi aldık
        
       Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       String Sql = " select * from NURSES WHERE NURSENAME = '"+nursesBox.getSelectionModel().getSelectedItem()+"'" ;
      //System.out.println(Sql);
       ps = con.prepareStatement(Sql);
       rs =ps.executeQuery(); 
       while(rs.next()) {
                  
                  NURSEıd = rs.getInt("NURSEID"); // result setten dönen değeri aldık name x olanın ı disini aldık
               
                  
                 
                  
                }  
        
    
        
        
    }

    private void loadNurseList() throws ClassNotFoundException, SQLException {//nurse isimlerini combo box a yazdırdık
     Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       String Sql = " select * from NURSES  ";
      //System.out.println(Sql);
       ps = con.prepareStatement(Sql);
       rs =ps.executeQuery(); 
       while(rs.next()) {
                  
                  String nurseName = rs.getString("NURSENAME"); // result setten dönen değeri aldık ve combo box ın içine yolladık
                  NURSEList.add(nurseName);
                  
                  
                }  
    }

    @FXML
    private void doctorIdNumber(ActionEvent event) throws ClassNotFoundException, SQLException { // reservatsyon listelemede doctor ıdyi aldık
        
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       String Sql = " select * from DOCTORS WHERE DOCTORNAME = '"+doctorsBox.getSelectionModel().getSelectedItem()+"'" ;
      //System.out.println(Sql);
       ps = con.prepareStatement(Sql);
       rs =ps.executeQuery(); 
       while(rs.next()) {
                  
                  doctorıd = rs.getInt("DOCTORID"); // result setten dönen değeri aldık name x olanın ı disini aldık
                 
                  
                 
                  
                }  
        
    }
    
}
