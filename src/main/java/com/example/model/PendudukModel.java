package com.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
	private int id;
    private String nik;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    private String jenis_kelamin;
    private String id_keluarga;
    private String alamat;
    private String rt;
    private String rw;
    private String kelurahan;
    private String kecamatan;
    private String kota;
    private String golongan_darah;
    private String agama;
    private String status_perkawinan;
    private String pekerjaan;
    private String is_wni;    
    private String is_wafat;
    private String status_dalam_keluarga;
  
    public boolean matchGolonganDarah(String golongan_darah) {
    	if(golongan_darah!=null) {
    		return golongan_darah.equalsIgnoreCase(this.golongan_darah);
    	}
    	return false;
    }
    
    public boolean matchGender(String gender) {
    	if(gender!=null) {
    		return gender.equals(this.jenis_kelamin);
    	}
    	return false;
    }
    public boolean matchNikah(String kawin) {
    	if(kawin!=null) {
    		return kawin.equals(this.status_perkawinan);
    	}
    	return false;
    }
    public boolean matchWN(String wn) {
    	if(wn!=null) {
    		return wn.equals(this.is_wni);
    	}
    	return false;
    }

	

}