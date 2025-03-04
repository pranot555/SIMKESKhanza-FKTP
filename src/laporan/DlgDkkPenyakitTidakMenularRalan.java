/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package laporan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class DlgDkkPenyakitTidakMenularRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,kr1l=0,kr1p=0,th1s4l=0,th1s4p=0,th5s14l=0,th5s14p=0,th15s44l=0,th15S44p=0,
                th45s64l=0,th45s64p=0,th65plusl=0,th65plusp=0,totall=0,totalp=0,totaljml=0,matil=0,matip=0,
                tkr1l=0,tkr1p=0,tth1s4l=0,tth1s4p=0,tth5s14l=0,tth5s14p=0,tth15s44l=0,tth15S44p=0,
                tth45s64l=0,tth45s64p=0,tth65plusl=0,tth65plusp=0,ttotall=0,ttotalp=0,ttotaljml=0,tmatil=0,tmatip=0;
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgDkkPenyakitTidakMenularRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowRwJlDr={"No.","ICD 10","Jenis Penyakit","< 1(L)","< 1(P)","1-4(L)","1-4(P)","5-14(L)","5-14(P)","15-44(L)","15-44(P)",
                            "45-64(L)","45-64(P)",">65(L)",">65(P)","Total(L)","Total(P)","Total(Jml)","Meninggal(L)","Meninggal(P)"};
        tabMode=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(75);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else{
                column.setPreferredWidth(47);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        try {
            ps=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as nm_penyakit from diagnosa_pasien inner join penyakit "+
                    "inner join reg_periksa on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                    "where diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1'  and reg_periksa.tgl_registrasi between ? and ? and left(upper(diagnosa_pasien.kd_penyakit),1)<>'A' and "+
                    "diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1'  and reg_periksa.tgl_registrasi between ? and ? and left(upper(diagnosa_pasien.kd_penyakit),1)<>'B' and "+
                    "diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1'  and reg_periksa.tgl_registrasi between ? and ? and left(upper(diagnosa_pasien.kd_penyakit),1)<>'-' group by diagnosa_pasien.kd_penyakit ");
            ps2=koneksi.prepareStatement("select concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk,pasien.no_rkm_medis from pasien inner join reg_periksa inner join diagnosa_pasien "+
                    "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=diagnosa_pasien.no_rawat where "+
                    "diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? and diagnosa_pasien.kd_penyakit=? "+
                    "group by diagnosa_pasien.no_rawat");
        } catch (Exception e) {
            System.out.println(e);
        }
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        BtnCari1 = new widget.Button();
        label12 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hasil Pelayanan Penyakit Tidak Menular Berbasis Puskesmas dan Rumah Sakit ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Masuk :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(Tgl2);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnCari1);

        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(label12);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Map<String, Object> param = new HashMap<>();            
            param.put("tkr1l",(tkr1l+tkr1p));
            param.put("tth1s4l",(tth1s4l+tth1s4p));
            param.put("tth5s14l",(tth5s14l+th5s14p));
            param.put("tth15s44l",(tth15s44l+tth15S44p));
            param.put("tth45s64l",(tth45s64l+tth45s64p));
            param.put("tth65plusl",(tth65plusl+tth65plusp));
            param.put("tanggal",Tgl2.getDate());
            param.put("ttotall",(ttotall+ttotalp));
            param.put("ttotaljml",ttotaljml);
            param.put("tmatil",(tmatil+tmatip));
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Sequel.queryu("truncate table temporary");
            for(int r=0;r<tabMode.getRowCount();r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("RUMAH SAKIT","                              RUMAH SAKIT")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,13).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,14).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,15).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,16).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,17).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,18).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,19).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
            }
               
            Valid.MyReport("rptDkkPenyakitTakMenularRalan.jasper","report","::[ Penyakit Tidak Menular Rawat Jalan ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbBangsalKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDkkPenyakitTidakMenularRalan dialog = new DlgDkkPenyakitTidakMenularRalan(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ScrollPane Scroll;
    private widget.TextBox TKd;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.panelisi panelGlass5;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);    
            ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            ps.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            ps.setString(4,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            ps.setString(5,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            ps.setString(6,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            rs=ps.executeQuery();
            i=1;
            tkr1l=0;tkr1p=0;tth1s4l=0;tth1s4p=0;tth5s14l=0;tth5s14p=0;tth15s44l=0;tth15S44p=0;tth45s64l=0;
            tth45s64p=0;tth65plusl=0;tth65plusp=0;ttotall=0;ttotalp=0;ttotaljml=0;tmatil=0;tmatip=0;
            while(rs.next()){
                kr1l=0;kr1p=0;th1s4l=0;th1s4p=0;th5s14l=0;th5s14p=0;th15s44l=0;th15S44p=0;th45s64l=0;th45s64p=0;th65plusl=0;th65plusp=0;totall=0;totalp=0;totaljml=0;matil=0;matip=0;
                ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps2.setString(3,rs.getString("kd_penyakit"));
                rs2=ps2.executeQuery();
                while(rs2.next()){   
                    switch (rs2.getString("jk")) {
                        case "L":
                            matil=matil+Sequel.cariInteger("select ifnull(count(pasien_mati.no_rkm_medis),0) from pasien_mati where pasien_mati.no_rkm_medis=?",rs2.getString("no_rkm_medis"));
                            tmatil=tmatil+Sequel.cariInteger("select ifnull(count(pasien_mati.no_rkm_medis),0) from pasien_mati where pasien_mati.no_rkm_medis=?",rs2.getString("no_rkm_medis"));
                            break;
                        case "P": 
                            matip=matip+Sequel.cariInteger("select ifnull(count(pasien_mati.no_rkm_medis),0) from pasien_mati where pasien_mati.no_rkm_medis=?",rs2.getString("no_rkm_medis"));
                            tmatip=tmatip+Sequel.cariInteger("select ifnull(count(pasien_mati.no_rkm_medis),0) from pasien_mati where pasien_mati.no_rkm_medis=?",rs2.getString("no_rkm_medis"));
                            break;
                    }
                    
                    if(rs2.getString("umur").contains("Hr")||rs2.getString("umur").contains("Bl")){
                        switch (rs2.getString("jk")) {
                            case "L":
                                kr1l=kr1l+1;
                                tkr1l=tkr1l+1;
                                break;
                            case "P":
                                kr1p=kr1p+1;
                                tkr1p=tkr1p+1;
                                break;
                        }
                    }else if(rs2.getString("umur").contains("Th")){
                        if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=4){
                            switch (rs2.getString("jk")) {
                                case "L":
                                    th1s4l=th1s4l+1;
                                    tth1s4l=tth1s4l+1;
                                    break;
                                case "P":
                                    th1s4p=th1s4p+1;
                                    tth1s4p=tth1s4p+1;
                                    break;
                            }
                        }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=14){
                            switch (rs2.getString("jk")) {
                                case "L":
                                    th5s14l=th5s14l+1;
                                    tth5s14l=tth5s14l+1;
                                    break;
                                case "P":
                                    th5s14p=th5s14p+1;
                                    tth5s14p=tth5s14p+1;
                                    break;
                            }
                        }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=44){
                            switch (rs2.getString("jk")) {
                                case "L":
                                    th15s44l=th15s44l+1;
                                    tth15s44l=tth15s44l+1;
                                    break;
                                case "P":
                                    th15S44p=th15S44p+1;
                                    tth15S44p=tth15S44p+1;
                                    break;
                            }
                        }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=64){
                            switch (rs2.getString("jk")) {
                                case "L":
                                    th45s64l=th45s64l+1;
                                    tth45s64l=tth45s64l+1;
                                    break;
                                case "P":
                                    th45s64p=th45s64p+1;
                                    tth45s64p=tth45s64p+1;
                                    break;
                            }
                        }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))>64){
                            switch (rs2.getString("jk")) {
                                case "L":
                                    th65plusl=th65plusl+1;
                                    tth65plusl=tth65plusl+1;
                                    break;
                                case "P":
                                    th65plusp=th65plusp+1;
                                    tth65plusp=tth65plusp+1;
                                    break;
                            }
                        }
                    }
                }
                totall=kr1l+th1s4l+th5s14l+th15s44l+th45s64l+th65plusl;
                totalp=kr1p+th1s4p+th5s14p+th15S44p+th45s64p+th65plusp;
                totaljml=totall+totalp;
                ttotall=ttotall+kr1l+th1s4l+th5s14l+th15s44l+th45s64l+th65plusl;
                ttotalp=ttotalp+kr1p+th1s4p+th5s14p+th15S44p+th45s64p+th65plusp;
                ttotaljml=ttotall+ttotalp;
                tabMode.addRow(new Object[]{
                   i,rs.getString("kd_penyakit"),rs.getString("nm_penyakit"),kr1l,kr1p,th1s4l,th1s4p,th5s14l,th5s14p,th15s44l,th15S44p,th45s64l,th45s64p,th65plusl,th65plusp,totall,totalp,totaljml,matil,matip
                });
                i++;                
            } 
            tabMode.addRow(new Object[]{
                   "","","RUMAH SAKIT",tkr1l,tkr1p,tth1s4l,tth1s4p,tth5s14l,tth5s14p,tth15s44l,tth15S44p,tth45s64l,tth45s64p,tth65plusl,tth65plusp,ttotall,ttotalp,ttotaljml,tmatil,tmatip
            });
                
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getData() {
        int row=tbBangsal.getSelectedRow();
        if(row!= -1){
            TKd.setText(tabMode.getValueAt(row,0).toString());
        }
    }

}
