package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;;

public interface AppService
{
    PendudukModel selectPenduduk (String nik);
    KeluargaModel selectKeluarga(String nomor_kk);
    void addPenduduk(PendudukModel penduduk);
    String selectKodeKecamatanByIdKeluarga(String id_keluarga);
    int countSimilarNIK(String nik_tmp);
    int countPenduduk();
    void addKeluarga(KeluargaModel keluarga);
    String selectKodeKecamatanByName(String kecamatan);
    int countSimilarNKK(String nkk_tmp);
    int countKeluarga();
    String selectIdKelurahanByName(String kelurahan);
    PendudukModel selectPendudukByNIK(String nik);
    void updatePenduduk(PendudukModel penduduk);
    KeluargaModel selectKeluargaByNKK(String nomor_kk);
    void updateKeluarga(KeluargaModel keluarga);
    void deactivatePenduduk(String nik);
    int countKeluargaAlive(String id);
    void deactivateKeluarga(String id);
    List<KotaModel> selectAllKota();
    List<KecamatanModel> selectKecamatanByKota(String id);
    KotaModel selectKotaById(String id);
    List<KelurahanModel> selectKelurahanByKecamatan(String id);
    KecamatanModel selectKecamatanById(String id);
    List<PendudukModel> selectPendudukByKelurahan(String id);
    KelurahanModel selectKelurahanById(String id);
    PendudukModel selectPendudukMuda(String id);
    PendudukModel selectPendudukTua(String id);
}
