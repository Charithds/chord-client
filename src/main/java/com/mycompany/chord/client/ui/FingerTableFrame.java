/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.ui;

import com.mycompany.chord.client.model.Finger;
import com.mycompany.chord.client.service.ChordOperation;
import java.math.BigInteger;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class FingerTableFrame extends javax.swing.JFrame {

    /**
     * Creates new form FingerTableFrame
     */
    public FingerTableFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTopic = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFingers = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTopic.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTopic.setText("Finger Table of Node ID ");

        tblFingers.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Finger", "Successor", "IP Address", "Port"
            }
        ) {
            Class[] types = new Class [] {
                String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFingers);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTopic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
        );

        pack();
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
            java.util.logging.Logger.getLogger(FingerTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FingerTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FingerTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FingerTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FingerTableFrame().setVisible(true);
            }
        });
    }
    
    public void setData(long id, Map<Integer, Finger> fingerMap)
    {
        lblTopic.setText(lblTopic.getText() + id);
        
        DefaultTableModel model = (DefaultTableModel) tblFingers.getModel();
        
        BigInteger bigQuery = BigInteger.valueOf(2L);
        BigInteger bigSelfId = BigInteger.valueOf(id);
        /*
        for (int i = 0; i < 32; i++) {
            BigInteger bigResult = bigQuery.pow(i);
            bigResult = bigResult.add(bigSelfId);
            long idFinger = bigResult.longValue();
            if (idFinger >= ChordOperation.RING_SIZE) {
            idFinger -= ChordOperation.RING_SIZE;
            }
            String[] data = {idFinger + "", fingerMap.get(i).getId()+"", fingerMap.get(i).getAddress(), fingerMap.get(i).getPort()+""};
            model.addRow(data);
        }*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTopic;
    private javax.swing.JTable tblFingers;
    // End of variables declaration//GEN-END:variables
}
