package com.appcaster.ppic.repository;

import com.appcaster.ppic.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository  //저장소 생성
public interface BoardRepository extends JpaRepository<Board,Integer> {  //JpaRepository<Entity,ID 타입> 상속받는다.

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);



}
