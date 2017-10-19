package com.gmail.s0rInb.service;

import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("patientService")
@Transactional
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;

	public List<Patient> findAll() {
		return patientRepository.findAllByOrderByIdDesc();
	}

	public List<Patient> findListPatientId() {
		return patientRepository.findListPatientId();
	}

	public Patient findById(Long id) {
		return patientRepository.findOne(id);
	}

	public Patient save(Patient patient){
		return patientRepository.save(patient);
	}

	public void delete(Patient patient){
		patientRepository.delete(patient);
	}
}
