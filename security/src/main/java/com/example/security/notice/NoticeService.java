package com.example.security.notice;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(final NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    /**
     * 모든 공지사항 조회
     *
     * @return 모든 공지사항 List
     */
    @Transactional(readOnly = true)
    public List<Notice> findAll() {
        return noticeRepository.findAll(Sort.by(Direction.DESC, "id"));
    }

    /**
     * 공지사항 저장
     *
     * @param title   제목
     * @param content 내용
     * @return 저장된 공지사항
     */
    public Notice saveNotice(final String title, final String content) {
        return noticeRepository.save(new Notice(title, content));
    }

    /**
     * 공지사항 삭제
     *
     * @param id ID
     */
    public void deleteNotice(final Long id) {
        noticeRepository.findById(id).ifPresent(noticeRepository::delete);
    }

}
