/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PonySolution.ui;

import PonySearcher.PageRankInfo;
import java.awt.Dimension;
import java.util.PriorityQueue;

/**
 *
 * @author Apostolidis
 */
public class SearchPanel extends javax.swing.JPanel {
    private PonyUi ponyUi;
    
    private static final String NOT_FOUND_HD = 
            "Your search did not match any documents";
    
    private static final String NOT_FOUND_BODY = 
            "Suggestions:\n" +
            "<ul>\n" +
            "<li>Make sure all words are spelled correctly.</li>\n" +
            "<li>Try different keywords.</li>\n" +
            "<li>Try more general keywords.</li>\n" +
            "<li>Try fewer keywords.</li>"
            + "</ul>";
    /**
     * Creates new form SearchPanel
     */
    public SearchPanel(PonyUi ponyUi) {
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

        sp_searchPolicies = new javax.swing.ButtonGroup();
        sp_searchButton = new javax.swing.JButton();
        sp_searchInput = new javax.swing.JTextField();
        sp_searchEntriesWrapper = new javax.swing.JScrollPane();
        sp_searchEntries = new javax.swing.JPanel();
        sp_vector = new javax.swing.JRadioButton();
        sp_opaki = new javax.swing.JRadioButton();
        sp_searchPolicyLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(550, 32767));

        sp_searchButton.setText("search");
        sp_searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSearchButtonClicked(evt);
            }
        });

        sp_searchInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sp_sp_searchInputActionPerformed(evt);
            }
        });

        sp_searchEntries.setLayout(new javax.swing.BoxLayout(sp_searchEntries, javax.swing.BoxLayout.Y_AXIS));
        sp_searchEntriesWrapper.setViewportView(sp_searchEntries);

        sp_searchPolicies.add(sp_vector);
        sp_vector.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        sp_vector.setSelected(true);
        sp_vector.setText("vector space");
        sp_vector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onVectorSpacePolicySelected(evt);
            }
        });

        sp_searchPolicies.add(sp_opaki);
        sp_opaki.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        sp_opaki.setText("opaki");
        sp_opaki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onopakiPolicySelected(evt);
            }
        });

        sp_searchPolicyLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        sp_searchPolicyLabel.setText("Search policy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sp_searchEntriesWrapper)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sp_searchInput, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sp_searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sp_searchPolicyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sp_vector)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sp_opaki)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sp_searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(sp_searchInput))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sp_searchEntriesWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sp_searchPolicyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sp_vector)
                    .addComponent(sp_opaki))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sp_sp_searchInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sp_sp_searchInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sp_sp_searchInputActionPerformed

    private void onSearchButtonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSearchButtonClicked
        clearResultEntries();
        String query = sp_searchInput.getText();
        if(query!=null && !query.isEmpty()){
            PriorityQueue<PageRankInfo> pagesRankInfo = ponyUi.onSearchTerms(query);
            if(pagesRankInfo!=null && !pagesRankInfo.isEmpty()){
                while(!pagesRankInfo.isEmpty()){
                    PageRankInfo pageRankInfo = pagesRankInfo.poll();
                    addResultEntry(pageRankInfo);

                    System.out.println("====================");
                    System.out.println(pageRankInfo.getRank());
                    System.out.println(pageRankInfo.getSnippets());
                } 
                return;
            }
        }

        addResultNotFoundEntry(new PageRankInfo(NOT_FOUND_HD, NOT_FOUND_BODY));
    }//GEN-LAST:event_onSearchButtonClicked

    private void onVectorSpacePolicySelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onVectorSpacePolicySelected
        ponyUi.changeRankingPolicy("vectorSpace");
    }//GEN-LAST:event_onVectorSpacePolicySelected

    private void onopakiPolicySelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onopakiPolicySelected
        ponyUi.changeRankingPolicy("opaki");
    }//GEN-LAST:event_onopakiPolicySelected

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton sp_opaki;
    private javax.swing.JButton sp_searchButton;
    private javax.swing.JPanel sp_searchEntries;
    private javax.swing.JScrollPane sp_searchEntriesWrapper;
    private javax.swing.JTextField sp_searchInput;
    private javax.swing.ButtonGroup sp_searchPolicies;
    private javax.swing.JLabel sp_searchPolicyLabel;
    private javax.swing.JRadioButton sp_vector;
    // End of variables declaration//GEN-END:variables

    public void clearResultEntries() {
        sp_searchEntries.removeAll();
    }

    private void addResultEntry(PageRankInfo pageRankInfo) {
        SearchEntry searchEntry = new SearchEntry(pageRankInfo);
        sp_searchEntries.add(searchEntry, 0);
        sp_searchEntriesWrapper.repaint();
        sp_searchEntriesWrapper.revalidate();
        
    }

    private void addResultNotFoundEntry(PageRankInfo pageRankInfo) {
        SearchEntry searchEntry = new SearchEntry(pageRankInfo);
        Dimension size = searchEntry.getPreferredSize();
        size.setSize(size.getSize().width, 250);
        searchEntry.setSize(size);
        searchEntry.setMaximumSize( size );
        sp_searchEntries.add(searchEntry);
        sp_searchEntriesWrapper.repaint();
        sp_searchEntriesWrapper.revalidate();
    }

}
