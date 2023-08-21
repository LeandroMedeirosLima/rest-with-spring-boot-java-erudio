package br.com.erudio.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.exceptions.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {
    
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
    
    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; //1h ;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    Algorithm algorithm = null;
    
    public TokenVO createAccessToken(String userName, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        
        String accessToken = getAccessToken(userName, roles, now, validity);
        String refreshToken = getRefreshToken(userName, roles, now, validity);
        
        return new TokenVO(userName, true, now, validity, accessToken, refreshToken);
    }
    
    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decoedJWT = verifier.verify(token);
                
        return decoedJWT;
    }

    private String getAccessToken(String userName, List<String> roles, Date now, Date validity) {
        String issuserUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(userName)
                .withIssuer(issuserUrl)
                .sign(algorithm)
                .strip();
    }
    
    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    
    private String getRefreshToken(String userName, List<String> roles, Date now, Date validity) {
        Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3)); //3h
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validityRefreshToken)
                .withSubject(userName)
                .sign(algorithm)
                .strip();
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }
    
    public String resolveToken(HttpServletRequest request) { 
        String bearerToken = request.getHeader("Authorization");
        String token = null;
        
        //Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.iagnLj4kDAEYFmkJ_9rPa57vbWLKsNLl7i5d3v28ODw
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring("Bearer ".length());
        } 
        
        return token;
    }
    
    public TokenVO refreshToken(String refreshToken) {
        
        if (refreshToken.contains("Bearer ")) {
            refreshToken = refreshToken.substring("Bearer ".length());
        }
        
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoedJWT = verifier.verify(refreshToken);
        
        String username = decoedJWT.getSubject();
        List<String> roles =  decoedJWT.getClaim("roles").asList(String.class);
        
        return createAccessToken(username, roles);
    }
    
    public boolean validateToken(String token) {
        DecodedJWT decodedJwt = decodedToken(token);
        boolean validToken = true;
        
        try {
            
            if (decodedJwt.getExpiresAt().before(new Date())) {
                validToken = false;
            }
            
            return validToken;
            
        } catch (Exception exp) {
            throw new InvalidJwtAuthenticationException("Expired or invalid Token");
        }
    }
    
}
