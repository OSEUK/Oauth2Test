package com.example.retrocam.service;

import com.example.retrocam.dto.*;
import com.example.retrocam.entity.UserEntity;
import com.example.retrocam.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // OAuth2User를 로드하여 사용자 정보를 가져옵니다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 로드한 사용자 정보 로그 출력
        log.info("oAuth2UserInfo = {}", oAuth2User);

        // 현재 사용자가 인증한 클라이언트의 등록 ID를 가져옵니다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2Response 객체를 초기화합니다.
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }

        // Provider, id를 통해 username이란 식별자 생성
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        // 해당 username으로 현재 데이터베이스에 같은 데이터가 존재하는지
        UserEntity existData = userRepository.findByUsername(username);

        // 데이터베이스에 없을 경우 --> 현재 정보를 바탕으로 새로운 UserEntity 생성
        if (existData == null) {

            // 데이터베이스에 삽입
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setName(oAuth2Response.getName());
            userEntity.setRole(Role.USER);

            userRepository.save(userEntity);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(Role.USER);

            // 내가 로그인한 정보가 잘 들어왔는지 체크
            log.info("생성 유저 정보 = {}", userDTO);

            return new CustomOAuth2User(userDTO);
        }
        // 데이터베이스에 있을 경우 -->
        else {

            // 기존 데이터 업데이트
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());

            log.info("현재 유저 정보 = {}", userDTO);

            return new CustomOAuth2User(userDTO);
        }
    }
}
