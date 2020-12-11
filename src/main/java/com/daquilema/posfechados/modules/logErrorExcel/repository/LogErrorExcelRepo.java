package com.daquilema.posfechados.modules.logErrorExcel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daquilema.posfechados.modules.logErrorExcel.entity.LogErrorExcel;

@Repository
public interface LogErrorExcelRepo extends JpaRepository<LogErrorExcel, Long> {

	
	@Query("SELECT a" + " FROM LogErrorExcel a WHERE"
			+ " (:empresaId IS NULL OR a.company.id=:empresaId) AND " + " (:ejercicioId IS NULL OR a.fiscalYear.id=:ejercicioId) AND "
			+ " DATE(a.creationDate) BETWEEN DATE(:startDate) AND DATE(:endDate)")
	public List<LogErrorExcel> findLogBySearch(@Param("empresaId") Long empresaId, @Param("ejercicioId") Long ejercicioId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
