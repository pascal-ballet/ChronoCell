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
        lblXMin = new javax.swing.JLabel();
        lblXMax = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
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

        lblXMin.setText("jLabel3");

        lblXMax.setText("jLabel4");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblXMin)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblXMax))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblYMax)
                        .addGap(178, 178, 178)
                        .addComponent(lblPhaseName, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 37, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(371, 371, 371))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhaseName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblYMax)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXMax)
                    .addComponent(lblXMin)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        jSlider1.setMaximum(f.theta[0].maxIndex-f.theta[0].minIndex-f.transitionProbabilities[0].values.length);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics gp1=jPanel1.getGraphics();
        FunctionStructure tempFunction1 =Operators.MultiplyFunctionRaw(f.oneMinusCumulativeFunctions[0],Operators.TranslateFunction(jSlider1.getValue()*f.theta[0].step,f.theta[0]));
        System.err.format("translation de: %f\n",jSlider1.getValue()*f.theta[0].step);
        double minVal1=0;
        double maxVal1=Math.max(1, Operators.GetFunctionMaxValue(tempFunction1));
        for (int i=tempFunction1.minIndex;i<=tempFunction1.maxIndex-1;i++){
            int absciss11=(int) Math.round((i-tempFunction1.minIndex)*jPanel1.getWidth()/(tempFunction1.maxIndex-tempFunction1.minIndex+1));
            int absciss12=(int) Math.round((i-tempFunction1.minIndex+1)*jPanel1.getWidth()/(tempFunction1.maxIndex-tempFunction1.minIndex+1));
            int ordinate11=(int) Math.round((jPanel1.getHeight()/(minVal1-maxVal1))*(tempFunction1.values[i]-maxVal1));
            int ordinate12=(int) Math.round(jPanel1.getHeight()/(minVal1-maxVal1)*(tempFunction1.values[i+1]-maxVal1));
            gp1.drawLine(absciss11,ordinate11, absciss12,ordinate12);
        }    
        
        Graphics gp2=jPanel2.getGraphics();
        FunctionStructure tempFunction2 =Operators.MultiplyFunctionRaw(f.oneMinusCumulativeFunctions[1],Operators.TranslateFunction(jSlider1.getValue()*f.theta[1].step,f.theta[1]));
        System.err.format("translation de: %f\n",jSlider1.getValue()*f.theta[1].step);
        double minVal2=0;
        double maxVal2=Operators.GetFunctionMaxValue(tempFunction2);
        for (int i=tempFunction2.minIndex;i<=tempFunction2.maxIndex-1;i++){
            int absciss1=(int) Math.round((i-tempFunction2.minIndex)*jPanel1.getWidth()/(tempFunction2.maxIndex-tempFunction2.minIndex+1));
            int absciss2=(int) Math.round((i-tempFunction2.minIndex+1)*jPanel1.getWidth()/(tempFunction2.maxIndex-tempFunction2.minIndex+1));
            int ordinate1=(int) Math.round((jPanel1.getHeight()/(minVal2-maxVal2))*(tempFunction2.values[i]-maxVal2));
            int ordinate2=(int) Math.round(jPanel1.getHeight()/(minVal2-maxVal2)*(tempFunction2.values[i+1]-maxVal2));
            gp2.drawLine(absciss1,ordinate1, absciss2,ordinate2);
        }
        lblXMin.setText((new Double(tempFunction1.min)).toString());
        lblXMax.setText((new Double(tempFunction1.max)).toString());
        lblYMax.setText((new Double(maxVal1)).toString());
        lblPhaseName.setText(f.phaseName[0]);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel lblPhaseName;
    private javax.swing.JLabel lblXMax;
    private javax.swing.JLabel lblXMin;
    private javax.swing.JLabel lblYMax;
    // End of variables declaration//GEN-END:variables
}
