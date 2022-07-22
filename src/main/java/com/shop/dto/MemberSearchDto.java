package com.shop.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchDto {

    private String searchBy;    // 조회 유형 : itemNm, createBy

    private String searchQuery = "";    // 조회할 검색어를 저장할 변수, searchBy 에 따라 기준이 바뀜


}
