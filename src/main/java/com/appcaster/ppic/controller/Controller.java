package com.appcaster.ppic.controller;

import com.appcaster.ppic.entity.Board;
import com.appcaster.ppic.repository.BoardRepository;
import com.appcaster.ppic.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String main(){

        return "boardWrite";

    }
    
    
    @GetMapping("/boardWrite")
    public String boardWrite(){
        return "boardWrite";
    }
    @PostMapping("/write") //게시글 작성 처리 페이지
    public String boardWriteOk(Board board,Model model){
        board.setCount(+1);
        boardService.write(board);



            model.addAttribute("message","글이 작성되었습니다.");
            model.addAttribute("searchUrl","/list");


        return "message";
    }

    @GetMapping("/list")  //리스트 페이지
    public String boardList(Model model,
                            @PageableDefault(page = 0,size = 10,sort = "no",direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){

        Page<Board> list = null;
        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else{
           list = boardService.boardSearchList(searchKeyword, pageable);

        }

        int nowPage = list.getPageable().getPageNumber()+1;
        int startPage=Math.max(nowPage-4,1); //매개변수 사이 의 값중 높은값을 반환한다.

        int endPage=Math.min(nowPage+5,list.getTotalPages());

        model.addAttribute("list",list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "list";
    }

    @GetMapping("/delete")
    public String boardDelete(Integer no,Model model){

        boardService.boardDelete(no);
        model.addAttribute("message","글이 삭제되었습니다.");
        model.addAttribute("searchUrl","/list");

        return "message";
    }

    @GetMapping("/view") //localhost:8080/view?no=1
    public String boardView(Model model,Integer no,Board board){
        Board boardTemp = boardService.boardView(no);


        boardTemp.setCount(boardTemp.getCount());






        model.addAttribute("board",boardService.boardView(no));


        return "boardView";
    }

    @GetMapping("/modify/{no}")
    public String boardModify(@PathVariable("no") Integer no,Model model,Board board){
        Board boardTemp = boardService.boardView(no);
        boardTemp.setPassword(board.getPassword());
        model.addAttribute("board",boardService.boardView(no));


        System.out.println("보드  패스워드 = " + board.getPassword());
        System.out.println("boardTemp.getPassword() = " + boardTemp.getPassword());
//
//        if(board.getPassword().equals(boardTemp.getPassword())){
//            model.addAttribute("message","비밀번호가 일치합니다");
//            model.addAttribute("searchUrl","/list");
//
//        }else if(board.getPassword().equals(null)){
//            model.addAttribute("message","비밀번호가 일치합니다");
//            model.addAttribute("searchUrl","/list");
//
//        }else{
//            model.addAttribute("message","비밀번호가 일치하지않습니다");
//            model.addAttribute("searchUrl","/list");
//
//        }





        return "boardModify";

    }

    @PostMapping("/update/{no}")
    public String boardUpdate(@PathVariable("no") Integer no,Board board,Model model){
       Board boardTemp = boardService.boardView(no);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContents(board.getContents());
        boardTemp.setName(board.getName());

        
        boardService.write(boardTemp);
        System.out.println("boardTemp = " + boardTemp);  //테스트 구문





        if(board != boardTemp){  // 수정사항이 있을시
            model.addAttribute("message","글이 수정되었습니다.");
            model.addAttribute("searchUrl","/list");
        }else{// 수정사항이 없을때
            model.addAttribute("message","수정된사항이 없습니다.");
            model.addAttribute("searchUrl","/modify/"+no);



        }



        return "message";
    }

}
