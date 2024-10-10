package com.htt.ecourse.service;

import com.htt.ecourse.dtos.TokenDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Token;

public interface TokenService {
    Token createToken(TokenDTO tokenDTO) throws DataNotFoundException;
}
