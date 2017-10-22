package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel
{
	private int id;
    private String nomor_kk;
    private String is_tidak_berlaku;
    private String alamat;
    private String rt;
    private String rw;
    private String kelurahan;
    private String kecamatan;
    private String kota;
    private String id_kelurahan;
    private List<PendudukModel> anggota_keluarga;
    
    public void convertWNA() {
    	for(int i=0;i<this.anggota_keluarga.size();i++) {
    		if(this.anggota_keluarga.get(i).getIs_wni().equals("0")) {
    			this.anggota_keluarga.get(i).setIs_wni("WNA");
    		}else {
    			this.anggota_keluarga.get(i).setIs_wni("WNI");
    		}
    	}
    }
    
    public void convertGender() {
    	for(int i=0;i<this.anggota_keluarga.size();i++) {
    		if(this.anggota_keluarga.get(i).getJenis_kelamin().equals("0")) {
    			this.anggota_keluarga.get(i).setJenis_kelamin("Laki-Laki");
    		}else {
    			this.anggota_keluarga.get(i).setJenis_kelamin("Perempuan");
    		}
    	}
    }
        
    
}
