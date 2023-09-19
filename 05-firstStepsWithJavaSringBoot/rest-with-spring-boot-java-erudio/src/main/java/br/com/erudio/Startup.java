package br.com.erudio;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
		
		/*
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        
        //d54007e56376adc9b83944f37bb108c50f3ab17b15d05073a325da2dc857befc65dada33e6fb3f71cf40c9f204a8da16
        String result = passwordEncoder.encode("admin234");
        System.out.println("My hash " + result);
        */
		
		Map<String, PasswordEncoder> encoders = new HashMap<>();
        
        Pbkdf2PasswordEncoder pbkdf2Encoder =
                new Pbkdf2PasswordEncoder(
                    "", 8, 185000,
                    SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        
        String result1 = passwordEncoder.encode("admin123");
//        String result2 = passwordEncoder.encode("admin234");
        System.out.println("My hash result1 " + result1);
//        System.out.println("My hash result2 " + result2);
        
        
	}
	
}
