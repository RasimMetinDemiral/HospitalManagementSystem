/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listReservertionIdvalue;

import ListDoctor.ListDoctorController;
import ListPatient.ListPatientController;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listnurseRelation.ListnurseRelationController;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class ListreservationıdvalueController implements Initializable {
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;      
        Statement st=null;
    ObservableList<nurseRelation> nurserelationList = FXCollections.observableArrayList(); 
    ListnurseRelationController metin = new ListnurseRelationController();
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private TableView< nurseRelation> reservationıdTable;
    @FXML
    private TableColumn<nurseRelation,String> reservationIdColumn;

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
     private void loadData() throws ClassNotFoundException, SQLException {
        
       
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select RESERVATIONID from  RESERVATIONS  where NURSEID = 23";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
       try {
            while(rs.next()) {
            String reservationId= rs.getString("RESERVATIONID");
           
            nurserelationList.add(new nurseRelation(reservationId));
            }
           
        } catch (SQLException e) {
            Logger.getLogger(ListreservationıdvalueController.class.getName()).log(Level.SEVERE,null,e);
        }
       
          reservationıdTable.getItems().setAll(nurserelationList);
    
    }
    public static class nurseRelation{
        private final SimpleStringProperty ReservationId;

        public nurseRelation(String ReservationId) {
            this.ReservationId = new SimpleStringProperty(ReservationId);
        }

        public String getReservationId() {
            return ReservationId.get();
        }
       
        
        
    }
    private void InitCol(){
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("ReservationId"));  
        
    }
    
    void LoadWindow(String loc,String title){
        
        try{
          
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            
            
        }
        catch(IOException e){
            Logger.getLogger(enterenceWindowController.class.getName()).log(Level.SEVERE,null,e);
            
        }
    }
    
    
}
