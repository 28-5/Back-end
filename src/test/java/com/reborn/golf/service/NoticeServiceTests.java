package com.reborn.golf.service;

import com.reborn.golf.dto.NoticeDto;
import com.reborn.golf.dto.PageRequestDto;
import com.reborn.golf.dto.PageResultDto;
import com.reborn.golf.entity.NoticeFractionation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class NoticeServiceTests {

    @Autowired
    NoticeService noticeService;

    @Test
    public void registerTest() {
        IntStream.rangeClosed(0, 100).forEach(i -> {
            NoticeDto noticeDto = NoticeDto.builder()
                    .title("title.........." + i)
                    .content("content.........." + i)
                    .views(0)
                    .build();

            noticeService.register(((i % 10) + 1), null, noticeDto, NoticeFractionation.NOTICE);
            noticeDto = NoticeDto.builder()
                    .title("Qna.........." + i)
                    .content("Qna.........." + i)
                    .views(0)
                    .build();
            noticeService.register((i % 10) + 1, null, noticeDto, NoticeFractionation.QNA);
        });
        IntStream.rangeClosed(0, 100).forEach(i -> {
            NoticeDto noticeDto = NoticeDto.builder()
                    .title("Qna..........plus" + i)
                    .content("content..........plus" + i)
                    .views(0)
                    .build();

            noticeService.register((i % 10) + 1, (i % 10) + 1L, noticeDto, NoticeFractionation.QNA);
        });
    }


    @Test
    public void getListNoticeTest(){
        PageRequestDto pageRequestDto = PageRequestDto.builder().page(1).size(10).build();
        var resultDto= noticeService.getList(pageRequestDto, NoticeFractionation.NOTICE);
        for (var list: resultDto.getDtoList()) {
            System.out.println(list);
        }
    }


    @Test
    public void getListQnaTest(){
        PageRequestDto pageRequestDto = PageRequestDto.builder().page(1).size(10).build();
        var resultDto= noticeService.getList(pageRequestDto, NoticeFractionation.QNA);
        for (var list: resultDto.getDtoList()) {
            System.out.println(list);
        }
    }

    @Test
    public void readTest(){
        System.out.println(noticeService.read(1L, NoticeFractionation.NOTICE));
    }

    @Test
    public void removeTest(){
        noticeService.remove(1L, NoticeFractionation.NOTICE);

    }
}
