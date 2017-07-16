package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PatientRepository")
public interface PatientRepository extends PagingAndSortingRepository<Patient,Long> {
	List<Patient> findAll();

	@Query("select new map(p.id as id) from Patient p")
	List<Patient> findListPatientId();
}
