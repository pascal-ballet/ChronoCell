/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author goby
 */
public class GUISimulation extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUISimulation() {
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

        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblXMin1 = new javax.swing.JLabel();
        lblXMax1 = new javax.swing.JLabel();
        lblYMax1 = new javax.swing.JLabel();
        lblPhaseName1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblXMin2 = new javax.swing.JLabel();
        lblXMax2 = new javax.swing.JLabel();
        lblYMax2 = new javax.swing.JLabel();
        lblPhaseName2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblXMin3 = new javax.swing.JLabel();
        lblXMax3 = new javax.swing.JLabel();
        lblYMax3 = new javax.swing.JLabel();
        lblPhaseName3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblXMin4 = new javax.swing.JLabel();
        lblXMax4 = new javax.swing.JLabel();
        lblYMax4 = new javax.swing.JLabel();
        lblPhaseName4 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));

        jPanel5.setLayout(new java.awt.GridLayout(2, 2, 4, 4));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel1.setForeground(new java.awt.Color(153, 153, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 100));

        lblXMin1.setText("X min");
        lblXMin1.setToolTipText("X min");

        lblXMax1.setText("X MAX");
        lblXMax1.setToolTipText("X MAX");

        lblYMax1.setText("Y MAX");
        lblYMax1.setToolTipText("Y MAX");

        lblPhaseName1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPhaseName1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPhaseName1.setText("PHASE");
        lblPhaseName1.setToolTipText("PHASE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblYMax1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(lblPhaseName1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblXMin1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblXMax1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYMax1)
                    .addComponent(lblPhaseName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXMin1)
                    .addComponent(lblXMax1)))
        );

        jPanel5.add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 100));

        lblXMin2.setText("X min");
        lblXMin2.setToolTipText("X min");

        lblXMax2.setText("X MAX");
        lblXMax2.setToolTipText("X MAX");

        lblYMax2.setText("Y MAX");
        lblYMax2.setToolTipText("Y MAX");

        lblPhaseName2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPhaseName2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPhaseName2.setText("PHASE");
        lblPhaseName2.setToolTipText("PHASE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblXMin2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblXMax2))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblYMax2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                .addComponent(lblPhaseName2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYMax2)
                    .addComponent(lblPhaseName2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXMin2)
                    .addComponent(lblXMax2)))
        );

        jPanel5.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 100));

        lblXMin3.setText("X min");
        lblXMin3.setToolTipText("X min");

        lblXMax3.setText("X MAX");
        lblXMax3.setToolTipText("X MAX");

        lblYMax3.setText("Y MAX");
        lblYMax3.setToolTipText("Y MAX");

        lblPhaseName3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPhaseName3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPhaseName3.setText("PHASE");
        lblPhaseName3.setToolTipText("PHASE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblYMax3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(lblPhaseName3, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblXMin3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblXMax3))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYMax3)
                    .addComponent(lblPhaseName3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXMin3)
                    .addComponent(lblXMax3)))
        );

        jPanel5.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 100));

        lblXMin4.setText("X min");
        lblXMin4.setToolTipText("X min");

        lblXMax4.setText("X MAX");
        lblXMax4.setToolTipText("X MAX");

        lblYMax4.setText("Y MAX");
        lblYMax4.setToolTipText("Y MAX");

        lblPhaseName4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPhaseName4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPhaseName4.setText("PHASE");
        lblPhaseName4.setToolTipText("PHASE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblYMax4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(lblPhaseName4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblXMin4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblXMax4))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYMax4)
                    .addComponent(lblPhaseName4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblXMin4)
                    .addComponent(lblXMax4)))
        );

        jPanel5.add(jPanel4);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jSlider1.setBackground(new java.awt.Color(204, 255, 204));
        jSlider1.setMaximum(1000);
        jSlider1.setValue(0);
        jSlider1.setPreferredSize(new java.awt.Dimension(128, 32));
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        getContentPane().add(jSlider1, java.awt.BorderLayout.PAGE_END);

        jLabel1.setBackground(new java.awt.Color(204, 255, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chrono-Cell");
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jLabel3.setText("Time:");
        jPanel6.add(jLabel3);

        jLabel4.setText("0");
        jPanel6.add(jLabel4);

        jButton1.setText("jButton1");
        jPanel6.add(jButton1);

        getContentPane().add(jPanel6, java.awt.BorderLayout.LINE_END);

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
            java.util.logging.Logger.getLogger(GUISimulation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUISimulation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUISimulation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUISimulation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUISimulation().setVisible(true);
            }
        });
    }
    SimulationStructure sim;
    public void SetFunction(SimulationStructure simulation){
        sim=simulation;
        jSlider1.setMaximum((int) (Math.round(sim.currentTime/sim.timeStep)));
        System.err.format("slider max %d\n", (int) (Math.round(sim.currentTime/sim.timeStep)));
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        FunctionStructure[] tempFunction=new FunctionStructure[4];
        for (int phase=0;phase<4;phase++){
            tempFunction[phase] =Operators.createFunction(0.0,sim.solution[0].transitionProbabilities[phase].max,sim.timeStep);
            for (int i=0;i<tempFunction[phase].maxIndex;i++){
                tempFunction[phase].values[i]=Operators.GetSimulationValue(sim, phase, jSlider1.getValue()*tempFunction[phase].step,i*tempFunction[phase].step);
            }
            FillPanelFunction(tempFunction[phase], phase);
        }
        
//        Operators.PrintFunction("temp", tempFunction[0], false);
//        FunctionStructure tempFunction2 =Operators.MultiplyFunctionRaw(sim.oneMinusCumulativeFunctions[1],Operators.TranslateFunction(jSlider1.getValue()*sim.theta[1].step,sim.theta[1]));
//        FunctionStructure tempFunction3 =Operators.MultiplyFunctionRaw(sim.oneMinusCumulativeFunctions[2],Operators.TranslateFunction(jSlider1.getValue()*sim.theta[2].step,sim.theta[2]));
//        FunctionStructure tempFunction4 =Operators.MultiplyFunctionRaw(sim.oneMinusCumulativeFunctions[3],Operators.TranslateFunction(jSlider1.getValue()*sim.theta[3].step,sim.theta[3]));

        
//        FillPanelFunction(tempFunction2, 1);
//        FillPanelFunction(tempFunction3, 2);
//        FillPanelFunction(tempFunction4, 3);
    }

    private void FillPanelFunction(FunctionStructure fun, int pos) {
        JPanel jp = null;
        if(pos == 0) jp = jPanel1;
        if(pos == 1) jp = jPanel2;
        if(pos == 2) jp = jPanel3;
        if(pos == 3) jp = jPanel4;
        Graphics gp = jp.getGraphics();
        double minVal1=0;
        double maxVal1=Math.max(1, Operators.GetFunctionMaxValue(fun));
        for (int i=fun.minIndex;i<=fun.maxIndex-1;i++){
            int absciss11=(int) Math.round((i-fun.minIndex)*jPanel1.getWidth()/(fun.maxIndex-fun.minIndex+1));
            int absciss12=(int) Math.round((i-fun.minIndex+1)*jPanel1.getWidth()/(fun.maxIndex-fun.minIndex+1));
            int ordinate11=(int) Math.round((jPanel1.getHeight()/(minVal1-maxVal1))*(fun.values[i]-maxVal1));
            int ordinate12=(int) Math.round(jPanel1.getHeight()/(minVal1-maxVal1)*(fun.values[i+1]-maxVal1));
            gp.drawLine(absciss11,ordinate11, absciss12,ordinate12);
        }    
        
        GetLabelFromItsTollTipText(jp, "X min").setText((new Double(fun.min)).toString()); // X min
        GetLabelFromItsTollTipText(jp, "X MAX").setText((new Double(fun.max)).toString()); // X MAX
        GetLabelFromItsTollTipText(jp, "Y MAX").setText((new Double(maxVal1)).toString()); // Y MAX
        GetLabelFromItsTollTipText(jp, "PHASE").setText(sim.solution[0].phaseName[pos] + " "); // PHASE
    }
    
    private JLabel GetLabelFromItsTollTipText(JPanel p, String txt) {
        for(int i=0; i<p.getComponentCount(); i++) {
            Component c = p.getComponents()[i];
            if(c instanceof JLabel) {
                if(((JLabel)c).getToolTipText().equals(txt)) {
                    return (JLabel)c;
                }
            }
        }
        return null;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel lblPhaseName1;
    private javax.swing.JLabel lblPhaseName2;
    private javax.swing.JLabel lblPhaseName3;
    private javax.swing.JLabel lblPhaseName4;
    private javax.swing.JLabel lblXMax1;
    private javax.swing.JLabel lblXMax2;
    private javax.swing.JLabel lblXMax3;
    private javax.swing.JLabel lblXMax4;
    private javax.swing.JLabel lblXMin1;
    private javax.swing.JLabel lblXMin2;
    private javax.swing.JLabel lblXMin3;
    private javax.swing.JLabel lblXMin4;
    private javax.swing.JLabel lblYMax1;
    private javax.swing.JLabel lblYMax2;
    private javax.swing.JLabel lblYMax3;
    private javax.swing.JLabel lblYMax4;
    // End of variables declaration//GEN-END:variables
}
