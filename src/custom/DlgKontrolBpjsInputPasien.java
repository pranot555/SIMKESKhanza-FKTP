/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package custom;

import simrskhanza.*;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import restore.DlgRestorePoli;

/**
 *
 * @author dosen
 */
public final class DlgKontrolBpjsInputPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgKontrolBpjsCariDiagnosa poli=new DlgKontrolBpjsCariDiagnosa(null,false);
    private int i=0;
    private PreparedStatement stat,ps;
    private ResultSet rs;
    private String status="";
    private final DecimalFormat df3 = new DecimalFormat("############");
    private final DecimalFormat df5 = new DecimalFormat("###,###,###,###,###,###,###.##");  
    private double BiayaReg=0,Tarifcasemix=0;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgKontrolBpjsInputPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"P","No.Rawat","No.MR","Nama Pasien","Kode Diagnosa","Nama Diagnosa","Kelas","Tarif","Tagihan","Selisih","Status"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,    
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(60);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(100);          
            }else if(i==10){
                column.setPreferredWidth(70);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        Kd.setDocument(new batasInput((byte)5).getKata(Kd));
        Tarif.setDocument(new batasInput((byte)13).getOnlyAngka(Tarif));       
        Nm.setDocument(new batasInput((byte)50).getKata(Nm));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));  
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgReg")){
                    if(poli.getTable().getSelectedRow()!= -1){                                          
                        Kd.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                        Nm.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                        if(cmbStatus.getSelectedIndex()==0){
                            Tarif.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());                       
                        }else if(cmbStatus.getSelectedIndex()==1){
                            Tarif.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),3).toString());                       
                        }else if(cmbStatus.getSelectedIndex()==2){
                            Tarif.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),4).toString());                       
                        }else if(cmbStatus.getSelectedIndex()==3){
                            Tarif.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),5).toString());                       
                        }
                        Kd.requestFocus();                      
                    }                
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TNoRw2 = new widget.TextBox();
        Tagihan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        Kd = new widget.TextBox();
        Tarif = new widget.TextBox();
        Nm = new widget.TextBox();
        BtnUnit = new widget.Button();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TNoRw2.setHighlighter(null);
        TNoRw2.setName("TNoRw2"); // NOI18N
        TNoRw2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw2KeyPressed(evt);
            }
        });

        Tagihan.setEditable(false);
        Tagihan.setText("0");
        Tagihan.setHighlighter(null);
        Tagihan.setName("Tagihan"); // NOI18N
        Tagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TagihanActionPerformed(evt);
            }
        });
        Tagihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TagihanKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kontrol BPJS - Input Diagnosa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbKamarKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 110));
        panelisi4.setLayout(null);

        label34.setText("Kode Diagnosa :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(10, 40, 85, 23);

        label32.setText("Tarif Diagnosa :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(230, 70, 120, 23);

        Kd.setHighlighter(null);
        Kd.setName("Kd"); // NOI18N
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        panelisi4.add(Kd);
        Kd.setBounds(100, 40, 100, 23);

        Tarif.setHighlighter(null);
        Tarif.setName("Tarif"); // NOI18N
        panelisi4.add(Tarif);
        Tarif.setBounds(360, 70, 150, 23);

        Nm.setHighlighter(null);
        Nm.setName("Nm"); // NOI18N
        Nm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKeyPressed(evt);
            }
        });
        panelisi4.add(Nm);
        Nm.setBounds(200, 40, 425, 23);

        BtnUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnUnit.setMnemonic('4');
        BtnUnit.setToolTipText("ALt+4");
        BtnUnit.setName("BtnUnit"); // NOI18N
        BtnUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUnitActionPerformed(evt);
            }
        });
        panelisi4.add(BtnUnit);
        BtnUnit.setBounds(630, 40, 30, 20);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelisi4.add(jLabel3);
        jLabel3.setBounds(25, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelisi4.add(TNoRw);
        TNoRw.setBounds(100, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        panelisi4.add(TNoRM);
        TNoRM.setBounds(225, 10, 85, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        panelisi4.add(TPasien);
        TPasien.setBounds(310, 10, 315, 23);

        jLabel12.setText("Kelas :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi4.add(jLabel12);
        jLabel12.setBounds(5, 70, 90, 23);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas3", "Kelas2", "Kelas1", "VIP" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(150, 23));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });
        panelisi4.add(cmbStatus);
        cmbStatus.setBounds(100, 70, 120, 23);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAll);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(LCount);

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelisi1.add(BtnEdit);

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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        Valid.pindah(evt,TCari,Nm);
}//GEN-LAST:event_KdKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"Kode Diagnosa");
        }else if(Nm.getText().trim().equals("")){
            Valid.textKosong(Nm,"Nama Diagnosa");
        }else if(Tarif.getText().trim().equals("")){
            Valid.textKosong(Tarif,"Tarif");        
        }else{
            BiayaReg=0; Tarifcasemix=0;            
            BiayaReg=BiayaReg+(Sequel.cariIsiAngka("select biaya_reg from reg_periksa where no_rawat='"+TNoRw.getText()+"' "));
            
            status="Aman";
            if(BiayaReg>Double.parseDouble(Tarif.getText())){
                status="Lebih";
            }
            Sequel.menyimpan("c_tarif_bpjspasien","'"+TNoRw.getText()+"','"+
                    Kd.getText()+"','"+
                    cmbStatus.getSelectedItem()+"','"+
                    Tarif.getText()+"','"+
                    //"0"+"','"+
                    Double.toString(BiayaReg)+"','"+
                    status+"'","No.Rawat");
            BtnCariActionPerformed(evt);
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Tarif,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(i=0;i<tbKamar.getRowCount();i++){ 
            if(tbKamar.getValueAt(i,0).toString().equals("true")){
                Sequel.queryu2("delete from c_tarif_bpjspasien where no_rawat='"+tbKamar.getValueAt(i,1).toString()+"'");           
            }
        } 
        BtnCariActionPerformed(evt);
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"Kode Diagnosa");
        }else if(Nm.getText().trim().equals("")){
            Valid.textKosong(Nm,"Nama Diagnosa");
        }else if(Tarif.getText().trim().equals("")){
            Valid.textKosong(Tarif,"Tarif");      
        }else{
            status="Aman";
            if(Double.parseDouble(Tagihan.getText())>Double.parseDouble(Tarif.getText())){
                status="Lebih";
            }
            Valid.editTable(tabMode,"c_tarif_bpjspasien","no_rawat",TNoRw,"kd_penyakit='"+Kd.getText()+"',tarif='"+Tarif.getText()+"',status='"+status+"',kelas='"+cmbStatus.getSelectedItem()+"'");                  
            if(tabMode.getRowCount()!=0){BtnCariActionPerformed(evt);}
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){     
                    Map<String, Object> param = new HashMap<>();  
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting"));        
            Valid.MyReportqry("rptPoli.jasper","report","::[ Data Unit ]::","select kd_poli, nm_poli, registrasi, registrasilama "+
                " from poliklinik where status='1' and kd_poli like '%"+TCari.getText().trim()+"%' or "+
                " status='1' and nm_poli like '%"+TCari.getText().trim()+"%' order by kd_poli",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

private void NmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKeyPressed
        Valid.pindah(evt,Kd,Tarif);
}//GEN-LAST:event_NmKeyPressed

    private void TNoRw2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRw2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbKamarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbKamarKeyReleased

    private void BtnUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUnitActionPerformed
        akses.setform("DlgReg");     
        poli.isCek();
        poli.tampil();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);           
    }//GEN-LAST:event_BtnUnitActionPerformed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TagihanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TagihanActionPerformed

    private void TagihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TagihanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TagihanKeyPressed

    private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
        cariTarif();
    }//GEN-LAST:event_cmbStatusItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKontrolBpjsInputPasien dialog = new DlgKontrolBpjsInputPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnUnit;
    private widget.TextBox Kd;
    private widget.Label LCount;
    private widget.TextBox Nm;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw2;
    private widget.TextBox TPasien;
    private widget.TextBox Tagihan;
    private widget.TextBox Tarif;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables
//"P","No.Rawat","No.MR","Nama Pasien","Kode Diagnosa","Nama Diagnosa","Tarif","Tagihan","Selisih","Status"};
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(" select reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,c_tarif_bpjs.kd_penyakit, c_tarif_bpjs.nm_penyakit, c_tarif_bpjspasien.kelas, c_tarif_bpjspasien.tarif, "+
                " c_tarif_bpjspasien.tagihan,c_tarif_bpjspasien.status "+
                " from pasien inner join reg_periksa inner join c_tarif_bpjs inner join c_tarif_bpjspasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                " and c_tarif_bpjspasien.no_rawat=reg_periksa.no_rawat and c_tarif_bpjs.kd_penyakit=c_tarif_bpjspasien.kd_penyakit where c_tarif_bpjs.kd_penyakit like ? or "+
                " c_tarif_bpjs.nm_penyakit like ? or pasien.nm_pasien like ? or reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? ");      
            try{
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                   
                    tabMode.addRow(new Object[]{false,
                                   rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   df5.format(rs.getDouble(7)),
                                   df5.format(rs.getDouble(8)),
                                   df5.format(rs.getDouble(7)-rs.getDouble(8)),                                  
                                   rs.getString(9)});
                }
            }catch(SQLException e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if( rs != null){
                    rs.close();
                }
                
                if( ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
            
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        Kd.setText("");
        TNoRw2.setText("");
        Tarif.setText("");       
        Tagihan.setText("");
        Nm.setText("");
        cmbStatus.setSelectedIndex(0);
        Kd.requestFocus();        
        //Valid.autoNomer("poliklinik","U",4,Kd);
    }
//"P","No.Rawat","No.MR","Nama Pasien","Kode Diagnosa","Nama Diagnosa","Tarif","Percent(%)","Batas Tarif","Tagihan","Status"};
    private void getData() {
        if(tbKamar.getSelectedRow()!= -1){
            TNoRw.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString());
            TNoRw2.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString());
            TNoRM.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString());
            TPasien.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString());
            Kd.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString());
            Nm.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString());
            cmbStatus.setSelectedItem(tbKamar.getValueAt(tbKamar.getSelectedRow(),6).toString());
            Tarif.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),7).toString().replaceAll(",",""));            
            Tagihan.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),8).toString().replaceAll(",",""));
        }
    }

    public JTextField getTextField(){
        return Kd;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getregistrasi());
        BtnHapus.setEnabled(akses.getregistrasi());
        BtnEdit.setEnabled(akses.getregistrasi());
        BtnPrint.setEnabled(akses.getregistrasi());       
    }
    
    public void setNoRm(String norm) {
        TNoRw.setText(norm);
        TCari.setText(norm);  
        isPsien(); 
        tampil();
    }
    
    private void isPsien() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());              
    }
    
    private void cariTarif() {
        if(cmbStatus.getSelectedItem().equals("VIP")){
            Tarif.setText(Sequel.cariIsi("select vip from c_tarif_bpjs where kd_penyakit='"+Kd.getText()+"' "));
        }else if(cmbStatus.getSelectedItem().equals("Kelas1")){
            Tarif.setText(Sequel.cariIsi("select kelas1 from c_tarif_bpjs where kd_penyakit='"+Kd.getText()+"' "));
        }else if(cmbStatus.getSelectedItem().equals("Kelas2")){
            Tarif.setText(Sequel.cariIsi("select kelas2 from c_tarif_bpjs where kd_penyakit='"+Kd.getText()+"' "));
        }else if(cmbStatus.getSelectedItem().equals("Kelas3")){
            Tarif.setText(Sequel.cariIsi("select kelas3 from c_tarif_bpjs where kd_penyakit='"+Kd.getText()+"' "));
        }
    }
    
    
}
