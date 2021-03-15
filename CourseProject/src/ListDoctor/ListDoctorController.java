/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDoctor;

import addingDoctor.FXMLController;
import adminMain.MainController;
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
public class ListDoctorController implements Initializable {
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;      
        Statement st=null;
    
 ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();
    @FXML
    private TableView<Doctor> DoctorTable;
    @FXML
    private TableColumn<Doctor,String> doctorIdColumn;
    @FXML
    private TableColumn<Doctor,String> doctorNameColumn;
    @FXML
    private TableColumn<Doctor,String> doctorSurnameColumn;
    @FXML
    private TableColumn<Doctor,String> docDepartmentColumn;
    @FXML
    private AnchorPane tablePanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       InitCol();
            try {
                loadData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ListDoctorController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
    } 

    private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from DOCTORS ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String doctorId= rs.getString("DOCTORID");
            String doctorName= rs.getString("DOCTORNAME");
            String doctorSurname= rs.getString("DOCTORSURNAME");
            String doctorDepartment= rs.getString("DOCTORDEPARTMENT");
            doctorsList.add(new Doctor(doctorId, doctorName, doctorSurname, doctorDepartment));
             
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(ListDoctorController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       DoctorTable.getItems().setAll(doctorsList);
    
    }
       
    public static class Doctor {
 
        // mandatory method for java fx and we are using the simple string factory for listing and showing of data to tablewiew  
        private final SimpleStringProperty DocId;
        private final SimpleStringProperty DocName;
        private final SimpleStringProperty DocSurname;
        private final SimpleStringProperty DocDept;

        public Doctor(String DocId, String DocName, String DocSurname, String DocDept) {
            this.DocId = new SimpleStringProperty (DocId);
            this.DocName =new SimpleStringProperty (DocName);
            this.DocSurname = new SimpleStringProperty (DocSurname);
            this.DocDept = new SimpleStringProperty (DocDept);
        }
   
        public String getDocId() {
            return DocId.get();
        }

        public String getDocName() {
            return DocName.get();
        }

        public String getDocSurname() {
            return DocSurname.get();
        }

        public String getDocDept() {
            return DocDept.get();
        }
 
    }
    private void InitCol(){
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("DocId"));  
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("DocName"));
        doctorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("DocSurname"));
        docDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("DocDept"));
        // aşağıdaki stringle aynı olmak zorunda 
        
    }
    @FXML
    private void DeleteDoctor(ActionEvent event) throws ClassNotFoundException, SQLException {// we are using this code for choosing the doctor delete ıf we dont choose the selection doctor   
                                                      //programme gives message "please choose the select doctor for delete operation " 
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
                                                     
        Doctor delete = DoctorTable.getSelectionModel().getSelectedItem(); // we generated 
        if (delete== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select doctor ");
            alert.setContentText("choose doctor for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "delete from DOCTORS where DOCTORID="+delete.getDocId();
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery(); 
            
            //doctorsList.remove(delete);
       
          JOptionPane.showMessageDialog( null,"delete operation was succesful" );
          
          Stage stage = (Stage) tablePanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
        }
    }

    @FXML
    private void editDoctor(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
              
   
        Doctor edit = DoctorTable.getSelectionModel().getSelectedItem(); // we generated 
        if (edit== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select doctor ");
            alert.setContentText("choose doctor for edit operation");
            alert.showAndWait();
            return; 
        }
        else{ 
            //update işlemini yaptığımız sırada seçtiğimiz satırın bilgilerini update pencerisine eklemek için kullandık 
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addingDoctor/FXML.fxml"));
            Parent parent = loader.load();
            
            FXMLController controller = loader.getController();
            controller.getTextForUpdate(edit);
            
            Stage stage = new Stage(StageStyle.DECORATED );
            stage.setTitle("Editing operation");
            stage.setScene(new Scene(parent));
            stage.show();
            
          
            
            
            //String sql = "update DOCTORS SET DOCTORNAME= ? ,DOCTORSURNAME=?,DOCTORDEPARTMENT=? where DOCTORID= ";
                  
        //    ps = con.prepareStatement(sql);
           // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
         
            //rs =ps.executeQuery();  // we are not using execute query beaceuse every time we are making update. changing the data 
           
          // JOptionPane.showMessageDialog( null,"update operation was succesful" ); 
            
          //  doctorsList.remove(edit);
     
        
        
        
    }
             
        
      Stage stage = (Stage) tablePanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
        
    
    }  
}
