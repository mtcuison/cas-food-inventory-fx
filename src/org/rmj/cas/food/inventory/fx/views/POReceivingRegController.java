package org.rmj.cas.food.inventory.fx.views;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Date;
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
import org.rmj.cas.parameter.agent.XMTerm;
import org.rmj.purchasing.agent.XMPOReceiving;


public class POReceivingRegController implements Initializable {

    @FXML
    private VBox VBoxForm;
    @FXML
    private Button btnExit;
    @FXML
    private FontAwesomeIconView glyphExit;
    @FXML
    private AnchorPane anchorField;
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField02;
    @FXML
    private TextField txtField29;
    @FXML
    private ComboBox Combo32;
    @FXML
    private TextField txtField20;
    @FXML
    private TextField txtField05;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField07;
    @FXML
    private TextField txtField08;
    @FXML
    private TextArea txtField16;
    @FXML
    private Label Label09;
    @FXML
    private TextField txtDetail03;
    @FXML
    private TextField txtDetail04;
    @FXML
    private TextField txtDetail80;
    @FXML
    private TextField txtDetail05;
    @FXML
    private ComboBox Combo06;
    @FXML
    private TextField txtDetail08;
    @FXML
    private TextField txtDetail09;
    @FXML
    private TextField txtDetail07;
    @FXML
    private TableView table;
    @FXML
    private TextField txtField10;
    @FXML
    private TextField txtField11;
    @FXML
    private TextField txtField15;
    @FXML
    private TextField txtField12;
    @FXML
    private TextField txtField13;
    @FXML
    private TextField txtField14;
    @FXML
    private ImageView imgTranStat;
    @FXML
    private TextField txtField01;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnBrowse;
    @FXML
    private Button btnVoid;
    @FXML
    private Button btnPrint;
    @FXML
    private TextField txtField50;
    @FXML
    private TextField txtField51;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        poTrans = new XMPOReceiving(poGRider, poGRider.getBranchCode(), false);
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
        
        pnEditMode = EditMode.UNKNOWN;
        
        Combo06.setItems(cUnitType);
        Combo06.getSelectionModel().select(1);
        
        Combo32.setItems(cDivision);
        Combo32.getSelectionModel().select(0);
        
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
        String lsOldCode = "";
        for(lnCtr = 0; lnCtr <= lnRow -1; lnCtr++){
            if (!"".equals((String) poTrans.getDetail(lnCtr, "sStockIDx"))) {
                loInventory = poTrans.GetInventory((String) poTrans.getDetail(lnCtr, "sStockIDx"), true, false);
                psBarCodex = (String) loInventory.getMaster("sBarCodex");
                psDescript = (String) loInventory.getMaster("sDescript");
                
                if (!"".equals((String) poTrans.getDetail(lnCtr, "sReplacID"))){
                    loInventory = poTrans.GetInventory((String) poTrans.getDetail(lnCtr, "sReplacID"), true, false);
                    lsOldCode = (String) loInventory.getMaster("sBarCodex");
                }
                    
                data.add(new TableModel(String.valueOf(lnCtr + 1), 
                                    (String) poTrans.getDetail(lnCtr, "sOrderNox"),
                                    psBarCodex, 
                                    psDescript,
                                    lsOldCode,
                                    cUnitType.get(Integer.parseInt((String) poTrans.getDetail(lnCtr, "cUnitType"))),
                                    String.valueOf(poTrans.getDetail(lnCtr, "nQuantity")),
                                    CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nUnitPrce").toString()), "#,##0.00"),
                                    CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nFreightx").toString()), "#,##0.00"),
                                    ""));
            } else {
                data.add(new TableModel(String.valueOf(lnCtr + 1), 
                                    "",
                                    "", 
                                    "",
                                    "",
                                    cUnitType.get(Integer.parseInt((String) poTrans.getDetail(lnCtr, "cUnitType"))),
                                    String.valueOf(poTrans.getDetail(lnCtr, "nQuantity")),
                                    CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nUnitPrce").toString()), "#,##0.00"),
                                    CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nFreightx").toString()), "#,##0.00"),
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
        
        Label09.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(9).toString()) + Double.valueOf(poTrans.getMaster(11).toString()), "#,##0.00"));
        txtField11.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(11).toString()), "#,##0.00"));
    }  
    
    private void setDetailInfo(){
        String lsStockIDx = (String) poTrans.getDetail(pnRow, "sStockIDx");
        if (pnRow >= 0){            
            txtDetail03.setText((String) poTrans.getDetail(pnRow, 3));
            Inventory loInventory = poTrans.GetInventory(lsStockIDx, true, false);
            psBarCodex = (String) loInventory.getMaster("sBarCodex");
            psDescript = (String) loInventory.getMaster("sDescript");
            txtDetail04.setText(psBarCodex);
            txtDetail80.setText(psDescript);
            
            lsStockIDx = (String) poTrans.getDetail(pnRow, "sReplacID");
            if (!lsStockIDx.equals("")){
                loInventory = poTrans.GetInventory(lsStockIDx, true, false);
                psBarCodex = (String) loInventory.getMaster("sBarCodex");
                psDescript = (String) loInventory.getMaster("sDescript");
                txtDetail05.setText(psBarCodex);
            } else 
                txtDetail05.setText("");
            
            txtDetail07.setText(String.valueOf(poTrans.getDetail(pnRow, 7)));
            txtDetail08.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, 8).toString()), "0.00"));
            txtDetail09.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, 9).toString()), "0.00"));
            
            Combo06.getSelectionModel().select(Integer.parseInt((String) poTrans.getDetail(pnRow, 6)));
        } else{
            txtDetail03.setText("");
            txtDetail04.setText("");
            txtDetail05.setText("");
            txtDetail07.setText("0");
            txtDetail08.setText("0.00");
            txtDetail09.setText("0.00");
            txtDetail80.setText("");   
        }
    }
    
    private void initGrid(){
        
        TableColumn index01 = new TableColumn("No.");
        TableColumn index02 = new TableColumn("Order No.");
        TableColumn index03 = new TableColumn("Bar Code");
        TableColumn index04 = new TableColumn("Description");
        TableColumn index05 = new TableColumn("Superseded");
        TableColumn index06 = new TableColumn("Unit Type");
        TableColumn index07 = new TableColumn("Qty");
        TableColumn index08 = new TableColumn("Unit Price");
        TableColumn index09 = new TableColumn("Freight");
        
        index01.setPrefWidth(30); index01.setStyle("-fx-alignment: CENTER;");
        index02.setPrefWidth(110);
        index03.setPrefWidth(110);
        index04.setPrefWidth(195);
        index05.setPrefWidth(110);
        index06.setPrefWidth(80);
        index07.setPrefWidth(40); index07.setStyle("-fx-alignment: CENTER;");
        index08.setPrefWidth(80); index08.setStyle("-fx-alignment: CENTER-RIGHT;");
        index09.setPrefWidth(80); index09.setStyle("-fx-alignment: CENTER-RIGHT;");
        
        index01.setSortable(false); index01.setResizable(false);
        index02.setSortable(false); index02.setResizable(false);
        index03.setSortable(false); index03.setResizable(false);
        index04.setSortable(false); index04.setResizable(false);
        index05.setSortable(false); index05.setResizable(false);
        index06.setSortable(false); index06.setResizable(false);
        index07.setSortable(false); index07.setResizable(false);
        index08.setSortable(false); index08.setResizable(false);
        index09.setSortable(false); index09.setResizable(false);

        table.getColumns().clear();        
        table.getColumns().add(index01);
        table.getColumns().add(index02);
        table.getColumns().add(index03);
        table.getColumns().add(index04);
        table.getColumns().add(index05);
        table.getColumns().add(index06);
        table.getColumns().add(index07);
        table.getColumns().add(index08);
        table.getColumns().add(index09);
        
        index01.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index04"));
        index05.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index05"));
        index06.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index06"));
        index07.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index07"));
        index08.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index08"));
        index09.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index09"));
        
        /*making column's position uninterchangebale*/
        table.widthProperty().addListener(new ChangeListener<Number>() {  
        public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
        {
            TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
            header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    header.setReordering(false);
                        }
                    });
                }
            });
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
                    if (poTrans.printRecord()){
                        clearFields();
                        initGrid();
                        pnEditMode = EditMode.UNKNOWN;
                    }
                }else 
                    ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to print!");
                
                break;
            case "btnBrowse":
                if(poTrans.BrowseRecord(txtField50.getText(), true)==true){
                    loadRecord(); 
                    pnEditMode = poTrans.getEditMode();
                    break;
                }

                if(!txtField50.getText().equals(psReferNox)){
                    clearFields();
                    break;
                }else txtField50.setText(psReferNox);

                return;
            case "btnVoid":
               if (!psOldRec.equals("")){
                    if(poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_CANCELLED) ||
                        poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_POSTED)){
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
        txtField03.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField06.setText((String) poTrans.getMaster(6));
        txtField50.setText((String) poTrans.getMaster(6));
        psReferNox = txtField50.getText();
        txtField07.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dRefernce")));
        txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(10).toString()), "0.00"));
        txtField11.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(11).toString()), "#,##0.00"));
        txtField12.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(12).toString()), "0.00"));
        txtField13.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(13).toString()), "#,##0.00"));
        txtField14.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(14).toString()), "#,##0.00"));
        txtField15.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(15).toString()), "#,##0.00"));
        txtField16.setText((String) poTrans.getMaster(16));
        
        XMBranch loBranch = poTrans.GetBranch((String)poTrans.getMaster(2), true);
        if (loBranch != null) txtField02.setText((String) loBranch.getMaster("sBranchNm"));
        
        XMDepartment loDept = poTrans.GetDepartment((String)poTrans.getMaster(29), true);
        if (loDept != null) txtField29.setText((String) loDept.getMaster("sDeptName"));

        XMInventoryType loInv = poTrans.GetInventoryType((String)poTrans.getMaster(20), true);
        if (loInv != null) txtField20.setText((String) loInv.getMaster("sDescript"));
        
        JSONObject loSupplier = poTrans.GetSupplier((String)poTrans.getMaster(5), true);
        if (loSupplier != null) {
            txtField05.setText((String) loSupplier.get("sClientNm"));
            txtField51.setText((String) loSupplier.get("sClientNm"));
        }

        XMTerm loTerm = poTrans.GetTerm((String)poTrans.getMaster(8), true);
        if (loTerm != null) txtField08.setText((String) loTerm.getMaster("sDescript"));
        
        try{
            Combo32.getSelectionModel().select(Integer.parseInt(poTrans.getMaster("cDivision").toString()));
        }catch (NumberFormatException ex){
            Combo32.getSelectionModel().select(String.valueOf(poTrans.getMaster("cDivision").toString()));
        }
        
        setTranStat((String) poTrans.getMaster("cTranStat"));
        Label09.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster(9).toString()) + Double.valueOf(poTrans.getMaster(11).toString()), "#,##0.00"));
        
        pnRow = 0;
        pnOldRow = 0;
        loadDetail();
        
        psOldRec = txtField01.getText();
    }
    
    private void clearFields(){
        txtField50.setText("");
        txtField51.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField05.setText("");
        txtField06.setText("");
        txtField07.setText("");
        txtField08.setText("");
        txtField10.setText("0.00");
        txtField11.setText("0.00");
        txtField12.setText("0.00");
        txtField13.setText("0.00");
        txtField14.setText("0.00");
        txtField15.setText("0.00");
        txtField15.setText("0.00");
        txtField16.setText("");
        txtField20.setText("");
        txtField29.setText("");
        
        txtDetail03.setText("");
        txtDetail04.setText("");
        txtDetail05.setText("");
        txtDetail07.setText("0");
        txtDetail08.setText("0.00");
        txtDetail09.setText("0.00");
        txtDetail80.setText("");
        
        Label09.setText("0.00");
        Combo06.getSelectionModel().select(1);
        
        pnRow = 51;
        pnOldRow = -1;
        pnIndex = -1;
        setTranStat("-1");
        
        psOldRec = "";
        psBranchNm = "";
        psInvTypNm = "";
        psSupplier = "";
        psDeptName = "";
        psReferNox = "";

        psOrderNox = "";
        
        data.clear();
    }
    
    private void unloadForm(){
        VBox myBox = (VBox) VBoxForm.getParent();
        myBox.getChildren().clear();
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    private final String pxeModuleName = "POReceivingRegController";
    private static GRider poGRider;
    private XMPOReceiving poTrans;
    
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
