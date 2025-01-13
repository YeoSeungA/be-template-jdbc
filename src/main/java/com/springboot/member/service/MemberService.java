package com.springboot.member.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * V2
 *  - 메서드 구현
 *  - DI 적용
 */
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
//        아래는 안티패턴 아래에 throw 던지는걸 좋아하지 않는다.
////        email로 조회해서 중복여부 확인하는 로직
//        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
//
//        if(optionalMember.isPresent ()) {
//            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
//        }
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
//        Member savedMember = memberRepository.save(member);
//        return savedMember;
//        요 member은 memberId가 있다. email 중복여부를 알아야 한다. => 비즈니스 로직
        verifyExistEmail(member.getEmail());
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
//        findMember 얘가 원본
//        throw는 아래 코드에서 발생
        Member findMember = findVerifiedMember(member.getMemberId());

////        name의 값이 안 바뀔때 field의 값이 null
//////        객체가 생성되면 초기화가 발생해서 null 이면 바뀌지 않다고 이해할 수 있다.
//        null 처리는 Optional이 기본!
        Optional.ofNullable ((member.getName ()))
                .ifPresent (name -> findMember.setName(name));
//        if(member.getEmail() != null) {
//            findMember.setName(member.getName());
//        }
        Optional.ofNullable((member.getPhone ()))
                .ifPresent(phone -> findMember.setPhone(phone));

        return memberRepository.save(findMember);
//        throw new BusinessLogicException ( ExceptionCode.NOT_IMPLEMENTATION );
    }

    public Member findMember(long memberId) {
//        Member findMember = findVerifiedMember(memberId);
//        return findMember;
        return findVerifiedMember(memberId);
    }

    public List<Member> findMembers() {
//        없으면 빈 List를 주면 된다. 예외 발생 X
        return (List<Member>)memberRepository.findAll();
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
    }

    public void deleteMember(long memberId) {
        Member findmember = findVerifiedMember(memberId);
        memberRepository.delete(findmember);
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
    }

    private void verifyExistEmail(String email) {
        //        email로 조회해서 중복여부 확인하는 로직
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if(optionalMember.isPresent ()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    private Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member findMember = optionalMember.orElseThrow (() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }
}
