package pl.javasolutions.security.samples;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Constans {

    public static final String DEFAULT_USER_ID = "1234567890";
    public static final String DEFAULT_USER_EMAIL = "john.doe@example.com";
    public static final String DEFAULT_USER_FULL_NAME = "John Doe";
    public static final String DEFAULT_USER_DETAILS_USERNAME = "johndoe";

    public static final String DEFAULT_TOKEN_ISSUER = "javasolutions";
    public static final String DEFAULT_TOKEN_SECRET = "c2VjcmV0X3NlY3JldA==";
    public static final String DEFAULT_TOKEN_ALGORITHM = "HS256";
    public static final String DEFAULT_TOKEN_TYPE = "JWT";
    public static final String DEFAULT_TOKEN_DEFAULT_AUTHORITY_ROLE = "ROLE_DEMO";
    public static final String DEFAULT_TOKEN_TEST_ROLE = "test";
    public static final String DEFAULT_TOKEN_TEST_AUTHORITY_ROLE = "ROLE_" + DEFAULT_TOKEN_TEST_ROLE.toUpperCase();

    public static final String DEFAULT_JWT_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJpc3MiOiJqYXZhc29sdXRpb25zIiwibmFtZSI6IkpvaG4gRG9lIiwiZXhwIjoxNTE1ODEwNjIyLCJsb2NhbGUiOiJwbCIsImlhdCI6MTUxNTgwNzAyMiwiZW1haWwiOiJqb2huLmRvZUBleGFtcGxlLmNvbSJ9.7NzQlFFQRNtXBlAgTr64YgyZ-d-hhlrejMsJ_Q0c_5sq4OXk_adXJkM0ZwSn-1CgfbB-yu7ihwiiN19uHf8ISg";
    public static final String DEFAULT_JWT_TOKEN_WITH_DETAILS = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huZG9lIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJyb2xlcyI6WyJ0ZXN0Il0sImlzcyI6ImphdmFzb2x1dGlvbnMiLCJuYW1lIjoiSm9obiBEb2UiLCJleHAiOjE1MTU4MTA2MjIsImxvY2FsZSI6InBsIiwiaWF0IjoxNTE1ODA3MDIyLCJlbWFpbCI6ImpvaG4uZG9lQGV4YW1wbGUuY29tIn0.mdEs4hvtVhJiNEEWa7ygtfHzWf1OF1GdwSkQXLNsG8bIwZMst4bwD25A_xoN-fGRlpndqiHsquvZjSYw9X6WhQ";

    public static final ZonedDateTime DEFAULT_TOKEN_CREATE_DATE = LocalDateTime.of(2018, 1, 13, 1, 30, 22).atZone(ZoneId.of("GMT"));
    public static final ZonedDateTime DEFAULT_TOKEN_EXPIRATION_DATE = LocalDateTime.of(2018, 1, 13, 2, 30, 22).atZone(ZoneId.of("GMT"));
}
