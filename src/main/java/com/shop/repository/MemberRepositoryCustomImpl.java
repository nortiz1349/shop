package com.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.Role;
import com.shop.dto.MainMemberDto;
import com.shop.dto.MemberSearchDto;
import com.shop.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("name", searchBy)){
            return QMember.member.name.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Member> getAdminMemberPage(MemberSearchDto memberSearchDto, Pageable pageable) {

        List<Member> content = queryFactory
                .selectFrom(QMember.member)
                .where(searchByLike(memberSearchDto.getSearchBy(), memberSearchDto.getSearchQuery()))
                .orderBy(QMember.member.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QMember.member)
                .where(searchByLike(memberSearchDto.getSearchBy(), memberSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

}