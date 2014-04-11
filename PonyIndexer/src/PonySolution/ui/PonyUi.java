/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonySolution.ui;

import PonyDB.Configuration;
import PonyDB.DBReader;
import PonyDB.DBWriter;
import PonyIndexer.IndexManager;
import PonyIndexer.IndexerListener;
import PonyIndexer.VocabularyInfoHolder;
import PonySearcher.OkapiRankingPolicy;
import PonySearcher.PageRankInfo;
import PonySearcher.Search;
import PonySearcher.VectorSpaceRankingPolicy;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Apostolidis
 */
public class PonyUi extends javax.swing.JFrame {
    
    private SelectDodumentPanel selectDodumentPanel;
    private IndexingStartPanel indexingStartPanel;
    private IndexingProcedurePanel indexingProcedurePanel;
    private LoadingVocabularPanel loadingVocabularPanel;
    private SearchPanel searchPanel;
    
    private String documentsPath;
    private String documentsIndexPath;
    private String stopWordsPath;
    
    private Thread indexingThread;
    private Thread loadingVocabularyThread;
    
    private byte uiState;
    private static final byte S_DOC_PANEL = 0;
    private static final byte S_INDEX_START = 1;
    private static final byte S_INDEX_PROC = 2;
    private static final byte S_LOAD_VOC = 3;
    private static final byte S_SEARCH = 4;
    
    private IndexerListener indexerListener;
    private Search search;
    /**
     * Creates new form PonyUi
     */
    public PonyUi() {
        initComponents();
        selectDodumentPanel = new SelectDodumentPanel(this);
        this.add(selectDodumentPanel);
        indexingStartPanel = new IndexingStartPanel(this);
        this.add(indexingStartPanel);
        indexingProcedurePanel = new IndexingProcedurePanel(this); 
        this.add(indexingProcedurePanel);
        loadingVocabularPanel = new LoadingVocabularPanel(this);
        this.add(loadingVocabularPanel);
        searchPanel = new SearchPanel(this);
        this.add(searchPanel);
        
        this.indexerListener = new IndexerListener(){

            @Override
            public void onChangeIndexingState(String state) {
                indexingProcedurePanel.setProcessStatusLabel(state);
            }

            @Override
            public void onNewIndexingMsg(String msg) {
                indexingProcedurePanel.setProcessStatusMsgLabel(msg);
            }

            @Override
            public void onPercentileLoad(double percent) {
                indexingProcedurePanel.setProcessBar((int)percent);
            }

        };
        
        changeUiState(S_DOC_PANEL);
    }
    
    private void hideAllComponents(){
        selectDodumentPanel.setVisible(false);
        indexingStartPanel.setVisible(false);
        indexingProcedurePanel.setVisible(false);
        loadingVocabularPanel.setVisible(false);
        searchPanel.setVisible(false);
    }
    
    private void changeUiState(byte newState){
        
        switch(newState){
            
            case S_DOC_PANEL:
                hideAllComponents();
                selectDodumentPanel.enablePanel();
                selectDodumentPanel.setVisible(true);
                break;
            case S_INDEX_START:
                selectDodumentPanel.enablePanel();
                indexingStartPanel.setVisible(true);
                break;
            case S_INDEX_PROC:
                indexingStartPanel.onStartIndexing();
                selectDodumentPanel.disablePanel();
                indexingProcedurePanel.setVisible(true);
                break;
            case S_LOAD_VOC:
                hideAllComponents();
                loadingVocabularPanel.startLoading();
                loadingVocabularPanel.setVisible(true);
                break;
            case S_SEARCH:
                hideAllComponents();
                searchPanel.setVisible(true);
                break;  
                
            default:
                assert(false);
        }
        uiState = newState;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pony");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(550, 50));
        jPanel1.setMinimumSize(new java.awt.Dimension(550, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(550, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/ponySmall.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(550, 50));
        jLabel1.setMinimumSize(new java.awt.Dimension(550, 50));
        jLabel1.setPreferredSize(new java.awt.Dimension(550, 50));
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1);

        setBounds(0, 0, 550, 706);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PonyUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PonyUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PonyUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PonyUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PonyUi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    protected void onDocumentFolderSelected(String absolutePath) {
        this.documentsPath = absolutePath;
        this.documentsIndexPath = documentsPath + "\\" + Configuration.INDEX_FOLDER_NAME;
        if(DBReader.indexFilesExist(documentsIndexPath)){
            
         loadingVocabularyThread = (new Thread() {
             @Override
             public void run() {           
                 try {
                    changeUiState(S_LOAD_VOC);
                    search = new Search(
                            documentsIndexPath, 
                            stopWordsPath,
                            new VectorSpaceRankingPolicy()
                        );
                    onLoadingSuccess();
                 } catch (FileNotFoundException ex) {
                     Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IOException ex) {
                     Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (ClassNotFoundException ex) {
                     Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 loadingVocabularPanel.stopLoading();
             }
         });
         loadingVocabularyThread.start();
        }else{
            changeUiState(S_INDEX_START);
        }
    }

    private void onLoadingSuccess() {
        changeUiState(S_SEARCH);
    } 

    private void onIndexingSuccess(){
         loadingVocabularyThread = (new Thread() {
             @Override
             public void run() {           
                 try {
                     changeUiState(S_LOAD_VOC);    
                     search = new Search(
                             VocabularyInfoHolder.getInstance(),
                             documentsIndexPath, 
                             stopWordsPath,
                             new VectorSpaceRankingPolicy()
                         );
                     onLoadingSuccess();
                 } catch (FileNotFoundException ex) {
                     Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IOException ex) {
                     Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (ClassNotFoundException ex) {
                     Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         });
         loadingVocabularyThread.start();
    }
    
   protected void onStartIndexingClicked() {

        indexingThread = (new Thread() {
            @Override
            public void run() {           
                try {
                    changeUiState(S_INDEX_PROC);
                    DBWriter.createFolder(documentsIndexPath);
                    IndexManager index = IndexManager.getInstance();
                    index.indexer(  
                                documentsPath,
                                stopWordsPath, 
                                documentsIndexPath,
                                indexerListener
                            );
                    onIndexingSuccess();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                    changeUiState(S_INDEX_START);
                } catch (IOException ex) {
                    Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
                    changeUiState(S_INDEX_START);
                }
                indexingStartPanel.onFinishIndexing();
                
            }
        });
        indexingThread.start(); 
        

    }

    protected PriorityQueue<PageRankInfo> onSearchTerms(String query) {
        PriorityQueue<PageRankInfo> pagesRankInfo = null;
        try {
            pagesRankInfo = search.retrieveAndRank(query);
        } catch (IOException ex) {
            Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PonyUi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pagesRankInfo;
    }

    void onStopWordsFolderSelected(String absolutePath) {
        stopWordsPath = absolutePath;
    }

    void changeRankingPolicy(String vectorSpace) {
        
        switch(vectorSpace){
            case "vectorSpace":
                search.setPageRankingPolicy(new VectorSpaceRankingPolicy());
                break;
            case "opaki":
                search.setPageRankingPolicy(new OkapiRankingPolicy());
                break;
            default:
                assert(false);
        }
        searchPanel.clearResultEntries();
        searchPanel.repaint();
        searchPanel.revalidate();
    }
}