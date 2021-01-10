
package org.rmj.cas.food.inventory.fx.views;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.constants.TransactionStatus;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.cas.inventory.base.InvTransfer;
import org.rmj.cas.parameter.agent.XMBranch;
import org.rmj.appdriver.agentfx.callback.IMasterDetail;

public class InvTransferController implements Initializable {

    @FXML
    private VBox VBoxForm;
    @FXML
    private Button btnExit;
    @FXML
    private FontAwesomeIconView glyphExit;
    @FXML
    private AnchorPane anchorField;
    @FXML
    private Label lblHeader;
    @FXML
    private TextField txtField01;
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField04;
    @FXML
    private TextField txtField18;
    @FXML
    private TextField txtField07;
    @FXML
    private TextField txtField13;
    @FXML
    private TextArea txtField05;
    @FXML
    private Label Label12;
    @FXML
    private TextField txtDetail05;
    @FXML
    private TextField txtDetail03;
    @FXML
    private TextField txtDetail80;
    @FXML
    private TextField txtDetail04;
    @FXML
    private TextField txtDetail07;
    @FXML
    private TextField txtDetail06;
    @FXML
    private TableView table;
    @FXML
    private TextField txtField06;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnBrowse;
    @FXML
    private ImageView imgTranStat;
    @FXML
    private TextArea txtDetail10;
    @FXML
    private TextField txtField50;
    @FXML
    private TextField txtField51;
    @FXML
    private Button btnPrint;
    @FXML
    private TextField txtOther02;

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        poTrans = new InvTransfer(poGRider, poGRider.getBranchCode(), false);
        poTrans.setCallBack(poCallBack);
        
        btnCancel.setOnAction(this::cmdButton_Click);
        btnSearch.setOnAction(this::cmdButton_Click);
        btnSave.setOnAction(this::cmdButton_Click);
        btnDel.setOnAction(this::cmdButton_Click);
        btnNew.setOnAction(this::cmdButton_Click);
        btnPrint.setOnAction(this::cmdButton_Click);
        btnClose.setOnAction(this::cmdButton_Click);
        btnExit.setOnAction(this::cmdButton_Click);
        btnBrowse.setOnAction(this::cmdButton_Click);
        
        txtField01.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField04.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtArea_Focus);
        txtField06.focusedProperty().addListener(txtField_Focus);
        txtField07.focusedProperty().addListener(txtField_Focus);
        txtField13.focusedProperty().addListener(txtField_Focus);
        txtField18.focusedProperty().addListener(txtField_Focus);
        txtField50.focusedProperty().addListener(txtField_Focus);
        txtField51.focusedProperty().addListener(txtField_Focus);
        
        txtDetail03.focusedProperty().addListener(txtDetail_Focus);
        txtDetail04.focusedProperty().addListener(txtDetail_Focus);
        txtDetail05.focusedProperty().addListener(txtDetail_Focus);
        txtDetail06.focusedProperty().addListener(txtDetail_Focus);
        txtDetail10.focusedProperty().addListener(txtDetailArea_Focus);
        txtDetail80.focusedProperty().addListener(txtDetail_Focus);
                
        /*Add keypress event for field with search*/
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField04.setOnKeyPressed(this::txtField_KeyPressed);
        txtField06.setOnKeyPressed(this::txtField_KeyPressed);
        txtField07.setOnKeyPressed(this::txtField_KeyPressed);
        txtField13.setOnKeyPressed(this::txtField_KeyPressed);
        txtField18.setOnKeyPressed(this::txtField_KeyPressed);
        txtField05.setOnKeyPressed(this::txtFieldArea_KeyPressed);
        txtField50.setOnKeyPressed(this::txtField_KeyPressed);
        txtField51.setOnKeyPressed(this::txtField_KeyPressed);
        
        txtDetail03.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail04.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail05.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail06.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail10.setOnKeyPressed(this::txtDetailArea_KeyPressed);
        txtDetail80.setOnKeyPressed(this::txtDetail_KeyPressed);
        
        pnEditMode = EditMode.UNKNOWN;
        clearFields();
        initGrid();
        initButton(pnEditMode);
        
        pbLoaded = true;
  
    }
    
    private void initButton(int fnValue){
        boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
        
        btnCancel.setVisible(lbShow);
        btnSearch.setVisible(lbShow);
        btnSave.setVisible(lbShow);
        btnDel.setVisible(lbShow);
        lblHeader.setVisible(lbShow);
        
        txtField50.setDisable(lbShow);
        txtField51.setDisable(lbShow);
                
        btnBrowse.setVisible(!lbShow);
        btnNew.setVisible(!lbShow);
        btnPrint.setVisible(!lbShow);
        btnClose.setVisible(!lbShow);
        
        txtField01.setDisable(!lbShow);
        txtField03.setDisable(!lbShow);
        txtField04.setDisable(!lbShow);
        txtField05.setDisable(!lbShow);
        //txtField06.setDisable(!lbShow);
        txtField07.setDisable(!lbShow);
        txtField13.setDisable(!lbShow);
        //txtField18.setDisable(!lbShow);
        
        txtDetail03.setDisable(!lbShow);
        txtDetail04.setDisable(!lbShow);
        txtDetail05.setDisable(!lbShow);
        txtDetail06.setDisable(!lbShow);
        txtDetail10.setDisable(!lbShow);
        txtDetail80.setDisable(!lbShow);
        
        if (lbShow)
            txtField03.requestFocus();
        else
            txtField51.requestFocus();
    }
    
    private void clearFields(){
        txtField01.setText("");
        txtField03.setText("");
        txtField04.setText("");
        txtField05.setText("");
        txtField06.setText("");
        txtField07.setText("0.00");
        txtField13.setText("0.00");
        txtField50.setText("");
        txtField51.setText("");
        txtOther02.setText("0");
        
        txtDetail03.setText("");
        txtDetail04.setText("");
        txtDetail05.setText("");
        txtDetail07.setText("0.00");
        txtDetail06.setText("0");
        txtDetail80.setText("");
        Label12.setText("0.00");
        
        pnRow = -1;
        pnOldRow = -1;
        pnIndex = 51;
        setTranStat("-1");
        
        psOldRec = "";
        psDestina = "";
        psTrukNme = "";
        psOrderNm = "";
        psOrderNox = "";
        psTransNox = "";
        
        data.clear();
    }
    
    private void initGrid(){
        
        TableColumn index01 = new TableColumn("No.");
        TableColumn index02 = new TableColumn("Order No.");
        TableColumn index03 = new TableColumn("Barcode");
        TableColumn index04 = new TableColumn("Description");
        TableColumn index05 = new TableColumn("Old Code");
        TableColumn index06 = new TableColumn("Unit Price");
        TableColumn index07 = new TableColumn("Qty");
        TableColumn index08 = new TableColumn("Notes");
        
        index01.setPrefWidth(30); index01.setStyle("-fx-alignment: CENTER;");
        index02.setPrefWidth(110);
        index03.setPrefWidth(110);
        index04.setPrefWidth(180);
        index05.setPrefWidth(110);
        index06.setPrefWidth(80);
        index07.setPrefWidth(40); index07.setStyle("-fx-alignment: CENTER;");
        index08.setPrefWidth(200);index08.setStyle("-fx-alignment: CENTER-LEFT;");
        
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
     
    private void unloadForm(){
        VBox myBox = (VBox) VBoxForm.getParent();
        myBox.getChildren().clear();
    }
    
    private void txtFieldArea_KeyPressed(KeyEvent event){
        if (event.getCode() == ENTER || event.getCode() == DOWN){ 
            event.consume();
            CommonUtils.SetNextFocus((TextArea)event.getSource());
        }else if (event.getCode() ==KeyCode.UP){
        event.consume();
            CommonUtils.SetPreviousFocus((TextArea)event.getSource());
        }
    }
    
     private void txtDetailArea_KeyPressed(KeyEvent event){
        if (event.getCode() == ENTER || event.getCode() == KeyCode.DOWN){
            event.consume();
            CommonUtils.SetNextFocus((TextArea)event.getSource());
        }else if (event.getCode() ==KeyCode.UP){
        event.consume();
            CommonUtils.SetPreviousFocus((TextArea)event.getSource());
        }
    }
    
     private void txtDetail_KeyPressed(KeyEvent event){
        TextField txtDetail = (TextField) event.getSource();
        int lnIndex = Integer.parseInt(txtDetail.getId().substring(9, 11));
        String lsValue = txtDetail.getText() + "%";
        
        if (event.getCode() == F3){
            switch (lnIndex){
                case 3: /*Barcode Search*/                   
                    if (poTrans.SearchDetail(pnRow, 3, lsValue, false, false)){
                        txtDetail03.setText(poTrans.getDetailOthers(pnRow, "sBarCodex").toString());
                        txtDetail80.setText(poTrans.getDetailOthers(pnRow, "sDescript").toString());
                        txtDetail06.setText(poTrans.getDetail(pnRow, "nQuantity").toString());
                        txtDetail07.setText(poTrans.getDetail(pnRow, "nInvCostx").toString());
                        txtOther02.setText(poTrans.getDetailOthers(pnRow, "nQtyOnHnd").toString());
                    } else {
                        txtDetail03.setText("");
                        txtDetail80.setText("");
                        txtDetail06.setText("");
                        txtDetail07.setText("");
                        txtOther02.setText("0");
                    }
                    break;
                case 5:
                    break;
                case 80: /*Description Search*/
                    if (poTrans.SearchDetail(pnRow, 3, lsValue, true, false)){
                        txtDetail03.setText(poTrans.getDetailOthers(pnRow, "sBarCodex").toString());
                        txtDetail80.setText(poTrans.getDetailOthers(pnRow, "sDescript").toString());
                        txtDetail06.setText(poTrans.getDetail(pnRow, "nQuantity").toString());
                        txtDetail07.setText(poTrans.getDetail(pnRow, "nInvCostx").toString());
                        txtOther02.setText(poTrans.getDetailOthers(pnRow, "nQtyOnHnd").toString());
                    } else {
                        txtDetail03.setText("");
                        txtDetail80.setText("");
                        txtDetail06.setText("");
                        txtDetail07.setText("");
                        txtOther02.setText("0");
                    }
                    
                    break;
                case 4:
                    if (poTrans.SearchDetail(pnRow, 4, lsValue, false, false)){
                        txtDetail.setText(poTrans.getDetailOthers(pnRow, "sOrigCode").toString());
                        loadDetail();
                    }
                    else 
                        txtDetail.setText("");
                    
                    break;
            }
        }
        
        switch (event.getCode()){
        case ENTER:
        case DOWN:
            CommonUtils.SetNextFocus(txtDetail);
            break;
        case UP:
            CommonUtils.SetPreviousFocus(txtDetail);
        }
    }
    
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText() + "%";
            if (event.getCode() == F3){
                switch (lnIndex){
                    case 4: /*sDestinat*/
                    case 6: /*sTruckIDx*/
                    case 18: /*sOrderNox */
                        if (poTrans.SearchMaster(lnIndex, txtField.getText(), false))
                            CommonUtils.SetNextFocus(txtField); 
                        break;
                    case 50: /*sTransNox*/
                        if(poTrans.BrowseRecord(lsValue, true)==true){
                            loadRecord();
                            pnEditMode = poTrans.getEditMode();
                        } else {
                            clearFields();
                            pnEditMode = EditMode.UNKNOWN;
                        }
                        return;
                    case 51: /*psDestina*/
                        if(poTrans.BrowseRecord(lsValue, false)== true){
                            loadRecord(); 
                            pnEditMode = poTrans.getEditMode();
                        } else {
                            clearFields();
                            pnEditMode = EditMode.UNKNOWN;
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
    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId();
        
        switch (lsButton){
            case "btnNew":
                if (poTrans.newTransaction()){  
                    clearFields();
                    loadRecord();
                    txtField50.setText("");
                    pnEditMode = poTrans.getEditMode();
                }  
                break;
            case "btnPrint": 
                if (!psOldRec.equals("")){
                    if(!poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_OPEN)){
                    ShowMessageFX.Warning("Trasaction may be CANCELLED/POSTED.", pxeModuleName, "Can't update processed transactions!!!");
                    return;
                    }
                    if( ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to print this transasction?")== true){
                        if (!printTransfer()) return;
                        
                        if (poTrans.closeTransaction(psOldRec)){
                            //ShowMessageFX.Information(null, pxeModuleName, "Transaction PRINTED successfully.");
                            clearFields();
                            initGrid();
                            pnEditMode = EditMode.UNKNOWN;
                            initButton(pnEditMode);
                        }
                    }
                    
                } else ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to print!");
                break;
             
            case "btnClose":
            case "btnExit":
                unloadForm();
                return;
                
            case "btnCancel": 
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to disregard changes?") == true){
                    clearFields();
                    pnEditMode = EditMode.UNKNOWN;
                    break;
                } else
                    return;
                
            case "btnSearch":return;
            case "btnSave": 
                if (poTrans.saveTransaction()){
                    ShowMessageFX.Information(null, pxeModuleName, "Transaction saved successfuly.");
                    clearFields();
                    initGrid();
                    pnEditMode = EditMode.UNKNOWN;
                    initButton(pnEditMode);
                    break;
                } else{
                    if (!poTrans.getErrMsg().equals(""))
                        ShowMessageFX.Error(poTrans.getErrMsg(), pxeModuleName, "Please inform MIS Department.");
                    else
                        ShowMessageFX.Warning(poTrans.getMessage(), pxeModuleName, "Please verify your entry.");
                    return;
                } 
            case "btnBrowse":
                switch(pnIndex){
                    case 50: /*sTransNox*/
                        if(poTrans.BrowseRecord(txtField50.getText(), true)==true){
                            loadRecord(); 
                            pnEditMode = poTrans.getEditMode();
                            break;
                        } else {
                            clearFields();
                            pnEditMode = EditMode.UNKNOWN;
                        }
                        return;    
                    case 51: /*sDestination*/
                        if(poTrans.BrowseRecord(txtField51.getText() + "%", false)== true){
                            loadRecord(); 
                            pnEditMode = poTrans.getEditMode();
                        }
                    default:
                        txtField51.requestFocus();
                }
                return;
            case "btnDel":  
               int lnIndex = table.getSelectionModel().getFocusedIndex();    
                if(table.getSelectionModel().getSelectedItem() == null){
                     ShowMessageFX.Warning(null, pxeModuleName, "Please select item to remove!");
                     break;
                }
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to remove this item?") == true){
                    poTrans.deleteDetail(lnIndex);
                    loadDetail();
                }     
                break;
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
                return;
        }
        
        initButton(pnEditMode);
    }
    
    private void loadRecord(){
        txtField01.setText((String) poTrans.getMaster("sTransNox"));
        txtField50.setText((String) poTrans.getMaster("sTransNox"));
        psTransNox = txtField50.getText();
        
        XMBranch loBranch = poTrans.GetBranch((String)poTrans.getMaster(4), true);
        if (loBranch != null) {
            txtField04.setText((String) loBranch.getMaster("sBranchNm"));
            txtField51.setText((String) loBranch.getMaster("sBranchNm"));
        }
        
        //TODO:
        // Order No. and Truck
        txtField18.setText("");
        txtField06.setText("");
        
        txtField03.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        psDestina = txtField51.getText();
        txtField05.setText((String) poTrans.getMaster("sRemarksx"));
        
        txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nFreightx").toString()), "0.00"));
        txtField13.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nDiscount").toString()), "0.00"));
        
        Label12.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nTranTotl").toString()), "#,##0.00"));
        
        pnRow = 0;
        pnOldRow = 0;
        loadDetail();
        setTranStat((String) poTrans.getMaster("cTranStat"));
        psOldRec = txtField01.getText();
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
    
    private void loadDetail(){
        int lnCtr;
        int lnRow = poTrans.ItemCount();
        
        data.clear();
        /*ADD THE DETAIL*/
        for(lnCtr = 0; lnCtr <= lnRow -1; lnCtr++){
            data.add(new TableModel(String.valueOf(lnCtr + 1), 
                                    (String) poTrans.getDetail(lnCtr, "sOrderNox"),
                                    (String) poTrans.getDetailOthers(lnCtr, "sBarCodex"), 
                                    (String) poTrans.getDetailOthers(lnCtr, "sDescript"),
                                    poTrans.getDetailOthers(lnCtr, "sOrigCode").toString(),
                                    CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nInvCostx").toString()), "0.00"),
                                    String.valueOf(poTrans.getDetail(lnCtr, "nQuantity")),
                                    (String) poTrans.getDetail(lnCtr, "sNotesxxx"),
                                    "",
                                    ""));
        }
    
        /*FOCUS ON FIRST ROW*/
        if (!data.isEmpty()){
            table.getSelectionModel().select(lnRow -1);
            table.getFocusModel().focus(lnRow -1);
            
            pnRow = table.getSelectionModel().getSelectedIndex();           
            
            setDetailInfo();
        }
        
        Label12.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nTranTotl").toString()), "#,##0.00"));
    }
    
    private void setDetailInfo(){
        int lnRow = table.getSelectionModel().getSelectedIndex();
        
        pnRow = lnRow;
        
        if (pnRow >= 0){
            txtDetail05.setText(String.valueOf(poTrans.getDetail(pnRow, "sOrderNox")));
            txtDetail03.setText((String) poTrans.getDetailOthers(pnRow, "sBarCodex"));
            txtDetail80.setText((String) poTrans.getDetailOthers(pnRow, "sDescript"));
            txtDetail04.setText((String) poTrans.getDetailOthers(pnRow, "sOrigCode"));
            txtDetail07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(pnRow, "nInvCostx").toString()), "0.00"));
            txtDetail06.setText(String.valueOf(poTrans.getDetail(pnRow, "nQuantity")));
            txtDetail10.setText(String.valueOf(poTrans.getDetail(pnRow, "sNotesxxx")));
            txtOther02.setText(String.valueOf(poTrans.getDetailOthers(pnRow, "nQtyOnHnd")));
        } else{
            txtDetail03.setText("");
            txtDetail04.setText("");
            txtDetail05.setText("");
            txtDetail06.setText("0");
            txtDetail07.setText("0.00");
            txtDetail10.setText("");
            txtDetail80.setText("");
            txtOther02.setText("0");
        }
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    private final String pxeModuleName = "InvTransferController";
    private static GRider poGRider;
    private InvTransfer poTrans;
    
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    
    private final String pxeDateFormat = "yyyy-MM-dd";
    private final String pxeDateDefault = java.time.LocalDate.now().toString();
    
    private TableModel model;
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    
    private int pnIndex = -1;
    private int pnRow = -1;
    private int pnOldRow = -1;
    
    private String psDestina = "";
    private String psTrukNme = "";
    private String psOrderNm = "";
    private String psTransNox = "";
    
    private String psOldRec = "";
    private String psOrderNox = "";
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/      
            switch (lnIndex){
                case 1: /*sTransNox*/
                    break;
                case 3: /*dTransact*/
                  if (CommonUtils.isDate(txtField.getText(), pxeDateFormat)){
                        poTrans.setMaster("dTransact", CommonUtils.toDate(txtField.getText()));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setMaster(lnIndex, CommonUtils.toDate(pxeDateDefault));
                    }
                    return;
                case 4:/*sDestinat*/
                case 6:/*sTruckIDx*/
                case 18:/*sOrderNox*/
                    break;
                case 7:/*nFreightx*/
                     double x = 0.00;
                    try {
                        /*this must be numeric*/
                        x = Double.parseDouble(lsValue);
                    } catch (Exception e) {
                        x = 0.00;
                        txtField.setText("0.00");
                    }
                    poTrans.setMaster("nFreightx",  Double.parseDouble(txtField.getText()));
                    break;
                case 13:/*nDiscount*/
                    double y = 0.00;
                    try {
                        /*this must be numeric*/
                        y = Double.parseDouble(lsValue);
                    } catch (Exception e) {
                        y = 0.00;
                        txtField.setText("0.00");
                    }
                    if (y > 1 ){
                        poTrans.setMaster("nDiscount", 0);
                    } else{
                        poTrans.setMaster("nDiscount", (y > 0 ? y : 0));
                    }
                    break;                   
                case 50:
                    if(lsValue.equals("") || lsValue.equals("%"))
                        txtField.setText("");
                    break; 
                case 51: 
                    if(lsValue.equals("") || lsValue.equals("%"))
                        txtField.setText("");
                    break;
                default:
                    ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                    return;
            }
            pnIndex = lnIndex;
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
    
    final ChangeListener<? super Boolean> txtDetail_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtDetail = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtDetail.getId().substring(9, 11));
        String lsValue = txtDetail.getText();
        
        if (pnRow < 0) return;
        if (lsValue == null) return;
        
        if(!nv){ /*Lost Focus*/     
            switch (lnIndex){
                case 3: /*Barcode Search*/
                case 80:/*sDescript Search*/
                case 4:/*sOrigIDxx*/
                case 5:/*sOrderNox Search*/
                    break;
                case 6:/*nQuantity*/
                    int x = 0;
                    try {
                        /*this must be numeric*/
                        x = Integer.parseInt(lsValue);
                    } catch (NumberFormatException e) {
                        x = 0;
                        txtDetail.setText("0");
                    }
                    poTrans.setDetail(pnRow, "nQuantity",  x);
                    break;
                case 7:/*nInvCostx*/
                    double y = 0.00;
                    try {
                        /*this must be numeric*/
                        y = Double.parseDouble(lsValue);
                    } catch (NumberFormatException e) {
                        y = 0.00;
                    }
                    poTrans.setDetail(pnRow,"nInvCostx", y);
                    break; 
            }
            pnOldRow = table.getSelectionModel().getSelectedIndex();
        } else {
            pnIndex = -1;
            txtDetail.selectAll();
        }
    };
    
    final ChangeListener<? super Boolean> txtDetailArea_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextArea txtDetail = (TextArea)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtDetail.getId().substring(9, 11));
        String lsValue = txtDetail.getText();
        
        if (lsValue == null) return;
        
        if(!nv){ /*Lost Focus*/            
            switch (lnIndex){
                case 10: /*sNotesxxx*/
                    if (lsValue.length() > 256) lsValue = lsValue.substring(0, 256);
                    
                    poTrans.setDetail(pnRow, "sNotesxxx", CommonUtils.TitleCase(lsValue));
            }
        }else{ 
            pnIndex = -1;
            txtDetail.selectAll();
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
                case 5: /*sRemarksx*/
                    if (lsValue.length() > 256) lsValue = lsValue.substring(0, 256);
                    
                    poTrans.setMaster("sRemarksx", CommonUtils.TitleCase(lsValue));
                    txtField.setText((String)poTrans.getMaster("sRemarksx"));
                    break;
            }
        }else{ 
            pnIndex = -1;
            txtField.selectAll();
        }
    };

    @FXML
    private void table_Clicked(MouseEvent event) {
        setDetailInfo(); 
        txtDetail03.requestFocus();
        txtDetail03.selectAll();
    }    
    
    private boolean printTransfer(){        
        JSONArray json_arr = new JSONArray();
        json_arr.clear();
        
        for(int lnCtr = 0; lnCtr <= poTrans.ItemCount()-1; lnCtr ++){
            JSONObject json_obj = new JSONObject();
            json_obj.put("sField01", (String) poTrans.getDetailOthers(lnCtr, "sBarCodex"));
            json_obj.put("sField02", (String) poTrans.getDetailOthers(lnCtr, "sDescript"));
            json_obj.put("nField01", (int) poTrans.getDetail(lnCtr, "nQuantity"));
            json_obj.put("lField01", Double.valueOf(poTrans.getDetail(lnCtr, "nInvCostx").toString()));
            json_arr.add(json_obj);
        }
        
        String lsSQL = "SELECT sBranchNm FROM Branch WHERE sBranchCD = " + SQLUtil.toSQL((String) poTrans.getMaster("sDestinat"));
        ResultSet loRS = poGRider.executeQuery(lsSQL);
        try {
            if (loRS.next())
                lsSQL = loRS.getString("sBranchNm");
            else
                lsSQL = (String) poTrans.getMaster("sDestinat");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            ShowMessageFX.Error(ex.getMessage(), pxeModuleName, "Unable to print...");
            System.exit(1);
        }
        
        //Create the parameter
        Map<String, Object> params = new HashMap<>();
        params.put("sCompnyNm", "Los Pedritos");  
        params.put("sBranchNm", poGRider.getBranchName());
        params.put("sDestinat", lsSQL);
        
        params.put("sTransNox", poTrans.getMaster("sTransNox").toString().substring(1));
        params.put("sReportDt", poTrans.getMaster("dTransact").toString());
        params.put("sPrintdBy", poGRider.getUserID());
                
        try {
            InputStream stream = new ByteArrayInputStream(json_arr.toJSONString().getBytes("UTF-8"));
            JsonDataSource jrjson = new JsonDataSource(stream); 
            
            JasperPrint _jrprint = JasperFillManager.fillReport("d:/GGC_Java_Systems/reports/InvTransferPrint.jasper", params, jrjson);
            JasperViewer jv = new JasperViewer(_jrprint, false);     
            jv.setVisible(true);
        } catch (JRException | UnsupportedEncodingException  ex) {
            Logger.getLogger(InvTransferController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    IMasterDetail poCallBack = new IMasterDetail() {
        @Override
        public void MasterRetreive(int fnIndex) {
            getMaster(fnIndex);
        }

        @Override
        public void DetailRetreive(int fnIndex) {
            switch(fnIndex){
                case 10:
                    txtDetail10.setText((String)poTrans.getDetail(pnRow,"sNotesxxx"));
                    loadDetail();
                    break;
                case 6:
                    txtDetail06.setText(String.valueOf(poTrans.getDetail(pnRow,"nQuantity")));
                    loadDetail();
                    
                    if (!poTrans.getDetail(poTrans.ItemCount()- 1, "sStockIDx").toString().isEmpty() && 
                            (int) poTrans.getDetail(poTrans.ItemCount()- 1, fnIndex) > 0){
                        poTrans.addDetail();
                        pnRow = poTrans.ItemCount()- 1;

                        //set the previous order numeber to the new ones.
                        poTrans.setDetail(pnRow, "sOrderNox", psOrderNox);
                    }                           
                    loadDetail();
                    if (!txtDetail03.getText().isEmpty()){
                        txtDetail07.requestFocus();
                        txtDetail07.selectAll();
                    } else{
                        txtDetail05.requestFocus();
                        txtDetail05.selectAll();
                    }
                case 7:
                    txtDetail07.setText(CommonUtils.NumberFormat((Double)poTrans.getDetail(pnRow,"nInvCostx"), "0.00")); 
            }
        }
    };
    
    private void getMaster(int fnIndex){
        switch(fnIndex){
            case 3:
                txtField03.setText(CommonUtils.xsDateLong((Date)poTrans.getMaster("dTransact")));
                break;
            case 4:
                XMBranch loBranch = poTrans.GetBranch((String)poTrans.getMaster(fnIndex), true);
                if (loBranch != null) txtField04.setText((String) loBranch.getMaster("sBranchNm"));
                break;    
            case 7:
                txtField07.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nFreightx"), "0.00"));
                break;
            case 13:
                txtField13.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nDiscount").toString()), "0.00"));
                
        }
    }
}
