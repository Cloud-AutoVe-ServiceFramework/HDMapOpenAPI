package kr.re.etri.datamanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "ftp.client")
public class FtpPropertiesConfig {
    private String host;
 
    private Integer port;
 
    private String protocol;
 
    private String username;
 
    private String password;
    
    private Integer keepAliveTimeout;
    
    private String hdmapPath;
    
}