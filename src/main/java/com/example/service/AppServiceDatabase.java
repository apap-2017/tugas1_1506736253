package com.example.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.AppMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppServiceDatabase implements AppService
{
    @Autowired
    private AppMapper appMapper;


    @Override
    public PendudukModel selectPenduduk(String nik)
    {
        log.info ("select pendudul with nik {}", nik);
        return appMapper.selectPenduduk(nik);
    }


	@Override
	public KeluargaModel selectKeluarga(String nomor_kk) {
		return appMapper.selectKeluarga(nomor_kk);
	}


	@Override
	public void addPenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		appMapper.addPenduduk(penduduk);
	}


	@Override
	public String selectKodeKecamatanByIdKeluarga(String id_keluarga) {
		// TODO Auto-generated method stub
		return appMapper.selectKodeKecamatanByIdKeluarga(id_keluarga);
	}


	@Override
	public int countSimilarNIK(String nik_tmp) {
		return appMapper.countSimilarNIK(nik_tmp);
	}


	@Override
	public int countPenduduk() {
		return appMapper.countPenduduk();
	}


	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		appMapper.addKeluarga(keluarga);
	}


	@Override
	public String selectKodeKecamatanByName(String kecamatan) {
		return appMapper.selectKodeKecamatanByName(kecamatan);
	}


	@Override
	public int countKeluarga() {
		return appMapper.countKeluarga();
	}


	@Override
	public int countSimilarNKK(String nkk_tmp) {
		return appMapper.countSimilarNKK(nkk_tmp);
	}


	@Override
	public String selectIdKelurahanByName(String kelurahan) {
		return appMapper.selectIdKelurahanByName(kelurahan);
	}


	@Override
	public PendudukModel selectPendudukByNIK(String nik) {
		return appMapper.selectPendudukByNIK(nik);
	}


	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		log.info ("update pendudul with id {}", penduduk.getId());   
		appMapper.updatePenduduk(penduduk);
	}


	@Override
	public KeluargaModel selectKeluargaByNKK(String nomor_kk) {
		return appMapper.selectKeluargaByNKK(nomor_kk);
	}


	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		log.info ("update keluarga with id {}", keluarga.getId());   
		appMapper.updateKeluarga(keluarga);
	}


	@Override
	public void deactivatePenduduk(String nik) {
		appMapper.deactivatePenduduk(nik);
	}


	@Override
	public int countKeluargaAlive(String id) {
		return appMapper.countKeluargaAlive(id);
	}


	@Override
	public void deactivateKeluarga(String id) {
		appMapper.deactivateKeluarga(id);
		
	}


	@Override
	public List<KotaModel> selectAllKota() {
		return appMapper.selectAllKota();
	}


	@Override
	public List<KecamatanModel> selectKecamatanByKota(String id) {
		return appMapper.selectKecamatanByKota(id);
	}


	@Override
	public KotaModel selectKotaById(String id) {
		return appMapper.selectKotaById(id);
	}


	@Override
	public List<KelurahanModel> selectKelurahanByKecamatan(String id) {
		return appMapper.selectKelurahanByKecamatan(id);
	}


	@Override
	public KecamatanModel selectKecamatanById(String id) {
		return appMapper.selectKecamatanById(id);
	}


	@Override
	public List<PendudukModel> selectPendudukByKelurahan(String id) {
		return appMapper.selectPendudukByKelurahan(id);
	}


	@Override
	public KelurahanModel selectKelurahanById(String id) {
		return appMapper.selectKelurahanById(id);
	}


	@Override
	public PendudukModel selectPendudukMuda(String id) {
		// TODO Auto-generated method stub
		return appMapper.selectPendudukMuda(id);
	}


	@Override
	public PendudukModel selectPendudukTua(String id) {
		// TODO Auto-generated method stub
		return appMapper.selectPendudukTua(id);
	}
    
}