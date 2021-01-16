
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
import org.rmj.cas.inventory.base.InvTransfer;
import org.rmj.cas.parameter.agent.XMBranch;

public class InvTransPostingController implements Initializable {

    @FXML private VBox VBoxForm;
    @FXML private Button btnExit;
    @FXML private FontAwesomeIconView glyphExit;
    @FXML private AnchorPane anchorField;
    @FXML private Label lblHeader;
    @FXML private TextField txtField01;
    @FXML private TextField txtField03;
    @FXML private TextField txtField04;
    @FXML private TextField txtField18;
    @FXML private TextField txtField06;
    @FXML private TextField txtField07;
    @FXML private TextField txtField13;
    @FXML private TextArea txtField05;
    @FXML private Label Label12;
    @FXML private TextField txtDetail05;
    @FXML private TextField txtDetail03;
    @FXML private TextField txtDetail80;
    @FXML private TextArea txtDetail10;
    @FXML private TextField txtDetail04;
    @FXML private TextField txtDetail07;
    @FXML private TextField txtDetail06;
    @FXML private TableView table;
    @FXML private ImageView imgTranStat;
    @FXML private TextField txtField50;
    @FXML private TextField txtField51;
    @FXML private Button btnPost;
    @FXML private Button btnClose;
    @FXML private Button btnBrowse;
    @FXML private TextField txtOther02;
    @FXML private TextField txtDetail08;
    @FXML private TextField txtField19;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        poTrans = new InvTransfer(poGRider, poGRider.getBranchCode(), false);
        poTrans.setTranStat(10);
        
        btnPost.setOnAction(this::cmdButton_Click);
        btnClose.setOnAction(this::cmdButton_Click);
        btnExit.setOnAction(this::cmdButton_Click);
        btnBrowse.setOnAction(this::cmdButton_Click);
        
        txtField50.focusedProperty().addListener(txtField_Focus);
        txtField51.focusedProperty().addListener(txtField_Focus);
        txtField19.focusedProperty().addListener(txtField_Focus);
        
        txtField50.setOnKeyPressed(this::txtField_KeyPressed);
        txtField51.setOnKeyPressed(this::txtField_KeyPressed);
        txtField19.setOnKeyPressed(this::txtField_KeyPressed);
        
        pnEditMode = EditMode.UNKNOWN;
        clearFields();
        initGrid();
        
        pbLoaded = true;
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
        txtField19.setEditable(false);
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
    
    private void cmdButton_Click(ActionEvent event) {
         String lsButton = ((Button)event.getSource()).getId();
        
        switch (lsButton){
                
            case "btnClose":
            case "btnExit": 
                unloadForm();
                return;
                
            case "btnBrowse":
                switch(pnIndex){
                   case 50: /*sTransNox*/
                    if(poTrans.BrowseAcceptance(txtField50.getText(), true)==true){
                        txtField19.setText(CommonUtils.xsDateMedium(poGRider.getServerDate()));
                        loadRecord(); 
                        pnEditMode = poTrans.getEditMode();
                        break;
                    }else
                        if(!txtField50.getText().equals(psTransNox)){
                            clearFields();
                            break;
                        }else{
                            txtField50.setText(psTransNox);
                        }
                    return;
                     
                case 51: /*sDestination*/
                    if(poTrans.BrowseAcceptance(txtField51.getText() + "%", false)== true){
                        txtField19.setText(CommonUtils.xsDateMedium(poGRider.getServerDate()));
                        loadRecord(); 
                        pnEditMode = poTrans.getEditMode();
                        break;
                    }if(!txtField51.getText().equals(psDestina)){
                        clearFields();
                        break;
                        }else{
                            txtField51.setText(psDestina);
                                 }
                    return;
                    
                    default:
                          ShowMessageFX.Warning("No Entry", pxeModuleName, "Please have at least one keyword to browse!");
                          txtField51.requestFocus();
                    }
                return;
                
            case "btnPost":
               if (!psOldRec.equals("")){
                     if(!poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_OPEN) && !poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_CLOSED)){
                            ShowMessageFX.Warning("Trasaction may be CANCELLED/POSTED.", pxeModuleName, "Can't update processed transactions!!!");
                            return;
                    }          
                    if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to post this transaction?")==true){
                        if (poTrans.postTransaction(psOldRec,txtField19.getText())){
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction POSTED successfully.");
                        clearFields();
                        initGrid();
                        pnEditMode = EditMode.UNKNOWN;
                        }
                    }
                    
                } else ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to post!");
                break;
                
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
                return;
        }
    }

    @FXML
    private void table_Clicked(MouseEvent event) {
        setDetailInfo();
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
        
        if (poTrans.getMaster("cTranStat").equals("1")){
            txtField19.setEditable(true);
            txtField19.requestFocus();
        }
        
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
            txtDetail08.setText(CommonUtils.xsDateLong((Date)poTrans.getDetail(pnRow, "dExpiryDt")));
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
            txtDetail08.setText("");
        }
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    private final String pxeModuleName = "InvTransferController";
    private static GRider poGRider;
    private InvTransfer poTrans;
    
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    
    private final String pxeDateFormat = "yyyy-MM-dd";
    private final String pxeDateDefault = "1900-01-01";
    
    private TableModel model;
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    
    private int pnIndex = -1;
    private int pnRow = -1;
    private int pnOldRow = -1;
    
    private String psDestina = "";
    private String psTransNox = "";
    private String psTrukNme = "";
    private String psOrderNm = "";
    
    private String psOldRec = "";
    private String psOrderNox = "";
    
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        if (event.getCode() == ENTER || event.getCode() == F3){
            switch (lnIndex){
                case 50: /*sTransNox*/
                    if(event.getCode() == F3) lsValue =  txtField.getText();
                    if(poTrans.BrowseAcceptance(lsValue, true)==true){
                       loadRecord(); 
                       pnEditMode = poTrans.getEditMode();
                       break;
                    }else
                        if(!txtField50.getText().equals(psTransNox)){
                            clearFields();
                            break;
                            }else{
                                txtField50.setText(psTransNox);
                                     }
                    return;
                     
                case 51: /*psDestina*/
                    if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                    if(poTrans.BrowseAcceptance(lsValue, false)== true){
                        loadRecord(); 
                        pnEditMode = poTrans.getEditMode();
                        break;
                    } else {
                        ShowMessageFX.Warning(poTrans.getMessage(), pxeModuleName, "Please inform MIS Department");
                    }
                    
                    if(!txtField51.getText().equals(psDestina)){
                        clearFields();
                        break;
                    }else{
                        txtField51.setText(psDestina);
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
                case 50: /*sTransNox*/
                    if(lsValue.equals("") || lsValue.equals("%"))
                        txtField.setText("");
                        break;
                case 51: /*sDestination*/
                   if(lsValue.equals("") || lsValue.equals("%"))
                        txtField.setText("");
                        break;
                case 19: /*dReceived*/
                    if (CommonUtils.isDate(txtField.getText(), pxeDateFormat)){
                        txtField.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(txtField.getText())));
                    }else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        txtField.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeDateDefault)));
                    }
                    return;
            }
            pnOldRow = table.getSelectionModel().getSelectedIndex();
            pnIndex= lnIndex;
        } else{
            switch (lnIndex){
                case 19: /*dReceived*/
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
    
}
