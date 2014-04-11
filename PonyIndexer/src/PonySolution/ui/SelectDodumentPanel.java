/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonySolution.ui;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Apostolidis
 */
public class SelectDodumentPanel extends javax.swing.JPanel {
    private PonyUi ponyUi;
    private boolean StopWordsEnable;
    /**
     * Creates new form SelectDodumentPanel
     */
    public SelectDodumentPanel(PonyUi ponyUi) {
        initComponents();
        this.ponyUi = ponyUi;
        hideStopWordsPanel();
        StopWordsEnable = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sd_selectFolderLabel = new javax.swing.JLabel();
        sd_folderPathLabel = new javax.swing.JLabel();
        sd_selectFolderButton = new javax.swing.JButton();
        sd_stopWordsPathLabel = new javax.swing.JLabel();
        sd_stopWordsEnable = new javax.swing.JCheckBox();
        sd_stopWordsChangeButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(550, 142));
        setPreferredSize(new java.awt.Dimension(550, 142));

        sd_selectFolderLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        sd_selectFolderLabel.setText("Select a folder of documents to search");

        sd_selectFolderButton.setText("select");
        sd_selectFolderButton.setMaximumSize(new java.awt.Dimension(99, 23));
        sd_selectFolderButton.setPreferredSize(new java.awt.Dimension(99, 23));
        sd_selectFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onFolderDocumentSelect(evt);
            }
        });

        sd_stopWordsPathLabel.setOpaque(true);

        sd_stopWordsEnable.setText("stopWords");
        sd_stopWordsEnable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sd_stopWordsEnableActionPerformed(evt);
            }
        });

        sd_stopWordsChangeButton.setText("change");
        sd_stopWordsChangeButton.setInheritsPopupMenu(true);
        sd_stopWordsChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sd_stopWordsChangeButtononFolderDocumentSelect(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sd_selectFolderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sd_stopWordsEnable)
                            .addComponent(sd_selectFolderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sd_folderPathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sd_stopWordsPathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sd_stopWordsChangeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(sd_selectFolderLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sd_stopWordsEnable)
                    .addComponent(sd_stopWordsPathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sd_stopWordsChangeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sd_selectFolderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sd_folderPathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onFolderDocumentSelect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onFolderDocumentSelect
        
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory( new File(".") );
        chooser.setDialogTitle("Select a folder to search");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
            File selectedFolder = chooser.getSelectedFile();
            sd_folderPathLabel.setText(selectedFolder.getAbsolutePath());
            ponyUi.onDocumentFolderSelected(selectedFolder.getAbsolutePath());
        }else{
            
        }
        
    }//GEN-LAST:event_onFolderDocumentSelect

    public void enablePanel(){
        sd_selectFolderButton.setEnabled(true);
        sd_stopWordsEnable.setEnabled(true);
    }
    
    public void disablePanel(){
        sd_selectFolderButton.setEnabled(false);
        sd_stopWordsEnable.setEnabled(false); 
    }
    
    private void showStopWordsPanel(){
        sd_stopWordsPathLabel.setEnabled(true);
        sd_stopWordsChangeButton.setEnabled(true);
        StopWordsEnable = true;
    }
    
    private void hideStopWordsPanel(){
        sd_stopWordsPathLabel.setEnabled(false);
        sd_stopWordsChangeButton.setEnabled(false);  
        StopWordsEnable = false;
    }
    
    private void selectStopWordsFolder(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory( new File(".") );
        chooser.setDialogTitle("Select a folder to search");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            sd_stopWordsPathLabel.setText(selectedFolder.getAbsolutePath());
            ponyUi.onStopWordsFolderSelected(selectedFolder.getAbsolutePath());
        }else{

        }
    }
    
    private void sd_stopWordsEnableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sd_stopWordsEnableActionPerformed
        if(StopWordsEnable){
            hideStopWordsPanel();
        }else{
            showStopWordsPanel();
            selectStopWordsFolder();
        }
        
        // or hide
    }//GEN-LAST:event_sd_stopWordsEnableActionPerformed

    private void sd_stopWordsChangeButtononFolderDocumentSelect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sd_stopWordsChangeButtononFolderDocumentSelect
        selectStopWordsFolder();
    }//GEN-LAST:event_sd_stopWordsChangeButtononFolderDocumentSelect

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel sd_folderPathLabel;
    private javax.swing.JButton sd_selectFolderButton;
    private javax.swing.JLabel sd_selectFolderLabel;
    private javax.swing.JButton sd_stopWordsChangeButton;
    private javax.swing.JCheckBox sd_stopWordsEnable;
    private javax.swing.JLabel sd_stopWordsPathLabel;
    // End of variables declaration//GEN-END:variables


}

