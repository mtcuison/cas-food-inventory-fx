package org.rmj.cas.food.inventory.fx.views;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.text.ParseException;
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
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.constants.TransactionStatus;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.cas.inventory.base.InvCount;

public class InvCountController implements Initializable {

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
    private TextArea txtField05;
    @FXML
    private TextField txtDetail03;
    @FXML
    private TextField txtDetail80;
    @FXML
    private TextField txtDetail05;
    @FXML
    private TextField txtDetail04;
    @FXML
    private TableView table;
    @FXML
    private ImageView imgTranStat;
    @FXML
    private TextField txtField02;
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
    private Button btnConfirm;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnBrowse;
    @FXML
    private TextArea txtDetail10;
    @FXML
    private TextField txtDetail09;
    @FXML
    private TextField txtField51;
    @FXML
    private TextField txtField50;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        poTrans = new InvCount(poGRider, poGRider.getBranchCode(), false);
        
        btnCancel.setOnAction(this::cmdButton_Click);
        btnSearch.setOnAction(this::cmdButton_Click);
        btnSave.setOnAction(this::cmdButton_Click);
        btnDel.setOnAction(this::cmdButton_Click);
        btnNew.setOnAction(this::cmdButton_Click);
        btnConfirm.setOnAction(this::cmdButton_Click);
        btnClose.setOnAction(this::cmdButton_Click);
        btnExit.setOnAction(this::cmdButton_Click);
        btnBrowse.setOnAction(this::cmdButton_Click);
        
        txtField01.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField02.focusedProperty().addListener(txtField_Focus);
        txtField50.focusedProperty().addListener(txtField_Focus);
        txtField51.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtArea_Focus);
        
        txtDetail03.focusedProperty().addListener(txtDetail_Focus);
        txtDetail04.focusedProperty().addListener(txtDetail_Focus);
        txtDetail05.focusedProperty().addListener(txtDetail_Focus);
        txtDetail09.focusedProperty().addListener(txtDetail_Focus);
        txtDetail80.focusedProperty().addListener(txtDetail_Focus);
        txtDetail10.focusedProperty().addListener(txtDetailArea_Focus);
        
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField02.setOnKeyPressed(this::txtField_KeyPressed);
        txtField50.setOnKeyPressed(this::txtField_KeyPressed);
        txtField51.setOnKeyPressed(this::txtField_KeyPressed);
        txtField05.setOnKeyPressed(this::txtFieldArea_KeyPressed);
        
        txtDetail03.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail04.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail05.setOnKeyPressed(this::txtDetail_KeyPressed);
        txtDetail09.setOnKeyPressed(this::txtDetail_KeyPressed);    
        txtDetail80.setOnKeyPressed(this::txtDetail_KeyPressed);    
        txtDetail10.setOnKeyPressed(this::txtDetailArea_KeyPressed);
        
        pnEditMode = EditMode.UNKNOWN;
        
        clearFields();
        initGrid();
        initButton(pnEditMode);
        
        pbLoaded = true;
    }
    
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        if (event.getCode() == ENTER || event.getCode() == F3){
            switch (lnIndex){
                case 2: /*sInvTypeCd*/
                    if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if (!psInvType.equals(lsValue)){ 
                        psInvType = poTrans.SearchMaster(lnIndex, txtField.getText(), false);
                        txtField.setText(psInvType);
                    }
                    break;
                    
                case 50: /*sTransNox*/
                    if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                        if(poTrans.BrowseRecord(lsValue, true)==true){
                                loadRecord();
                                pnEditMode = poTrans.getEditMode();
                            }
                            if(!txtField50.getText().equals(psTransNox)){
                                clearFields();
                                pnEditMode = EditMode.UNKNOWN; break;
                                }else{
                                    txtField50.setText(psTransNox);
                                }   
                       return;
                     
                case 51: /*psDestina*/
                    if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                        if(poTrans.BrowseRecord(lsValue, false)== true){
                                loadRecord(); 
                                pnEditMode = poTrans.getEditMode();
                            }
                            if(!txtField51.getText().equals(psInvTypCd)){
                                clearFields();
                                pnEditMode = EditMode.UNKNOWN; break;
                                }else{
                                    txtField51.setText(psInvTypCd);
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
        String lsValue = txtDetail.getText();
        
        if (event.getCode() == ENTER || event.getCode() == F3){
            switch (lnIndex){
                case 3:
                    if (event.getCode() == F3) lsValue = txtDetail.getText() + "%";
                    
                    if (poTrans.SearchDetail(pnRow, 3, lsValue, false, false)){
                        txtDetail03.setText(poTrans.getDetailOthers(pnRow, "sBarCodex").toString());
                        txtDetail80.setText(poTrans.getDetailOthers(pnRow, "sDescript").toString());
                        txtDetail05.setText(poTrans.getDetail(pnRow, "nQtyOnHnd").toString());
                    } else {
                        txtDetail03.setText("");
                        txtDetail80.setText("");
                        txtDetail05.setText("0");
                    }
                    break;
                    
                case 80:
                    if (event.getCode() == F3) lsValue = txtDetail.getText() + "%";
                     if (poTrans.SearchDetail(pnRow, 3, lsValue, true, false)){
                        txtDetail03.setText(poTrans.getDetailOthers(pnRow, "sBarCodex").toString());
                        txtDetail80.setText(poTrans.getDetailOthers(pnRow, "sDescript").toString());
                        txtDetail05.setText(poTrans.getDetail(pnRow, "nQtyOnHnd").toString());
                    } else {
                        txtDetail03.setText("");
                        txtDetail80.setText("");
                        txtDetail05.setText("0");
                    }
                    
                    break;
                    
                case 4:
                    if (event.getCode() == F3) lsValue = txtDetail.getText() + "%";
                     if (poTrans.SearchDetail(pnRow, 4, lsValue, true, false)){
                        txtDetail.setText(poTrans.getDetailOthers(pnRow, "sLocatnNm").toString());
                     }else {
                        txtDetail.setText(""); }
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
    
    private void unloadForm(){
        VBox myBox = (VBox) VBoxForm.getParent();
        myBox.getChildren().clear();
    }
    
    private void loadRecord(){
        txtField01.setText((String) poTrans.getMaster("sTransNox"));
        txtField50.setText((String) poTrans.getMaster("sTransNox"));
        psTransNox = txtField50.getText();
        txtField03.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField02.setText(poTrans.SearchMaster(2, (String) poTrans.getMaster("sInvTypCd"), true));
        txtField51.setText(poTrans.SearchMaster(2, (String) poTrans.getMaster("sInvTypCd"), true));
        psInvTypCd = txtField51.getText();
        txtField05.setText((String) poTrans.getMaster("sRemarksx"));
        
        pnRow = 0;
        pnOldRow = 0;
        loadDetail();
        setTranStat((String) poTrans.getMaster("cTranStat"));
        
        psOldRec = txtField01.getText();
    }
    
    private void loadDetail(){
        int lnCtr;
        int lnRow = poTrans.ItemCount();
        
        data.clear();
        /*ADD THE DETAIL*/
        for(lnCtr = 0; lnCtr <= lnRow -1; lnCtr++){
            data.add(new TableModel(String.valueOf(lnCtr + 1), 
                                    poTrans.getDetailOthers(lnCtr, "sBarCodex").toString(), 
                                    poTrans.getDetailOthers(lnCtr, "sDescript").toString(),
                                    poTrans.getDetailOthers(lnCtr, "sLocatnNm").toString(),
                                    String.valueOf(poTrans.getDetail(lnCtr, "nQtyOnHnd")),
                                    String.valueOf(poTrans.getDetail(lnCtr, "nFinalCtr")),
                                    "",
                                    "",
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
    }
    
    private void setDetailInfo(){
        int lnRow = table.getSelectionModel().getSelectedIndex();
        
        pnRow = lnRow;
        
        if (pnRow >= 0){
            txtDetail05.setText(String.valueOf(poTrans.getDetail(pnRow, "sOrderNox")));
            txtDetail03.setText((String) poTrans.getDetailOthers(pnRow, "sBarCodex"));
            txtDetail80.setText((String) poTrans.getDetailOthers(pnRow, "sDescript"));
            txtDetail04.setText((String) poTrans.getDetailOthers(pnRow, "sLocatnNm"));
            txtDetail05.setText(String.valueOf(poTrans.getDetail(pnRow, "nQtyOnHnd")));
            txtDetail09.setText(String.valueOf(poTrans.getDetail(pnRow, "nFinalCtr")));
            txtDetail10.setText(String.valueOf(poTrans.getDetail(pnRow, "sRemarksx")));
        } else{
            txtDetail03.setText("");
            txtDetail04.setText("");
            txtDetail05.setText("0");
            txtDetail09.setText("0");
            txtDetail10.setText("");
            txtDetail80.setText("");
        }
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
    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId();
        
        switch (lsButton){
            case "btnNew":
                if (poTrans.newTransaction()){  
                    clearFields();
                    loadRecord();
                    txtField50.setText("");
                    pnEditMode = EditMode.ADDNEW;
                }  
                break;
                
            case "btnConfirm": 
                if (!psOldRec.equals("")){
                    if(!poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_OPEN)){
                        ShowMessageFX.Warning("Trasaction may be CANCELLED/POSTED.", pxeModuleName, "Can't update processed transactions!!!");
                        return;
                    }
                    
                    if( ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to confirm this transasction?")== true){
                        if (poTrans.postTransaction(psOldRec)){
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction CONFIRMED successfully.");
                        clearFields();
                        initGrid();
                        pnEditMode = EditMode.UNKNOWN;
                        initButton(pnEditMode);
                        }
                    }
                } else ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to confirm!");
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
                
            case "btnSearch":
                switch (pnIndex){
                case 2: /*sInvTypeCd*/
                        psInvType = poTrans.SearchMaster(pnIndex, "%", false);
                        txtField02.setText(psInvType);
                        break;
                case 90:
                    if (poTrans.SearchDetail(pnRow, 3, "%", false, false)){
                        txtDetail03.setText(poTrans.getDetailOthers(pnRow, "sBarCodex").toString());
                        txtDetail80.setText(poTrans.getDetailOthers(pnRow, "sDescript").toString());
                        txtDetail05.setText(poTrans.getDetail(pnRow, "nQtyOnHnd").toString());
                    } else {
                        txtDetail03.setText("");
                        txtDetail80.setText("");
                        txtDetail05.setText("");
                    }
                     break;
                case 91:
                    if (poTrans.SearchDetail(pnRow, 3, "%", true, false)){
                        txtDetail03.setText(poTrans.getDetailOthers(pnRow, "sBarCodex").toString());
                        txtDetail80.setText(poTrans.getDetailOthers(pnRow, "sDescript").toString());
                        txtDetail05.setText(poTrans.getDetail(pnRow, "nQtyOnHnd").toString());
                    } else {
                        txtDetail03.setText("");
                        txtDetail80.setText("");
                        txtDetail05.setText("");
                    }
                     break;
                case 92:
                     if (poTrans.SearchDetail(pnRow, 4, "%", false, false))
                        txtDetail04.setText(poTrans.getDetailOthers(pnRow, "sLocatnNm").toString());
                    else 
                        txtDetail04.setText("");
                    break;
                    }
                
                return;
                
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
                    }else
                        if(!txtField50.getText().equals(psTransNox)){
                        clearFields();
                        pnEditMode = EditMode.UNKNOWN;
                        break;
                        }else{
                            txtField50.setText(psTransNox);
                                 }
                        return;
                     
                    case 51: /*sDestination*/
                        if(poTrans.BrowseRecord(txtField51.getText() + "%", false)== true){
                            loadRecord(); 
                            pnEditMode = poTrans.getEditMode();
                            break;
                        }
                    
                        if(!txtField51.getText().equals(psInvTypCd)){
                            clearFields();
                            pnEditMode = EditMode.UNKNOWN;
                            break;
                        }else txtField51.setText(psInvTypCd);

                        return;
                    default:
                        ShowMessageFX.Warning("No Entry", pxeModuleName, "Please have at least one keyword to browse!");
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
    
    private void clearFields(){
        txtField01.setText("");
        txtField03.setText("");
        txtField05.setText("");
        txtField02.setText("");
        txtField50.setText("");
        txtField51.setText("");
        
        txtDetail03.setText("");
        txtDetail04.setText("");
        txtDetail05.setText("0");
        txtDetail10.setText("");
        txtDetail80.setText("");
        txtDetail09.setText("0");
        
        pnRow = -1;
        pnOldRow = -1;
        pnIndex = 51;
        setTranStat("-1");
        
        psOldRec = "";
        psOrderNox = "";
        psInvType = "";
        psInvTypCd = "";
        
        data.clear();
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
        btnConfirm.setVisible(!lbShow);
        btnClose.setVisible(!lbShow);
        
        txtField01.setDisable(!lbShow);
        txtField03.setDisable(!lbShow);
        txtField02.setDisable(!lbShow);
        txtField05.setDisable(!lbShow);
        
        txtDetail03.setDisable(!lbShow);
        txtDetail04.setDisable(!lbShow);
        txtDetail05.setDisable(!lbShow);
        txtDetail09.setDisable(!lbShow);
        txtDetail10.setDisable(!lbShow);
        txtDetail80.setDisable(!lbShow);
        
        if (lbShow)
            txtField03.requestFocus();
        else
            txtField51.requestFocus();
    }
    
    private void initGrid(){
        
        TableColumn index01 = new TableColumn("No.");
        TableColumn index02 = new TableColumn("Barcode.");
        TableColumn index03 = new TableColumn("Description");
        TableColumn index04 = new TableColumn("Location");
        TableColumn index05 = new TableColumn("On Hand");
        TableColumn index06 = new TableColumn("Count");
        
        index01.setPrefWidth(70); index01.setStyle("-fx-alignment: CENTER;");
        index02.setPrefWidth(204);
        index03.setPrefWidth(204);
        index04.setPrefWidth(100); index04.setStyle("-fx-alignment: CENTER;");
        index05.setPrefWidth(80); index05.setStyle("-fx-alignment: CENTER;");
        index06.setPrefWidth(80); index06.setStyle("-fx-alignment: CENTER;");
        
        index01.setSortable(false); index01.setResizable(false);
        index02.setSortable(false); index02.setResizable(false);
        index03.setSortable(false); index03.setResizable(false);
        index04.setSortable(false); index04.setResizable(false);
        index05.setSortable(false); index05.setResizable(false);
        index06.setSortable(false); index06.setResizable(false);
        
        table.getColumns().clear();        
        table.getColumns().add(index01);
        table.getColumns().add(index02);
        table.getColumns().add(index03);
        table.getColumns().add(index04);
        table.getColumns().add(index05);
        table.getColumns().add(index06);
        
        index01.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index04"));
        index05.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index05"));
        index06.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index06"));
        
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

    @FXML
    private void table_Clicked(MouseEvent event) {
        setDetailInfo(); 
        txtDetail03.requestFocus();
        txtDetail03.selectAll();
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    private final String pxeModuleName = "InvCountController";
    private static GRider poGRider;
    private InvCount poTrans;
    
    private String psInvType = "";
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    
    private final String pxeDateFormat = "yyyy-MM-dd";
    private final String pxeDateDefault = java.time.LocalDate.now().toString();
    
    private TableModel model;
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    
    private int pnIndex = -1;
    private int pnRow = -1;
    private int pnOldRow = -1;
    
    private String psOldRec = "";
    private String psOrderNox = "";
    private String psTransNox = "";
    private String psInvTypCd = "";
    
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
                case 2: /*sInvTypCd*/
                    if(txtField02.getText().equals("") || txtField02.getText().equals("%")){
                        txtField.setText("");
                        poTrans.setMaster(lnIndex, "");
                    }else
                    txtField.setText(psInvType); return;
                case 3: /*dTransact*/
                    if (CommonUtils.isDate(txtField.getText(), pxeDateFormat)){
                        poTrans.setMaster("dTransact", CommonUtils.toDate(txtField.getText()));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setMaster(lnIndex, CommonUtils.toDate(pxeDateDefault));
                    }
                    /*get the value from the class*/
                    txtField.setText(CommonUtils.xsDateLong((Date)poTrans.getMaster("dTransact")));
                    return;
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
                    txtDetail.setText((String) poTrans.getDetailOthers(pnRow, "sBarCodex"));
                    pnIndex = 90;
                    break;
                case 80:/*sDescript Search*/
                    txtDetail.setText((String) poTrans.getDetailOthers(pnRow, "sDescript"));
                    pnIndex = 91;
                    break;
                case 4:/*sLocatnCd*/
                    txtDetail.setText((String) poTrans.getDetailOthers(pnRow, "sLocatnNm"));
                    pnIndex = 92;
                    break;
                case 5:
                    /*This must be numeric*/
                    int y =0;
                    try{
                        y =Integer.parseInt(lsValue);
                    }catch (NumberFormatException e){
                        y = 0;
                    }
                    poTrans.setDetail(pnRow, "nQtyOnHnd", y);
                    txtDetail.setText(String.valueOf(poTrans.getDetail(pnRow,"nQtyOnHnd")));
                    break;
                case 9:
                    int x = 0;
                    try {
                        /*this must be numeric*/
                        x = Integer.parseInt(lsValue);
                    } catch (NumberFormatException e) {
                        x = 0;
                        txtDetail.setText("0");
                    }
                    poTrans.setDetail(pnRow, "nFinalCtr",  x);
                    txtDetail.setText(String.valueOf(poTrans.getDetail(pnRow,"nFinalCtr")));

                    if (!poTrans.getDetail(poTrans.ItemCount()- 1, "sStockIDx").toString().isEmpty() && 
                            (int) poTrans.getDetail(poTrans.ItemCount()- 1, lnIndex) > 0){
                        poTrans.addDetail();
                        pnRow = poTrans.ItemCount()-1;

                        //set the previous order numeber to the new ones.
                       // poTrans.setDetail(pnRow, "sOrderNox", psOrderNox);
                    }                            

                    loadDetail();
                    
                     if (!txtDetail03.getText().isEmpty()){
                        txtDetail09.requestFocus();
                        txtDetail09.selectAll();
                    } else{
                        txtDetail03.requestFocus();
                        txtDetail03.selectAll();
                    }
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
                case 10: /*sRemarksx*/
                    if (lsValue.length() > 256) lsValue = lsValue.substring(0, 256);
                    
                    poTrans.setDetail(pnRow, "sRemarksx", CommonUtils.TitleCase(lsValue));
                    txtDetail.setText((String)poTrans.getDetail(pnRow,"sRemarksx"));
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
    
}
