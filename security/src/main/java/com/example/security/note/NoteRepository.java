package com.example.security.note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.user.User;

public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * 유저의 ID로 노트 목록 조회
     *
     * @param user 노트 목록을 조회할 유저
     * @return 해당 유저의 노트 목록
     */
    List<Note> findByUserOrderByIdDesc(User user);

    Note findByIdAndUser(Long id, User user);

}
