package automata;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Kerry Powell
 */
public class Automata extends javax.swing.JFrame {

    private final WGraph graph = new WGraph();
    private NFAAutomata nfa;
    private DFAAutomata dfa;
    
    /**
     * Creates new form Automata
     */
    public Automata() {
        initComponents();
        getContentPane().setSize(600, 400);
        getContentPane().setMinimumSize(new Dimension(600, 400));
        
        graph.setPreferredSize(new Dimension(600, 400));
        graph.setMinimumSize(new Dimension(600, 400));
        getContentPane().add(graph, java.awt.BorderLayout.CENTER);
        pack();
    }
    
    private void enableNFAtoDFA() {
        disableNFAandDFA();
        NFAtoDFA.setEnabled(true);
        
    }
    
    private void enableDFAtoNFA() {
        disableNFAandDFA();
        DFAtoNFA.setEnabled(true);
    }
    
    private void disableNFAandDFA() {
        NFAtoDFA.setEnabled(false);
        DFAtoNFA.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        Open = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        NFAtoDFA = new javax.swing.JMenuItem();
        DFAtoNFA = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuFile.setText("File");

        Open.setText("Open NFA Automata");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });
        jMenuFile.add(Open);

        jMenuBar.add(jMenuFile);

        jMenuEdit.setText("Edit");

        NFAtoDFA.setText("Convert NFA to DFA");
        NFAtoDFA.setEnabled(false);
        NFAtoDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NFAtoDFAActionPerformed(evt);
            }
        });
        jMenuEdit.add(NFAtoDFA);

        DFAtoNFA.setText("Convert DFA to NFA");
        DFAtoNFA.setEnabled(false);
        DFAtoNFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DFAtoNFAActionPerformed(evt);
            }
        });
        jMenuEdit.add(DFAtoNFA);

        jMenuBar.add(jMenuEdit);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                nfa = new NFAAutomata(file);
                DFAtoNFAActionPerformed(null);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Automata.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_OpenActionPerformed

    private void NFAtoDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NFAtoDFAActionPerformed
        enableDFAtoNFA();
        if (dfa == null) {
            dfa = new DFAAutomata(nfa);
        }
        dfa.draw(graph);
        repaint();
    }//GEN-LAST:event_NFAtoDFAActionPerformed

    private void DFAtoNFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DFAtoNFAActionPerformed
        enableNFAtoDFA();
        nfa.draw(graph);
        repaint();
    }//GEN-LAST:event_DFAtoNFAActionPerformed

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
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Automata().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DFAtoNFA;
    private javax.swing.JMenuItem NFAtoDFA;
    private javax.swing.JMenuItem Open;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    // End of variables declaration//GEN-END:variables
}
