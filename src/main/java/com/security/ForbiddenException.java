package com.security;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="You do not have admin role. Access denied.")
public class ForbiddenException extends RuntimeException {}
