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
import org.rmj.cas.inventory.base.InvCount;

public class InvCountRegController implements Initializable {

    @FXML private VBox VBoxForm;
    @FXML private Button btnExit;
    @FXML private FontAwesomeIconView glyphExit;
    @FXML private AnchorPane anchorField;
    @FXML private TextField txtField01;
    @FXML private TextField txtField03;
    @FXML private TextField txtField02;
    @FXML private TextArea txtField05;
    @FXML private ImageView imgTranStat;
    @FXML private TextField txtDetail03;
    @FXML private TextField txtDetail80;
    @FXML private TextField txtDetail04;
    @FXML private TextArea txtDetail10;
    @FXML private TextField txtDetail05;
    @FXML private TextField txtDetail09;
    @FXML private Button btnVoid;
    @FXML private Button btnClose;
    @FXML private Button btnPrint;
    @FXML private Button btnBrowse;
    @FXML private TableView table;
    @FXML private TextField txtField51;
    @FXML private TextField txtField50;
    @FXML
    private TextField txtDetail11;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        poTrans = new InvCount(poGRider, poGRider.getBranchCode(), false);
        poTrans.setTranStat(1230);
        
        btnPrint.setOnAction(this::cmdButton_Click);
        btnVoid.setOnAction(this::cmdButton_Click);
        btnClose.setOnAction(this::cmdButton_Click);
        btnExit.setOnAction(this::cmdButton_Click);
        btnBrowse.setOnAction(this::cmdButton_Click);
        
        txtField50.focusedProperty().addListener(txtField_Focus);
        txtField51.focusedProperty().addListener(txtField_Focus);
        
        txtField50.setOnKeyPressed(this::txtField_KeyPressed);
        txtField51.setOnKeyPressed(this::txtField_KeyPressed);
        
        pnEditMode = EditMode.UNKNOWN;
        
        clearFields();
        initGrid();
        
        pbLoaded = true;
    }  
    
    private void loadRecord(){
        txtField01.setText((String) poTrans.getMaster("sTransNox"));
        txtField50.setText((String) poTrans.getMaster("sTransNox"));
        psTransNox = txtField50.getText();
        txtField03.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField02.setText(poTrans.SearchMaster(2, (String) poTrans.getMaster("sInvTypCd"), true));
        txtField51.setText(poTrans.SearchMaster(2, (String) poTrans.getMaster("sInvTypCd"), true));
        psInvType = txtField51.getText();
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
                                    String.valueOf(CommonUtils.xsDateShort((Date) poTrans.getDetail(lnCtr, "dExpiryDt"))),
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
           txtDetail03.setText((String) poTrans.getDetailOthers(pnRow, "sBarCodex"));
            txtDetail80.setText((String) poTrans.getDetailOthers(pnRow, "sDescript"));
            txtDetail04.setText((String) poTrans.getDetailOthers(pnRow, "sLocatnNm"));
            txtDetail05.setText(String.valueOf(poTrans.getDetail(pnRow, "nQtyOnHnd")));
            txtDetail09.setText(String.valueOf(poTrans.getDetail(pnRow, "nFinalCtr")));
            txtDetail10.setText(String.valueOf(poTrans.getDetail(pnRow, "sRemarksx")));
            txtDetail11.setText(CommonUtils.xsDateMedium((Date) poTrans.getDetail(pnRow, "dExpiryDt")));
        } else{
            txtDetail03.setText("");
            txtDetail04.setText("");
            txtDetail05.setText("0");
            txtDetail09.setText("0");
            txtDetail10.setText("");
            txtDetail80.setText("");
            txtDetail11.setText("");
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
                
            case "btnClose":
            case "btnExit": 
                unloadForm();
                return;
               
            case "btnPrint": 
               if(!psOldRec.equals("")){
                    if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to print this transaction?")==true){
                        //if (poTrans.printTransaction(psOldRec))
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction printed successfully.");
                        clearFields();
                        initGrid();
                        pnEditMode = EditMode.UNKNOWN;
                        break;
                    }else
                        return;
                }else 
                    ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to print!");
                break;
                
            case "btnBrowse":
                 switch(pnIndex){
                    case 50: /*sTransNox*/
                        if(poTrans.BrowseRecord(txtField50.getText()+"%", false)==true){
                            loadRecord(); 
                            pnEditMode = poTrans.getEditMode();
                            break;
                        }else
                            if(!txtField50.getText().equals(psTransNox)){
                                clearFields();
                                break;
                            }else txtField50.setText(psTransNox);
                            
                        return;
                case 51: /*sInvTypCd*/
                    if(poTrans.BrowseRecord(txtField51.getText() + "%", false)== true){
                        loadRecord(); 
                        pnEditMode = poTrans.getEditMode();
                        break;
                    }
                    if(!txtField51.getText().equals(psInvType)){
                                clearFields();
                                break;
                                }else{
                                    txtField51.setText(psInvType);
                                         }
                    return;
                    
                    default:
                          ShowMessageFX.Warning("No Entry", pxeModuleName, "Please have at least one keyword to browse!");
                          txtField51.requestFocus();
                    }
                 return;
                
            case "btnVoid":
               if (!psOldRec.equals("")){
                    if(!poTrans.getMaster("cTranStat").equals(TransactionStatus.STATE_OPEN)){
                        ShowMessageFX.Warning("Trasaction may be CANCELLED/POSTED.", pxeModuleName, "Can't update processed transactions!!!");
                        return;
                    }  
                    
                    if(ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to cancel this transaction?")==true){
                        if (poTrans.cancelTransaction(psOldRec))
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction CANCELLED successfully.");
                        clearFields();
                        initGrid();
                        pnEditMode = EditMode.UNKNOWN;
                        break;
                    }else
                        return;
                    
                } else 
                   ShowMessageFX.Warning(null, pxeModuleName, "Please select a record to cancel!");
                break;
                
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
                return;
        }
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
        pnIndex = -1;
        setTranStat("-1");
        
        psOldRec = "";
        psOrderNox = "";
        psInvType = "";
        psTransNox = "";
        
        data.clear();
    }
    
    private void unloadForm(){
        VBox myBox = (VBox) VBoxForm.getParent();
        myBox.getChildren().clear();
    }
    
    private void initGrid(){
        
        TableColumn index01 = new TableColumn("No.");
        TableColumn index02 = new TableColumn("Barcode.");
        TableColumn index03 = new TableColumn("Description");
        TableColumn index04 = new TableColumn("Location");
        TableColumn index05 = new TableColumn("On Hand");
        TableColumn index06 = new TableColumn("Count");
        TableColumn index07 = new TableColumn("Expiration");
        
        index01.setPrefWidth(50); index01.setStyle("-fx-alignment: CENTER;");
        index02.setPrefWidth(150);
        index03.setPrefWidth(180);
        index04.setPrefWidth(100); index04.setStyle("-fx-alignment: CENTER;");
        index05.setPrefWidth(80); index05.setStyle("-fx-alignment: CENTER;");
        index06.setPrefWidth(80); index06.setStyle("-fx-alignment: CENTER;");
        index07.setPrefWidth(98); index07.setStyle("-fx-alignment: CENTER;");
        
        index01.setSortable(false); index01.setResizable(false);
        index02.setSortable(false); index02.setResizable(false);
        index03.setSortable(false); index03.setResizable(false);
        index04.setSortable(false); index04.setResizable(false);
        index05.setSortable(false); index05.setResizable(false);
        index06.setSortable(false); index06.setResizable(false);
        index07.setSortable(false); index07.setResizable(false);
        
        table.getColumns().clear();        
        table.getColumns().add(index01);
        table.getColumns().add(index02);
        table.getColumns().add(index03);
        table.getColumns().add(index04);
        table.getColumns().add(index05);
        table.getColumns().add(index06);
        table.getColumns().add(index07);
        
        index01.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index04"));
        index05.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index05"));
        index06.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index06"));
        index07.setCellValueFactory(new PropertyValueFactory<org.rmj.cas.food.inventory.fx.views.TableModel,String>("index07"));
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
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    private final String pxeModuleName = "InvCountController";
    private static GRider poGRider;
    private InvCount poTrans;
    
    private String psInvType = "";
    private String psTransNox = "";
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    
    private final String pxeDateFormat = "yyyy-MM-dd";
    private final String pxeDateDefault = "1900-01-01";
    
    private TableModel model;
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    
    private int pnIndex = -1;
    private int pnRow = -1;
    private int pnOldRow = -1;
    
    private String psOldRec = "";
    private String psOrderNox = "";
    
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        if (event.getCode() == ENTER || event.getCode() == F3){
            switch (lnIndex){
                case 50: /*sTransNox*/
                     if(poTrans.BrowseRecord(lsValue, true)==true){
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
                     
                case 51: /*sInvTypCd*/
                     if(event.getCode() == F3) lsValue = txtField.getText() + "%";
                     if(poTrans.BrowseRecord(lsValue, false)== true){
                    loadRecord(); 
                    pnEditMode = poTrans.getEditMode();
                    break;
                }if(!txtField51.getText().equals(psInvType)){
                    clearFields();
                    break;
                    }else{
                        txtField51.setText(psInvType);
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
                    if(lsValue.equals("") || lsValue.equals("%")){
                       txtField.setText("");
                    }else
                    txtField.setText(psTransNox); break;
                case 51: /*sSupplierId*/
                    if(lsValue.equals("") || lsValue.equals("%")){
                       txtField.setText("");
                    }else
                    txtField.setText(psInvType); break;
                default:
                    ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
            }
            pnIndex = lnIndex;
        }
        
    };
    
}
