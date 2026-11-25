package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.PesananController;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.RingkasanPendapatan;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/** JFrame Dashboard Driver: order masuk, pengantaran, status, dan ringkasan. */
public class DriverDashboardFrame extends JFrame {
    private final User user;
    private final PesananController pesananController = new PesananController();
    private final DefaultTableModel pendingModel = readOnlyModel(
            "ID", "Pelanggan", "Layanan", "Kategori", "Tujuan", "Tarif", "Tanggal");
    private final JTable pendingTable = new JTable(pendingModel);
    private final DefaultTableModel assignedModel = readOnlyModel(
            "ID", "Pelanggan", "Layanan", "Tujuan", "Tarif", "Status", "Tanggal");
    private final JTable assignedTable = new JTable(assignedModel);
    private final JLabel earningsLabel = new JLabel("Pesanan selesai: 0 | Akumulasi tarif selesai: Rp0");

    public DriverDashboardFrame(User user) {
        super("Dashboard Driver / Kurir - JSBJ");
        if (user == null) throw new IllegalArgumentException("Data Driver tidak tersedia.");
        this.user = user;
        UiSupport.installDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(980, 620));
        setSize(1180, 730);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UiSupport.BACKGROUND);
        add(UiSupport.header("Dashboard Driver / Kurir", "Ambil pesanan masuk dan perbarui progres pengantaran",
                user, this::openProfile, this::logout), BorderLayout.NORTH);

        UiSupport.styleTable(pendingTable);
        UiSupport.styleTable(assignedTable);
        pendingTable.setAutoCreateRowSorter(true);
        assignedTable.setAutoCreateRowSorter(true);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Order Masuk (Pending)", buildPendingPanel());
        tabs.addTab("Pengantaran Saya & Riwayat", buildAssignedPanel());
        tabs.addChangeListener(e -> refreshAll());
        add(tabs, BorderLayout.CENTER);

        refreshAll();
        setLocationRelativeTo(null);
    }

    private JPanel buildPendingPanel() {
        JPanel page = new JPanel(new BorderLayout(10, 10));
        page.setBackground(UiSupport.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UiSupport.BACKGROUND);
        JLabel info = new JLabel("Hanya pesanan Pending yang belum memiliki Driver ditampilkan di sini.");
        info.setForeground(UiSupport.MUTED);
        top.add(info, BorderLayout.WEST);
        javax.swing.JButton refresh = UiSupport.button("Muat Ulang", false);
        refresh.addActionListener(e -> refreshPending());
        top.add(refresh, BorderLayout.EAST);

        javax.swing.JButton take = UiSupport.button("Ambil Pesanan Terpilih", true);
        take.addActionListener(e -> takeSelectedOrder());
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(UiSupport.BACKGROUND);
        footer.add(take);

        page.add(top, BorderLayout.NORTH);
        page.add(UiSupport.section(UiSupport.scroll(pendingTable), "Antrean Order Pending"), BorderLayout.CENTER);
        page.add(footer, BorderLayout.SOUTH);
        return page;
    }

    private JPanel buildAssignedPanel() {
        JPanel page = new JPanel(new BorderLayout(10, 10));
        page.setBackground(UiSupport.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UiSupport.BACKGROUND);
        earningsLabel.setFont(UiSupport.BOLD);
        earningsLabel.setForeground(UiSupport.TEAL);
        top.add(earningsLabel, BorderLayout.WEST);
        javax.swing.JButton refresh = UiSupport.button("Muat Ulang", false);
        refresh.addActionListener(e -> refreshAssigned());
        top.add(refresh, BorderLayout.EAST);

        javax.swing.JButton progress = UiSupport.button("Perbarui Progres Pesanan", true);
        progress.addActionListener(e -> advanceSelectedOrder());
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(UiSupport.BACKGROUND);
        footer.add(progress);
        JLabel note = new JLabel("Alur status: Diproses → Diantar → Selesai.");
        note.setForeground(UiSupport.MUTED);
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(UiSupport.BACKGROUND);
        bottom.add(note, BorderLayout.WEST);
        bottom.add(footer, BorderLayout.EAST);

        page.add(top, BorderLayout.NORTH);
        page.add(UiSupport.section(UiSupport.scroll(assignedTable), "Pesanan yang Ditangani dan Riwayat Pengantaran"), BorderLayout.CENTER);
        page.add(bottom, BorderLayout.SOUTH);
        return page;
    }

    private void refreshAll() {
        refreshPending();
        refreshAssigned();
    }

    private void refreshPending() {
        try {
            List<Pesanan> orders = pesananController.pesananPending(user);
            pendingModel.setRowCount(0);
            for (Pesanan item : orders) {
                pendingModel.addRow(new Object[]{item.getIdPesanan(), item.getNamaPelanggan(),
                    item.getNamaLayanan(), item.getKategori(), item.getTujuan(),
                    UiSupport.money(item.getTotalBiaya()), UiSupport.date(item.getTanggalPesanan())});
            }
        } catch (ControllerException ex) {
            showError("Gagal memuat order masuk", ex);
        }
    }

    private void refreshAssigned() {
        try {
            List<Pesanan> orders = pesananController.pesananDriver(user);
            assignedModel.setRowCount(0);
            for (Pesanan item : orders) {
                assignedModel.addRow(new Object[]{item.getIdPesanan(), item.getNamaPelanggan(),
                    item.getNamaLayanan(), item.getTujuan(), UiSupport.money(item.getTotalBiaya()),
                    item.getStatusPesanan(), UiSupport.date(item.getTanggalPesanan())});
            }
            RingkasanPendapatan summary = pesananController.ringkasanDriver(user);
            earningsLabel.setText("Pesanan selesai: " + summary.getJumlahPesanan()
                    + "  |  Akumulasi tarif selesai: " + UiSupport.money(summary.getTotalPendapatan()));
        } catch (ControllerException ex) {
            showError("Gagal memuat pengantaran", ex);
        }
    }

    private void takeSelectedOrder() {
        int id = selectedId(pendingTable, pendingModel);
        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Pilih satu order masuk terlebih dahulu.",
                    "Order belum dipilih", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Ambil pesanan #" + id + "? Statusnya akan berubah menjadi Diproses.",
                "Konfirmasi Ambil Pesanan", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;
        try {
            pesananController.ambilPesanan(user, id);
            refreshAll();
            JOptionPane.showMessageDialog(this, "Pesanan berhasil diambil. Status: Diproses.");
        } catch (ControllerException ex) {
            showError("Pesanan tidak dapat diambil", ex);
        }
    }

    private void advanceSelectedOrder() {
        int id = selectedId(assignedTable, assignedModel);
        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan pada tabel terlebih dahulu.",
                    "Pesanan belum dipilih", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String next = pesananController.lanjutkanStatus(user, id);
            refreshAssigned();
            refreshPending();
            JOptionPane.showMessageDialog(this, "Status pesanan #" + id + " diperbarui menjadi " + next + ".");
        } catch (ControllerException ex) {
            showError("Status tidak dapat diperbarui", ex);
        }
    }

    private int selectedId(JTable table, DefaultTableModel model) {
        int selected = table.getSelectedRow();
        if (selected < 0) return -1;
        int row = table.convertRowIndexToModel(selected);
        return ((Number) model.getValueAt(row, 0)).intValue();
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
