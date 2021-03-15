/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InformationDoctor;

import DoctorWindow.DoctorAndPatientController;
import InformationPatient.InformationPatientController;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class InformationDoctorController implements Initializable {
    
    
     ObservableList<DoctorInformation> doctorRelationList = FXCollections.observableArrayList(); 
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private TableView<DoctorInformation> doctorInformationTable;
    @FXML
    private TableColumn<DoctorInformation, String> doctorIdColumn;
    @FXML
    private TableColumn<DoctorInformation, String> doctorNameColumn;
    @FXML
    private TableColumn<DoctorInformation, String> doctorSurnameColumn;
    @FXML
    private TableColumn<DoctorInformation, String> patientNameColumn;
    @FXML
    private TableColumn<DoctorInformation, String> patientSurnameColumn;
    @FXML
    private TableColumn<DoctorInformation, String> reservationDateColumn;

      
    @FXML
    private TableColumn<DoctorInformation, String> patientIdColumn;
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
                Logger.getLogger(InformationDoctorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }  
    
     private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select d.DOCTORID,d.DOCTORNAME,d.DOCTORSURNAME,p.PATIENTID,p.PATIENTNAME,p.PATIENTSURNAME,r.RESERVATIONDATE from RESERVATIONS r,PATIENTS p,DOCTORS d where r.PATIENTID = p.PATIENTID and r.DOCTORID = d.DOCTORID";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String Doctorıd= rs.getString("DOCTORID");
            String Doctorname= rs.getString("DOCTORNAME");
            String Doctorsurname= rs.getString("DOCTORSURNAME");
            String PatientId= rs.getString("PATIENTID");
            String Patientname= rs.getString("PATIENTNAME");
            String Patientsurname= rs.getString("PATIENTSURNAME");
            String Reservationdate= rs.getString("RESERVATIONDATE");
            
            doctorRelationList.add(new DoctorInformation(Doctorıd, Doctorname, Doctorsurname,PatientId, Patientname, Patientsurname,Reservationdate));
            
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(DoctorAndPatientController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       doctorInformationTable.getItems().setAll(doctorRelationList);
    
    }
     public static class DoctorInformation{
        private final SimpleStringProperty doctorID;
        private final SimpleStringProperty doctorName;
        private final SimpleStringProperty doctorSurname;
        private final SimpleStringProperty patientId;
        private final SimpleStringProperty patientName;
        private final SimpleStringProperty patientSurname;
        private final SimpleStringProperty reservationDate;

        public DoctorInformation(String doctorID, String doctorName, String doctorSurname,String patientId, String patientName, String patientSurname, String reservationDate) {
            this.doctorID = new SimpleStringProperty(doctorID);
            this.doctorName = new SimpleStringProperty(doctorName);
            this.doctorSurname = new SimpleStringProperty(doctorSurname);
            this.patientId = new SimpleStringProperty(patientId);
            this.patientName = new SimpleStringProperty(patientName);
            this.patientSurname = new SimpleStringProperty(patientSurname);
            this.reservationDate = new SimpleStringProperty(reservationDate);
        }

        public String getDoctorID() {
            return doctorID.get();
        }

        public String getDoctorName() {
            return doctorName.get();
        }

        public String getDoctorSurname() {
            return doctorSurname.get();
        }
         public String getPatientId() {
            return patientId.get();
        }
        
        public String getPatientName() {
            return patientName.get();
        }

        public String getPatientSurname() {
            return patientSurname.get();
        }

        public String getReservationDate() {
            return reservationDate.get();
        }
         
         
     }
     private void InitCol(){
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));  
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        doctorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorSurname"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));   
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));   
        patientSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("patientSurname"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
    }    
    
}
