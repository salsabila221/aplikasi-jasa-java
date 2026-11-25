package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.PesananController;
import com.mycompany.jasa.model.Pesanan;
import com.mycompany.jasa.model.RingkasanPendapatan;
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

/** Admin transaction monitoring and completed-order revenue reports. */
public class LaporanAdminFrame extends JFrame {
    private static final DateTimeFormatter DATE=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final User admin;
    private final PesananController controller=new PesananController();
    private final JTable transactions=SwingViewSupport.table("ID","Pelanggan","Layanan","Driver","Tujuan","Tarif","Status","Tanggal");
    private final JTable daily=SwingViewSupport.table("Tanggal","Jumlah pesanan selesai","Pendapatan");
    private final JTable monthly=SwingViewSupport.table("Bulan","Jumlah pesanan selesai","Pendapatan");
    public LaporanAdminFrame(User admin){
        super("Monitoring Transaksi & Laporan");this.admin=admin;setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel page=SwingViewSupport.page();page.add(SwingViewSupport.header("Monitoring Transaksi & Laporan","Pendapatan dihitung dari total tarif pesanan berstatus Selesai."),BorderLayout.NORTH);
        JTabbedPane tabs=new JTabbedPane();tabs.addTab("Semua Transaksi",new JScrollPane(transactions));tabs.addTab("Rekap Harian",new JScrollPane(daily));tabs.addTab("Rekap Bulanan",new JScrollPane(monthly));page.add(tabs,BorderLayout.CENTER);
        JPanel footer=new JPanel(new FlowLayout(FlowLayout.RIGHT));footer.setOpaque(false);javax.swing.JButton refresh=SwingViewSupport.button("Muat Ulang",true);refresh.addActionListener(e->load());footer.add(refresh);page.add(footer,BorderLayout.SOUTH);
        setContentPane(page);setSize(1050,560);setLocationRelativeTo(null);load();
    }
    private void load(){
        try{
            List<Pesanan> all=controller.adminOrders(admin);var tm=SwingViewSupport.model(transactions);tm.setRowCount(0);
            for(Pesanan p:all)tm.addRow(new Object[]{p.getIdPesanan(),p.getNamaPelanggan(),p.getNamaLayanan(),p.getNamaDriver()==null?"-":p.getNamaDriver(),p.getTujuan(),SwingViewSupport.money(p.getTotalBiaya()),p.getStatusPesanan(),p.getTanggalPesanan()==null?"-":p.getTanggalPesanan().format(DATE)});
            fill(daily,controller.dailyReport(admin));fill(monthly,controller.monthlyReport(admin));
        }catch(ControllerException ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Laporan gagal dimuat",JOptionPane.ERROR_MESSAGE);}
    }
    private void fill(JTable table,List<RingkasanPendapatan> rows){var m=SwingViewSupport.model(table);m.setRowCount(0);for(RingkasanPendapatan r:rows)m.addRow(new Object[]{r.getPeriode(),r.getJumlahPesanan(),SwingViewSupport.money(r.getTotalPendapatan())});}
}
