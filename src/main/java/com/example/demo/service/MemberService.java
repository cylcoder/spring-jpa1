package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Long join(Member member) {
    validateDuplicateMember(member);
    memberRepository.save(member);
    return member.getId();
  }

  private void validateDuplicateMember(Member member) {
    List<Member> foundMembers = memberRepository.findByName(member.getUsername());
    if (!foundMembers.isEmpty()) {
      throw new IllegalStateException("Member already exists.");
    }
  }

  public List<Member> findAll() {
    return memberRepository.findAll();
  }

  public Member findById(Long id) {
    return memberRepository.findById(id);
  }

}
