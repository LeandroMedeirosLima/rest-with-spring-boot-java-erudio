package br.com.erudio.integrationtests.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlRootElement;

//@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class TokenVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;
    
    private Date timestamp;
    private String message;
    private String details;
    
    public TokenVO() {

    }

    public TokenVO(String username, Boolean authenticated, Date created, Date expiration, String accessToken,
            String refreshToken) {
        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TokenVO other = (TokenVO) obj;
        return Objects.equals(accessToken, other.accessToken) && Objects.equals(authenticated, other.authenticated)
                && Objects.equals(created, other.created) && Objects.equals(expiration, other.expiration)
                && Objects.equals(refreshToken, other.refreshToken) && Objects.equals(username, other.username);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public Date getCreated() {
        return created;
    }

    public String getDetails() {
        return details;
    }

    public Date getExpiration() {
        return expiration;
    }

    public String getMessage() {
        return message;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, authenticated, created, expiration, refreshToken, username);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
