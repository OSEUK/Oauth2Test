package com.example.retrocam.dto;

public interface OAuth2Response {

    //제공자 (Ex. naver, google, ...)
    String getProvider();
    //제공자에서 발급해주는 아이디(번호)
    String getProviderId();
    //이메일
    String getEmail();
    //사용자 실명 (설정한 이름)
    String getName();
}

/**
 * Naver 데이터 형식
 * {
 * 		resultcode=00, message=success, response={id=123123123, name=개발자유미}
 * }
 *
 * Gogle 데이터 형식
 * {
 * 		resultcode=00, message=success, id=123123123, name=개발자유미
 * }
 */
