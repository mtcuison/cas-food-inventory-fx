package org.rmj.cas.food.inventory.fx.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.cas.inventory.base.Inventory;
import org.rmj.cas.parameter.agent.XMBranch;
import org.rmj.cas.parameter.agent.XMDepartment;
import org.rmj.cas.parameter.agent.XMInventoryType;
import org.rmj.purchasing.agent.XMPOReceiving;
import org.rmj.purchasing.agent.XMPOReturn;
import org.rmj.appdriver.agentfx.callback.IMasterDetail;

public class POReturnController implements Initializable {
    @FXML private VBox VBoxForm;
    @FXML private Button btnExit;
    @FXML private AnchorPane anchorField;
    @FXML private TextField txtField01;
    @FXML private TextField txtField03;
    @FXML private TextField txtField02;
    @FXML private TextField txtField18;
    @FXML private TextField txtField05;
    @FXML private TextField txtField16;
    @FXML private TextArea txtField12;
    @FXML private TextField txtDetail80;
    @FXML private TextField txtDetail07;
    @FXML private TableView table;
    @FXML private TextField txtField07;
    @FXML private TextField txtField08;
    @FXML private TextField txtField11;
    @FXML private TextField txtField09;
    @FXML private TextField txtField10;
    @FXML private TextField txtField13;
    @FXML private Button btnNew;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnClose;
    @FXML private Button btnSearch;
    @FXML private Button btnConfirm;
    @FXML private Button btnDel;
    @FXML private TextField txtDetail03;
    @FXML private ComboBox Combo04;
    @FXML private TextField txtDetail06;
    @FXML private TextField txtDetail05;
    @FXML private Label Label06;
    @FXML private FontAwesomeIconView glyphExit;
    @FXML private Button btnBrowse;
    @FXML private TextField txtField27;
    @FXML private ComboBox Combo28;
    @FXML private ImageView imgTranStat;
    @FXML private ImageView imgTranStat1;
    @FXML private TextField txtField50;
    @FXML private TextField txtField51;
    @FXML private TextField txtDetail08;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Initialize class*/
        poTrans = new XMPOReturn(poGRider, poGRider.getBranchCode(), false);
        poTrans.setCallBack(poCallBack);
        
        txtField50.focusedProperty().addListener(txtField_Focus);
        txtField51.focusedProperty().addListener(txtField_Focus);
        
        txtField50.setOnKeyPressed(this::txtField_KeyPressed);
        txtField51.setOnKeyPressed(this::txtField_KeyPressed);
                
        /*Set action event handler for the buttons*/
        btnCancel.setOnAction(this::cmdButton_Click);
        btnSearch.setOnAction(this::cmdButton_Click);
        btnSave.setOnAction(this::cmdButton_Click);
        btnDel.setOnAction(this::cmdButton_Click);
        btnNew.setOnAction(this::cmdButton_Click);
        btnConfirm.setOnAction(this::cmdButton_Click);
        btnClose.setOnAction(this::cmdButton_Click);
        btnExit.setOnAction(this::cmdButton_Click);
        btnBrowse.setOnAction(this::cmdButton_Click);
        
        /*Add listener to text fields*/
        txtField02.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtField_Focus);
        txtField07.focusedProperty().addListener(txtField_Focus);
        txtField08.focusedProperty().addListener(txtField_Focus);
        txtField09.focusedProperty().addListener(txtField_Focus);
        txtField10.focusedProperty().addListener(txtField_Focus);
        txtField11.focusedProperty().addListener(txtField_Focus);
        txtField13.focusedProperty().addListener(txtField_Focus);
        txtField16.focusedProperty().addListener(txtField_Focus);
        txtField18.focusedProperty().addListener(txtField_Focus);
        txtField27.focusedProperty().addListener(txtField_Focus);
        txtField12.focusedProperty().addListener(txtArea_Focus);
        
        txtDetail03.focusedProperty().addListener(txtDetail_Focus);
        txtDetail05.focusedProperty().addListener(txtDetail_Focus);
        txtDetail06.focusedProperty().addListener(txtDetail_Focus);
        txtDetail07.focusedProperty().addListener(txtDetail_Focus);
        txtDetail08.focusedProperty().addListener(txtDetail_Focus);
        txtDetail80.focusedProperty().addListener(txtDetail_Focus);
                
        /*Add keypress event for field with search*/
        txtField02.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField05.setOnKeyPressed(this::txtField_KeyPressed);
        txtField07.setOnKeyPressed(this::txtField_KeyPressed);
        txtField08.setOnKeyPressed(this::txtField_KeyPressed);
        txtField09.setOnKeyPressed(this::txtField_KeyPressed);
        txtField10.setOnKeyPressed(this::txtField_KeyPressed);
        txtField11.setOnKeyPressed(this::txtField_KeyPressed);
        txtField13.setOnKeyPressed(this::txtField_KeyPressed);
        txtField16.setOnKeyPressed(this::txtField_KeyPressed);
        txtField18.setOnKeyPressed(this::txtField_KeyPressed);
        txtField27.setOnKeyPressed(this::txtField_KeyPressed);
        txtField12.setOnKeyPressed(this::txtFieldArea_KeyPressed);
        
        txtDetail03.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail05.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail06.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail07.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail08.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail80.setOnKeyPressed(this::txtDetail_KeyPressed);
        
        Combo04.setOnKeyPressed(this::ComboBox_KeyPressed);
        Combo04.focusedProperty().addListener(Combo_Focus);
        Combo04.setItems(cUnitType);
        Combo04.getSelectionModel().select(1);
        
        Combo28.setOnKeyPressed(this::ComboBox_KeyPressed);
        Combo28.focusedProperty().addListener(Combo_Focus);
        Combo28.setItems(cDivision);
        Combo28.getSelectionModel().select(cDivision.size() - 1);
        
        pnEditMode = EditMode.UNKNOWN;
        
        clearFields();
        initGrid();
        initButton(pnEditMode);
        
        pbLoaded = true;
    }    

    @FXML
    private void table_Clicked(MouseEvent event) {
        pnRow = table.getSelectionModel().getSelectedIndex();
        
        setDetailInfo(); 
        txtDetail03.requestFocus();
        txtDetail03.selectAll();
    }
    
    private void setDetailInfo(){
        String lsStockIDx = (String) poTrans.getDetail(pnRow, "sStockIDx");
        //&& !lsStockIDx.equals("")
        if (pnRow >= 0 && !lsStockIDx.equals("")){
            Inventory loInventory = poTrans.GetInventory(lsStockIDx, true, false);
            psBarCodex = (String) loInventory.getMaster("sBarCodex");
            psDescript = (String) loInventory.getMaster("sDescript");
            txtDetail03.setText(psBarCodex);
            txtDetail80.setText(psDescript);
            
            txtDetail05.setText(String.valueOf(poTrans.getDetail(pnRow, 5))); /*Quantity*/
            txtDetail06.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, 6).toString()), "###0.00")); /*Unit Price*/
            txtDetail07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, 7).toString()), "###0.00")); /*Freight*/
            txtDetail08.setText(CommonUtils.xsDateMedium((Date) poTrans.getDetail(pnRow, "dExpiryDt"))); //date
            
            Combo04.getSelectionModel().select(Integer.parseInt((String) poTrans.getDetail(pnRow, 4)));
        } else{
            txtDetail03.setText("");
            txtDetail05.setText("0");
            txtDetail06.setText("0.00");
            txtDetail07.setText("0.00");
            txtDetail08.setText("");
            txtDetail80.setText("");   
        }
    }
    
    private void initButton(int fnValue){
        boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
        
        btnCancel.setVisible(lbShow);
        btnSearch.setVisible(lbShow);
        btnSave.setVisible(lbShow);
        btnDel.setVisible(lbShow);
        
        txtField50.setDisable(lbShow);
        txtField51.setDisable(lbShow);
                
        btnBrowse.setVisible(!lbShow);
        btnNew.setVisible(!lbShow);
        btnConfirm.setVisible(!lbShow);
        btnClose.setVisible(!lbShow);
        
        txtField01.setDisable(!lbShow);
        txtField02.setDisable(!lbShow);
        txtField03.setDisable(!lbShow);
        txtField05.setDisable(!lbShow);
        txtField07.setDisable(!lbShow);
        txtField08.setDisable(!lbShow);
        txtField09.setDisable(!lbShow);
        txtField10.setDisable(!lbShow);
        txtField11.setDisable(!lbShow);
        txtField13.setDisable(!lbShow);
        txtField16.setDisable(!lbShow);
        txtField18.setDisable(!lbShow);
        txtField27.setDisable(!lbShow);
        txtField12.setDisable(!lbShow);
        txtDetail03.setDisable(!lbShow);
        txtDetail05.setDisable(!lbShow);
        txtDetail06.setDisable(!lbShow);
        txtDetail07.setDisable(!lbShow);
        txtDetail80.setDisable(!lbShow);
        Combo04.setDisable(!lbShow);
        Combo28.setDisable(!lbShow);
        
        if (lbShow)
            txtField02.requestFocus();
        else
            txtField50.requestFocus();
    }
    
    private void setTranStat(String fsValue){
        switch (fsValue){
            case "0":
                imgTranStat.setImage(new Image("org/rmj/cas/food/inventory/fx/images/open.png")); break;
            case "1":
                imgTranStat.setImage(new Image("org/rmj/cas/food/inventory/fx/images/closed.png")); break;
            case "2":
                imgTranStat.setImage(new Image("org/rmj/cas/food/inventory/fx/images/posted.png")); break;
            case "3":
                imgTranStat.setImage(new Image("org/rmj/cas/food/inventory/fx/images/cancelled.png")); break;
            case "4":
                imgTranStat.setImage(new Image("org/rmj/cas/food/inventory/fx/images/void.png")); break;
            default:
                imgTranStat.setImage(new Image("org/rmj/cas/food/inventory/fx/images/unknown.png"));
        }    
    }
    
    private void initGrid(){
        TableColumn index01 = new TableColumn("No.");
        TableColumn index02 = new TableColumn("Bar Code");
        TableColumn index03 = new TableColumn("Description");
        TableColumn index04 = new TableColumn("Unit Type");
        TableColumn index05 = new TableColumn("Qty");
        TableColumn index06 = new TableColumn("Unit Price");
        TableColumn index07 = new TableColumn("Freight");
        TableColumn index08 = new TableColumn("Total");
        
        index01.setPrefWidth(31);
        index02.setPrefWidth(110);
        index03.setPrefWidth(260);
        index04.setPrefWidth(100);
        index05.setPrefWidth(41); index05.setStyle("-fx-alignment: CENTER-RIGHT;");
        index06.setPrefWidth(100); index06.setStyle("-fx-alignment: CENTER-RIGHT;");
        index07.setPrefWidth(100); index07.setStyle("-fx-alignment: CENTER-RIGHT;");
        index08.setPrefWidth(100); index08.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        index01.setSortable(false); index01.setResizable(false);
        index02.setSortable(false); index02.setResizable(false);
        index03.setSortable(false); index03.setResizable(false);
        index04.setSortable(false); index04.setResizable(false);
        index05.setSortable(false); index05.setResizable(false);
        index06.setSortable(false); index06.setResizable(false);
        index07.setSortable(false); index07.setResizable(false);
        index08.setSortable(false); index08.setResizable(false);

        table.getColumns().clear();        
        table.getColumns().add(index01);
        table.getColumns().add(index02);
        table.getColumns().add(index03);
        table.getColumns().add(index04);
        table.getColumns().add(index05);
        table.getColumns().add(index06);
        table.getColumns().add(index07);
        table.getColumns().add(index08);
        
        index01.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index04"));
        index05.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index05"));
        index06.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index06"));
        index07.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index07"));
        index08.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index08"));

        /*Set data source to table*/
        table.setItems(data);
    }
    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId();
        
        switch (lsButton){
            case "btnBrowse":
                if(poTrans.BrowseRecord(txtField50.getText(), true)==true){
                    loadRecord(); 
                    pnEditMode = poTrans.getEditMode();
                } else {
                    clearFields();
                    pnEditMode = EditMode.UNKNOWN;
                }                        
                return;
            case "btnNew":
                if (poTrans.newRecord()){
                    clearFields();
                    loadRecord();
                    pnEditMode = poTrans.getEditMode();
                }
                break;
            case "btnConfirm":
                if (!psOldRec.equals("")){
                    if (poTrans.postRecord((String) poTrans.getMaster("sTransNox"))){
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction posted successfully.");
                        if (poTrans.openRecord(psOldRec)) loadRecord(); pnEditMode = poTrans.getEditMode();
                    }
                }
                return;
            case "btnClose":
            case "btnExit": 
                unloadForm();
                return;
            case "btnCancel": 
                clearFields();
                pnEditMode = EditMode.UNKNOWN;
                break;
            case "btnSearch": getMaster(pnIndex); return;
            case "btnSave": 
                if (poTrans.saveRecord()){
                    ShowMessageFX.Information(null, pxeModuleName, "Transaction saved successfuly.");
                    
                    //re open and print the record
                    if (poTrans.openRecord((String) poTrans.getMaster("sTransNox"))){
                        loadRecord(); 
                        psOldRec = (String) poTrans.getMaster("sTransNox");
                        
                        if (poTrans.printRecord()) poTrans.closeRecord(psOldRec);
                        
                        pnEditMode = poTrans.getEditMode();
                    } else {
                        clearFields();
                        initGrid();
                        pnEditMode = EditMode.UNKNOWN;
                    }
                    
                    initButton(pnEditMode);
                    break;
                } else return;
            case "btnDel":  
                deleteDetail();
                return;
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
                return;
        }
        
        initButton(pnEditMode);
    }
    
    private void loadRecord(){
        txtField01.setText((String) poTrans.getMaster(1));
        txtField03.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster(3)));
        
        XMPOReceiving loPORec = poTrans.GetPOReceving((String)poTrans.getMaster("sPOTransx"), true);
        if (loPORec != null) {
            txtField50.setText((String) loPORec.getMaster("sReferNox"));
            txtField16.setText((String) loPORec.getMaster("sReferNox"));
        }
        
        XMBranch loBranch = poTrans.GetBranch((String)poTrans.getMaster(2), true);
        if (loBranch != null) txtField02.setText((String) loBranch.getMaster("sBranchNm"));
        
        JSONObject loSupplier = poTrans.GetSupplier((String)poTrans.getMaster(5), true);
        if (loSupplier != null) {
            txtField05.setText((String) loSupplier.get("sClientNm"));
            txtField51.setText((String) loSupplier.get("sClientNm"));
        }
        
        XMInventoryType loInv = poTrans.GetInventoryType((String)poTrans.getMaster(18), true);
        if (loInv != null) txtField18.setText((String) loInv.getMaster("sDescript"));
        
        XMDepartment loDept = poTrans.GetDepartment((String)poTrans.getMaster(27), true);
        if (loBranch != null) txtField27.setText((String) loBranch.getMaster("sDeptName"));

        txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(7).toString()), "0.00"));
        txtField09.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(9).toString()), "0.00"));
        txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(8).toString()), "#,##0.00"));
        txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(10).toString()), "#,##0.00"));
        txtField11.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(11).toString()), "#,##0.00"));
        txtField13.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(13).toString()), "#,##0.00"));
        
        if (!String.valueOf(poTrans.getMaster("cDivision")).equals("")){
            Combo28.getSelectionModel().select(Integer.parseInt((String) poTrans.getMaster("cDivision")));
        } else Combo28.getSelectionModel().select(cDivision.size() - 1);
        
        Label06.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(6).toString()) + Double.valueOf(poTrans.getMaster(8).toString()), "###0.00"));
        
        setTranStat((String) poTrans.getMaster("cTranStat"));
        
        pnRow = 0;
        pnOldRow = 0;
        loadDetail();
        
        psOldRec = txtField01.getText();
    }
    
    private void clearFields(){
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField05.setText("");        
        txtField12.setText("");
        txtField16.setText("");
        txtField18.setText("");
        txtField27.setText("");
        txtField50.setText("");
        txtField51.setText("");
        
        txtField07.setText("0.00");
        txtField08.setText("0.00");
        txtField09.setText("0.00");
        txtField10.setText("0.00");
        txtField11.setText("0.00");
        txtField13.setText("0.00");
        
        txtDetail03.setText("");
        txtDetail80.setText("");
        txtDetail05.setText("0");
        txtDetail06.setText("0.00");
        txtDetail07.setText("0.00");
        txtDetail08.setText(CommonUtils.xsDateLong((Date) java.sql.Date.valueOf(LocalDate.now())));
        
        
        Label06.setText("0.00");
        Combo04.getSelectionModel().select(1);
        Combo28.getSelectionModel().select(cDivision.size() - 1);
        
        pnRow = -1;
        pnOldRow = -1;
        pnIndex = -1;
        
        psOldRec = "";
        
        psBarCodex = "";
        psDescript = "";
        
        setTranStat("-1");
        data.clear();
    }
    
    private void unloadForm(){
        VBox myBox = (VBox) VBoxForm.getParent();
        myBox.getChildren().clear();
    }
    
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        
        switch (event.getCode()){
        case F3:
            switch (lnIndex){
                case 2: /*sBranchCd*/
                case 5: /*sSupplier*/
                case 18: /*sInvTypCd*/
                case 27: /*sDeptIDxx*/
                    if (poTrans.SearchMaster(lnIndex, txtField.getText(), false)==true)CommonUtils.SetNextFocus(txtField);
                    else txtField.setText("");
                    return;
                case 16: /*sPOTransx*/
                    if (poTrans.SearchMaster(lnIndex, txtField.getText(), false))
                        txtField12.requestFocus();
                    else txtField02.requestFocus();
                    return;
                case 50: /*Refer No*/
                    if(poTrans.BrowseRecord(txtField.getText(), true) == true){
                        loadRecord(); 
                        pnEditMode = poTrans.getEditMode();
                    } else {
                        clearFields();
                        pnEditMode = EditMode.UNKNOWN;
                    }
                    return;
                case 51: /*sSupplier*/
                    if(poTrans.BrowseRecord(txtField.getText(), false) == true){
                        loadRecord(); 
                        pnEditMode = poTrans.getEditMode();
                    } else {
                        clearFields();
                        pnEditMode = EditMode.UNKNOWN;
                    }
                    return;
            }
            break;
        case ENTER:
        case DOWN:
            CommonUtils.SetNextFocus(txtField);
            break;
        case UP:
            CommonUtils.SetPreviousFocus(txtField);
        }
    }
    
    
    private void ComboBox_KeyPressed(KeyEvent event){
        if (event.getCode() == ENTER){ 
            event.consume();
            CommonUtils.SetNextFocus((ComboBox)event.getSource());
        }
    }
    
    private void txtFieldArea_KeyPressed(KeyEvent event){
        if (event.getCode() == ENTER){ 
            event.consume();
            CommonUtils.SetNextFocus((TextArea)event.getSource());
        }
    }
    
    private void txtDetail_KeyPressed(KeyEvent event){
        TextField txtDetail = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(txtDetail.getId().substring(9, 11));
        JSONObject loJSON;
        String lsValue = txtDetail.getText();
        
        switch (event.getCode()){
            case F3:
                switch (lnIndex){
                    case 3:
                        loJSON = poTrans.SearchDetail(pnRow, 3, lsValue, false, false);
                        if (loJSON != null){
                            psBarCodex = (String) loJSON.get("sBarCodex");
                            psDescript = (String) loJSON.get("sDescript");
                            txtDetail03.setText(psBarCodex);
                            txtDetail80.setText(psDescript);
                            loadDetail();
                        }
                        break;
                    case 80:
                        loJSON = poTrans.SearchDetail(pnRow, 3, lsValue, true, false);
                        if (loJSON != null){
                            psBarCodex = (String) loJSON.get("sBarCodex");
                            psDescript = (String) loJSON.get("sDescript");
                            txtDetail03.setText(psBarCodex);
                            txtDetail80.setText(psDescript);
                            loadDetail();
                        }
                        break;
                }
                break;
            case ENTER:
                CommonUtils.SetNextFocus(txtDetail);
        }
        
        
    }
    
    private void deleteDetail(){
        if (pnOldRow == -1) return;
        if (poTrans.deleteDetail(pnOldRow)){
            pnRow = poTrans.getDetailCount() - 1;
            pnOldRow = pnRow;
            
            loadDetail();
            setDetailInfo();
        }
    }
    
        
    private void loadDetail(){
        int lnCtr;
        int lnRow = poTrans.getDetailCount();
        
        data.clear();
        /*ADD THE DETAIL*/
        
        Inventory loInventory;
        for(lnCtr = 0; lnCtr <= lnRow -1; lnCtr++){
            if (!"".equals((String) poTrans.getDetail(lnCtr, "sStockIDx"))) {
                loInventory = poTrans.GetInventory((String) poTrans.getDetail(lnCtr, "sStockIDx"), true, false);
                psBarCodex = (String) loInventory.getMaster("sBarCodex");
                psDescript = (String) loInventory.getMaster("sDescript");
                
                data.add(new TableModel(String.valueOf(lnCtr + 1), 
                                    psBarCodex, 
                                    psDescript, 
                                    cUnitType.get(Integer.parseInt((String) poTrans.getDetail(lnCtr, "cUnitType"))),
                                    String.valueOf(poTrans.getDetail(lnCtr, "nQuantity")),
                                    CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nUnitPrce").toString()), "#,##0.00"),
                                    CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nFreightx").toString()), "#,##0.00"),
                                    CommonUtils.NumberFormat(((int)poTrans.getDetail(lnCtr, "nQuantity") 
                                                            * Double.valueOf(poTrans.getDetail(lnCtr, "nUnitPrce").toString()))
                                                            + Double.valueOf(poTrans.getDetail(lnCtr, "nFreightx").toString()), "#,##0.00"),
                                    "",
                                    ""));
            } else {
                data.add(new TableModel(String.valueOf(lnCtr + 1), 
                                    "", 
                                    "", 
                                    "",
                                    "0",
                                    "0.00",
                                    "0.00",
                                    "0.00",
                                    "",
                                    ""));
            }
            
        }
                
        /*FOCUS ON FIRST ROW*/
        if (!data.isEmpty()){
            table.getSelectionModel().select(pnRow);
            table.getFocusModel().focus(pnRow);
            
            pnRow = table.getSelectionModel().getSelectedIndex();           
            setDetailInfo();
        }
        
        Label06.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(6).toString()) + Double.valueOf(poTrans.getMaster(8).toString()), "#,##0.00"));
        txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(8).toString()), "#,##0.00"));
    }    
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    private final String pxeModuleName = "POReceivingController";
    private static GRider poGRider;
    private XMPOReturn poTrans;
    
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    
    private final String pxeDateFormat = "yyyy-MM-dd";
    private final String pxeDateDefault = "1900-01-01";
    
    private TableModel model;
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    ObservableList<String> cUnitType = FXCollections.observableArrayList("Demo", "Regular", "Repo");
    ObservableList<String> cDivision = FXCollections.observableArrayList("Motorcycle", "Mobile Phone", "Hotel", "General");
    
    private int pnIndex = -1;
    private int pnRow = -1;
    private int pnOldRow = -1;
    
    private String psOldRec = "";
    
    private String psBarCodex = "";
    private String psDescript = "";
    
    final ChangeListener<? super Boolean> Combo_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        ComboBox loField = (ComboBox)((ReadOnlyBooleanPropertyBase)o).getBean();
        
        if(!nv){ /*Lost Focus*/
                switch (loField.getId()){
                    case "Combo04":
                        poTrans.setDetail(pnRow, "cUnitType", String.valueOf(loField.getSelectionModel().getSelectedIndex()));
                        loadDetail();
                        break;
                    case "Combo28":
                        String sDivision = (String) loField.getValue();
//                        if (loField.getSelectionModel().getSelectedIndex() != cDivision.size() -1){
//                            poTrans.setMaster("cDivision", String.valueOf(loField.getSelectionModel().getSelectedIndex()));
//                        } else poTrans.setMaster("cDivision", "");
                        if(!cDivision.contains(sDivision)){
                        Combo28.getSelectionModel().select(0);
                    }
                    Combo28.getSelectionModel().getSelectedIndex();
                    poTrans.setMaster("cDivision", String.valueOf(loField.getSelectionModel().getSelectedIndex()));
                }
        }         
    };
    
    final ChangeListener<? super Boolean> txtArea_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextArea txtField = (TextArea)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
        
        if(!nv){ /*Lost Focus*/            
            switch (lnIndex){
                case 12: /*sRemarksx*/
                    if (lsValue.length() > 256) lsValue = lsValue.substring(0, 256);
                    
                    poTrans.setMaster(lnIndex, CommonUtils.TitleCase(lsValue));
                    txtField.setText((String)poTrans.getMaster(lnIndex));
            }
        }else{ 
            pnIndex = -1;
            txtField.selectAll();
        }
    };
    
    final ChangeListener<? super Boolean> txtDetail_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(9, 11));
        String lsValue = txtField.getText();
        
        if (pnRow < 0) return;
        if (lsValue == null) return;
        
        if(!nv){ /*Lost Focus*/     
            switch (lnIndex){
                case 3: /*Barcode*/
                    //txtField.setText(psBarCodex); 
                    break;
                case 80: /*Description*/
                    //txtField.setText(psDescript); 
                    break;
                case 5: /*Quantity*/
                    int lnValue = 0;
                    try {
                        /*this must be numeric*/
                        lnValue = Integer.parseInt(lsValue);
                    } catch (Exception e) {
                        ShowMessageFX.Warning("Please input numbers only.", pxeModuleName, e.getMessage());
                        txtField.requestFocus(); 
                    }
                    
                    poTrans.setDetail(pnRow, lnIndex, lnValue);
                    break;
                case 6: /*nUnitPrce*/
                case 7: /*nFreightx*/
                    double x = 0.00;
                    try {
                        /*this must be numeric*/
                        x = Double.parseDouble(lsValue);
                    } catch (Exception e) {
                        ShowMessageFX.Warning("Please input numbers only.", pxeModuleName, e.getMessage());
                        txtField.requestFocus(); 
                    } 
                    poTrans.setDetail(pnRow, lnIndex, x);
                    break;
                case 8: /*dExpiryDt*/
                        if (CommonUtils.isDate(txtField.getText(), pxeDateFormat)){
                            poTrans.setDetail(pnRow, "dExpiryDt", CommonUtils.toDate(txtField.getText()));
                        }else{
                            ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                            poTrans.setDetail(pnRow, "dExpiryDt",CommonUtils.toDate(pxeDateDefault));
                        }
                        return;
                }
                pnOldRow = table.getSelectionModel().getSelectedIndex();
                pnIndex= lnIndex;
        } else{
            switch (lnIndex){
                case 8: /*dExpiryDt*/
                    try{
                        txtField.setText(CommonUtils.xsDateShort(lsValue));
                    }catch(ParseException e){
                        ShowMessageFX.Error(e.getMessage(), pxeModuleName, null);
                    }
                    txtField.selectAll();
                    break;
                default:
            }
            pnIndex = -1;
            txtField.selectAll();
        }
    };
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/           
            switch (lnIndex){
                case 2: /*sBranchCd*/
                case 5: /*sSupplier*/
                case 16: /*sPOTransx*/
                case 27: /*sDeptIDxx*/    
                case 18: /*sInvTypCd*/
                    return;
                case 3: /*dTransact*/
                    if (CommonUtils.isDate(txtField.getText(), pxeDateFormat)){
                        poTrans.setMaster(lnIndex, CommonUtils.toDate(txtField.getText()));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setMaster(lnIndex, CommonUtils.toDate(pxeDateDefault));
                    }
                    return;
                case 7: /*nVATRatex*/
                case 9: /*nDiscount*/
                    poTrans.setMaster(lnIndex, Double.parseDouble(txtField.getText()));
                    break;
                case 8: /*nTWithHld*/
                case 10: /*nAddDiscx*/
                case 11: /*nFreightx*/
                case 13: /*nAmtPaidx*/
                    poTrans.setMaster(lnIndex, Double.parseDouble(txtField.getText()));
                    break;
                case 50:
                case 51:
                    return;
                default:
                    ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
            }
            pnIndex = -1;
        } else{
            switch (lnIndex){
                case 3: /*dTransact*/
                    try{
                        txtField.setText(CommonUtils.xsDateShort(lsValue));
                    }catch(ParseException e){
                        ShowMessageFX.Error(e.getMessage(), pxeModuleName, null);
                    }
                    txtField.selectAll();
                    break;
                default:
            }
            pnIndex = lnIndex;
            txtField.selectAll();
        }
    };    
    
    IMasterDetail poCallBack = new IMasterDetail() {
        @Override
        public void MasterRetreive(int fnIndex) {
            getMaster(fnIndex);
        }

        @Override
        public void DetailRetreive(int fnIndex) {
            switch(fnIndex){
                case 3:
                    break;
                case 5:
                    txtField05.setText(String.valueOf(poTrans.getDetail(pnRow, fnIndex)));
                    loadDetail();
                    
                    if (!poTrans.getDetail(poTrans.getDetailCount() - 1, "sStockIDx").toString().isEmpty() && 
                            (int) poTrans.getDetail(poTrans.getDetailCount() - 1, fnIndex) > 0){
                        poTrans.addDetail();
                        pnRow = poTrans.getDetailCount() - 1;
                    }                            
                    loadDetail();

                    if (txtDetail03.getText().isEmpty()){
                        txtDetail03.requestFocus();
                        txtDetail03.selectAll();
                    } else{
                        Combo04.requestFocus();
                    }
                    break;
                case 6:
                case 7:
                    txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, fnIndex).toString()), "0.00"));
                    loadDetail();
                case 8:
                    txtDetail08.setText(CommonUtils.xsDateLong((Date)poTrans.getDetail(pnRow,"dExpiryDt")));
            }
        }
    };
    
    private void getMaster(int fnIndex){
        switch(fnIndex){
        case 2:
            XMBranch loBranch = poTrans.GetBranch((String)poTrans.getMaster(fnIndex), true);
            if (loBranch != null) txtField02.setText((String) loBranch.getMaster("sBranchNm"));
            break;
        case 5:
            JSONObject loSupplier = poTrans.GetSupplier((String)poTrans.getMaster(fnIndex), true);
            if (loSupplier != null) txtField05.setText((String) loSupplier.get("sClientNm"));
            break;
        case 16:
            XMPOReceiving loPORec = poTrans.GetPOReceving((String)poTrans.getMaster(fnIndex), true);
            if (loPORec != null) txtField16.setText((String) loPORec.getMaster("sTransNox"));
            break;
        case 18:
            XMInventoryType loInv = poTrans.GetInventoryType((String)poTrans.getMaster(fnIndex), true);
            if (loInv != null) txtField18.setText((String) loInv.getMaster("sDescript"));
            break;
        case 27:
            XMDepartment loDept = poTrans.GetDepartment((String)poTrans.getMaster(fnIndex), true);
            if (loDept != null) txtField27.setText((String) loDept.getMaster("sDeptName"));
            break;
        case 28:
            Combo28.getSelectionModel().select(Integer.parseInt((String) poTrans.getMaster("cDivision")));
            break;
        case 3:
            txtField03.setText(CommonUtils.xsDateLong((Date)poTrans.getMaster(fnIndex)));
            break;
        case 6:
            Label06.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(6).toString()), "#,##0.00"));      
            break;
        case 7:
            txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(fnIndex).toString()), "0.00"));
            break;
        case 9:
            txtField09.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(fnIndex).toString()), "0.00"));
            break;
        case 8:
            txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(fnIndex).toString()), "#,##0.00"));
            break;
        case 10:
            txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(fnIndex).toString()), "#,##0.00"));
            break;
        case 11:
            txtField11.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(fnIndex).toString()), "#,##0.00"));
            break;
        case 13:
            txtField13.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(fnIndex).toString()), "#,##0.00"));
            break;
        }
    }
}
