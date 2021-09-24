package com.reborn.golf.controller;

import com.reborn.golf.dto.MemberDto;
import com.reborn.golf.security.dto.AuthMemeberDto;
import com.reborn.golf.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 회원가입할 때 사용
    @PostMapping()
    public ResponseEntity<String> register(@RequestBody @Valid MemberDto memberDto) {
        if (memberService.register(memberDto)) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The same email already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    //회원 정보 조회할 때 사용
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MemberDto> read(@AuthenticationPrincipal AuthMemeberDto authMemeberDto) {
        Integer idx = authMemeberDto.getIdx();
        log.info(idx);
        MemberDto memberDto = memberService.read(idx);
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    //회원 정보 수정할 때 사용
    @PutMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> modify(@AuthenticationPrincipal AuthMemeberDto authMemeberDto, @RequestBody @Valid MemberDto memberDto) throws Exception {
        Integer idx = authMemeberDto.getIdx();
        memberService.modify(idx, memberDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    //회원 탈퇴할 때 사용
    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> remove(@AuthenticationPrincipal AuthMemeberDto authMemeberDto) {
        Integer idx = authMemeberDto.getIdx();
        memberService.remove(idx);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
