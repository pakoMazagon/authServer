package com.tpv.auth.domain.vo;

import java.util.List;

public record CreateUserVO(String username, String password, String mail, List<String> roles) {
}
