package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  void memberRepositoryTest() {
    Member savedMember = new Member();
    savedMember.setUsername("John");
    memberRepository.save(savedMember);
    Member foundMember = memberRepository.findById(savedMember.getId());
    assertThat(foundMember).isEqualTo(savedMember);
  }

}