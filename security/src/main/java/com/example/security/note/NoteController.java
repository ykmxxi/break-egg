package com.example.security.note;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.security.user.User;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * 노트(게시글) 조회
     *
     * @return 노트 view (note/index.html)
     */
    @GetMapping
    public String getNote(final Authentication authentication, final Model model) {
        User user = (User) authentication.getPrincipal();
        List<Note> notes = noteService.findByUser(user);
        // note/index.html 에서 notes 사용가능
        model.addAttribute("notes", notes);
        // note/index.html 제공
        return "note/index";
    }

    /**
     * 노트 저장
     */
    @PostMapping
    public String saveNote(final Authentication authentication, @ModelAttribute final NoteRegisterDto noteDto) {
        User user = (User) authentication.getPrincipal();
        noteService.saveNote(user, noteDto.title(), noteDto.content());
        return "redirect:note";
    }

    /**
     * 노트 삭제
     */
    @DeleteMapping
    public String deleteNote(final Authentication authentication, @RequestParam final Long id) {
        User user = (User) authentication.getPrincipal();
        noteService.deleteNote(user, id);
        return "redirect:note";
    }

}
