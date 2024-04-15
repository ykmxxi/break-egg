package com.example.security.notice;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.security.note.NoteRegisterDto;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(final NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * 공지사항 조회
     *
     * @return notice/index.html
     */
    @GetMapping
    public String getNotice(final Model model) {
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "notice/index";
    }

    /**
     * 공지사항 등록
     *
     * @param noteDto 노트 등록 Dto
     * @return redirect to notice/index.html
     */
    @PostMapping
    public String postNotice(@ModelAttribute final NoteRegisterDto noteDto) {
        noticeService.saveNotice(noteDto.title(), noteDto.content());
        return "redirect:notice";
    }

    /**
     * 공지사항 삭제
     *
     * @param id 공지사항 ID
     * @return redirect to notice/index.html
     */
    @DeleteMapping
    public String deleteNotice(@RequestParam final Long id) {
        noticeService.deleteNotice(id);
        return "redirect:notice";
    }

}
