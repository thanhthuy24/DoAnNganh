package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.TokenDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Token;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.TokenRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    public final TokenRepository tokenRepository;
    public final UserRepository userRepository;

    @Override
    public Token createToken(TokenDTO tokenDTO) throws DataNotFoundException {
        User existingUser = userRepository.findById(tokenDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found!!"));

        Token newToken = Token.builder()
                .token(tokenDTO.getToken())
                .user(existingUser)
                .build();
        tokenRepository.save(newToken);
        return newToken;
    }
}
