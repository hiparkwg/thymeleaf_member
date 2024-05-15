package kr.jobtc.member;

import java.util.List;

import lombok.Data;

@Data
public class MemberVo {
    String id;
    String name;
    String phone;
    String address1;
    String photo; // 대표사진 1잗
    List<PhotoVo> photos; // 사진 여러장
}
