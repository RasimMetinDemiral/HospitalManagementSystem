/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformationPatient;

import DoctorWindow.DoctorAndPatientController;
import ListPatient.ListPatientController;
import java.net.URL;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author khas
 */
public class InformationPatientController implements Initializable {
    ObservableList<Patient> listOfPatient = FXCollections.observableArrayList(); 
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> patiendIdColumn;
    @FXML
    private TableColumn<Patient, String> patientNameColumn;
    @FXML
    private TableColumn<Patient, String> patientSurnameColumn;
    @FXML
    private TableColumn<Patient, String> patientAgeColumn;
    @FXML
    private TableColumn<Patient, String> patientGenderColumn;
       
    @FXML
    private TableColumn<Patient, String> patientDateColumn;
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;
        Statement st=null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InitCol();
            try {
                loadData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(InformationPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    } 
    private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from PATIENTS ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String PatientId= rs.getString("PATIENTID");
            String PatientName= rs.getString("PATIENTNAME");
            String PatientSurname= rs.getString("PATIENTSURNAME");
            String PatientAge= rs.getString("PATIENTAGE");
            String PatientGender= rs.getString("PATIENTGENDER");
            String PatientDate= rs.getString("PATIENTDATE");
            
            listOfPatient.add(new Patient(PatientId, PatientName, PatientSurname, PatientAge, PatientGender,PatientDate));
            
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(DoctorAndPatientController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       patientTable.getItems().setAll(listOfPatient);
    
    }
    public static class Patient{
        private final SimpleStringProperty patientID;
        private final SimpleStringProperty patientName;
        private final SimpleStringProperty patientSurname;
        private final SimpleStringProperty patientAge;
        private final SimpleStringProperty patientGender;
        private final SimpleStringProperty patientDate;

        public Patient(String patientID, String patientName, String patientSurname, String patientAge, String patientGender,String patientDate) {
            this.patientID = new SimpleStringProperty(patientID);
            this.patientName = new SimpleStringProperty(patientName);
            this.patientSurname = new SimpleStringProperty(patientSurname);
            this.patientAge = new SimpleStringProperty(patientAge);
            this.patientGender = new SimpleStringProperty(patientGender);
            this.patientDate = new SimpleStringProperty(patientDate);        
        }

        public String getPatientID() {
            return patientID.get();
        }

        public String getPatientName() {
            return patientName.get();
        }

        public String getPatientSurname() {
            return patientSurname.get();
        }

        public String getPatientAge() {
            return patientAge.get();
        }

        public String getPatientGender() {
            return patientGender.get();
        }
        public String getPatientDate() {
            return patientDate.get();
        }
        
        
    }

    @FXML
    private void deletePatient(ActionEvent event) throws SQLException, ClassNotFoundException {
         Class.forName("oracle.jdbc.driver.OracleDriver"); 
         con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
                                                     
       Patient delete = patientTable.getSelectionModel().getSelectedItem(); // we generated 
        if (delete== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select patient ");
            alert.setContentText("choose patient for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "delete from PATIENTS where PATIENTID="+delete.getPatientID();
           // tablo ismini değiştir
            
           
           
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery(); 
            
            //nursesList.remove(delete);
       
          JOptionPane.showMessageDialog( null,"delete operation was succesful" );
          
          Stage stage = (Stage) mainPanel.getScene().getWindow();
          stage.close(); // we close the window when we clicked the cancel button
    
        }
    
        
    }
    private void InitCol(){
        patiendIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));  
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        patientSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("patientSurname"));
        patientAgeColumn.setCellValueFactory(new PropertyValueFactory<>("patientAge"));   
        patientGenderColumn.setCellValueFactory(new PropertyValueFactory<>("patientGender"));
        patientDateColumn.setCellValueFactory(new PropertyValueFactory<>("patientDate"));
    }    
    
}
