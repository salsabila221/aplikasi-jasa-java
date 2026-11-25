package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.LayananController;
import com.mycompany.jasa.controller.PesananController;
import com.mycompany.jasa.model.LayananUmkm;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/** Catalog and order form for a logged-in customer. */
public class KatalogPesananFrame extends JFrame {
    private final User customer;
    private final LayananController layananController = new LayananController();
    private final PesananController pesananController = new PesananController();
    private final JTable services = SwingViewSupport.table("ID", "Layanan", "Kategori", "Lokasi Asal", "Tarif");
    private final JComboBox<String> categoryFilter = new JComboBox<>(new String[]{"Semua kategori", "Delivery Makanan", "Jastip Toko", "Kirim Barang"});
    private final TableRowSorter<javax.swing.table.TableModel> sorter = new TableRowSorter<>(services.getModel());

    public KatalogPesananFrame(User customer) {
        super("Katalog Layanan - JSBJ"); this.customer = customer;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel page = SwingViewSupport.page();
        page.add(SwingViewSupport.header("Katalog Layanan & UMKM", "Pilih layanan lokal, lalu masukkan alamat tujuan."), BorderLayout.NORTH);
        services.setRowSorter(sorter);
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filterBar.setOpaque(false); filterBar.add(new javax.swing.JLabel("Kategori:")); filterBar.add(categoryFilter);
        categoryFilter.addActionListener(e -> applyFilter());
        JPanel center = new JPanel(new BorderLayout(0, 10)); center.setOpaque(false);
        center.add(filterBar, BorderLayout.NORTH); center.add(new JScrollPane(services), BorderLayout.CENTER);
        page.add(center, BorderLayout.CENTER);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT)); actions.setOpaque(false);
        javax.swing.JButton orderButton = SwingViewSupport.button("Pesan Layanan", true);
        javax.swing.JButton historyButton = SwingViewSupport.button("Pesanan Saya", false);
        javax.swing.JButton refreshButton = SwingViewSupport.button("Muat Ulang", false);
        orderButton.addActionListener(e -> placeOrder());
        historyButton.addActionListener(e -> new RiwayatPesananFrame(customer).setVisible(true));
        refreshButton.addActionListener(e -> loadCatalog());
        actions.add(historyButton); actions.add(refreshButton); actions.add(orderButton);
        page.add(actions, BorderLayout.SOUTH);
        setContentPane(page); setSize(900, 560); setLocationRelativeTo(null);
        loadCatalog();
    }

    private void loadCatalog() {
        try {
            List<LayananUmkm> rows = layananController.catalog(customer);
            var model = SwingViewSupport.model(services); model.setRowCount(0);
            for (LayananUmkm row : rows) model.addRow(new Object[]{row.getIdLayanan(), row.getNamaLayanan(),
                    row.getKategori(), row.getLokasiAsal(), SwingViewSupport.money(row.getBiayaDasar())});
            applyFilter();
            if (rows.isEmpty()) JOptionPane.showMessageDialog(this,
                    "Katalog masih kosong. Admin perlu menambahkan layanan terlebih dahulu.", "Katalog kosong", JOptionPane.INFORMATION_MESSAGE);
        } catch (ControllerException ex) { showError("Katalog tidak dapat dimuat", ex); }
    }
    private void applyFilter() {
        String selected = (String) categoryFilter.getSelectedItem();
        sorter.setRowFilter(selected == null || selected.equals("Semua kategori") ? null : RowFilter.regexFilter("^" + java.util.regex.Pattern.quote(selected) + "$", 2));
    }
    private void placeOrder() {
        int viewRow = services.getSelectedRow();
        if (viewRow < 0) { JOptionPane.showMessageDialog(this, "Pilih layanan yang ingin dipesan."); return; }
        int row = services.convertRowIndexToModel(viewRow);
        int idLayanan = ((Number) services.getModel().getValueAt(row, 0)).intValue();
        String tarif = String.valueOf(services.getModel().getValueAt(row, 4));
        javax.swing.JTextField tujuan = new javax.swing.JTextField();
        Object[] fields = {"Alamat tujuan", tujuan, "Tarif otomatis (biaya dasar layanan)", tarif};
        if (JOptionPane.showConfirmDialog(this, fields, "Buat pesanan", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            pesananController.placeOrder(customer, idLayanan, tujuan.getText());
            JOptionPane.showMessageDialog(this, "Pesanan dibuat dengan status Pending. Driver akan mengambilnya.");
        } catch (ControllerException ex) { showError("Pesanan tidak berhasil dibuat", ex); }
    }
    private void showError(String title, Exception ex) {
        JOptionPane.showMessageDialog(this, title + ": " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
