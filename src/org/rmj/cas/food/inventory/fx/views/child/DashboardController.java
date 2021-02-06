/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rmj.cas.food.inventory.fx.views.child;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;

/**
 * FXML Controller class
 *
 * @author user
 */
public class DashboardController implements Initializable {

    @FXML private VBox VBox;
    @FXML private Button btnExit;
    @FXML private TableView table;
    
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    private final String pxeModuleName = "DashboardController";
    private ResultSet poRS = null;
    private static GRider poGRider;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initGridLedger();
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
   

    private String getSQLInventory(){
        return "SELECT" +
            " a.sBarCodex" +
            ",a.sDescript" +
            ",c.dBegInvxx" +
            ",c.dExpiryDt" +
            ",c.nQtyOnHnd" +
            "   FROM Inventory a" +
            "      ,Inv_Master b" +
            "      ,Inv_Master_Expiration c" +
            "	WHERE  a.sStockIDx = b.sStockIDx" +
            "		AND b.sStockIDx = c.sStockIDx" +
            "		AND b.sBranchCd = c.sBranchCd" +
            "		AND b.sBranchCd = oaap.branchcd" +
            "		AND b.nQtyOnHnd > 0" +
            "		AND c.nQtyOnHnd > 0" +
            "		AND (c.dExpiryDt < " + "OR" +
            "			DATEDIFF(c.dExpiryDt, SYSDATE()) >= 5)" +
            "ORDER BY c.dExpiryDt, a.sBarCodex;";
        
    }
    
    public void loadDetail2Grid() {               
        data.clear();
        
        if (poRS == null) return;
        
        try {
            poRS.first();
            for (int lnCtr = 1; lnCtr <= MiscUtil.RecordCount(poRS); lnCtr++){
               
                    poRS.absolute(lnCtr);
                    data.add(new TableModel(String.valueOf(lnCtr), 
                                            poRS.getString("dTransact"),
                                            poRS.getString("sDescript"),
                                            poRS.getString("sSourceNo"),
                                            poRS.getString("nQtyInxxx"),
                                            poRS.getString("nQtyOutxx"),
                                            "",
                                            "",
                                            "",
                                            ""));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FoodLedgerController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
    
    private void initGridLedger(){
        TableColumn index01= new TableColumn("Barcode");
        TableColumn index02= new TableColumn("Description");
        TableColumn index03= new TableColumn("Expiration");
        TableColumn index04= new TableColumn("Quantiy");
        
        index01.setStyle("-fx-alignment: CENTER;");
        index02.setStyle("-fx-alignment: CENTER-LEFT;");
        index03.setStyle("-fx-alignment: CENTER-LEFT;");
        index04.setStyle("-fx-alignment: CENTER;");
     
        index01.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.child.TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.child.TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.child.TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.child.TableModel,String>("index04"));
        
        table.setItems(data);
    }
    
}
