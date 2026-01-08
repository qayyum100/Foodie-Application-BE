package com.org.Auth_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}


//import io.jsonwebtoken.security.Keys;
//import java.util.Base64;
//
//public class KeyGen {
//	public static void main(String[] args) {
//		byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
//		System.out.println(Base64.getEncoder().encodeToString(key));
//	}
//}