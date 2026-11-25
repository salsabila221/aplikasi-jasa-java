package com.mycompany.jasa.view;

import com.mycompany.jasa.controller.ControllerException;
import com.mycompany.jasa.controller.LayananController;
import com.mycompany.jasa.model.LayananUmkm;
import com.mycompany.jasa.model.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/** Admin CRUD screen for service and UMKM catalog records. */
public class KelolaLayananFrame extends JFrame {
    private final User admin;
    private final LayananController controller = new LayananController();
    private final JTable services = SwingViewSupport.table("ID", "Nama layanan", "Kategori", "Lokasi asal", "Biaya dasar");
    public KelolaLayananFrame(User admin) {
        super("Kelola Katalog UMKM"); this.admin = admin;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel page = SwingViewSupport.page();
        page.add(SwingViewSupport.header("Manajemen Katalog & UMKM", "Tarif di kolom biaya dasar dipakai sebagai tarif otomatis pesanan."), BorderLayout.NORTH);
        page.add(new JScrollPane(services), BorderLayout.CENTER);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT)); actions.setOpaque(false);
        javax.swing.JButton add=SwingViewSupport.button("Tambah",true), edit=SwingViewSupport.button("Edit",false), delete=SwingViewSupport.button("Hapus",false), refresh=SwingViewSupport.button("Muat Ulang",false);
        add.addActionListener(e -> editService(null)); edit.addActionListener(e -> editSelected()); delete.addActionListener(e -> deleteSelected()); refresh.addActionListener(e -> load());
        actions.add(refresh); actions.add(add); actions.add(edit); actions.add(delete); page.add(actions,BorderLayout.SOUTH);
        setContentPane(page); setSize(850,520); setLocationRelativeTo(null); load();
    }
    private void load() {
        try { List<LayananUmkm> rows=controller.manageList(admin); var m=SwingViewSupport.model(services); m.setRowCount(0);
            for(LayananUmkm r:rows)m.addRow(new Object[]{r.getIdLayanan(),r.getNamaLayanan(),r.getKategori(),r.getLokasiAsal(),SwingViewSupport.money(r.getBiayaDasar())});
        } catch(ControllerException ex){showError(ex);}
    }
    private void editSelected(){int row=services.getSelectedRow();if(row<0){JOptionPane.showMessageDialog(this,"Pilih layanan yang ingin diedit.");return;}int id=((Number)services.getValueAt(row,0)).intValue();editService(new LayananUmkm(id,String.valueOf(services.getValueAt(row,1)),String.valueOf(services.getValueAt(row,2)),String.valueOf(services.getValueAt(row,3)),parseMoney(services.getValueAt(row,4))));}
    private double parseMoney(Object value){String s=String.valueOf(value).replace("Rp","").replace(".","").trim();return Double.parseDouble(s);}
    private void editService(LayananUmkm current){
        JTextField name=new JTextField(current==null?"":current.getNamaLayanan());
        JComboBox<String> category=new JComboBox<>(LayananController.KATEGORI.toArray(String[]::new));if(current!=null)category.setSelectedItem(current.getKategori());
        JTextField location=new JTextField(current==null?"":current.getLokasiAsal());
        JTextField fee=new JTextField(current==null?"":String.valueOf(current.getBiayaDasar()));
        JPanel form=new JPanel(new GridLayout(0,1,4,4));form.add(new javax.swing.JLabel("Nama layanan"));form.add(name);form.add(new javax.swing.JLabel("Kategori"));form.add(category);form.add(new javax.swing.JLabel("Lokasi asal"));form.add(location);form.add(new javax.swing.JLabel("Biaya dasar (angka Rupiah)"));form.add(fee);
        if(JOptionPane.showConfirmDialog(this,form,current==null?"Tambah layanan":"Edit layanan",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION)return;
        try{double amount=Double.parseDouble(fee.getText().trim());if(current==null)controller.create(admin,name.getText(),(String)category.getSelectedItem(),location.getText(),amount);else controller.update(admin,current.getIdLayanan(),name.getText(),(String)category.getSelectedItem(),location.getText(),amount);load();}
        catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Biaya harus berupa angka.","Input tidak valid",JOptionPane.WARNING_MESSAGE);}catch(ControllerException ex){showError(ex);}
    }
    private void deleteSelected(){int row=services.getSelectedRow();if(row<0){JOptionPane.showMessageDialog(this,"Pilih layanan yang ingin dihapus.");return;}int id=((Number)services.getValueAt(row,0)).intValue();if(JOptionPane.showConfirmDialog(this,"Hapus layanan ini? Layanan yang sudah dipakai pesanan akan ditolak agar riwayat tetap ada.","Konfirmasi",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;try{controller.delete(admin,id);load();}catch(ControllerException ex){showError(ex);}}
    private void showError(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Katalog gagal diproses",JOptionPane.ERROR_MESSAGE);}
}
