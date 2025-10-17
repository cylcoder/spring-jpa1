package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  private MemberService memberService;

  @Test
  void shouldJoinMemberSuccessfully() {
    // Given
    Member savedMember = new Member();
    savedMember.setUsername("John");

    // When
    memberService.join(savedMember);

    // Then
    Member foundMember = memberService.findById(savedMember.getId());
    assertThat(foundMember).isEqualTo(savedMember);
  }

  @Test
  void shouldThrowExceptionWhenDuplicateMemberJoins() {
    // Given
    Member savedMember1 = new Member();
    savedMember1.setUsername("John");
    Member savedMember2 = new Member();
    savedMember2.setUsername("John");

    // When
    memberService.join(savedMember1);

    // Then
    assertThatThrownBy(() -> memberService.join(savedMember2))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("이미 존재하는 회원입니다.");
  }

}