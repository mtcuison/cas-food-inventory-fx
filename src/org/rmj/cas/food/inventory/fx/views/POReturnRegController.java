package org.rmj.cas.food.inventory.fx.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
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
import org.rmj.appdriver.constants.TransactionStatus;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.cas.inventory.base.Inventory;
import org.rmj.cas.parameter.agent.XMBranch;
import org.rmj.cas.parameter.agent.XMDepartment;
import org.rmj.cas.parameter.agent.XMInventoryType;
import org.rmj.purchasing.agent.XMPOReceiving;
import org.rmj.purchasing.agent.XMPOReturn;


public class POReturnRegController implements Initializable {

    @FXML private VBox VBoxForm;
    @FXML private Button btnExit;
    @FXML private FontAwesomeIconView glyphExit;
    @FXML private AnchorPane anchorField;
    @FXML private TextField txtField03;
    @FXML private TextField txtField02;
    @FXML private TextField txtField05;
    @FXML private TextField txtField07;
    @FXML private TextField txtField08;
    @FXML private TextField txtField16;
    @FXML private TextField txtDetail03;
    @FXML private TextField txtDetail80;
    @FXML private TableView table;
    @FXML private TextField txtField10;
    @FXML private ImageView imgTranStat;
    @FXML private TextField txtField01;
    @FXML private Button btnClose;
    @FXML private Button btnBrowse;
    @FXML private Button btnVoid;
    @FXML private Button btnPrint;
    @FXML private TextField txtField50;
    @FXML private TextField txtField51;
    @FXML private TextField txtField27;
    @FXML private ComboBox Combo28;
    @FXML private TextField txtField18;
    @FXML private TextArea txtField12;
    @FXML private Label Label06;
    @FXML private ComboBox Combo04;
    @FXML private TextField txtDetail06;
    @FXML private TextField txtDetail07;
    @FXML private TextField txtDetail05;
    @FXML private TextField txtField11;
    @FXML private TextField txtField09;
    @FXML private TextField txtField13;
    @FXML private ImageView imgTranStat1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        poTrans = new XMPOReturn(poGRider, poGRider.getBranchCode(), false);
        poTrans.setTranStat(1230);
        
        btnClose.setOnAction(this::cmdButton_Click);
        btnExit.setOnAction(this::cmdButton_Click);
        btnBrowse.setOnAction(this::cmdButton_Click);
        btnVoid.setOnAction(this::cmdButton_Click);
        btnPrint.setOnAction(this::cmdButton_Click);
        
        txtField50.focusedProperty().addListener(txtField_Focus);
        txtField51.focusedProperty().addListener(txtField_Focus);
        
        txtField50.setOnKeyPressed(this::txtField_KeyPressed);
        txtField51.setOnKeyPressed(this::txtField_KeyPressed);
        
        Combo04.setItems(cUnitType);
        Combo04.getSelectionModel().select(1);
        
        Combo28.setItems(cDivision);
        Combo28.getSelectionModel().select(cDivision.size() - 1);
        
        pnEditMode = EditMode.UNKNOWN;
        
        clearFields();
        initGrid();
        
        pbLoaded = true;
    }    

    @FXML
    private void table_Clicked(MouseEvent event) {
        pnRow = table.getSelectionModel().getSelectedIndex();
        setDetailInfo();
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
    
    private void setDetailInfo(){
        String lsStockIDx = (String) poTrans.getDetail(pnRow, "sStockIDx");
        if (pnRow >= 0 && !lsStockIDx.equals("")){
            Inventory loInventory = poTrans.GetInventory(lsStockIDx, true, false);
            psBarCodex = (String) loInventory.getMaster("sBarCodex");
            psDescript = (String) loInventory.getMaster("sDescript");
            txtDetail03.setText(psBarCodex);
            txtDetail80.setText(psDescript);
            
            txtDetail05.setText(String.valueOf(poTrans.getDetail(pnRow, 5))); /*Quantity*/
            txtDetail06.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, 6).toString()), "###0.00")); /*Unit Price*/
            txtDetail07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, 7).toString()), "###0.00")); /*Freight*/
            
            Combo04.getSelectionModel().select(Integer.parseInt((String) poTrans.getDetail(pnRow, 4)));
        } else{
            txtDetail03.setText("");
            txtDetail05.setText("0");
            txtDetail06.setText("0.00");
            txtDetail07.setText("0.00");
            txtDetail80.setText("");   
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
            
            case "btnClose":
            case "btnExit": 
                unloadForm();
                return;
               
            case "btnPrint":
                if(!psOldRec.equals("")){
                    if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to print this transaction?")==true){
                        if (poTrans.printRecord()){
                            ShowMessageFX.Information(null, pxeModuleName, "Transaction printed successfully.");
                            clearFields();
                            initGrid();
                            pnEditMode = EditMode.UNKNOWN;
                        }
                        break;
                    }else
                        return;
                }else 
                    ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to print!");
                break;
                
            case "btnBrowse":
                if(poTrans.BrowseRecord(txtField50.getText(), true)==true){
                    loadRecord(); 
                    pnEditMode = poTrans.getEditMode();
                } else {
                    clearFields();
                    pnEditMode = EditMode.UNKNOWN;
                }
                return;
            case "btnVoid":
               if (!psOldRec.equals("")){
                    if(!poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_OPEN)){
                        ShowMessageFX.Warning("Trasaction may be CANCELLED/POSTED.", pxeModuleName, "Can't update processed transactions!!!");
                        return;
                    }
                    if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to cancel this transaction?")==true){
                        if (poTrans.cancelRecord(psOldRec))
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction CANCELLED successfully.");
                        clearFields();
                        initGrid();
                        pnEditMode = EditMode.UNKNOWN;
                        break;
                    }else
                        return;
                    
                } else ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to cancel!");
                break;
                
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
                return;
        }
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
        } else 
            Combo28.getSelectionModel().select(cDivision.size() - 1);
        
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
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    private final String pxeModuleName = "POReceivingRegController";
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
    private String psBranchNm = "";
    private String psInvTypNm = "";
    private String psTermName = "";
    private String psSupplier = "";
    private String psDeptName = "";
    private String psReferNox = "";
    
    private String psBarCodex;
    private String psDescript;
    
    private String psOrderNox = "";
    
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
    
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        if (event.getCode() == ENTER || event.getCode() == F3){
            switch (lnIndex){
                case 50: /*sTransNox*/
                       if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                            if(poTrans.BrowseRecord(lsValue, true)==true){
                                 loadRecord(); 
                                 pnEditMode = poTrans.getEditMode();
                             }
                            if(!txtField50.getText().equals(psReferNox)){
                                 clearFields();
                                 break;
                             }else{
                                 txtField50.setText(psReferNox);
                                  }
                             return;

                case 51: /*sSupplier*/
                    if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if(poTrans.BrowseRecord(lsValue, false)== true){
                        loadRecord(); 
                        pnEditMode = poTrans.getEditMode();
                        break;
                    }if(!txtField51.getText().equals(psSupplier)){
                        clearFields();
                        break;
                        }else{
                            txtField51.setText(psSupplier);
                                 }
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
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/           
            switch (lnIndex){
                case 50: /*sReferNox*/
                     if(lsValue.equals("") || lsValue.equals("%")){
                       txtField.setText("");
                    }else
                    txtField.setText(psReferNox); break;
                case 51: /*sSupplierId*/
                     if(lsValue.equals("") || lsValue.equals("%")){
                       txtField.setText("");
                    }else
                    txtField.setText(psSupplier); break;
                default:
                    ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
            }
            pnIndex = lnIndex;
        }
        
    };
    
}
