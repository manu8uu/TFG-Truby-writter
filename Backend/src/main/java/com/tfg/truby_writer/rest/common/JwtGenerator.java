package com.tfg.truby_writer.rest.common;

public interface JwtGenerator {
	
    String generateToken(JwtInfo info);
	
    JwtInfo getInfo(String token);
}