package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.nis.AdverseEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository("AdverseEventRepository")
@Transactional
public interface AdverseEventRepository extends PagingAndSortingRepository<AdverseEvent,Long>{
	@Query("select new map (a.id as id, a.patientId as pId, a.sendDate as sendDate) " +
			"from AdverseEvent a")
	List<HashMap> findHashMapList();

}
