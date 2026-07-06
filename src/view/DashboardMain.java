/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;


public class DashboardMain extends javax.swing.JFrame {

    private model.user currentUser;
    
    public DashboardMain(model.user user) {
        initComponents();
        jLabel4.setText("Selamat Datang, " + user.getNamaPetugas());
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        this.currentUser = user;
        setTitle("Dashboard — " + user.getNamaPetugas() + " (" + user.getRole() + ")");
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int konfirm = javax.swing.JOptionPane.showConfirmDialog(
                    DashboardMain.this, "Yakin ingin keluar?",
                    "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION);
                if (konfirm == javax.swing.JOptionPane.YES_OPTION) {
                    controller.usercontroller.logout();
                    System.exit(0);
                }
            }
        });
    }
    
     public DashboardMain() {
        initComponents();
        setLocationRelativeTo(null);
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jLabel3 = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        pnlSidebar = new javax.swing.JPanel();
        btnVehicle = new javax.swing.JButton();
        btnCustomers = new javax.swing.JButton();
        btnRental = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlHeader.setBackground(new java.awt.Color(25, 25, 42));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sistem Rental Kendaraan");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Selamat Datang,");

        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setContentAreaFilled(false);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 435, Short.MAX_VALUE))
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout)))
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addContainerGap())
        );

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlSidebar.setBackground(new java.awt.Color(18, 18, 32));
        pnlSidebar.setPreferredSize(new java.awt.Dimension(220, 350));
        pnlSidebar.setLayout(new javax.swing.BoxLayout(pnlSidebar, javax.swing.BoxLayout.Y_AXIS));

        btnVehicle.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnVehicle.setForeground(new java.awt.Color(255, 255, 255));
        btnVehicle.setText("Kendaraan");
        btnVehicle.setBorderPainted(false);
        btnVehicle.setContentAreaFilled(false);
        btnVehicle.setFocusPainted(false);
        btnVehicle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnVehicle.setMaximumSize(new java.awt.Dimension(220, 40));
        btnVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehicleActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnVehicle);

        btnCustomers.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCustomers.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomers.setText("Customers");
        btnCustomers.setBorderPainted(false);
        btnCustomers.setContentAreaFilled(false);
        btnCustomers.setFocusPainted(false);
        btnCustomers.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCustomers.setMaximumSize(new java.awt.Dimension(220, 40));
        btnCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomersActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnCustomers);

        btnRental.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnRental.setForeground(new java.awt.Color(255, 255, 255));
        btnRental.setText("Rental");
        btnRental.setBorderPainted(false);
        btnRental.setContentAreaFilled(false);
        btnRental.setFocusPainted(false);
        btnRental.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRental.setMaximumSize(new java.awt.Dimension(220, 40));
        btnRental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRentalActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnRental);

        btnReturn.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnReturn.setForeground(new java.awt.Color(255, 255, 255));
        btnReturn.setText("Pengembalian Kendaraan");
        btnReturn.setBorderPainted(false);
        btnReturn.setContentAreaFilled(false);
        btnReturn.setFocusPainted(false);
        btnReturn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReturn.setMaximumSize(new java.awt.Dimension(220, 40));
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnReturn);

        btnReport.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnReport.setForeground(new java.awt.Color(255, 255, 255));
        btnReport.setText("Laporan Keuangan");
        btnReport.setBorderPainted(false);
        btnReport.setContentAreaFilled(false);
        btnReport.setFocusPainted(false);
        btnReport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReport.setMaximumSize(new java.awt.Dimension(220, 40));
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });
        pnlSidebar.add(btnReport);

        getContentPane().add(pnlSidebar, java.awt.BorderLayout.LINE_START);

        pnlContent.setBackground(new java.awt.Color(30, 30, 50));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomersActionPerformed
        new view.CustomerForm().setVisible(true);
    }//GEN-LAST:event_btnCustomersActionPerformed

    private void btnVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVehicleActionPerformed
        try {
            new view.VehicleForm().setVisible(true);
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Gagal membuka form Kendaraan: " + e.getMessage(),
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnVehicleActionPerformed

    private void btnRentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRentalActionPerformed
        new view.RentalForm().setVisible(true);
    }//GEN-LAST:event_btnRentalActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        new view.ReturnForm().setVisible(true);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        new view.ReportForm().setVisible(true);
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
    int konfirm = javax.swing.JOptionPane.showConfirmDialog(
            this, "Yakin ingin logout?",
            "Konfirmasi Logout", javax.swing.JOptionPane.YES_NO_OPTION);
        if (konfirm == javax.swing.JOptionPane.YES_OPTION) {
            controller.usercontroller.logout();
            this.dispose();
            new view.LoginForm().setVisible(true);
            }            
    }//GEN-LAST:event_btnLogoutActionPerformed

    public model.user getCurrentUser() { return currentUser; }
    
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
            java.util.logging.Logger.getLogger(DashboardMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRental;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnVehicle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSidebar;
    // End of variables declaration//GEN-END:variables
}
