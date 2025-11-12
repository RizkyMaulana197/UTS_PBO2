/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;
/**
 *
 * @author User
 */
    public class crud {
        private Connection Koneksidb;
        private String username="root";
        private String password="";
        private String dbname="db_tambang";
        private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
        public String CEK_INSTANSI_KPL, CEK_DLM_GALI_KPL = null;
        public String CEK_LOKASI_IUP, CEK_NO_SK_IUP, CEK_TGL_BERLAKU_IUP, CEK_NO_SERT_IUP, CEK_KET_IUP = null;
        public String CEK_NM_KPL_CAD, CEK_BULAN_CAD, CEK_TAHUN_CAD, CEK_NO_IUP_CAD, CEK_LAUT_CAD, CEK_LUAS_CAD, CEK_DDH_CAD, CEK_IDH_CAD, CEK_TDH_CAD, CEK_PDH_CAD = null;
        public String CEK_KD_CAD_REAL, CEK_DSB_REAL, CEK_ISB_REAL, CEK_TSB_REAL, CEK_PSB_REAL = null;
        public boolean duplikasi=false;

    public crud(){
            try {
                Driver dbdriver = new com.mysql.jdbc.Driver();
                DriverManager.registerDriver(dbdriver);
                Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
                System.out.print("Database Terkoneksi");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.toString());
            }
        }
   
    public void simpanKapal01(String nm_kpl, String instansi, String dlm_gali){
        try {
            String sqlsimpan="insert into Kapal(nm_kpl, instansi, dlm_gali) value"
                    + " ('"+nm_kpl+"', '"+instansi+"', '"+dlm_gali+"')";
            String sqlcari="select*from Kapal where nm_kpl='"+nm_kpl+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Nama Kapal sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Kapal berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanKapal02(String nm_kpl, String instansi, String dlm_gali){
        try {
            String sqlsimpan="INSERT INTO Kapal (nm_kpl, instansi, dlm_gali) VALUES (?, ?, ?)";
            String sqlcari= "SELECT*FROM Kapal WHERE nm_kpl = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, nm_kpl);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Nama Kapal sudah terdaftar");
                this.duplikasi = true;
                this.CEK_INSTANSI_KPL = data.getString("instansi");
                this.CEK_DLM_GALI_KPL = data.getString("dlm_gali");
            } else {
                this.duplikasi = false;
                this.CEK_INSTANSI_KPL = null;
                this.CEK_DLM_GALI_KPL = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, nm_kpl);
                perintah.setString(2, instansi);
                perintah.setString(3, dlm_gali);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Kapal berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahKapal(String nm_kpl, String instansi, String dlm_gali){
        try {
            String sqlubah="UPDATE Kapal SET instansi = ?, dlm_gali = ? WHERE nm_kpl = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, instansi);
            perintah.setString(2, dlm_gali);
            perintah.setString(3, nm_kpl); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Kapal berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusKapal(String nm_kpl){
        try {
            String sqlhapus="DELETE FROM Kapal WHERE nm_kpl = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, nm_kpl);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Kapal berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataKapal(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("Nama Kapal");
            modeltabel.addColumn("Instansi");
            modeltabel.addColumn("Dalam Gali");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            // e.printStackTrace(); 
        }
    }

    public void simpanIUP01(String no_iup, String lokasi, String no_sk, String tgl_berlaku, String no_sert, String ket){
        try {
            String sqlsimpan="insert into IUP(no_iup, lokasi, no_sk, tgl_berlaku, no_sert, ket) value"
                    + " ('"+no_iup+"', '"+lokasi+"', '"+no_sk+"', '"+tgl_berlaku+"', '"+no_sert+"', '"+ket+"')";
            String sqlcari="select*from IUP where no_iup='"+no_iup+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "No IUP sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data IUP berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanIUP02(String no_iup, String lokasi, String no_sk, String tgl_berlaku, String no_sert, String ket){
        try {
            String sqlsimpan="INSERT INTO IUP (no_iup, lokasi, no_sk, tgl_berlaku, no_sert, ket) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM IUP WHERE no_iup = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, no_iup);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "No IUP sudah terdaftar");
                this.duplikasi = true;
                this.CEK_LOKASI_IUP = data.getString("lokasi");
                this.CEK_NO_SK_IUP = data.getString("no_sk");
                this.CEK_TGL_BERLAKU_IUP = data.getString("tgl_berlaku");
                this.CEK_NO_SERT_IUP = data.getString("no_sert");
                this.CEK_KET_IUP = data.getString("ket");
            } else {
                this.duplikasi = false;
                this.CEK_LOKASI_IUP = null;
                this.CEK_NO_SK_IUP = null;
                this.CEK_TGL_BERLAKU_IUP = null;
                this.CEK_NO_SERT_IUP = null;
                this.CEK_KET_IUP = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, no_iup);
                perintah.setString(2, lokasi);
                perintah.setString(3, no_sk);
                perintah.setString(4, tgl_berlaku);
                perintah.setString(5, no_sert);
                perintah.setString(6, ket);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data IUP berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahIUP(String no_iup, String lokasi, String no_sk, String tgl_berlaku, String no_sert, String ket){
        try {
            String sqlubah="UPDATE IUP SET lokasi = ?, no_sk = ?, tgl_berlaku = ?, no_sert = ?, ket = ? WHERE no_iup = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, lokasi);
            perintah.setString(2, no_sk);
            perintah.setString(3, tgl_berlaku);
            perintah.setString(4, no_sert);
            perintah.setString(5, ket);
            perintah.setString(6, no_iup); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data IUP berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusIUP(String no_iup){
        try {
            String sqlhapus="DELETE FROM IUP WHERE no_iup = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, no_iup);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data IUP berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataIUP(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("No IUP");
            modeltabel.addColumn("Lokasi");
            modeltabel.addColumn("No SK");
            modeltabel.addColumn("Tgl Berlaku");
            modeltabel.addColumn("No Sertifikat");
            modeltabel.addColumn("Keterangan");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public void simpanCadangan01(String kd_cad, String nm_kpl, String bulan, String tahun, String no_iup, String laut, String luas, String ddh, String idh, String tdh, String pdh){
        try {
            String sqlsimpan="insert into Cadangan(kd_cad, nm_kpl, bulan, tahun, no_iup, laut, luas, ddh, idh, tdh, pdh) value"
                    + " ('"+kd_cad+"', '"+nm_kpl+"', '"+bulan+"', '"+tahun+"', '"+no_iup+"', '"+laut+"', '"+luas+"', '"+ddh+"', '"+idh+"', '"+tdh+"', '"+pdh+"')";
            String sqlcari="select*from Cadangan where kd_cad='"+kd_cad+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Kode Cadangan sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Cadangan berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanCadangan02(String kd_cad, String nm_kpl, String bulan, String tahun, String no_iup, String laut, String luas, String ddh, String idh, String tdh, String pdh){
        try {
            String sqlsimpan="INSERT INTO Cadangan (kd_cad, nm_kpl, bulan, tahun, no_iup, laut, luas, ddh, idh, tdh, pdh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM Cadangan WHERE kd_cad = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, kd_cad);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Kode Cadangan sudah terdaftar");
                this.duplikasi = true;
                this.CEK_NM_KPL_CAD = data.getString("nm_kpl");
                this.CEK_BULAN_CAD = data.getString("bulan");
                this.CEK_TAHUN_CAD = data.getString("tahun");
                this.CEK_NO_IUP_CAD = data.getString("no_iup");
                this.CEK_LAUT_CAD = data.getString("laut");
                this.CEK_LUAS_CAD = data.getString("luas");
                this.CEK_DDH_CAD = data.getString("ddh");
                this.CEK_IDH_CAD = data.getString("idh");
                this.CEK_TDH_CAD = data.getString("tdh");
                this.CEK_PDH_CAD = data.getString("pdh");
            } else {
                this.duplikasi = false;
                this.CEK_NM_KPL_CAD = null;
                this.CEK_BULAN_CAD = null;
                this.CEK_TAHUN_CAD = null;
                this.CEK_NO_IUP_CAD = null;
                this.CEK_LAUT_CAD = null;
                this.CEK_LUAS_CAD = null;
                this.CEK_DDH_CAD = null;
                this.CEK_IDH_CAD = null;
                this.CEK_TDH_CAD = null;
                this.CEK_PDH_CAD = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, kd_cad);
                perintah.setString(2, nm_kpl);
                perintah.setString(3, bulan);
                perintah.setString(4, tahun);
                perintah.setString(5, no_iup);
                perintah.setString(6, laut);
                perintah.setString(7, luas);
                perintah.setString(8, ddh);
                perintah.setString(9, idh);
                perintah.setString(10, tdh);
                perintah.setString(11, pdh);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Cadangan berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahCadangan(String kd_cad, String nm_kpl, String bulan, String tahun, String no_iup, String laut, String luas, String ddh, String idh, String tdh, String pdh){
        try {
            String sqlubah="UPDATE Cadangan SET nm_kpl = ?, bulan = ?, tahun = ?, no_iup = ?, laut = ?, luas = ?, ddh = ?, idh = ?, tdh = ?, pdh = ? WHERE kd_cad = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, nm_kpl);
            perintah.setString(2, bulan);
            perintah.setString(3, tahun);
            perintah.setString(4, no_iup);
            perintah.setString(5, laut);
            perintah.setString(6, luas);
            perintah.setString(7, ddh);
            perintah.setString(8, idh);
            perintah.setString(9, tdh);
            perintah.setString(10, pdh);
            perintah.setString(11, kd_cad); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Cadangan berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusCadangan(String kd_cad){
        try {
            String sqlhapus="DELETE FROM Cadangan WHERE kd_cad = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, kd_cad);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Cadangan berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataCadangan(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("Kd Cadangan");
            modeltabel.addColumn("Nama Kapal");
            modeltabel.addColumn("Bulan");
            modeltabel.addColumn("Tahun");
            modeltabel.addColumn("No IUP");
            modeltabel.addColumn("Laut");
            modeltabel.addColumn("Luas");
            modeltabel.addColumn("DDH");
            modeltabel.addColumn("IDH");
            modeltabel.addColumn("TDH");
            modeltabel.addColumn("PDH");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }

    public void simpanRealisasi01(String kd_real, String kd_cad, String dsb, String isb, String tsb, String psb){
        try {
            String sqlsimpan="insert into Realisasi(kd_real, kd_cad, dsb, isb, tsb, psb) value"
                    + " ('"+kd_real+"', '"+kd_cad+"', '"+dsb+"', '"+isb+"', '"+tsb+"', '"+psb+"')";
            String sqlcari="select*from Realisasi where kd_real='"+kd_real+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Kode Realisasi sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Realisasi berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanRealisasi02(String kd_real, String kd_cad, String dsb, String isb, String tsb, String psb){
        try {
            String sqlsimpan="INSERT INTO Realisasi (kd_real, kd_cad, dsb, isb, tsb, psb) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM Realisasi WHERE kd_real = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, kd_real);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "Kode Realisasi sudah terdaftar");
                this.duplikasi = true;
                this.CEK_KD_CAD_REAL = data.getString("kd_cad");
                this.CEK_DSB_REAL = data.getString("dsb");
                this.CEK_ISB_REAL = data.getString("isb");
                this.CEK_TSB_REAL = data.getString("tsb");
                this.CEK_PSB_REAL = data.getString("psb");
            } else {
                this.duplikasi = false;
                this.CEK_KD_CAD_REAL = null;
                this.CEK_DSB_REAL = null;
                this.CEK_ISB_REAL = null;
                this.CEK_TSB_REAL = null;
                this.CEK_PSB_REAL = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, kd_real);
                perintah.setString(2, kd_cad);
                perintah.setString(3, dsb);
                perintah.setString(4, isb);
                perintah.setString(5, tsb);
                perintah.setString(6, psb);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Realisasi berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahRealisasi(String kd_real, String kd_cad, String dsb, String isb, String tsb, String psb){
        try {
            String sqlubah="UPDATE Realisasi SET kd_cad = ?, dsb = ?, isb = ?, tsb = ?, psb = ? WHERE kd_real = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, kd_cad);
            perintah.setString(2, dsb);
            perintah.setString(3, isb);
            perintah.setString(4, tsb);
            perintah.setString(5, psb);
            perintah.setString(6, kd_real); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Realisasi berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusRealisasi(String kd_real){
        try {
            String sqlhapus="DELETE FROM Realisasi WHERE kd_real = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, kd_real);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Realisasi berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataRealisasi(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("Kd Realisasi");
            modeltabel.addColumn("Kd Cadangan");
            modeltabel.addColumn("DSB");
            modeltabel.addColumn("ISB");
            modeltabel.addColumn("TSB");
            modeltabel.addColumn("PSB");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    } 
}
    
