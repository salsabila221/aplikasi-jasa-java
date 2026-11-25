package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.LayananController;
import com.mycompany.jasa.controller.PesananController;
import com.mycompany.jasa.model.Layanan;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/** JFrame Dashboard Pelanggan: katalog, pemesanan, tracking dan riwayat. */
public class PelangganDashboardFrame extends JFrame {
    private final User user;
    private final LayananController layananController = new LayananController();
    private final PesananController pesananController = new PesananController();

    private final JComboBox<String> categoryFilter = new JComboBox<>(new String[]{
        "Semua Kategori", "Delivery Makanan", "Jastip Toko", "Kirim Barang"
    });
    private final JTextArea tujuanArea = new JTextArea(3, 32);
    private final JLabel tariffLabel = new JLabel("Tarif otomatis: pilih layanan terlebih dahulu");
    private final DefaultTableModel catalogModel = readOnlyModel(
            "ID", "Nama Layanan / UMKM", "Kategori", "Lokasi Asal", "Biaya Dasar");
    private final JTable catalogTable = new JTable(catalogModel);
    private final DefaultTableModel ordersModel = readOnlyModel(
            "ID Pesanan", "Layanan", "Tujuan", "Driver", "Total Tarif", "Status", "Tanggal");
    private final JTable ordersTable = new JTable(ordersModel);

    public PelangganDashboardFrame(User user) {
        super("Dashboard Pelanggan - JSBJ");
        if (user == null) throw new IllegalArgumentException("Data pelanggan tidak tersedia.");
        this.user = user;
        UiSupport.installDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(980, 650));
        setSize(1180, 760);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(UiSupport.BACKGROUND);

        add(UiSupport.header("Dashboard Pelanggan", "Pesan layanan, pantau pesanan, dan lihat riwayat Anda",
                user, this::openProfile, this::logout), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Katalog & Buat Pesanan", buildCatalogPanel());
        tabs.addTab("Tracking & Riwayat Pesanan", buildOrdersPanel());
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedIndex() == 1) refreshOrders();
        });
        add(tabs, BorderLayout.CENTER);

        refreshCatalog();
        refreshOrders();
        setLocationRelativeTo(null);
    }

    private JPanel buildCatalogPanel() {
        JPanel page = new JPanel(new BorderLayout(12, 12));
        page.setBackground(UiSupport.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));

        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
        filterBar.setBackground(UiSupport.BACKGROUND);
        JLabel filterLabel = new JLabel("Filter kategori:");
        filterLabel.setFont(UiSupport.BOLD);
        filterBar.add(filterLabel);
        filterBar.add(categoryFilter);
        javax.swing.JButton refreshButton = UiSupport.button("Muat Ulang Katalog", false);
        refreshButton.addActionListener(e -> refreshCatalog());
        filterBar.add(refreshButton);
        categoryFilter.addActionListener(e -> refreshCatalog());

        UiSupport.styleTable(catalogTable);
        catalogTable.setAutoCreateRowSorter(true);
        catalogTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) updateSelectedTariff();
        });

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(java.awt.Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiSupport.BORDER),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 8);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        JLabel formTitle = new JLabel("Form Pemesanan");
        formTitle.setForeground(UiSupport.NAVY);
        formTitle.setFont(UiSupport.BOLD);
        form.add(formTitle, c);

        c.gridy = 1; c.gridwidth = 1;
        form.add(new JLabel("Alamat tujuan / detail pengiriman:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
        tujuanArea.setLineWrap(true);
        tujuanArea.setWrapStyleWord(true);
        tujuanArea.setFont(UiSupport.NORMAL);
        form.add(new JScrollPane(tujuanArea), c);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2; c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        tariffLabel.setForeground(UiSupport.TEAL);
        tariffLabel.setFont(UiSupport.BOLD);
        form.add(tariffLabel, c);

        c.gridy = 3;
        javax.swing.JButton createButton = UiSupport.button("Buat Pesanan", true);
        createButton.addActionListener(e -> createOrder());
        form.add(createButton, c);

        page.add(filterBar, BorderLayout.NORTH);
        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBackground(UiSupport.BACKGROUND);
        content.add(UiSupport.section(UiSupport.scroll(catalogTable), "Daftar Layanan & UMKM"), BorderLayout.CENTER);
        content.add(form, BorderLayout.SOUTH);
        page.add(content, BorderLayout.CENTER);
        return page;
    }

    private JPanel buildOrdersPanel() {
        JPanel page = new JPanel(new BorderLayout(10, 10));
        page.setBackground(UiSupport.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));
        UiSupport.styleTable(ordersTable);
        ordersTable.setAutoCreateRowSorter(true);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UiSupport.BACKGROUND);
        JLabel info = new JLabel("Status diperbarui oleh Driver setelah pesanan diambil.");
        info.setForeground(UiSupport.MUTED);
        top.add(info, BorderLayout.WEST);
        javax.swing.JButton refreshButton = UiSupport.button("Perbarui Status", false);
        refreshButton.addActionListener(e -> refreshOrders());
        top.add(refreshButton, BorderLayout.EAST);

        page.add(top, BorderLayout.NORTH);
        page.add(UiSupport.section(UiSupport.scroll(ordersTable), "Tracking dan Riwayat Pesanan Saya"), BorderLayout.CENTER);
        return page;
    }

    private void refreshCatalog() {
        try {
            String category = (String) categoryFilter.getSelectedItem();
            List<Layanan> items = layananController.getLayananByKategori(category);
            catalogModel.setRowCount(0);
            for (Layanan item : items) {
                catalogModel.addRow(new Object[]{item.getIdLayanan(), item.getNamaLayanan(),
                    item.getKategori(), item.getLokasiAsal(), UiSupport.money(item.getBiayaDasar())});
            }
            updateSelectedTariff();
        } catch (ControllerException ex) {
            showError("Gagal memuat katalog", ex);
        }
    }

    private void updateSelectedTariff() {
        int selected = catalogTable.getSelectedRow();
        if (selected < 0) {
            tariffLabel.setText("Tarif otomatis: pilih layanan terlebih dahulu");
            return;
        }
        int row = catalogTable.convertRowIndexToModel(selected);
        tariffLabel.setText("Estimasi tarif berdasarkan biaya dasar: " + catalogModel.getValueAt(row, 4));
    }

    private void createOrder() {
        int selected = catalogTable.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Pilih layanan pada tabel katalog terlebih dahulu.",
                    "Layanan belum dipilih", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int row = catalogTable.convertRowIndexToModel(selected);
        int idLayanan = ((Number) catalogModel.getValueAt(row, 0)).intValue();
        String serviceName = String.valueOf(catalogModel.getValueAt(row, 1));
        String destination = tujuanArea.getText();
        if (destination == null || destination.isBlank()) {
            JOptionPane.showMessageDialog(this, "Alamat tujuan wajib diisi.",
                    "Data belum lengkap", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Buat pesanan " + serviceName + " dengan tarif " + catalogModel.getValueAt(row, 4) + "?",
                "Konfirmasi Pesanan", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Pesanan saved = pesananController.buatPesanan(user, idLayanan, destination);
            tujuanArea.setText("");
            refreshOrders();
            JOptionPane.showMessageDialog(this, "Pesanan #" + saved.getIdPesanan()
                    + " berhasil dibuat. Status awal: Pending.", "Pesanan tersimpan", JOptionPane.INFORMATION_MESSAGE);
        } catch (ControllerException ex) {
            showError("Pesanan gagal dibuat", ex);
        }
    }

    private void refreshOrders() {
        try {
            List<Pesanan> orders = pesananController.riwayatPelanggan(user);
            ordersModel.setRowCount(0);
            for (Pesanan item : orders) {
                ordersModel.addRow(new Object[]{item.getIdPesanan(), item.getNamaLayanan(), item.getTujuan(),
                    item.getNamaDriver() == null ? "Menunggu Driver" : item.getNamaDriver(),
                    UiSupport.money(item.getTotalBiaya()), item.getStatusPesanan(), UiSupport.date(item.getTanggalPesanan())});
            }
        } catch (ControllerException ex) {
            showError("Gagal memuat riwayat pesanan", ex);
        }
    }

    private void openProfile() { new ProfilFrame(user, () -> {}).setVisible(true); }
    private void logout() { dispose(); new LoginFrame().setVisible(true); }

    private void showError(String title, Exception ex) {
        JOptionPane.showMessageDialog(this, title + ": " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }

    private static DefaultTableModel readOnlyModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }
}
