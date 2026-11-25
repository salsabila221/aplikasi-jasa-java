package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.PesananController;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;

/** Incoming orders, delivery progress, and completed-order earnings for drivers. */
public class DriverOrdersFrame extends JFrame {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final User driver;
    private final PesananController controller = new PesananController();
    private final JTable pending = SwingViewSupport.table("ID", "Pelanggan", "Layanan", "Kategori", "Tujuan", "Tarif", "Dibuat");
    private final JTable assigned = SwingViewSupport.table("ID", "Pelanggan", "Layanan", "Tujuan", "Tarif", "Status", "Tanggal");
    private final javax.swing.JLabel earnings = new javax.swing.JLabel("Penghasilan: -");

    public DriverOrdersFrame(User driver) {
        super("Pesanan Driver - JSBJ"); this.driver = driver;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel page = SwingViewSupport.page();
        page.add(SwingViewSupport.header("Pesanan Driver", "Ambil order Pending dan perbarui progres pengantaran."), BorderLayout.NORTH);
        JTabbedPane tabs = new JTabbedPane();
        JPanel pendingPanel = new JPanel(new BorderLayout(0, 8)); pendingPanel.add(new JScrollPane(pending), BorderLayout.CENTER);
        JPanel incomingActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        javax.swing.JButton claimButton = SwingViewSupport.button("Ambil Pesanan", true);
        javax.swing.JButton refreshIncoming = SwingViewSupport.button("Muat Ulang", false);
        claimButton.addActionListener(e -> claimSelected()); refreshIncoming.addActionListener(e -> load());
        incomingActions.add(refreshIncoming); incomingActions.add(claimButton); pendingPanel.add(incomingActions, BorderLayout.SOUTH);
        tabs.addTab("Order Masuk (Pending)", pendingPanel);

        JPanel assignedPanel = new JPanel(new BorderLayout(0, 8)); assignedPanel.add(new JScrollPane(assigned), BorderLayout.CENTER);
        JPanel assignedActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        javax.swing.JButton advanceButton = SwingViewSupport.button("Perbarui Progres", true);
        javax.swing.JButton refreshAssigned = SwingViewSupport.button("Muat Ulang", false);
        earnings.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        advanceButton.addActionListener(e -> advanceSelected()); refreshAssigned.addActionListener(e -> load());
        assignedActions.add(earnings); assignedActions.add(refreshAssigned); assignedActions.add(advanceButton);
        assignedPanel.add(assignedActions, BorderLayout.SOUTH); tabs.addTab("Pesanan Saya & Penghasilan", assignedPanel);
        page.add(tabs, BorderLayout.CENTER);
        setContentPane(page); setSize(1000, 560); setLocationRelativeTo(null); load();
    }
    private void load() {
        try {
            List<Pesanan> incoming = controller.pendingOrders(driver);
            var incomingModel = SwingViewSupport.model(pending); incomingModel.setRowCount(0);
            for (Pesanan p : incoming) incomingModel.addRow(new Object[]{p.getIdPesanan(), p.getNamaPelanggan(), p.getNamaLayanan(), p.getKategori(), p.getTujuan(), SwingViewSupport.money(p.getTotalBiaya()), format(p)});
            List<Pesanan> mine = controller.driverOrders(driver);
            var mineModel = SwingViewSupport.model(assigned); mineModel.setRowCount(0);
            for (Pesanan p : mine) mineModel.addRow(new Object[]{p.getIdPesanan(), p.getNamaPelanggan(), p.getNamaLayanan(), p.getTujuan(), SwingViewSupport.money(p.getTotalBiaya()), p.getStatusPesanan(), format(p)});
            earnings.setText("Total tarif order selesai: " + SwingViewSupport.money(controller.driverEarnings(driver)));
        } catch (ControllerException ex) { showError(ex); }
    }
    private void claimSelected() {
        int row = pending.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Pilih order Pending terlebih dahulu."); return; }
        int id = ((Number) pending.getValueAt(row, 0)).intValue();
        try { controller.claimOrder(driver, id); JOptionPane.showMessageDialog(this, "Order diambil. Status sekarang Diproses."); load(); }
        catch (ControllerException ex) { showError(ex); }
    }
    private void advanceSelected() {
        int row = assigned.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Pilih pesanan milikmu terlebih dahulu."); return; }
        int id = ((Number) assigned.getValueAt(row, 0)).intValue();
        try { String next = controller.advanceDriverOrder(driver, id); JOptionPane.showMessageDialog(this, "Status pesanan menjadi " + next + "."); load(); }
        catch (ControllerException ex) { showError(ex); }
    }
    private String format(Pesanan p) { return p.getTanggalPesanan() == null ? "-" : p.getTanggalPesanan().format(DATE); }
    private void showError(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Operasi pesanan gagal", JOptionPane.ERROR_MESSAGE); }
}
