package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.nis.AdverseEvent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("AdverseEventRepository")
@Transactional
public interface AdverseEventRepository extends PagingAndSortingRepository<AdverseEvent,Long>{
}
