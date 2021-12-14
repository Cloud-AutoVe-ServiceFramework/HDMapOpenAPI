package kr.re.etri.datamanager.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;

import kr.re.etri.datamanager.config.FtpPropertiesConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FtpClientUtils {
	private static FtpPropertiesConfig config;
	private static FTPClient ftpClient = null;

	public static void open(FtpPropertiesConfig ftpPropertiesConfig) {
		config = ftpPropertiesConfig;
		ftpClient = new FTPClient();
	}

//	public static void close() {
//		if (ftpClient != null) {
//			try {
//				ftpClient.logout();
//				ftpClient.disconnect();
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			}
//		}
//	}

	private static void connect() {
		try {
			ftpClient.connect(config.getHost(), config.getPort());
			ftpClient.login(config.getUsername(), config.getPassword());
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			//ftpClient.setControlKeepAliveTimeout(config.getKeepAliveTimeout());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private static void disconnect() {
		try {
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

//	private static void openCheck() {
//		try {
//			boolean answer = ftpClient.sendNoOp();
//			if (!answer) {
//				connect();
//			}
//		} catch (IOException e) {
//			connect();
//			log.error(e.getMessage(), e);
//		}
//	}

	public static boolean saveFile(InputStream inputStream, String workingDirectory, String fileName, Logger logger) {
		try {
			connect();
			
			String currentDirectory = ftpClient.printWorkingDirectory();
			if (currentDirectory.indexOf(workingDirectory) == -1) {
				boolean isChange = ftpClient.changeWorkingDirectory(workingDirectory);
				if (!isChange) {
					ftpClient.makeDirectory(workingDirectory);
					logger.info(ftpClient.getReplyString());
					ftpClient.changeWorkingDirectory(workingDirectory);
					logger.info(ftpClient.getReplyString());
				}
			}
			
			boolean result = ftpClient.storeFile(fileName, inputStream);
			logger.info(ftpClient.getReplyString());
			return result;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		} finally {
			disconnect();
		}
	}

	public static boolean loadFile(String remotePath, OutputStream outputStream) {
		try {
			connect();
			return ftpClient.retrieveFile(remotePath, outputStream);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return false;
		} finally {
			disconnect();
		}
	}

	public static boolean deleteFile(String filePath) {
		try {
			connect();
			
			boolean result = ftpClient.deleteFile(filePath);
			log.info(ftpClient.getReplyString());
			
			return result;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return false;
		} finally {
			disconnect();
		}
	}
}
