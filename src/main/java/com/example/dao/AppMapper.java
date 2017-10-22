package com.example.dao;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
//
//
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;


@Mapper
public interface AppMapper
{
	@Select("SELECT DISTINCT p.*, k.alamat, k.rt, k.rw, 	kl.nama_kelurahan as kelurahan, kc.nama_kecamatan as kecamatan, kt.nama_kota as kota  "+
			" FROM penduduk p, keluarga k, kelurahan kl, kecamatan kc, kota kt "+
			" WHERE kt.id=kc.id_kota and kc.id=kl.id_kecamatan and kl.id=k.id_kelurahan and k.id=p.id_keluarga and p.nik=#{nik}")
	PendudukModel selectPenduduk(@Param("nik") String nik);
	
	@Select("SELECT DISTINCT K.*, KL.nama_kelurahan as kelurahan, kc.nama_kecamatan as kecamatan, kt.nama_kota as kota " + 
			"FROM KELUARGA K, kelurahan kl, kecamatan kc, kota kt " + 
			"WHERE kt.id=kc.id_kota and kc.id=kl.id_kecamatan and kl.id=k.id_kelurahan and k.nomor_kk=#{nomor_kk}")
	@Results(value= {
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="anggota_keluarga", column="nomor_kk",
					javaType = List.class,
					many=@Many(select="selectAnggotaKeluarga"))
	    })
	KeluargaModel selectKeluarga(@Param("nomor_kk") String nomor_kk);
	
	@Select("SELECT P.* FROM PENDUDUK P, KELUARGA K WHERE P.id_keluarga=K.id AND K.nomor_kk=#{nomor_kk}")
	PendudukModel selectAnggotaKeluarga(@Param("nomor_kk") String nomor_kk);
	
	@Insert("INSERT INTO penduduk(" + 
			"id,nik,nama,tempat_lahir,tanggal_lahir,jenis_kelamin,is_wni,id_keluarga,agama,pekerjaan,status_perkawinan,status_dalam_keluarga,golongan_darah,is_wafat)"+
			" VALUES (#{id},#{nik},#{nama},#{tempat_lahir},#{tanggal_lahir},#{jenis_kelamin},#{is_wni},#{id_keluarga},#{agama},#{pekerjaan},#{status_perkawinan},#{status_dalam_keluarga},#{golongan_darah},#{is_wafat})")
	void addPenduduk (PendudukModel penduduk);
	
	@Select("SELECT DISTINCT KC.kode_kecamatan FROM PENDUDUK P, KELUARGA K, KELURAHAN KL, KECAMATAN KC "+
			"WHERE KC.id=KL.id_kecamatan and KL.id=K.id_kelurahan and K.id=P.id_keluarga AND P.id_keluarga=#{id_keluarga}")
	String selectKodeKecamatanByIdKeluarga(@Param("id_keluarga") String id_keluarga);
	
	@Select("SELECT Count(nik) as jum_nik FROM penduduk WHERE nik LIKE CONCAT(#{nik},'%')")
	int countSimilarNIK(@Param("nik") String nik);
	
	@Select("SELECT COUNT(id) as id FROM penduduk")
	int countPenduduk();
	
	@Insert("INSERT INTO keluarga(id, nomor_kk, alamat,rt,rw,id_kelurahan,is_tidak_berlaku)"+
			"VALUE (#{id},#{nomor_kk},#{alamat},#{rt},#{rw},#{id_kelurahan},0)")
	void addKeluarga(KeluargaModel keluarga);
	
	@Select("SELECT kode_kecamatan FROM kecamatan WHERE nama_kecamatan=#{kecamatan}")
	String selectKodeKecamatanByName(@Param("kecamatan") String kecamatan);
	
	@Select("SELECT id FROM kelurahan WHERE nama_kelurahan=#{kelurahan}")
	String selectIdKelurahanByName(@Param("kelurahan") String kelurahan);
	
	@Select("SELECT Count(nomor_kk) as jum_nkk FROM keluarga WHERE nomor_kk LIKE CONCAT(#{nkk},'%')")
	int countSimilarNKK(@Param("nkk") String nkk);
	
	@Select("SELECT COUNT(id) as id FROM keluarga")
	int countKeluarga();
	
	@Select("SELECT * FROM PENDUDUK WHERE nik = #{nik}")
	PendudukModel selectPendudukByNIK(@Param("nik") String nik);
	
	@Update("UPDATE penduduk SET nik=#{nik}, nama=#{nama},tempat_lahir=#{tempat_lahir},tanggal_lahir=#{tanggal_lahir},jenis_kelamin=#{jenis_kelamin},"+
	"is_wni=#{is_wni},id_keluarga=#{id_keluarga},agama=#{agama},pekerjaan=#{pekerjaan},status_perkawinan=#{status_perkawinan},status_dalam_keluarga=#{status_dalam_keluarga},golongan_darah=#{golongan_darah} "+
			" WHERE id=#{id}")
    void updatePenduduk (PendudukModel penduduk);
    
	@Select("SELECT * FROM keluarga where nomor_kk=#{nomor_kk}")
	KeluargaModel selectKeluargaByNKK(@Param("nkk") String nomor_kk);
	
	@Update("UPDATE keluarga SET nomor_kk=#{nomor_kk}, alamat=#{alamat}, rt=#{rt}, rw=#{rw}, id_kelurahan=#{id_kelurahan} WHERE id=#{id}")
	void updateKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE penduduk SET is_wafat=1 WHERE nik=#{nik}")
	void deactivatePenduduk(@Param("nik") String nik);
	
	@Select("SELECT count(*) FROM PENDUDUK P, KELUARGA K WHERE P.id_keluarga=K.id and P.is_wafat=0 and P.id_keluarga=#{id}")
	int countKeluargaAlive(@Param("id") String id);
	
	@Select("UPDATE keluarga SET is_tidak_berlaku=1 WHERE id=#{id}")
	void deactivateKeluarga(@Param("id") String id);
	
	@Select("SELECT * FROM KOTA")
	List<KotaModel> selectAllKota();
	
	@Select("SELECT * FROM KECAMATAN WHERE id_kota=#{id}")
	List<KecamatanModel> selectKecamatanByKota(@Param("id") String id); 

	@Select("SELECT * FROM KOTA WHERE id=#{id}")
	KotaModel selectKotaById(@Param("id") String id);
	
	@Select("SELECT * FROM KELURAHAN WHERE id_kecamatan=#{id}")
	List<KelurahanModel> selectKelurahanByKecamatan(@Param("id")String id);
	
	@Select("SELECT * FROM KECAMATAN WHERE id=#{id}")
	KecamatanModel selectKecamatanById(@Param("id") String id);

	@Select("SELECT p.nik, p.nama, p.jenis_kelamin FROM PENDUDUK P, KELUARGA K, KELURAHAN KL\r\n" + 
			"WHERE P.id_keluarga=K.id and K.id_kelurahan=KL.id and KL.id=#{id}")
	List<PendudukModel> selectPendudukByKelurahan(@Param("id") String id);
	
	@Select("SELECT * FROM KELURAHAN WHERE id=#{id}")
	KelurahanModel selectKelurahanById(@Param("id") String id);
	
	@Select("SELECT p.nik,p.nama,p.tanggal_lahir FROM penduduk p, keluarga k WHERE p.id_keluarga = k.id AND k.id_kelurahan=#{id}" + 
			"ORDER BY P.tanggal_lahir DESC " + 
			"LIMIT 1")
	PendudukModel selectPendudukMuda(@Param("id") String id);
	@Select("SELECT p.nik,p.nama,p.tanggal_lahir FROM penduduk p, keluarga k WHERE p.id_keluarga = k.id AND k.id_kelurahan=#{id}" + 
			"ORDER BY P.tanggal_lahir ASC " + 
			"LIMIT 1")
	PendudukModel selectPendudukTua(@Param("id") String id);
}

