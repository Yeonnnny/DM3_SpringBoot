package net.kdigital.board.controller;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.board.dto.BoardDTO;
import net.kdigital.board.service.BoardService;
import net.kdigital.board.util.PageNavigator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 파일의 경로 요청
    @Value("${spring.servlet.multipart.location}")
    String uploadPath;

    // 페이지 당 글의 개수
    @Value("${user.board.pageLimit}")
    int pageLimit; // 한 페이지에 보여 줄 글의 개수

    /**
     * 글 목록 요청
     * 1 : index에서 넘어올 경우에 searchItem과 searchWord가 없으므로 기본값 세팅, 1 페이지 요청
     * 2 : 목록에서 검색하여 넘어올 경우 searchItem과 searchWord가 있으므로 그 값 사용, 1 페이지 요청
     * 3 : 목록의 하단에서 페이지 선택한 경우 선택한 값을 사용, searchItem과 searchWord가 없으므로 기본값 세팅
     * @return
     */
    @GetMapping("/boardList")
    public String boardList(@PageableDefault(page=1) Pageable pageable, //페이징을 해주는 객체, 요청한 페이지가 없으면 1로 세팅
                            @RequestParam(name="searchItem", defaultValue = "boardTitle")String searchItem,
                            @RequestParam(name="searchWord", defaultValue = "") String searchWord,   
                            Model model) {

        // List<BoardDTO> list = boardService.selectAll(searchItem,searchWord);
        // Pagination
        Page<BoardDTO> list = boardService.selectAll(pageable, searchItem, searchWord);
        
        int totalPages = (int)list.getTotalPages();
        int page = pageable.getPageNumber();

        PageNavigator  navi = new PageNavigator(pageLimit, page, totalPages);

        // 댓글 수
        Map<Long, String> replyCount = boardService.replyCount();
    
        // log.info("{}",replyCount);
        // log.info("{}",replyCount.get(125L));

        model.addAttribute("list", list);
        model.addAttribute("replyCount", replyCount);
        model.addAttribute("searchItem", searchItem);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("navi", navi);

        return "board/boardList";
    }
    


    /**
     * 글쓰기 화면 요청
     * 
     * @return
     */
    @GetMapping("/boardWrite")
    public String boardWrite() {
        log.info("글쓰기 화면 요청");
        return "board/boardWrite";
    }

    /**
     * 글 등록 요청
     * 
     * @param boardDTO
     * @return
     */
    @PostMapping("/boardWrite")
    public String boardWrite(@ModelAttribute BoardDTO boardDTO) {
        
        boardService.insertBoard(boardDTO);

        return "redirect:/board/boardList";
    }

    /**
     * boardNum에 해당하는 글 한 개 조회
     * 
     * @param boardNum
     * @param model
     * @return
     */
    @GetMapping("/boardDetail")
    public String boardDetail(@RequestParam(name = "boardNum") Long boardNum,
                                @RequestParam(name="searchItem") String searchItem,
                                @RequestParam(name="searchWord") String searchWord,
                                HttpServletRequest request,
                                Model model) {

        BoardDTO boardDTO = boardService.selectOne(boardNum);
        String contextPath = request.getContextPath();
        
        // 조회수 증가
        boardService.incrementHitCount(boardNum);
        
        model.addAttribute("board", boardDTO);
        model.addAttribute("searchItem", searchItem);
        model.addAttribute("searchWord", searchWord);
        // contextPath 보내기
        model.addAttribute("contextPath", contextPath); // 원래 thymeleaf로 @{요청}하면 알아서 contextPath를 붙여주는데 JQuery에선 적용이 안되므로 contextPath를 따로 보내줘야 함
        
        return "board/boardDetail";
    }
 

    /**
     * boardNum에 해당하는 글 삭제
     * 
     * @param boardNum
     * @return
     */
    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam(name = "boardNum") Long boardNum,
                                @RequestParam(name="searchItem") String searchItem,
                                @RequestParam(name="searchWord") String searchWord,
                                RedirectAttributes rttr
                                ) {
    
        boardService.deleteOne(boardNum);

        rttr.addAttribute("searchItem",searchItem);
        rttr.addAttribute("searchWord", searchWord);

        return "redirect:/board/boardList";
    }

    /**
     * boardNum에 해당하는 게시글 수정을 위한 요청
     * 
     * @param boardNum
     * @param model
     * @return
     */
    @GetMapping("/boardUpdate")
    public String boardUpdate(@RequestParam(name = "boardNum") Long boardNum, 
                                @RequestParam(name = "searchItem") String searchItem,
                                @RequestParam(name = "searchWord") String searchWord, 
                                Model model) {
        BoardDTO boardDTO = boardService.selectOne(boardNum);
        model.addAttribute("board", boardDTO);
        model.addAttribute("searchItem", searchItem);
        model.addAttribute("searchWord", searchWord);
        
        return "board/boardUpdate";
    }

    /**
     * 변경된 boardDTO를 DB에 적용시키기 위한 수정작업 요청
     * @param boardDTO 
     * @param rttr
     * @return
     */
    @PostMapping("/boardUpdate")
    public String boardUpdate(@ModelAttribute BoardDTO boardDTO, 
                                @RequestParam(name="searchItem") String searchItem, 
                                @RequestParam(name = "searchWord") String searchWord, 
                                RedirectAttributes rttr) {
        
        boardService.updateOne(boardDTO);

        rttr.addAttribute("boardNum", boardDTO.getBoardNum());
        rttr.addAttribute("searchItem",searchItem);
        rttr.addAttribute("searchWord", searchWord);
        return "redirect:/board/boardDetail";
    }
 

    /**
     * boardNum에 해당하는 첨부파일 다운로드 요청
     * @param boardNum
     * @return
     */
    @GetMapping("/download")
    public String download(@RequestParam(name = "boardNum") Long boardNum, HttpServletResponse response) {
        
        BoardDTO boardDTO =  boardService.selectOne(boardNum);

        String originalFileName = boardDTO.getOriginalFileName();
        String savedFileName = boardDTO.getSavedFileName();
        
        log.info("오리지날 : {}",originalFileName);
        log.info("저장명 : {}",savedFileName);
        log.info("디렉토리명 : {}",uploadPath);
        

        try {
            String tempName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString()); // 파일명에 한글이 섞여있을 때를 대비하기 위함
            response.setHeader("Content-Disposition", "attachment;filename="+tempName);  
            // 위 코드가 없을 경우 브라우저가 실행 가능한 파일(이미지 파일이 대표적인 예임)인 경우 브라우저 자체에서 오픈함
            // 즉, 위 코드는 브라우저 자체에서 실행되도록 하지 않고 다운로드 받게 하기 위한 코드임
        } catch (UnsupportedEncodingException e) { //encoding 하지 못할 경우 에러 처리
            e.printStackTrace();
        }

        String fullPath = uploadPath+"/"+savedFileName; 

        // 스트림 설정 (실제 다운로드)
        FileInputStream filein = null;
        ServletOutputStream fileout=null; // 원격지의 장소에 데이터를 쏠 떄

        // local에 있는 파일을 메모리로 끌어와야 함
        try {
            filein = new FileInputStream(fullPath); // 하드디스크->메모리에 올림 (서버입장에서의 로컬이기 떄문에 input 작업임)
            fileout = response.getOutputStream();   // 웹에서 원격지의 데이터를 쏴주는 것. 로컬에서 벗어난 다른 쪽으로 데이터를 쏘는 역할

            FileCopyUtils.copy(filein, fileout);    // copy(원본, 내보낼 객체) : 원본을 읽어서 내보냄

            fileout.close();  // 연 순서의 반대로 닫아야 함
            filein.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/incrementFavoriteCount")
    @ResponseBody
    public int incrementFavoriteCount(@RequestParam(name = "boardNum") Long boardNum,
                                        @RequestParam(name = "count") int count) {
        int like = boardService.incrementFavoriteCount(boardNum,count);
        return like;
    }
    



}