/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ponycrawler.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import ponycrawler.core.CrawlerListener;
import ponycrawler.core.PageInfo;
import ponycrawler.core.PonyCrawler;

/**
 *
 * @author Apostolidis
 */
public class PonyCrawlerUi extends javax.swing.JFrame {

    private File OutputFile;
    private  boolean isFetching;
    private Thread fetchingThread;
    
    /**
     * Creates new form PonyCrawlerUi
     */
    public PonyCrawlerUi() {
        this.isFetching = false;
        this.OutputFile = new File("sites");
        initComponents();
        outputLabel.setText(this.OutputFile.getAbsolutePath());
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
        UrlLabel = new javax.swing.JLabel();
        UrlInput = new javax.swing.JTextField();
        outputLabel = new javax.swing.JLabel();
        outputButton = new javax.swing.JButton();
        DepthLabel = new javax.swing.JLabel();
        depthInput = new javax.swing.JSpinner();
        startButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        processView = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PonyCrawler");

        UrlLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        UrlLabel.setText("Url");

        UrlInput.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        UrlInput.setText("http://");
        UrlInput.setToolTipText("");
        UrlInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UrlInputActionPerformed(evt);
            }
        });

        outputLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N

        outputButton.setText("Output");
        outputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputButtonActionPerformed(evt);
            }
        });

        DepthLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        DepthLabel.setText("Depth level");

        depthInput.setValue(1);

        startButton.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DepthLabel)
                    .addComponent(UrlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(UrlInput)
                            .addComponent(outputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(depthInput, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UrlInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UrlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DepthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(depthInput)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        UrlInput.getAccessibleContext().setAccessibleName("");

        processView.setEditable(false);
        processView.setColumns(20);
        processView.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        processView.setRows(5);
        processView.setText("PonyCrawler is a web crawling tool.\n\n 1) The tool will download the page from the specific url\n 2) It will save it at the Output file\n 3) It will repeat this procedure openning the links of the \n    page as many times as the depth");
        jScrollPane1.setViewportView(processView);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void outputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(this.OutputFile.getAbsoluteFile());
        chooser.setDialogTitle("Selecter output folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
              this.OutputFile = chooser.getSelectedFile();
              outputLabel.setText(this.OutputFile.toString());
        }
    }//GEN-LAST:event_outputButtonActionPerformed

    
    private static String url;
    private static int depthValue;
        
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if(this.isFetching){
            fetchingThread.stop();
            onStopFetching();
        }else{
            url = UrlInput.getText();
            try {
                new URL(url); // check if it is valid url
                Object depthValueObj = depthInput.getValue();
                depthValue = Integer.parseInt(depthValueObj.toString());
                if(depthValue<=0)
                    throw new IllegalArgumentException();
                disableAllInputFields();
                processView.setText("");
                fetchingThread = (new Thread() {
                    @Override
                    public void run() {           
                        try {
                            isFetching = true;
                            PonyCrawler.crawl( url, depthValue, OutputFile.getAbsolutePath(), crawlerListener );
                        } catch (Exception ex) {
                            Logger.getLogger(PonyCrawlerUi.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        onStopFetching();
                    }
                });
                fetchingThread.start();
            } catch (MalformedURLException ex) {
                processView.setText("invalid url");
            } catch (IllegalArgumentException ex){ 
                processView.setText("invalid number");
           }  
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private CrawlerListener crawlerListener = new CrawlerListener() {

        @Override
        public void startFetching(final String url) {
            processView.insert("\nstart fetching: " + url + " ...\n", 0);
        }

        @Override
        public void pageFetched(final PageInfo pageinfo) {
            if(pageinfo==null){
                processView.insert( "cannot fetch page.\n", 0);
            }else{
                processView.insert( "fetched!\n", 0 );
                if( pageinfo.getLinks()!=null ){
                    processView.insert( pageinfo.getLinks().size() + " linkes found.\n", 0);
                }
            }
        }

        @Override
        public void pageSaved(final String path) {
            processView.insert( "page saved to " + path + "\n", 0);
        }
    };
    
    private void UrlInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UrlInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UrlInputActionPerformed

    private void onStopFetching(){
        isFetching = false;
        processView.insert( "\nfinish!\n", 0);
        enableAllInputFields();
    }
    
    private void disableAllInputFields(){
        UrlInput.setEnabled(false);
        depthInput.setEnabled(false);
        outputButton.setEnabled(false);
        startButton.setEnabled(true);
        startButton.setText("stop");
    }
    
    private void enableAllInputFields(){
        UrlInput.setEnabled(true);
        depthInput.setEnabled(true);
        outputButton.setEnabled(true);
        startButton.setEnabled(true);
        startButton.setText("start");
    }
    
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
            java.util.logging.Logger.getLogger(PonyCrawlerUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PonyCrawlerUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PonyCrawlerUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PonyCrawlerUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PonyCrawlerUi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DepthLabel;
    private javax.swing.JTextField UrlInput;
    private javax.swing.JLabel UrlLabel;
    private javax.swing.JSpinner depthInput;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton outputButton;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JTextArea processView;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
