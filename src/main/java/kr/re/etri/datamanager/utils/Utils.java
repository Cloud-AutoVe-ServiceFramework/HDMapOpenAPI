package kr.re.etri.datamanager.utils;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class Utils {
	private final static long KB_FACTOR = 1024;
	private final static long MB_FACTOR = 1024 * KB_FACTOR;
	private final static long GB_FACTOR = 1024 * MB_FACTOR;
	
    public static Map<String, Object> saveFile(String uploadFolderPath, MultipartFile multipartFile, Logger logger) throws Exception {
        Map<String, Object> fileUploadData = null;
        
        InputStream fis = null;
        if (multipartFile != null) {
			long size = multipartFile.getSize();  
			
			if (size > GB_FACTOR) {
				new Exception("�뙆�씪 �궗�씠利덇� 理쒕� ���옣 �궗�씠利덈낫�떎 �겱�땲�떎.");
			}
			
			String orgFileName = multipartFile.getOriginalFilename();
			if (orgFileName != null) {
				if(orgFileName.endsWith(".txt") || orgFileName.endsWith(".dbf") || orgFileName.endsWith(".shp") || orgFileName.endsWith(".shx")  || orgFileName.endsWith(".sbn")
						|| orgFileName.endsWith(".sbx") || orgFileName.endsWith(".prj") || orgFileName.endsWith(".cpg") || orgFileName.endsWith(".idx")  || orgFileName.endsWith(".xml")
						|| orgFileName.endsWith(".csv") || orgFileName.endsWith(".xlsx") || orgFileName.endsWith(".xls") || orgFileName.endsWith(".htm")  || orgFileName.endsWith(".html")
						|| orgFileName.endsWith(".doc") || orgFileName.endsWith(".docx") || orgFileName.endsWith(".zip")) {
					String fileExtension = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);

					String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;
					String filePath = uploadFolderPath + "/" + uniqueFileName;

					fileUploadData = new HashMap<>();
					fileUploadData.put("file_type", fileExtension);
					fileUploadData.put("file_name", orgFileName);
					fileUploadData.put("file_size", size);
					fileUploadData.put("file_location", filePath);
	             
					 
			        try {
			        	fis = multipartFile.getInputStream();
			        } catch (Exception e) {
			            throw e;
			        } finally {
			        	try {  
			        		if (fis != null) {  
			        			fis.close();  
			        		}  
			        	} catch (Exception e) {  
			        		throw e;  
			        	}  
					}
					FtpClientUtils.saveFile(fis, uploadFolderPath, uniqueFileName, logger);
				}
			}
    	}

        return fileUploadData;
    } 
    
    public static String convertGMTDateFormat(String creationDateTime) throws ParseException {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		Date creationDate = format.parse(creationDateTime);
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		return newFormat.format(creationDate);
    }
}
