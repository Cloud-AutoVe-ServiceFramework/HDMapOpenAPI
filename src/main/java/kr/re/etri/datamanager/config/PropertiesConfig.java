package kr.re.etri.datamanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.Getter;

@Configuration
@ConfigurationProperties(prefix = "application")
@Primary
@Getter
public class PropertiesConfig {	

	@Value("${hdmap-add:false}")
	private boolean hdmapAdd;
	
	@Value("${hdmap-update:false}")
	private boolean hdmapUpdate;
	
	@Value("${hdmap-delete:false}")
	private boolean hdmapDelete;
	
	@Value("${hdmap-search:false}")
	private boolean hdmapSearch;
	
	@Value("${hdmap-download:false}")
	private boolean hdmapDownload;
	
	@Value("${code-list:false}")
	private boolean codeList;
	
	@Value("${download-result:false}")
	private boolean downloadResult;
}