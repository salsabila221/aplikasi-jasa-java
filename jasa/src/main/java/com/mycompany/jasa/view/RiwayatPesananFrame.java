package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.PesananController;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/** Customer order history and live status tracking. */
public class RiwayatPesananFrame extends JFrame {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final User customer;
    private final PesananController controller = new PesananController();
    private final JTable orders = SwingViewSupport.table("ID", "Layanan", "Kategori", "Tujuan", "Tarif", "Status", "Driver", "Tanggal");
    private final JComboBox<String> statusFilter = new JComboBox<>(new String[]{"Semua status", "Pending", "Diproses", "Diantar", "Selesai"});
    private final TableRowSorter<javax.swing.table.TableModel> sorter = new TableRowSorter<>(orders.getModel());
    public RiwayatPesananFrame(User customer) {
        super("Tracking & Riwayat Pesanan"); this.customer = customer;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel page = SwingViewSupport.page();
        page.add(SwingViewSupport.header("Tracking & Riwayat Pesanan", "Status diperbarui saat driver mengubah progres."), BorderLayout.NORTH);
        orders.setRowSorter(sorter);
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT)); bar.setOpaque(false);
        bar.add(new javax.swing.JLabel("Filter status:")); bar.add(statusFilter);
        statusFilter.addActionListener(e -> applyFilter());
        JPanel center = new JPanel(new BorderLayout(0, 8)); center.setOpaque(false);
        center.add(bar, BorderLayout.NORTH); center.add(new JScrollPane(orders), BorderLayout.CENTER);
        page.add(center, BorderLayout.CENTER);
        javax.swing.JButton refresh = SwingViewSupport.button("Muat Ulang", true);
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT)); footer.setOpaque(false);
        refresh.addActionListener(e -> loadOrders()); footer.add(refresh); page.add(footer, BorderLayout.SOUTH);
        setContentPane(page); setSize(1050, 520); setLocationRelativeTo(null); loadOrders();
    }
    private void loadOrders() {
        try {
            List<Pesanan> rows = controller.customerOrders(customer);
            var model = SwingViewSupport.model(orders); model.setRowCount(0);
            for (Pesanan p : rows) model.addRow(new Object[]{p.getIdPesanan(), p.getNamaLayanan(), p.getKategori(),
                    p.getTujuan(), SwingViewSupport.money(p.getTotalBiaya()), p.getStatusPesanan(),
                    p.getNamaDriver() == null ? "Menunggu driver" : p.getNamaDriver(),
                    p.getTanggalPesanan() == null ? "-" : p.getTanggalPesanan().format(DATE)});
            applyFilter();
        } catch (ControllerException ex) { showError(ex); }
    }
    private void applyFilter() {
        String status = (String) statusFilter.getSelectedItem();
        sorter.setRowFilter(status == null || status.equals("Semua status") ? null : RowFilter.regexFilter("^" + status + "$", 5));
    }
    private void showError(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Gagal memuat pesanan", JOptionPane.ERROR_MESSAGE); }
}
