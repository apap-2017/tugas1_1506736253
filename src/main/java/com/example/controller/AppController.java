package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.AppService;


@Controller
public class AppController
{
	@Autowired
	AppService pendudukDAO;
	
	@RequestMapping("/")
	public String index() {
		return "home";
	}
	
	@RequestMapping("/penduduk")
	public String viewPenduduk(Model model, @RequestParam(value = "nik", required = false) String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		if(penduduk!=null) {
			model.addAttribute("penduduk", penduduk);
			return "view-penduduk";
		}else {
			model.addAttribute("error", "Data Penduduk Tidak Ditemukan");
			return "error";
		}
		
	}
	
	@RequestMapping("/keluarga")
	public String viewKeluarga(Model model,	@RequestParam(value="nomor_kk",required=false) String nomor_kk) {
		KeluargaModel keluarga = pendudukDAO.selectKeluarga(nomor_kk);
		if(keluarga!=null) {	
			model.addAttribute("keluarga",keluarga);
			return "view-keluarga";
		}else {
			model.addAttribute("error", "Data Keluarga Tidak Ditemukan");
			return "error";
		}
		
	}
	@RequestMapping(value = "/penduduk/tambah")
    public String tambahPenduduk(Model model) {
    	return "add-penduduk";
    }
	
	@RequestMapping(value = "/penduduk/tambah", method= RequestMethod.POST)
    public String tambahPenduduk(Model model,PendudukModel penduduk) {
		
		//Format NIK
		String kode_kecamatan=pendudukDAO.selectKodeKecamatanByIdKeluarga(penduduk.getId_keluarga());
    	String[] tgl= penduduk.getTanggal_lahir().split("-");
    	if(penduduk.getJenis_kelamin().equals("1")) {
    		int tmp=Integer.parseInt(tgl[2])+40;
    		tgl[2]=Integer.toString(tmp);
    	}
    	String tanggal_lahir=tgl[2]+tgl[1]+tgl[0].substring(2, 4);
    	String nik = kode_kecamatan.substring(0,kode_kecamatan.length()-1)+tanggal_lahir;
    	int count = pendudukDAO.countSimilarNIK(nik);
    	nik = nik+String.format("%04d", count+1);
    	penduduk.setNik(nik);
    	penduduk.setId(pendudukDAO.countPenduduk()+1);
		pendudukDAO.addPenduduk(penduduk);
		model.addAttribute("nik",nik);
		return "add-success";
    }
	
	@RequestMapping(value = "/keluarga/tambah")
    public String tambahKeluarga() {
    	return "add-keluarga";
    }
	
	@RequestMapping(value = "/keluarga/tambah", method= RequestMethod.POST)
    public String tambahkeluarga(Model model,KeluargaModel keluarga) {
		String kode_kecamatan =pendudukDAO.selectKodeKecamatanByName(keluarga.getKecamatan());
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		Date date=new Date();
		String today= dateFormat.format(date);
		String nkk = kode_kecamatan.substring(0,kode_kecamatan.length()-1)+today;
		int count = pendudukDAO.countSimilarNKK(nkk);
    	nkk = nkk+String.format("%04d", count+1);
		keluarga.setNomor_kk(nkk);
		keluarga.setId(pendudukDAO.countKeluarga()+1);
		keluarga.setId_kelurahan(pendudukDAO.selectIdKelurahanByName(keluarga.getKelurahan()));
		model.addAttribute("nkk",nkk);
		pendudukDAO.addKeluarga(keluarga);
		return "add-keluarga-success";
    }
	
	@RequestMapping(value = "/penduduk/ubah/{nik}")
    public String ubahPenduduk(Model model, @PathVariable(value="nik") String nik ){
    	PendudukModel penduduks=pendudukDAO.selectPendudukByNIK(nik);   		
    	model.addAttribute("penduduk", penduduks);
    	return "update-penduduk";
    }
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method= RequestMethod.POST)
    public String ubahPenduduk(Model model, PendudukModel penduduk, @PathVariable(value="nik") String nik) {
	
		//Format NIK
		String kode_kecamatan=pendudukDAO.selectKodeKecamatanByIdKeluarga(penduduk.getId_keluarga());
    	String[] tgl= penduduk.getTanggal_lahir().split("-");
    	if(penduduk.getJenis_kelamin().equals("1")) {
    		int tmp=Integer.parseInt(tgl[2])+40;
    		tgl[2]=Integer.toString(tmp);
    	}
    	String tanggal_lahir=tgl[2]+tgl[1]+tgl[0].substring(2, 4);
    	nik = kode_kecamatan.substring(0,kode_kecamatan.length()-1)+tanggal_lahir;
    	int count = pendudukDAO.countSimilarNIK(nik);
    	nik = nik+String.format("%04d", count+1);
    	model.addAttribute("nik",penduduk.getNik());
    	penduduk.setNik(nik);
		pendudukDAO.updatePenduduk(penduduk);
		return "update-penduduk-success";
    }
	@RequestMapping(value = "/keluarga/ubah/{nomor_kk}")
    public String ubahKeluarga(Model model, @PathVariable(value="nomor_kk") String nomor_kk ){
    	KeluargaModel keluarga = pendudukDAO.selectKeluarga(nomor_kk);	
    	model.addAttribute("keluarga", keluarga);
    	return "update-keluarga";
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nomor_kk}", method= RequestMethod.POST)
	public String ubahKeluarga(Model model, KeluargaModel keluarga, @PathVariable(value="nomor_kk") String nomor_kk) {
		keluarga.setId_kelurahan(pendudukDAO.selectIdKelurahanByName(keluarga.getKelurahan()));	
		model.addAttribute("nkk", keluarga.getNomor_kk());
		String kode_kecamatan =pendudukDAO.selectKodeKecamatanByName(keluarga.getKecamatan());
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		Date date=new Date();
		String today= dateFormat.format(date);
		String nkk = kode_kecamatan.substring(0,kode_kecamatan.length()-1)+today;
		int count = pendudukDAO.countSimilarNKK(nkk);
    	nkk = nkk+String.format("%04d", count+1);
		keluarga.setNomor_kk(nkk);
		pendudukDAO.updateKeluarga(keluarga);
		return "update-keluarga-success";
	}
	
	@RequestMapping(value="/penduduk/mati", method=RequestMethod.POST)
	public String deactivate_penduduk(Model model, PendudukModel penduduk) {
		pendudukDAO.deactivatePenduduk(penduduk.getNik());
		model.addAttribute("nik", penduduk.getNik());
		int keluarga_alive = pendudukDAO.countKeluargaAlive(penduduk.getId_keluarga());
		if(keluarga_alive==0) {
			pendudukDAO.deactivateKeluarga(penduduk.getId_keluarga());
		}
		return "mati-success";
	}
	
	@RequestMapping(value="/penduduk/cari")
	public String cari(Model model, @RequestParam(value="kt", required=false) String kt,
			@RequestParam(value="kc", required=false) String kc,
			@RequestParam(value="kl", required=false) String kl) {
		if(kl==null && kc==null && kt==null) {
			List<KotaModel> kota = pendudukDAO.selectAllKota();
			model.addAttribute("kota", kota);
			return "lihat-kota";
		}else {
			KotaModel kota=pendudukDAO.selectKotaById(kt);
			if(kl==null && kc==null) {
				List<KecamatanModel> kecamatan = pendudukDAO.selectKecamatanByKota(kt);
				model.addAttribute("kecamatan", kecamatan);
				model.addAttribute("kota", kota);
				return "lihat-kecamatan";
			}else {
				KecamatanModel kecamatan = pendudukDAO.selectKecamatanById(kc);
				if(kl==null) {
					List<KelurahanModel> kelurahan = pendudukDAO.selectKelurahanByKecamatan(kc);
					model.addAttribute("kelurahan", kelurahan);
					model.addAttribute("kecamatan", kecamatan);
					model.addAttribute("kota",kota);
					return "lihat-kelurahan";
				}else {
					KelurahanModel kelurahan = pendudukDAO.selectKelurahanById(kl);
					List<PendudukModel> penduduk = pendudukDAO.selectPendudukByKelurahan(kl);
					
					PendudukModel pendudukTua = pendudukDAO.selectPendudukTua(kl);
					PendudukModel pendudukMuda = pendudukDAO.selectPendudukMuda(kl);
					model.addAttribute("pendudukTua", pendudukTua);
					model.addAttribute("pendudukMuda", pendudukMuda);
					model.addAttribute("penduduk", penduduk);
					model.addAttribute("kelurahan", kelurahan);
					model.addAttribute("kecamatan", kecamatan);
					model.addAttribute("kota",kota);
					return "cari-penduduk";
				}
				
			}
		}
	}
	
	
}
