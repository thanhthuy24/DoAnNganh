package com.htt.ecourse.service.impl;

import com.google.api.Http;
import com.htt.ecourse.dtos.TokenDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Token;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.TokenRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    public final TokenRepository tokenRepository;
    public final UserRepository userRepository;

    @Override
    public Token createToken(TokenDTO tokenDTO) throws DataNotFoundException {
        User existingUser = userRepository.findById(tokenDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!"));

        Token existingToken = tokenRepository.findByTokenAndUserId(tokenDTO.getToken(), existingUser.getId());
        if (existingToken != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Token already exists");
        }

        Token newToken = Token.builder()
                .token(tokenDTO.getToken())
                .user(existingUser)
                .build();
        tokenRepository.save(newToken);
        return newToken;
    }

    @Override
    public void saveToken(String token, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!"));

        Optional<Token> existingToken = tokenRepository.findByToken(token);
        if (existingToken.isPresent()) {
            return;
        }

        Token fcmToken  = new Token();
        fcmToken .setToken(token);
        fcmToken .setUser(user);
        tokenRepository.save(fcmToken );

    }

    @Override
    public void removeTokensByUserId(Long userId) {
        tokenRepository.deleteByUserId(userId);
    }

    @Override
    public void removeFcmToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }
}
