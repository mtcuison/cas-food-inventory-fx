package org.rmj.cas.food.inventory.fx.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.callback.IFXML;
//import org.rmj.appdriver.agentfx.service.ITokenize;
//import org.rmj.appdriver.agentfx.service.TokenApprovalFactory;
import org.rmj.appdriver.agentfx.ui.showFXDialog;
import org.rmj.appdriver.constants.UserRight;
import org.rmj.cas.food.reports.classes.FoodReports;
import org.rmj.cas.parameter.agent.XMCP_Financer;
import org.rmj.cas.parameter.base.CP_Financer;
import org.rmj.cas.parameter.fx.ParameterFX;
import org.rmj.cas.pos.reports.BIRReports;

public class MDIMainController implements Initializable {
    @FXML private VBox dataPane;
    @FXML private MenuItem mnuClose;
    @FXML private MenuItem mnuTerm;
    @FXML private MenuItem mnuInvType;
    @FXML private MenuItem mnuCompany;
    @FXML private MenuItem mnuInventory;
    @FXML private MenuItem mnuBrand;
    @FXML private MenuItem mnuModel;
    @FXML private MenuItem mnuColor;
    @FXML private MenuItem mnuCategory;
    @FXML private MenuItem mnuCategory2;
    @FXML private MenuItem mnuCategory3;
    @FXML private MenuItem mnuCategory4;
    @FXML private MenuItem mnuSupplier;
    @FXML private MenuItem mnuInvLocation;
    @FXML private Label lblUser;
    @FXML private Button btnExit;
    @FXML private Button btnMinimize;
    @FXML private MenuBar mnuBar;
    @FXML private Label lblDate;
    @FXML private Label lblCompany;
    @FXML private MenuItem mnuPOReceiving;
    @FXML private MenuItem mnuStocks;
    @FXML private CheckMenuItem chkLight;
    @FXML private MenuItem mnu_InventoryTransfer;
    @FXML private MenuItem mnu_inventoryCount;
    @FXML private MenuItem mnu_DailyProduction;
    @FXML private MenuItem mnu_POReceivingReg;
    @FXML private MenuItem mnu_InvTransReg;
    @FXML private MenuItem mnu_InvCountReg;
    @FXML private MenuItem mnu_InvDailyProdReg;
    @FXML private MenuItem menu_TransferPosting;
    @FXML private ToggleButton btnRestoreDown;
    @FXML private FontAwesomeIconView cmdRestore;
    @FXML private AnchorPane rootPane;
    @FXML private FontAwesomeIconView file;
    @FXML private FontAwesomeIconView transaction;
    @FXML private FontAwesomeIconView utilities;
    @FXML private FontAwesomeIconView reports;
    @FXML private FontAwesomeIconView history;
    @FXML private FontAwesomeIconView settings;
    @FXML private MenuItem mnuStandard;
    @FXML private MenuItem mnuMeasure;
    @FXML private MenuItem mnuWasteInventory;
    @FXML private MenuItem mnuPurchaseOrder;
    @FXML private MenuItem mnu_InvWasteReg;
    @FXML private MenuItem mnuPOReturn;
    @FXML private MenuItem mnu_PurchaseOrderReg;
    @FXML private MenuItem mnu_POReturnReg;
    @FXML private MenuItem mnuBIRrep;
    @FXML private Label lblFormTitle;
    @FXML private MenuItem mnuResetPOS;
    @FXML private MenuItem mnuDiscounts;
    @FXML private MenuItem mnuSerialUpload;
    @FXML private Menu mnuFiles;
    @FXML private Menu mnuTransactions;
    @FXML private Menu mnuUtilities;
    @FXML private Menu mnuReports;
    @FXML private Menu mnuHistory;
    @FXML private Menu mnuSettings;
    @FXML private AnchorPane mnuMain;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if ("IntegSys»Telecom".contains(poGRider.getProductID())){
            //for POS Back End use only
            if (!initMachine()){
                System.err.println("UNIDENTIFIED MACHINE DETECTED.");
                System.exit(1);
            }
            
            mnuStocks.setVisible(false);
            mnuCompany.setVisible(false);
            mnuTerm.setVisible(false);
            mnuSupplier.setVisible(false);
            
            mnuTransactions.setVisible(false);
            mnuUtilities.setVisible(false);
            mnuHistory.setVisible(false);
            mnuSettings.setVisible(false);
            mnuStandard.setVisible(false);
            
            mnuBIRrep.setVisible(true);
            mnuResetPOS.setVisible(false);
        } else{ //General
            mnuSerialUpload.setVisible(false);
            mnuBIRrep.setVisible(false); 
            mnuResetPOS.setVisible(false);
            
            mnu_DailyProduction.setVisible(poGRider.getBranchCode().contains("P"));
            mnu_inventoryCount.setVisible(poGRider.getBranchCode().contains("P"));
            menu_TransferPosting.setVisible(poGRider.getBranchCode().contains("P"));
            mnuWasteInventory.setVisible(poGRider.getBranchCode().contains("P"));

            mnu_InvDailyProdReg.setVisible(poGRider.getBranchCode().contains("P"));
            mnu_InvTransReg.setVisible(poGRider.getBranchCode().contains("P"));
            mnu_InvWasteReg.setVisible(poGRider.getBranchCode().contains("P"));
            mnu_InvCountReg.setVisible(poGRider.getBranchCode().contains("P"));
        } 

        if (!poGRider.getProductID().equalsIgnoreCase("general")){
            String lsTranMode = "";
        
            if (!System.getProperty("pos.clt.tran.mode").equalsIgnoreCase("a")) lsTranMode = " (Training Mode)";

            switch (poGRider.getClientID().substring(0, 3)){
                case "GTC":
                    lblFormTitle.setText(System.getProperty("app.product.id.telecom") + lsTranMode);
                    mnuSerialUpload.setVisible(true);
                    break;
                case "GGC":
                    lblFormTitle.setText(System.getProperty("app.product.id.integsys") + lsTranMode);
                    mnuSerialUpload.setVisible(false);
                    break;
            }
        } else 
            lblFormTitle.setText(System.getProperty("app.product.id.general"));
        
        
        btnExit.setTooltip(new Tooltip("Exit"));
        btnMinimize.setTooltip(new Tooltip("Minimize"));
        btnRestoreDown.setTooltip(new Tooltip("Restore down"));
        Tooltip.install(btnExit, new Tooltip("Exit"));
        Tooltip.install(btnMinimize, new Tooltip("Minimize"));
        Tooltip.install(btnRestoreDown, new Tooltip("Restore down"));
        getTime();
        lblCompany.setText(poGRider.getClientName());
        loadRecord();
        
        initXMFinancer();
    }
    
    public void loadRecord(){
        ResultSet name;
        String lsQuery = "SELECT a.sClientNm "+
                            " FROM Client_Master a" +
                            " LEFT JOIN xxxSysUser b" + 
                                " ON a.sClientID = b.sEmployNo" + 
                            " WHERE sUserIDxx = " + SQLUtil.toSQL(poGRider.getUserID());
        name = poGRider.executeQuery(lsQuery);
        try {
            if(name.next()){
                lblUser.setText(name.getString("sClientNm"));
                System.setProperty("user.name", name.getString("sClientNm"));
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(MDIMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mnuClose_Click(ActionEvent event) throws IOException {
        if (ShowMessageFX.YesNo(null, pxeModuleName, "Are you sure you want to logout?")) System.exit(0);
    }
    
    @FXML
    private void mnuTerm_Click(ActionEvent event) throws IOException {
        showParameter("Term");
    }
    
    @FXML
    private void mnuInvType_Click(ActionEvent event) throws IOException {
        if (poGRider.getUserLevel() != UserRight.ENGINEER){
            ShowMessageFX.Information("Only MIS Department can add/modify INVENTORY TYPES.", "Notice", "Please inform MIS Department.");
            return;
        }
        
        showParameter("InventoryType");
    }
    
    @FXML
    private void mnuCompany_Click(ActionEvent event) throws IOException {
        showParameter("Company");
    }
    
    @FXML
    private void mnuInventory_Click(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeInventory));
    }
    
    @FXML
    private void mnuInvLocation_Click(ActionEvent event) throws IOException {
        showParameter("InventoryLocation");
    }
    
    @FXML
    private void mnuBrand_Click(ActionEvent event) throws IOException {
        showParameter("Brand");
    }

    @FXML
    private void mnuModel_Click(ActionEvent event) throws IOException {
        showParameter("Model");
    }

    @FXML
    private void mnuColor_Click(ActionEvent event) throws IOException {
        showParameter("Color");
    }
    
    @FXML
    private void mnuCategory_Click(ActionEvent event) throws IOException {
        showParameter("Category");
    }
    
    @FXML
    private void mnuCategory2_Click(ActionEvent event) throws IOException {
        showParameter("Category2");
    }

    @FXML
    private void mnuCategory3_Click(ActionEvent event) throws IOException {
        showParameter("Category3");
    }

    @FXML
    private void mnuCategory4_Click(ActionEvent event) throws IOException {
        showParameter("Category4");
    }
    
    @FXML
    private void mnuSupplier_Click(ActionEvent event) throws IOException {
        showParameter("Supplier");
    }
    
    public void setDataPane(Node node) {      
        dataPane.getChildren().setAll(node);
    }
    
    public VBox fadeAnimate(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(url));
        
        Object fxObj = getContoller(url);
        
        fxmlLoader.setController(fxObj);
        
        VBox v = (VBox) fxmlLoader.load();
               
        MouseGestures mg = new MouseGestures();
        mg.makeDraggable(dataPane, v);

        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
        return v;
    }
    
    public Object getContoller(String fsValue){
        switch (fsValue){
            case FoodInventoryFX.pxePOReceiving:
                POReceivingController loPOReceivingObj = new POReceivingController();
                loPOReceivingObj.setGRider(poGRider);
                
                return loPOReceivingObj; 
                
            case FoodInventoryFX.pxeStocks:
                InvMasterController loStocksObj = new InvMasterController();
                loStocksObj.setGRider(poGRider);
                
                return loStocksObj;
                
             case FoodInventoryFX.pxeInvTransfer:
                InvTransferController loInvTransferObj = new InvTransferController();
                loInvTransferObj.setGRider(poGRider);
                
                return loInvTransferObj;
                
            case FoodInventoryFX.pxeInvCount:
                InvCountController loInvCountObj = new InvCountController();
                loInvCountObj.setGRider(poGRider);
                
                return loInvCountObj;
                
            case FoodInventoryFX.pxeDailyProd:
                DailyProductionController loDailyProdObj = new DailyProductionController();
                loDailyProdObj.setGRider(poGRider);
                
                return loDailyProdObj;
            case FoodInventoryFX.pxePurchaseOrder:
                PurchaseOrderController loPOObj = new PurchaseOrderController();
                loPOObj.setGRider(poGRider);
                
                return loPOObj;
            case FoodInventoryFX.pxePOReturn:
                POReturnController loPORet = new POReturnController();
                loPORet.setGRider(poGRider);
                
                return loPORet;
            case FoodInventoryFX.pxePOReceivingReg:
                POReceivingRegController loPOReceivingRegObj = new POReceivingRegController();
                loPOReceivingRegObj.setGRider(poGRider);
                
                return loPOReceivingRegObj;
                
            case FoodInventoryFX.pxeInvTransferReg:
                InvTransferRegController loInvTransferRegObj = new InvTransferRegController();
                loInvTransferRegObj.setGRider(poGRider);
                
                return loInvTransferRegObj;
                
            case FoodInventoryFX.pxeInvCountReg:
                InvCountRegController loInvCountRegObj = new InvCountRegController();
                loInvCountRegObj.setGRider(poGRider);
                
                return loInvCountRegObj;
                
            case FoodInventoryFX.pxeDailyProdReg:
                DailyProductionRegController loDailyProdRegObj = new DailyProductionRegController();
                loDailyProdRegObj.setGRider(poGRider);
                
                return loDailyProdRegObj;
                
            case FoodInventoryFX.pxeInvTransPosting:
                InvTransPostingController loInvTransPostingObj = new InvTransPostingController();
                loInvTransPostingObj.setGRider(poGRider);
                
                return loInvTransPostingObj;
            case FoodInventoryFX.pxeInvWaste:
                InvWasteController loInvWaste = new InvWasteController();
                loInvWaste.setGRider(poGRider);
                
                return loInvWaste;
            case FoodInventoryFX.pxeInvWasteReg:
                InvWasteRegController loInvWasteReg = new InvWasteRegController();
                loInvWasteReg.setGRider(poGRider);
                
                return loInvWasteReg;
            case FoodInventoryFX.pxePurchaseOrderReg:
                PurchaseOrderRegController loPOReg = new PurchaseOrderRegController();
                loPOReg.setGRider(poGRider);
                
                return loPOReg;
            case FoodInventoryFX.pxePOReturnReg:
                POReturnRegController loPORetReg = new POReturnRegController();
                
                loPORetReg.setGRider(poGRider);
                
                return loPORetReg;
                
            case FoodInventoryFX.pxeInventory:
                InventoryController loInv = new InventoryController();
                loInv.setGRider(poGRider);
                return loInv;
                
            case FoodInventoryFX.pxeSerialUpload:
                SerialUploadController loUpload = new SerialUploadController();
                loUpload.setGRider(poGRider);
                return loUpload;
            default:
                return null;
        }
    }


    @FXML
    private void btnExit_Clicke(ActionEvent event) {
       if (ShowMessageFX.OkayCancel(null, "Confirm", "Do you want to exit?") == true)
                CommonUtils.closeStage(btnExit);
    }

    @FXML
    private void btnMinimize_Click(ActionEvent event) {
        CommonUtils.minimizeStage(btnMinimize);
    }

    @FXML
    private void mnuPOReceiving(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxePOReceiving));
    }
    
    @FXML
    private void mnuStocks(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeStocks));
    }


    @FXML
    private void chkLight_Click(ActionEvent event) {
        changeTheme();
    }

    @FXML
    private void mnu_InventoryTransferClick(ActionEvent event) throws IOException{
         setDataPane(fadeAnimate(FoodInventoryFX.pxeInvTransfer));
    }

    @FXML
    private void mnu_inventoryCountClick(ActionEvent event) throws IOException {
         setDataPane(fadeAnimate(FoodInventoryFX.pxeInvCount));
    }

    @FXML
    private void mnu_DailyProductionClick(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeDailyProd));
    }

    @FXML
    private void mnu_POReceivingRegClick(ActionEvent event)throws IOException {
         setDataPane(fadeAnimate(FoodInventoryFX.pxePOReceivingReg));
    }

    @FXML
    private void mnu_InvTransRegClick(ActionEvent event)throws IOException {
         setDataPane(fadeAnimate(FoodInventoryFX.pxeInvTransferReg));
    }

    @FXML
    private void mnu_InvCountRegClick(ActionEvent event)throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeInvCountReg));
    }

    @FXML
    private void mnu_InvDailyProdRegClick(ActionEvent event)throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeDailyProdReg));
    }

    @FXML
    private void menu_TransferPostingClick(ActionEvent event)throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeInvTransPosting));
    }

    @FXML
    private void btnRestoreDown_Clicke(ActionEvent event) {
        Stage stage=(Stage) dataPane.getScene().getWindow();
        if (btnRestoreDown.isSelected()) {
            cmdRestore.setGlyphName("EXPAND");
            btnRestoreDown.setTooltip(new Tooltip("Maximize"));
            Tooltip.install(btnRestoreDown, new Tooltip("Maximize"));
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setY(bounds.getMinY());
            stage.setWidth(1100);
            stage.setHeight(bounds.getHeight());
            stage.centerOnScreen();
        } else {
            cmdRestore.setGlyphName("COMPRESS");
            btnRestoreDown.setTooltip(new Tooltip("RestoreDown"));
            Tooltip.install(btnRestoreDown, new Tooltip("RestoreDown"));
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setY(bounds.getMinY());
            stage.setX(bounds.getMinX());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
        }
    }

    @FXML
    private void mnuStandard_Click(ActionEvent event) {
        FoodReports loReports = new FoodReports();
        loReports.setGRider(poGRider);
        
        try {
            CommonUtils.showModal(loReports);
        } catch (Exception ex) {
            Logger.getLogger(MDIMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loReports = null;
    }

    @FXML
    private void mnuMeasure_Click(ActionEvent event) throws IOException {
        showParameter("Measure");
    }

    @FXML
    private void mnuWasteInventory(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeInvWaste));
    }

    @FXML
    private void mnuPurchaseOrder(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxePurchaseOrder));
    }

    @FXML
    private void mnu_InvWasteReg(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeInvWasteReg));
    }

    @FXML
    private void mnuPOReturn(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxePOReturn));
    }

    @FXML
    private void mnu_PurchaseOrderReg(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxePurchaseOrderReg));
    }
    

    @FXML
    private void mnu_POReturnReg(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxePOReturnReg));
    }

    @FXML
    private void mnuBIRrep_Click(ActionEvent event) {
        BIRReports instance = new BIRReports();
        instance.setGRider(poGRider);
        
        if (instance.getParam()){
            if (!instance.processReport()){
                ShowMessageFX.Warning(instance.getMessage(), "Warning", "Unable to generate report.");
            }
        } else 
            ShowMessageFX.Information(instance.getMessage(), "Notice", "Report generation cancelled.");
    }

    @FXML
    private void mnuResetPOS_Click(ActionEvent event) {
        if (showFXDialog.resetPOS(poGRider)){
            ShowMessageFX.Information(null, "Success", "POS was successfully reset.");
        }
    }

    @FXML
    private void mnuDiscounts_Click(ActionEvent event) {
        showParameter("PromoDiscount");
    }

    @FXML
    private void mnuSerialUpload_Click(ActionEvent event) throws IOException {
        setDataPane(fadeAnimate(FoodInventoryFX.pxeSerialUpload));
    }

    @FXML
    private void mnuMain_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.F12){
            if (showFXDialog.resetPOS(poGRider)){
                ShowMessageFX.Information(null, "Success", "POS was successfully reset.");
            }
        } else if (event.getCode() == KeyCode.F10){     
//            ITokenize instance = TokenApprovalFactory.make("CASys_DBF.PO_Master");
//            instance.setGRider(poGRider);
//            instance.setTransNmbr("M00120000001");
//            if (instance.createCodeRequest()){
//                System.out.println(instance.getMessage());
//            } else {
//                System.err.println(instance.getMessage());
//            }
        } else if (event.getCode() == KeyCode.F11){
//            if (showFXDialog.getTokenApproval(poGRider, "CASys_DBF.PO_Master", "M00120000001")){
//                //TODO:
//                //  execute approving of transaction here
//                ShowMessageFX.Information(null, "Success", "Transaction was approved successfully..");
//            }
        }     
    }
       
    public static class MouseGestures {
        class DragContext {
            double x;
            double y;
        }

        DragContext dragContext = new DragContext();

        public void makeDraggable(VBox pane, VBox node) {
            node.setOnMousePressed(onMousePressedEventHandler);
            node.setOnMouseDragged(onMouseDraggedEventHandler);
            
            double centerXPosition = (pane.getWidth() - node.getPrefWidth())/2;
            double centerYPosition = (pane.getHeight() - node.getPrefHeight())/2;

            node.setTranslateX(centerXPosition);
            node.setTranslateY(centerYPosition);
        }

        EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
            Node node = ((Node) (event.getSource()));
            
            dragContext.x = node.getTranslateX() - event.getSceneX();
            dragContext.y = node.getTranslateY() - event.getSceneY();
        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
            Node node = ((Node) (event.getSource()));
            
            node.setTranslateX( dragContext.x + event.getSceneX());            
            if (dragContext.y + event.getSceneY() > 0)
                node.setTranslateY(dragContext.y + event.getSceneY());
        };
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    private final String pxeModuleName = "MDIMainController";
    private static GRider poGRider;
    
    public boolean changeTheme(){
        if(chkLight.isSelected()){
            rootPane.setStyle("-fx-background-color: white");
            mnuBar.setStyle("-fx-background-color: #F37024");
        }else{
            rootPane.setStyle("-fx-background-color: #4d5656");
            mnuBar.setStyle("-fx-background-color: none");
        }
        return false;
    }
    
    private void getTime(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
        Calendar cal = Calendar.getInstance();
        int second = cal.get(Calendar.SECOND);        
        String temp = "" + second;
        
        Date date = new Date();
        String strTimeFormat = "hh:mm:";
        String strDateFormat = "MMMM dd, yyyy";
        String secondFormat = "ss";
        
        DateFormat timeFormat = new SimpleDateFormat(strTimeFormat + secondFormat);
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        
        String formattedTime= timeFormat.format(date);
        String formattedDate= dateFormat.format(date);
        
        lblDate.setText(formattedDate+ " || " + formattedTime);
        
        }),
         new KeyFrame(Duration.seconds(1))
        );
        
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    private boolean initMachine(){
        if ("telecom»integsys".contains(poGRider.getProductID().toLowerCase())){
            try {
                String lsMIN = System.getProperty("pos.clt.crm.no");
                
                if (lsMIN.isEmpty()){
                    System.err.println("Invalid Machine Identification Info Detected...");
                    return false;
                }

                String lsSQL = "SELECT" +
                                    "  a.sAccredtn" +
                                    ", a.sPermitNo" +
                                    ", a.sSerialNo" +
                                    ", a.nPOSNumbr" +
                                    ", a.nZReadCtr" +
                                    ", IFNULL(b.sWebSrver, '') sWebSrver" +
                                    ", IFNULL(b.sPrinter1, '') sPrinter1" +
                                    ", a.cTranMode" +
                                " FROM Cash_Reg_Machine a" +
                                    " LEFT JOIN Cash_Reg_Machine_Printer b" +
                                        " ON a.sIDNumber = b.sIDNumber" +
                                " WHERE a.sIDNumber = " + SQLUtil.toSQL(lsMIN);

                ResultSet loRS = poGRider.executeQuery(lsSQL);
                long lnRow = MiscUtil.RecordCount(loRS);

                if (lnRow != 1){
                    System.err.println("Invalid Config for MIN Detected..");
                    return false;
                }

                loRS.first();
                System.setProperty("pos.clt.accrd.no", loRS.getString("sAccredtn"));
                System.setProperty("pos.clt.prmit.no", loRS.getString("sPermitNo"));
                System.setProperty("pos.clt.srial.no", loRS.getString("sSerialNo"));
                System.setProperty("pos.clt.trmnl.no", loRS.getString("nPOSNumbr"));
                System.setProperty("pos.clt.zcounter", String.valueOf(loRS.getInt("nZReadCtr")));

                System.setProperty("pos.clt.web.svrx", loRS.getString("sWebSrver"));
                System.setProperty("pos.clt.prntr.01", loRS.getString("sPrinter1"));
                
                System.setProperty("pos.clt.tran.mode", loRS.getString("cTranMode"));
                
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.getMessage());
            }
            return false;
        } else 
            return true;
    }   
    
    private void showParameter(String fsFormName){
        try {
            IFXML fxObj = getController(fsFormName);
        
            ParameterFX instance = new ParameterFX();
            instance.setFXMLForm(fsFormName + ".fxml");
            instance.setFXController(fxObj);
            
            CommonUtils.showModal(instance);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    private void initXMFinancer(){
        XMCP_Financer poTrans;
        poTrans = new XMCP_Financer(poGRider, poGRider.getBranchCode(), false);
//        
//        poTrans.browseRecord("", false);
        
//        System.err.println(poTrans.getMaster("sFnancrID"));
//        System.err.println(poTrans.getMaster("nDiscount"));
//        System.err.println(poTrans.getMaster("nCredLimt"));
//        System.err.println(poTrans.getMaster("nABalance"));
//        System.err.println(poTrans.SearchMaster(7, (String)poTrans.getMaster("sTermIDxx"), true));
//        System.err.println(poTrans.getTerm());
//        System.err.println(SQLUtil.FORMAT_SHORT_DATEX);
    }
    
    
    
    private IFXML getController(String fsValue){                
        IFXML instance;
        
        switch (fsValue){
            case "Brand":
                instance = new org.rmj.cas.parameter.fx.BrandController();
                instance.setGRider(poGRider);
                return instance;                
            case "InventoryType":
                instance = new org.rmj.cas.parameter.fx.InventoryTypeController();
                instance.setGRider(poGRider);
                return instance;      
            case "Category":
                instance = new org.rmj.cas.parameter.fx.CategoryController();
                instance.setGRider(poGRider);
                return instance;          
            case "Category2":
                instance = new org.rmj.cas.parameter.fx.Category2Controller();
                instance.setGRider(poGRider);
                return instance;          
            case "Category3":
                instance = new org.rmj.cas.parameter.fx.Category3Controller();
                instance.setGRider(poGRider);
                return instance;    
            case "Category4":
                instance = new org.rmj.cas.parameter.fx.Category4Controller();
                instance.setGRider(poGRider);
                return instance;    
            case "PromoDiscount":
                instance = new org.rmj.cas.parameter.fx.PromoDiscountController();
                instance.setGRider(poGRider);
                return instance;    
            case "Model":
                instance = new org.rmj.cas.parameter.fx.ModelController();
                instance.setGRider(poGRider);
                return instance;   
            case "Color":
                instance = new org.rmj.cas.parameter.fx.ColorController();
                instance.setGRider(poGRider);
                return instance;  
            case "Company":
                instance = new org.rmj.cas.parameter.fx.CompanyController();
                instance.setGRider(poGRider);
                return instance;  
            case "Measure":
                instance = new org.rmj.cas.parameter.fx.MeasureController();
                instance.setGRider(poGRider);
                return instance;      
            case "Supplier":
                instance = new org.rmj.cas.parameter.fx.SupplierController();
                instance.setGRider(poGRider);
                return instance;      
            case "Term":
                instance = new org.rmj.cas.parameter.fx.TermController();
                instance.setGRider(poGRider);
                return instance;      
            case "InventoryLocation":
                instance = new org.rmj.cas.parameter.fx.InventoryLocationController();
                instance.setGRider(poGRider);
                return instance;
            default: 
                return null;
        }
    }
}
