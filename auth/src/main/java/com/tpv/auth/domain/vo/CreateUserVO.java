package com.tpv.auth.domain.vo;

import java.util.List;

public record CreateUserVO(String username, String password, List<String> roles) {
}
