package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.Files.Files;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository("fileService")
@Transactional
public interface FileRepository extends PagingAndSortingRepository<Files,Long>{

}
