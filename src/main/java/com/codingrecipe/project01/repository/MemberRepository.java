package com.codingrecipe.project01.repository;

import com.codingrecipe.project01.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    // 의존성 주입
    // 마이바티스 클라스
    private final SqlSessionTemplate sql;
    // db와 관련
    // 마이바티스를 사용
    public int save(MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);

        // Member -> memberMapper namespace명
        // .save -> memberMapper id명
        return sql.insert("Member.save", memberDTO);
    }
}
