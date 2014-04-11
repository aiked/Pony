/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonySolution.ui;

/**
 *
 * @author Apostolidis
 */
public class IndexingStartPanel extends javax.swing.JPanel {
    private PonyUi ponyUi;
    /**
     * Creates new form IndexingStartPanel
     */
    public IndexingStartPanel(PonyUi ponyUi) {
        initComponents();
        this.ponyUi = ponyUi;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        is_notFoundLabel = new javax.swing.JLabel();
        is_msgLabel = new javax.swing.JLabel();
        is_startIndexingButton = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setMaximumSize(new java.awt.Dimension(32767, 135));
        setPreferredSize(new java.awt.Dimension(550, 135));

        is_notFoundLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        is_notFoundLabel.setText("No indexing folder found");

        is_msgLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        is_msgLabel.setText("This procedure will take several seconds");

        is_startIndexingButton.setText("start indexing");
        is_startIndexingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onStartIndexingClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(is_notFoundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(is_startIndexingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(is_msgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(is_notFoundLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(is_startIndexingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(is_msgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onStartIndexingClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onStartIndexingClicked
        ponyUi.onStartIndexingClicked();
    }//GEN-LAST:event_onStartIndexingClicked

    public void onStartIndexing(){
        is_startIndexingButton.setEnabled(false);
    }
    
    public void onFinishIndexing(){
        is_startIndexingButton.setEnabled(true);
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel is_msgLabel;
    private javax.swing.JLabel is_notFoundLabel;
    private javax.swing.JButton is_startIndexingButton;
    private javax.swing.JComboBox jComboBox1;
    // End of variables declaration//GEN-END:variables
}