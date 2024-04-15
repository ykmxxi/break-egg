package com.example.security.admin;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.security.note.Note;
import com.example.security.note.NoteService;
import com.example.security.user.User;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final NoteService noteService;

    public AdminController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * 관리자인 경우 노트 조회
     *
     * @return admin/index.html
     */
    @GetMapping
    public String getNoteForAdmin(final Authentication authentication, final Model model) {
        User user = (User) authentication.getPrincipal();

        List<Note> notes = noteService.findByUser(user);
        model.addAttribute("notes", notes);
        return "admin/index";
    }

}
