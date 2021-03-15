/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListPatient;

import ListDoctor.ListDoctorController;
import ListNurse.ListNurseController;
import courseproject.enterenceWindowController;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class ListPatientController implements Initializable {
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;
        Statement st=null;
    
     ObservableList<Reservation> patientList = FXCollections.observableArrayList();     
    @FXML
    private TableColumn<Reservation, String> reservationIDColumn;
    @FXML
    private TableColumn<Reservation, String> doctorIdColumn;
    @FXML
    private TableColumn<Reservation, String> patientIdColumn;
    @FXML
    private TableColumn<Reservation, String> nurseIdColumn;
    @FXML
    private TableColumn<Reservation, String> reservationDateColumn;
    @FXML
    private TableColumn<Reservation, String> wardIdColumn;
    @FXML
    private TableView<Reservation> patientTable;
    @FXML
    private AnchorPane mainPanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           InitCol();
            try {
                loadData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ListPatientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }  
     @FXML
    private void nurseRelationButton(ActionEvent event) {
        LoadWindow("/listnurseRelation/listnurseRelation.fxml", "list  window");
        
    }
    
    private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from RESERVATIONS ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String reservationid= rs.getString("RESERVATIONID");
            String doctorid= rs.getString("DOCTORID");
            String patientid= rs.getString("PATIENTID");
            String nurseid= rs.getString("NURSEID");
            String reservationdate= rs.getString("RESERVATIONDATE");
            String wardid= rs.getString("WARDID");
            patientList.add(new Reservation(reservationid, doctorid, patientid, nurseid, reservationdate, wardid));
            
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(ListPatientController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       patientTable.getItems().setAll(patientList);
    
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
   
     public static class Reservation{
        private final SimpleStringProperty reservationID;
        private final SimpleStringProperty doctorID;
        private final SimpleStringProperty patientID;
        private final SimpleStringProperty nurseID;
        private final SimpleStringProperty reservationDate;
        private final SimpleStringProperty wardID;

        public Reservation(String reservationID,String doctorID, String patientID, String nurseID, String reservationDate, String wardID) {
            this.reservationID = new SimpleStringProperty(reservationID);
            this.doctorID = new SimpleStringProperty(doctorID);
            this.patientID = new SimpleStringProperty(patientID);
            this.nurseID = new SimpleStringProperty(nurseID);
            this.reservationDate = new SimpleStringProperty(reservationDate);
            this.wardID = new SimpleStringProperty(wardID);
        }

        public String getReservationID() {
            return reservationID.get();
        }

        public String getDoctorID() {
            return doctorID.get();
        }

        public String getPatientID() {
            return patientID.get();
        }

        public String getNurseID() {
            return nurseID.get();
        }

        public String getReservationDate() {
            return reservationDate.get();
        }

        public String getWardID() {
            return wardID.get();
        }
        
        
     }
         
        private void InitCol(){
        reservationIDColumn.setCellValueFactory(new PropertyValueFactory<>("reservationID"));  
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        nurseIdColumn.setCellValueFactory(new PropertyValueFactory<>("nurseID"));   
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        wardIdColumn.setCellValueFactory(new PropertyValueFactory<>("wardID"));
    }
    
  
    @FXML
    private void deleteReservation(ActionEvent event) throws ClassNotFoundException, SQLException {
         Class.forName("oracle.jdbc.driver.OracleDriver"); 
         con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
                                                     
       Reservation delete = patientTable.getSelectionModel().getSelectedItem(); // we generated 
        if (delete== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select reservation ");
            alert.setContentText("choose reservation for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "delete from RESERVATIONS where RESERVATIONID="+delete.getReservationID();
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery(); 
            
            //nursesList.remove(delete);
       
          JOptionPane.showMessageDialog( null,"delete operation was succesful" );
          
          Stage stage = (Stage) mainPanel.getScene().getWindow();
          stage.close(); // we close the window when we clicked the cancel button
    
        }
    
        
    }

    
}
