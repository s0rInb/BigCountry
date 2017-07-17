package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.FileLink;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("fileService")
@Transactional
public interface FileRepository extends PagingAndSortingRepository<FileLink,Long>{
	List<FileLink> findAllByPatientId(Long id);
}
