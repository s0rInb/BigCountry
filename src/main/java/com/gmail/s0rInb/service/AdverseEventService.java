package com.gmail.s0rInb.service;

import com.gmail.s0rInb.entities.nis.AdverseEvent;
import com.gmail.s0rInb.entities.nis.AdverseEvent;
import com.gmail.s0rInb.repository.AdverseEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adverseEventService")
@Transactional
public class AdverseEventService {
	@Autowired
	AdverseEventRepository adverseEventRepository;

	public AdverseEvent findById(Long id) {
		return adverseEventRepository.findOne(id);
	}

	public AdverseEvent save(AdverseEvent adverseEvent){
		return adverseEventRepository.save(adverseEvent);
	}

	public void delete(AdverseEvent adverseEvent){
		adverseEventRepository.delete(adverseEvent);
	}
}
