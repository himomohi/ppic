package com.appcaster.ppic.service;


import com.appcaster.ppic.entity.Board;
import com.appcaster.ppic.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

        @Autowired
        private BoardRepository boardRepository;  //기능구현에 필요한 인터페이스를 불러온다.

        public void write(Board board){   //기능을 구현할 메서드 작성
            boardRepository.save(board);  // 보드에 저장하는 기능

        }

        public Page<Board> boardList(Pageable pageable){
            return boardRepository.findAll(pageable);
        }

        //게시글 삭제
        public void boardDelete(Integer no){
             boardRepository.deleteById(no);
        }

        //게시글 상세 내용
        public Board boardView(Integer no){

            return boardRepository.findById(no).get(); //옵셔널으로 반환하기때문에 .get() 처리해준다.

        }


        public Page<Board> boardSearchList(String searchKeyword,Pageable pageable){
            return boardRepository.findByTitleContaining(searchKeyword,pageable);
        }

}
