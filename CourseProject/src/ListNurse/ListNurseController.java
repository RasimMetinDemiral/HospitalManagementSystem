/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListNurse;

import ListDoctor.ListDoctorController;
import addingDoctor.FXMLController;
import addingNurse.AddingNurseController;
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
public class ListNurseController implements Initializable {
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;
        Statement st=null;
    ObservableList<Nurse> nursesList = FXCollections.observableArrayList();    
    @FXML
    private TableView<Nurse> NurseTable;
    @FXML
    private TableColumn<Nurse,String> nurseIdColumn;
    @FXML
    private TableColumn<Nurse,String> nurseNameColumn;
    @FXML
    private TableColumn<Nurse,String> nurseSurnameColumn;
    @FXML
    private TableColumn<Nurse,String> nurseExperimentColumn;
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
                Logger.getLogger(ListDoctorController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    
     private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from NURSES ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String NurseId= rs.getString("NURSEID");
            String NurseName= rs.getString("NURSENAME");
            String NurseSurname= rs.getString("NURSESURNAME");
            String NurseExperiment= rs.getString("NURSEEXPERIMENT");
            nursesList.add(new Nurse(NurseId, NurseName, NurseSurname, NurseExperiment));
            
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(ListDoctorController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       NurseTable.getItems().setAll(nursesList);
    
    }


    @FXML
    private void DeleteNurse(ActionEvent event) throws ClassNotFoundException, SQLException {
             Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
                                                     
        Nurse delete = NurseTable.getSelectionModel().getSelectedItem(); // we generated 
        if (delete== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select nurse ");
            alert.setContentText("choose nurse for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "delete from NURSES where NURSEID="+delete.getN_id();
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery(); 
            
            //nursesList.remove(delete);
       
          JOptionPane.showMessageDialog( null,"delete operation was succesful" );
          
          Stage stage = (Stage) mainPanel.getScene().getWindow();
          stage.close(); // we close the window when we clicked the cancel button
    
        }
    
    }
    
    

    @FXML
    private void editNurse(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");   
    
        Nurse edit = NurseTable.getSelectionModel().getSelectedItem(); // we generated 
        if (edit== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select nurse ");
            alert.setContentText("choose nurse for edit operation");
            alert.showAndWait();
            return; 
        }
        else{ 
            //update işlemini yaptığımız sırada seçtiğimiz satırın bilgilerini update pencerisine eklemek için kullandık 
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addingNurse/addingNurse.fxml"));
            Parent parent = loader.load();
            
            AddingNurseController controller = loader.getController();
            controller.getTextForUpdates(edit);
            
            Stage stage = new Stage(StageStyle.DECORATED );
            stage.setTitle("Editing operation");
            stage.setScene(new Scene(parent));
            stage.show();
            
             
            
        
           
      
        
    }   
             
             Stage stage = (Stage) mainPanel.getScene().getWindow();
             stage.close(); // we close the window when we clicked the cancel button
             
        
    }
    

    
    public static class Nurse {
        
        
        private final SimpleStringProperty N_id;
        private final SimpleStringProperty Nname;
        private final SimpleStringProperty Nsurname;
        private final SimpleStringProperty Nexp;
        public Nurse(String N_id,String Nname, String Nsurname,String Nexp) {
            this.N_id = new SimpleStringProperty(N_id);
            this.Nname = new SimpleStringProperty(Nname);
            this.Nsurname = new SimpleStringProperty(Nsurname);
        
            this.Nexp = new SimpleStringProperty(Nexp);
            
            }

      

        public String getN_id() {
            return N_id.get();
        }

        public String getNname() {
            return Nname.get();
        }

        public String getNsurname() {
            return Nsurname.get();
        }
          public String getNexp() {
            return Nexp.get();
        }
       

    } 
    private void InitCol(){
        nurseIdColumn.setCellValueFactory(new PropertyValueFactory<>("N_id"));  
        nurseNameColumn.setCellValueFactory(new PropertyValueFactory<>("Nname"));
        nurseSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("Nsurname"));
        nurseExperimentColumn.setCellValueFactory(new PropertyValueFactory<>("Nexp"));    
       
        // aşağıdaki stringle aynı olmak zorunda 
        
    }
    
 
}
   

/*

*/