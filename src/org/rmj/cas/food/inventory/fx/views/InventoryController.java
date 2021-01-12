package org.rmj.cas.food.inventory.fx.views;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.cas.inventory.base.Inventory;

public class InventoryController implements Initializable {

    @FXML private VBox VBoxForm;
    @FXML private Button btnExit;
    @FXML private AnchorPane anchorField;
    @FXML private TextField txtField01;
    @FXML private TextField txtField02;
    @FXML private TextField txtField03;
    @FXML private TextField txtField04;
    @FXML private TextField txtField05;
    @FXML private TextField txtField06;
    @FXML private TextField txtField07;
    @FXML private TextField txtField08;
    @FXML private TextField txtField09;
    @FXML private TextField txtField10;
    @FXML private TextField txtField11;
    @FXML private TextField txtField12;
    @FXML private TextField txtField13;
    @FXML private TextField txtField14;
    @FXML private TextField txtField15;
    @FXML private TextField txtField16;
    @FXML private TextField txtField17;
    @FXML private TextField txtField18;
    @FXML private TextField txtField19;
    @FXML private ComboBox Combo23;
    @FXML private ComboBox Combo24;
    @FXML private CheckBox Check20;
    @FXML private CheckBox Check21;
    @FXML private CheckBox Check22;
    @FXML private CheckBox Check26;
    @FXML private Button btnSave;
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;
    @FXML private Button btnClose;
    @FXML private Button btnSearch;
    @FXML private Button btnBrowse;
    @FXML private Button btnNew;
    @FXML private Button btnActivate;
    @FXML private TextField txtField25;
    @FXML private FontAwesomeIconView glyphExit;
    @FXML private Label lblHeader;
    @FXML private TableView tblInvetoryDetail;
    @FXML private TableColumn index01;
    @FXML private TableColumn index02;
    @FXML private TableColumn index03;
    @FXML private TableColumn index04;
    @FXML private TextField txtField50;
    @FXML private TextField txtField51;
    @FXML private TextField txtField52;
    @FXML private Button btnDeleteItem;
    @FXML private TextField txtField80;
    @FXML private TextField txtField81;
    @FXML private Button btnAuto;
    @FXML private TextField txtField29;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Initialize class*/
        poRecord = new Inventory(poGRider, poGRider.getBranchCode(), false);
                
        /*Set action event handler for the buttons*/
        btnBrowse.setOnAction(this::cmdButton_Click);
        btnCancel.setOnAction(this::cmdButton_Click);
        btnClose.setOnAction(this::cmdButton_Click);
        btnExit.setOnAction(this::cmdButton_Click);
        btnNew.setOnAction(this::cmdButton_Click);
        btnSave.setOnAction(this::cmdButton_Click);
        btnSearch.setOnAction(this::cmdButton_Click);
        btnUpdate.setOnAction(this::cmdButton_Click);
        btnActivate.setOnAction(this::cmdButton_Click);
        btnDeleteItem.setOnAction(this::cmdButton_Click);
        btnAuto.setOnAction(this::cmdButton_Click);
                
        /*Add listener to text fields*/
        txtField02.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField04.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtField_Focus);
        txtField06.focusedProperty().addListener(txtField_Focus);
        txtField07.focusedProperty().addListener(txtField_Focus);
        txtField08.focusedProperty().addListener(txtField_Focus);
        txtField09.focusedProperty().addListener(txtField_Focus);
        txtField10.focusedProperty().addListener(txtField_Focus);
        txtField11.focusedProperty().addListener(txtField_Focus);
        txtField12.focusedProperty().addListener(txtField_Focus);
        txtField13.focusedProperty().addListener(txtField_Focus);
        txtField14.focusedProperty().addListener(txtField_Focus);
        txtField15.focusedProperty().addListener(txtField_Focus);
        txtField16.focusedProperty().addListener(txtField_Focus);
        txtField17.focusedProperty().addListener(txtField_Focus);
        txtField18.focusedProperty().addListener(txtField_Focus);
        txtField19.focusedProperty().addListener(txtField_Focus);
        txtField25.focusedProperty().addListener(txtField_Focus);
        txtField29.focusedProperty().addListener(txtField_Focus);
        txtField50.focusedProperty().addListener(txtField_Focus);
        txtField51.focusedProperty().addListener(txtField_Focus);
        txtField52.focusedProperty().addListener(txtField_Focus);
        txtField80.focusedProperty().addListener(txtField_Focus);
        txtField81.focusedProperty().addListener(txtField_Focus);
        
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField02.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField04.setOnKeyPressed(this::txtField_KeyPressed);
        txtField05.setOnKeyPressed(this::txtField_KeyPressed);
        txtField06.setOnKeyPressed(this::txtField_KeyPressed);
        txtField07.setOnKeyPressed(this::txtField_KeyPressed);
        txtField08.setOnKeyPressed(this::txtField_KeyPressed);
        txtField09.setOnKeyPressed(this::txtField_KeyPressed);
        txtField10.setOnKeyPressed(this::txtField_KeyPressed);
        txtField11.setOnKeyPressed(this::txtField_KeyPressed);
        txtField12.setOnKeyPressed(this::txtField_KeyPressed);
        txtField13.setOnKeyPressed(this::txtField_KeyPressed);
        txtField14.setOnKeyPressed(this::txtField_KeyPressed);
        txtField15.setOnKeyPressed(this::txtField_KeyPressed);
        txtField16.setOnKeyPressed(this::txtField_KeyPressed);
        txtField17.setOnKeyPressed(this::txtField_KeyPressed);
        txtField18.setOnKeyPressed(this::txtField_KeyPressed);
        txtField19.setOnKeyPressed(this::txtField_KeyPressed);
        txtField25.setOnKeyPressed(this::txtField_KeyPressed);
        txtField29.setOnKeyPressed(this::txtField_KeyPressed);
        txtField50.setOnKeyPressed(this::txtField_KeyPressed);
        txtField51.setOnKeyPressed(this::txtField_KeyPressed);
        txtField52.setOnKeyPressed(this::txtField_KeyPressed);
        txtField80.setOnKeyPressed(this::txtField_KeyPressed);
        txtField81.setOnKeyPressed(this::txtField_KeyPressed);
        
        Combo23.setOnKeyPressed(this::ComboBox_KeyPressed);
        Combo24.setOnKeyPressed(this::ComboBox_KeyPressed);
        
        Check20.setOnKeyPressed(this::Check_KeyPressed);
        Check21.setOnKeyPressed(this::Check_KeyPressed);
        Check22.setOnKeyPressed(this::Check_KeyPressed);
        Check26.setOnKeyPressed(this::Check_KeyPressed);
        
        Combo23.setItems(cUnitType);
        Combo23.getSelectionModel().select(1);
        
        Combo24.setItems(cInvStatx);
        Combo24.getSelectionModel().select(1);
        pnEditMode = EditMode.UNKNOWN;
        
        clearFields();
        initGrid();
        initButton(pnEditMode);
        
        pbLoaded = true;
    }
    
    private void ComboBox_KeyPressed(KeyEvent event){
        ComboBox cmbBox = (ComboBox)event.getSource();
        
        switch (event.getCode()){
        case ENTER:
        case DOWN:
            CommonUtils.SetNextFocus(cmbBox);
            break;
        case UP:
            CommonUtils.SetPreviousFocus(cmbBox);
        }
    }
    
    private void Check_KeyPressed(KeyEvent event){
        CheckBox chkBox = (CheckBox)event.getSource();
            switch (event.getCode()){
            case ENTER:
            case DOWN:
                CommonUtils.SetNextFocus(chkBox);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(chkBox);
            }
    }
      
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(((TextField)event.getSource()).getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == F3 || event.getCode() == ENTER){
            switch (lnIndex){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 25:
                    break;
                case 6:
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psCateg1.equals(lsValue)){ 
                        psCateg1 = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psCateg1);
                    } break;
                    
                case 7:
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psCateg2.equals(lsValue)){ 
                        psCateg2 = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psCateg2);
                    } break;
                    
                case 8: 
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psCateg3.equals(lsValue)){ 
                        psCateg3 = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psCateg3);
                    } break;
                    
                case 9: 
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psCateg4.equals(lsValue)){ 
                        psCateg4 = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psCateg4);
                    } break;
                    
                case 10:
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psBrand.equals(lsValue)){ 
                        psBrand = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psBrand);
                    } break;
                    
                case 11:
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psModel.equals(lsValue)){ 
                        psModel = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psModel);
                    } break;
                    
                case 12:
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psColor.equals(lsValue)){ 
                        psColor = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psColor);
                    } break;
                case 13: 
                   if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                   if (!psInvType.equals(lsValue)){ 
                        psInvType = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psInvType);
                    } break;
                case 29: 
                   if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                   if (!psMeasurID.equals(lsValue)){ 
                        psMeasurID = poRecord.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psMeasurID);
                    } break;
                case 50:
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    poRecord.SearchSubUnit(pnRow, "sItmSubID", lsValue, false, false);
                    loadDetail2Grid();
                    break;
                    
                case 51:
                    if (event.getCode() == F3) lsValue = txtField.getText() + "%";
                    poRecord.SearchSubUnit(pnRow, "sItmSubID", lsValue, true, false);
                    loadDetail2Grid();
                    break;
                    
                case 52:
                    int lnQty;
                    try {
                        lnQty = Integer.parseInt(txtField.getText());
                    } catch (Exception e) {
                        lnQty = 0;
                        txtField.setText("0");
                    }
                    poRecord.setSubUnit(pnRow, "nQuantity", lnQty);
                    txtField50.setText("");
                    txtField51.setText(""); 
                    loadDetail2Grid();
                    return;
                 case 80: /*Search for Barcode*/
                    if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                    
                        if(poRecord.BrowseRecord(lsValue, false, false)==true){
                            loadRecord();
                            pnEditMode = poRecord.getEditMode();
                            break;
                        }
                        if(!txtField80.getText().equals(psBarcode)){
                            clearFields();
                            break;
                        }else { txtField80.setText(psBarcode); } return;
                     
                case 81: /*Search for Description*/
                    if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                        if(poRecord.BrowseRecord(lsValue, false, true)== true){
                            loadRecord(); 
                            pnEditMode = poRecord.getEditMode();
                            break;
                        }
                        if(!txtField81.getText().equals(psDescript)){
                            clearFields();
                            break;
                            }else { txtField81.setText(psDescript); } return;
                default:
                    ShowMessageFX.Warning("Please inform MIS Dept.", pxeModuleName, "Text field with index " + lnIndex + " not registered for QuickSearch.");
                    return;
            }
        }
            
            switch (event.getCode()){
            case ENTER:
            case DOWN:
                CommonUtils.SetNextFocus(txtField);
                break;
            case UP:
                CommonUtils.SetPreviousFocus(txtField);
            }
    }   
        
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId();    
        String lsValue;
        
        switch (lsButton){
            case "btnBrowse":
               switch(pnIndex){
                case 80: /*sTransNox*/
                    lsValue = txtField80.getText() + "%";
                    if(poRecord.BrowseRecord(lsValue, false, false) == true){
                        loadRecord(); 
                        pnEditMode = poRecord.getEditMode();
                        break;
                    }
                    
                    if(!txtField80.getText().equals(psBarcode)){
                        clearFields();
                        }else{
                            txtField80.setText(psBarcode); } return;
                     
                case 81: /*sDescript*/
                    lsValue = txtField81.getText() + "%";
                    
                    if(poRecord.BrowseRecord(lsValue, false, true)== true){
                        loadRecord(); 
                        pnEditMode = poRecord.getEditMode();
                        break;
                    }
                     
                    if(!txtField81.getText().equals(psDescript)){
                        clearFields();
                    }else{
                        txtField81.setText(psDescript); } return;       
                                        
                default:
                      ShowMessageFX.Warning("No Entry", pxeModuleName, "Please have at least one keyword to browse!");
                      txtField81.requestFocus();
                }
                return;
                
            case "btnCancel":
               if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to disregard changes?") == true){
                    clearFields();
                    pnEditMode = EditMode.UNKNOWN;
                    break;
                } else
                    return;
               
            case "btnClose":
            case "btnExit":
                unloadForm();
                return;
                
            case "btnNew":
                if (poRecord.NewRecord()) loadRecord(); pnEditMode = poRecord.getEditMode();
                break;
                
            case "btnSave":
                if(sendOtherDetail()){
                    if (poRecord.SaveRecord()){
                        if (poRecord.OpenRecord(psOldRec)) loadRecord();
                        ShowMessageFX.Information(null, pxeModuleName, "Record Save Successfully.");
                    }else {
                        if (!poRecord.getErrMsg().equals(""))
                            ShowMessageFX.Error(poRecord.getErrMsg(), pxeModuleName, "Please inform MIS Department.");
                        else
                            ShowMessageFX.Warning(poRecord.getMessage(), pxeModuleName, "Please verify your entry.");
                    return;
                    }    
                }
                break;
                
            case "btnSearch":
                switch (pnIndex){
                   case 6:
                        psCateg1 = poRecord.SearchMaster(pnIndex, "%", false);
                        txtField06.setText(psCateg1);
                     break;
                     
                case 7:
                        psCateg2 = poRecord.SearchMaster(pnIndex, "%", false);
                        txtField07.setText(psCateg2);
                     break;
                     
                case 8: 
                        psCateg3 = poRecord.SearchMaster(pnIndex, "%", false);
                        txtField08.setText(psCateg3);
                     break;
                     
                case 9: 
                        psCateg4= poRecord.SearchMaster(pnIndex, "%", false);
                        txtField09.setText(psCateg4);
                     break;
                     
                case 10:
                        psBrand = poRecord.SearchMaster(pnIndex, "%", false);
                        txtField10.setText(psBrand);
                     break;
                     
                case 11:
                        psModel = poRecord.SearchMaster(pnIndex, "%", false);
                        txtField11.setText(psModel);
                     break;
                     
                case 12:
                        psColor = poRecord.SearchMaster(pnIndex, "%", false);
                        txtField12.setText(psColor);
                     break;
                     
                case 13: 
                        psInvType = poRecord.SearchMaster(pnIndex, "%", false);
                        txtField13.setText(psInvType);
                     break; 
                     
                case 50:
                     poRecord.SearchSubUnit(pnRow, "sItmSubID", "%", false, false);
                     loadDetail2Grid();
                    break;
                    
                case 51:
                    poRecord.SearchSubUnit(pnRow, "sItmSubID", "%", true, false);
                    loadDetail2Grid();
                    break;
                }
                return;
                
            case "btnUpdate":
                if (poRecord.getMaster(1) != null && !txtField01.getText().equals("")){
                    txtField02.setEditable(false);
                    txtField05.setEditable(false);
                    txtField80.setText("");
                    txtField81.setText("");
                    if (poRecord.UpdateRecord()){
                        pnEditMode = poRecord.getEditMode();
                    }
                }else ShowMessageFX.Error(null, pxeModuleName, "Please select record to update");
                break;
                
            case "btnActivate":
                if (poRecord.getMaster(1) != null && !txtField01.getText().equals("")){
                    if (poRecord.getMaster(1) != null && !poRecord.getMaster(1).toString().equals("")){
                    if (btnActivate.getText().equals("Activate")){
                        if (poRecord.ActivateRecord(poRecord.getMaster(1).toString())){
                            if (poRecord.OpenRecord(psOldRec)) loadRecord();
                            ShowMessageFX.Information(null, pxeModuleName, "Record Activated Successfully.");
                        }
                    } else{
                        if (poRecord.DeactivateRecord(poRecord.getMaster(1).toString())){
                            if (poRecord.OpenRecord(psOldRec)) loadRecord();
                            ShowMessageFX.Information(null, pxeModuleName, "Record Deactivated Successfully.");
                        }      
                    }
                }
                }else 
                   ShowMessageFX.Error(null, pxeModuleName, "Please select record to Activate");
                break;
                
            case "btnDeleteItem":               
                if (txtField52.getText().trim().equals("") || 
                    txtField52.getText().trim().equals("0")){
                    
                    int lnIndex = tblInvetoryDetail.getSelectionModel().getFocusedIndex();    
                    if(tblInvetoryDetail.getSelectionModel().getSelectedItem() == null){
                         ShowMessageFX.Warning(null, pxeModuleName, "Please select item to remove!");
                         break;
                    }
                    if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove this item?") == true){
                        poRecord.delSubUnit(lnIndex);
                        loadDetail2Grid();
                    }
                } else {
                    ShowMessageFX.Information("You can't delete record with assigned quantity.\n" +
                                                "If this is an unsaved record, please CANCEL the update and RELOAD the item to add sub items.\n\n" +
                                                "If this is an old record, please contact your SYSTEM ADMINISTRATOR to update the sub items info.", pxeModuleName, "Unable to delete record.");
                }     
                break;
            case "btnAuto":
                txtField02.setText(CommonUtils.getNextBarcode(poGRider.getConnection(), "Inventory", "sBarCodex", true));
                txtField02.requestFocus();
                break;
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
                return;
        }
        
        initButton(pnEditMode);
    }
    
    public void initGrid() {
        index01.setStyle("-fx-alignment: CENTER;");
        index02.setStyle("-fx-alignment: CENTER-LEFT;");
        index03.setStyle("-fx-alignment: CENTER-LEFT;");
        index04.setStyle("-fx-alignment: CENTER;");
        
        index01.setCellValueFactory(new PropertyValueFactory<TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<TableModel,String>("index04"));
        
         /*making column's position uninterchangebale*/
        tblInvetoryDetail.widthProperty().addListener(new ChangeListener<Number>() {  
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                TableHeaderRow header = (TableHeaderRow) tblInvetoryDetail.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                            }
                        });
                    }
                });
        /*Assigning data to table*/
        tblInvetoryDetail.setItems(data);
        
    }
    
    public void loadDetail2Grid() {
        int lnCtr;
        
        poRecord.addSubUnit();
        int lnRow = poRecord.SubUnitCount();
        
        data.clear();
        /*ADD THE DETAIL*/
        for(lnCtr = 0; lnCtr <= lnRow -1; lnCtr++){
            data.add(new TableModel(String.valueOf(lnCtr + 1),
                                    poRecord.SearchSubUnit(lnCtr, "sItmSubID", String.valueOf(poRecord.getSubUnit(lnCtr, "sItmSubID")), false, true),
                                    poRecord.SearchSubUnit(lnCtr, "sItmSubID", String.valueOf(poRecord.getSubUnit(lnCtr, "sItmSubID")), true, true), 
                                    String.valueOf(poRecord.getSubUnit(lnCtr, "nQuantity")),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""));
        }
        
        pnRow = lnRow - 1;
        txtField50.setText(poRecord.SearchSubUnit(pnRow, "sItmSubID", String.valueOf(poRecord.getSubUnit(pnRow, "sItmSubID")), false, true));
        txtField51.setText(poRecord.SearchSubUnit(pnRow, "sItmSubID", String.valueOf(poRecord.getSubUnit(pnRow, "sItmSubID")), true, true));
        txtField52.setText(poRecord.getSubUnit(pnRow, "nQuantity").toString());
        txtField50.requestFocus();
    }
    
    private void unloadForm(){
        VBox myBox = (VBox) VBoxForm.getParent();
        myBox.getChildren().clear();
    }
    
    private void initButton(int fnValue){
        boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
        
        btnCancel.setVisible(lbShow);
        btnSearch.setVisible(lbShow);
        btnSave.setVisible(lbShow);
        btnDeleteItem.setVisible(lbShow);
        lblHeader.setVisible(lbShow);
        
        txtField80.setDisable(lbShow);
        txtField81.setDisable(lbShow);
                
        btnClose.setVisible(!lbShow);
        btnBrowse.setVisible(!lbShow);
        btnActivate.setVisible(!lbShow);
        btnUpdate.setVisible(!lbShow);
        btnNew.setVisible(!lbShow);
        
        txtField01.setDisable(!lbShow);
        txtField02.setDisable(!lbShow);
        txtField03.setDisable(!lbShow);
        txtField04.setDisable(!lbShow);
        txtField05.setDisable(!lbShow);
        txtField06.setDisable(!lbShow);
        txtField07.setDisable(!lbShow);
        txtField08.setDisable(!lbShow);
        txtField09.setDisable(!lbShow);
        txtField10.setDisable(!lbShow);
        txtField11.setDisable(!lbShow);
        txtField12.setDisable(!lbShow);
        txtField13.setDisable(!lbShow);
        txtField14.setDisable(!lbShow);
        txtField15.setDisable(!lbShow);
        txtField16.setDisable(!lbShow); //!lbShow
        txtField17.setDisable(!lbShow); //!lbShow
        txtField18.setDisable(!lbShow); //!lbShow
        txtField19.setDisable(!lbShow); //!lbShow
        txtField25.setDisable(!lbShow);
        txtField29.setDisable(!lbShow);
        txtField50.setDisable(!lbShow); //!lbShow
        txtField51.setDisable(!lbShow); //!lbShow
        txtField52.setDisable(!lbShow); //!lbShow
        
        btnAuto.setDisable(!lbShow);
        Combo23.setDisable(!lbShow); //!lbShow
        Combo24.setDisable(!lbShow);
        Check20.setDisable(!lbShow);
        Check21.setDisable(!lbShow);
        Check22.setDisable(!lbShow);
        Check26.setDisable(true);
        
        txtField02.setEditable(txtField02.getText().trim().equals(""));
        txtField03.setEditable(txtField03.getText().trim().equals(""));
        txtField05.setEditable(txtField05.getText().trim().equals(""));
        txtField25.setEditable(txtField25.getText().trim().equals(""));
        
        //temporarily disable categ 2 and 3
        txtField07.setDisable(!lbShow); //true
        txtField08.setDisable(!lbShow); //true
        
        if (lbShow)
            txtField02.requestFocus();
        else
            txtField81.requestFocus();
            
    }
    
    private void clearFields(){ 
        txtField02.setEditable(true);
        txtField05.setEditable(true);
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("");
        txtField05.setText("");
        txtField06.setText("");
        txtField07.setText("");
        txtField08.setText("");
        txtField09.setText("");
        txtField10.setText("");
        txtField11.setText("");
        txtField12.setText("");
        txtField13.setText("");
        txtField14.setText("0.0");
        txtField15.setText("0.0");
        txtField16.setText("0.0");
        txtField17.setText("0.0");
        txtField18.setText("0.0");
        txtField19.setText("0.0");
        txtField29.setText("");
        txtField80.setText("");
        txtField81.setText("");
        
        Combo23.getSelectionModel().select(0);
        Combo24.getSelectionModel().select(0);
        
        Check20.selectedProperty().setValue(false);
        Check21.selectedProperty().setValue(false);
        Check22.selectedProperty().setValue(false);
        Check26.selectedProperty().setValue(false);
        btnActivate.setText("Activate");
        txtField50.setText("");
        txtField51.setText("");
        txtField52.setText("0");
        data.clear();
        
        psOldRec = "";
        psBarcode = "";
        psDescript = "";
        psCateg1= "";
        psCateg2 = "";
        psCateg3 = "";
        psCateg4 = "";
        psBrand = "";
        psModel = "";
        psColor = "";
        psInvType = "";
        psMeasurID = "";
        
        pnIndex = 81;
    }
    
    private void loadRecord(){
        txtField01.setText((String) poRecord.getMaster(1));
        txtField02.setText((String) poRecord.getMaster(2));
        txtField80.setText(txtField02.getText());
        psBarcode = txtField80.getText();
        txtField03.setText((String) poRecord.getMaster(3));
        txtField81.setText(txtField03.getText());
        psDescript = txtField81.getText();
        txtField04.setText((String) poRecord.getMaster(4));
        txtField05.setText((String) poRecord.getMaster(5));
        
        txtField06.setText(poRecord.SearchMaster(6, (String) poRecord.getMaster(6), true));
        psCateg1 = txtField06.getText();
        txtField07.setText(poRecord.SearchMaster(7, (String) poRecord.getMaster(7), true));
        psCateg2 = txtField07.getText();
        txtField08.setText(poRecord.SearchMaster(8, (String) poRecord.getMaster(8), true));
        psCateg3 = txtField08.getText();
        txtField09.setText(poRecord.SearchMaster(9, (String) poRecord.getMaster(9), true));
        psCateg4 = txtField09.getText();
        txtField10.setText(poRecord.SearchMaster(10, (String) poRecord.getMaster(10), true));
        psBrand = txtField10.getText();
        System.out.println((String) poRecord.getMaster(11));
        txtField11.setText(poRecord.SearchMaster(11, (String) poRecord.getMaster(11), true));
        psModel = txtField11.getText();
        txtField12.setText(poRecord.SearchMaster(12, (String) poRecord.getMaster(12), true));
        psColor = txtField12.getText();
        txtField13.setText(poRecord.SearchMaster(13, (String) poRecord.getMaster(13), true));
        psInvType = txtField13.getText();
        txtField29.setText(poRecord.SearchMaster(29, (String) poRecord.getMaster(29), true));
        psMeasurID = txtField29.getText();
        
        txtField14.setText(String.valueOf(poRecord.getMaster(14)));
        txtField15.setText(String.valueOf(poRecord.getMaster(15)));
        txtField16.setText(String.valueOf(poRecord.getMaster(16)));
        txtField17.setText(String.valueOf(poRecord.getMaster(17)));
        txtField18.setText(String.valueOf(poRecord.getMaster(18)));
        txtField19.setText(String.valueOf(poRecord.getMaster(19)));
        
        Combo23.getSelectionModel().select(Integer.parseInt(poRecord.getMaster("cUnitType").toString()));
        Combo24.getSelectionModel().select(Integer.parseInt(poRecord.getMaster("cInvStatx").toString()));
                
        boolean lbCheck;

        lbCheck = poRecord.getMaster("cComboInv").toString().equals("1");
        Check20.selectedProperty().setValue(lbCheck);

        lbCheck = poRecord.getMaster("cWthPromo").toString().equals("1");
        Check21.selectedProperty().setValue(lbCheck);

        lbCheck = poRecord.getMaster("cSerialze").toString().equals("1");
        Check22.selectedProperty().setValue(lbCheck);

        lbCheck = poRecord.getMaster("cRecdStat").toString().equals("1");
        Check26.selectedProperty().setValue(lbCheck);
        
        btnAuto.setVisible(txtField02.getText().trim().equals(""));
        
        if (poRecord.getMaster("cRecdStat").toString().equals("1"))
            btnActivate.setText("Deactivate");
        else
            btnActivate.setText("Activate");
        
        loadDetail2Grid();
        
        psOldRec = txtField01.getText();
        pnEditMode = EditMode.READY;
    }
        
    private boolean sendOtherDetail(){
        if (Combo23.getSelectionModel().getSelectedIndex() < 0){
            ShowMessageFX.Warning("No `Unit Type` selected.", pxeModuleName, "Please select `Unit Type` value.");
            Combo23.requestFocus();
            return false;
        }else 
            poRecord.setMaster(23, String.valueOf(Combo23.getSelectionModel().getSelectedIndex()));
        
        if (Combo24.getSelectionModel().getSelectedIndex() < 0){
            ShowMessageFX.Warning("No `Inv. Status` selected.", pxeModuleName, "Please select `Inv. Status` value.");
            Combo24.requestFocus();
            return false;
        }else 
            poRecord.setMaster(24, String.valueOf(Combo24.getSelectionModel().getSelectedIndex()));
        
        poRecord.setMaster(20, Check20.isSelected() == true ? "1" : "0");
        poRecord.setMaster(21, Check21.isSelected() == true ? "1" : "0");
        poRecord.setMaster(22, Check22.isSelected() == true ? "1" : "0");
               
        return true;
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    private final String pxeModuleName = "org.rmj.foodinventoryfx.views.InventoryController";
    private static GRider poGRider;
    private Inventory poRecord;
    
    private String psCateg1= "";
    private String psCateg2= "";
    private String psCateg3= "";
    private String psCateg4 = "";
    private String psBrand = "";
    private String psModel = "";
    private String psColor = "";
    private String psInvType = "";
    private String psMeasurID = "";
    
    private int pnIndex = -1;
    private int pnEditMode;
    private int pnRow = 0;
    private int pnSubItems = 0;
    private boolean pbLoaded = false;
    private String psOldRec;
    private String psBarcode = "";
    private String psDescript = "";
    
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    ObservableList<String> cUnitType = FXCollections.observableArrayList("Demo", "Regular", "Repo");
    ObservableList<String> cInvStatx = FXCollections.observableArrayList("Inactive", "Active", "Limited Inv.",
                                                                            "Push Product", "Stop Production");
   
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){
                case 2: /*sBarCodex*/
                    if (lsValue.length() > 12){
                        ShowMessageFX.Warning(null, pxeModuleName, "Max length for 'Barcode' exceeds the maximum limit.");
                        txtField.requestFocus();
                        return;
                    }
                    break;
                case 3: /*sDescript*/
                    if (lsValue.length() > 64) lsValue = lsValue.substring(0, 64);
                    break;
                case 4: /*sBriefDsc*/
                    if (lsValue.length() > 20){
                        ShowMessageFX.Warning(null, pxeModuleName, "Max length for 'Brief Desc.' exceeds the maximum limit.");
                        txtField.requestFocus();
                        return;
                    }
                    break;
                case 5: /*sAltBarCd*/
                    if (lsValue.length() > 12){
                        ShowMessageFX.Warning(null, pxeModuleName, "Max length for 'Alternate BCode' exceeds the maximum limit.");
                        txtField.requestFocus();
                        return;
                    }
                    break;
                case 6:
                    if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                     txtField.setText(psCateg1); return;
                case 7:
                    if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psCateg2); return;
                case 8:
                     if(txtField.getText().equals("") || txtField.getText().equals("%") ){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psCateg3); return;
                case 9:
                     if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psCateg4); return;
                case 10:
                     if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psBrand); return;
                case 11:
                     if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psModel); return;
                case 12:
                     if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psColor); return;
                case 13: 
                     if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psInvType); return;
                case 14: /*nUnitPrce*/
                case 15: /*nSelPrice*/
                case 16: /*nDiscLev1*/   
                case 17: /*nDiscLev2*/
                case 18: /*nDiscLev3*/
                case 19: /*nDealrDsc*/
                    double lnValue = 0;
                    try {
                        /*this must be numeric*/
                        lnValue = Double.parseDouble(lsValue);
                    } catch (Exception e) {
                        ShowMessageFX.Warning("Please input numbers only.", pxeModuleName, e.getMessage());
                        txtField.requestFocus();
                    }
                    
                    poRecord.setMaster(lnIndex, lnValue);
                    txtField.setText(String.valueOf(poRecord.getMaster(lnIndex)));
                    return;
                case 25: /**/
                    break;
                case 29: 
                     if(txtField.getText().equals("") || txtField.getText().equals("%")){
                       txtField.setText("");
                       poRecord.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psMeasurID); return;    
                case 50: /**/
                case 51: /**/
                     return;
                case 52: 
                    int lnQuantity;
                    try{
                        lnQuantity=Integer.parseInt(txtField.getText());
                    }catch (Exception e){
                        lnQuantity = 0;
                        txtField.setText("0");
                    }
                    poRecord.setSubUnit(pnRow, "nQuantity", lnQuantity);
                    txtField.setText(String.valueOf(poRecord.getSubUnit(pnRow, "nQuantity")));
                    break;
                    
                case 80:
                   if(txtField.getText().equals("") || txtField.getText().equals("%"))
                       txtField.setText("");
                       return;
                    
                case 81: 
                     if(txtField.getText().equals("") || txtField.getText().equals("%"))
                       txtField.setText("");
                       return;
                    
                default:
                    ShowMessageFX.Warning("Please inform MIS Dept.", pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
            }
            
            poRecord.setMaster(lnIndex, lsValue);
            txtField.setText((String)poRecord.getMaster(lnIndex)); 
        } else
            pnIndex = lnIndex;
            txtField.selectAll();    
    };

    @FXML
    private void tblInvetoryDetail_Click(MouseEvent event) {
        pnRow = tblInvetoryDetail.getSelectionModel().getSelectedIndex();        
        boolean lbShow = (pnEditMode == EditMode.ADDNEW || pnEditMode == EditMode.UPDATE);
        
        try{
            if (poRecord.getSubUnit(pnRow, "sItmSubID") != null){  
                txtField50.setText((String) poRecord.SearchSubUnit(pnRow, "sItmSubID", String.valueOf(poRecord.getSubUnit(pnRow, "sItmSubID")), false, true));
                txtField51.setText((String) poRecord.SearchSubUnit(pnRow, "sItmSubID", String.valueOf(poRecord.getSubUnit(pnRow, "sItmSubID")), true, true));
                txtField52.setText(String.valueOf(poRecord.getSubUnit(pnRow, "nQuantity")));
                
                if (lbShow){
                    txtField50.setDisable(!txtField52.getText().equals("0"));
                    txtField51.setDisable(!txtField52.getText().equals("0"));
                    txtField52.setDisable(!txtField52.getText().equals("0"));
                    if (txtField52.getText().equals("0")) txtField50.requestFocus();
                }
            } else{
                txtField50.setText("");
                txtField51.setText("");
                txtField52.setText("0");
                
                if (lbShow){
                    txtField50.setDisable(false);
                    txtField51.setDisable(false);
                    txtField52.setDisable(false);
                    txtField50.requestFocus();
                }
            }     
        }catch(ArrayIndexOutOfBoundsException ex){
            System.err.println(ex);
        
        }
        
    }
};