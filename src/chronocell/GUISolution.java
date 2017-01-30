/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import java.awt.Graphics;

/**
 *
 * @author goby
 */
public class GUISolution extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUISolution() {
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

        jPanel1 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        lblYMax = new javax.swing.JLabel();
        lblPhaseName = new javax.swing.JLabel();
        lblYMin = new javax.swing.JLabel();
        lblXMin = new javax.swing.JLabel();
        lblXMax = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
        );

        jSlider1.setMaximum(1000);
        jSlider1.setValue(0);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        lblYMax.setText("jLabel1");

        lblPhaseName.setText("jLabel5");

        lblYMin.setText("jLabel2");

        lblXMin.setText("jLabel3");

        lblXMax.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblYMin)
                    .addComponent(lblYMax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblXMin)
                        .addGap(421, 421, 421)
                        .addComponent(lblXMax))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(813, 813, 813))
            .addGroup(layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(lblPhaseName, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPhaseName, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblYMax)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblYMin))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblXMin)
                    .addComponent(lblXMax))
                .addGap(18, 18, 18)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        // TODO add your handling code here:
        repaint();
    }//GEN-LAST:event_jSlider1StateChanged

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
            java.util.logging.Logger.getLogger(GUISolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUISolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUISolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUISolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUISolution().setVisible(true);
            }
        });
    }
    SolutionStructure f;
    public void SetFunction(SolutionStructure sol){
        f=sol;
        jSlider1.setMaximum(f.theta[0].values.length-f.transitionProbabilities[0].values.length);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics gp=jPanel1.getGraphics();        
        FunctionStructure tempFunction =Operators.MultiplyFunctionRaw(Operators.AffineFunctionTransformation(-1.0, 1.0, f.cumulativeFunctions[0]),f.theta[0]);
//        FunctionStructure tempFunction =Operators.MultiplyFunctionRaw(Operators.AffineFunctionTransformation(-1.0, 1.0, f.cumulativeFunctions[0]),Operators.TranslateFunction(jSlider1.getValue()*f.theta[0].step,f.theta[0]));
        Operators.PrintFunction(tempFunction);
//        double minVal=Operators.GetFunctionMinValue(tempFunction);
        double minVal=0;
        double maxVal=Operators.GetFunctionMaxValue(tempFunction);
        for (int i=tempFunction.minIndex;i<=tempFunction.maxIndex-1;i++){
            int absciss1=(int) Math.round((i-tempFunction.minIndex)*jPanel1.getWidth()/(tempFunction.maxIndex-tempFunction.minIndex+1));
            int absciss2=(int) Math.round((i-tempFunction.minIndex+1)*jPanel1.getWidth()/(tempFunction.maxIndex-tempFunction.minIndex+1));
            int ordinate1=(int) Math.round((jPanel1.getHeight()/(minVal-maxVal))*(tempFunction.values[i]-maxVal));
            int ordinate2=(int) Math.round(jPanel1.getHeight()/(minVal-maxVal)*(tempFunction.values[i+1]-maxVal));
            gp.drawLine(absciss1,ordinate1, absciss2,ordinate2);
        }
        lblXMin.setText((new Double(tempFunction.min)).toString());
        lblXMax.setText((new Double(tempFunction.max)).toString());
        lblYMin.setText((new Double(minVal)).toString());
        lblYMax.setText((new Double(maxVal)).toString());
        lblPhaseName.setText(f.phaseName[0]);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel lblPhaseName;
    private javax.swing.JLabel lblXMax;
    private javax.swing.JLabel lblXMin;
    private javax.swing.JLabel lblYMax;
    private javax.swing.JLabel lblYMin;
    // End of variables declaration//GEN-END:variables
}