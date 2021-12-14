package kr.re.etri.datamanager;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import kr.re.etri.datamanager.config.FtpPropertiesConfig;
import kr.re.etri.datamanager.config.PropertiesConfig;
import kr.re.etri.datamanager.utils.FtpClientUtils;

@ManagedBean
public class ApplicationLoadHandler implements ApplicationRunner  {
	@Autowired
	FtpPropertiesConfig ftpPropertiesconfig;
	
	@Autowired
	PropertiesConfig propertiesConfig;
		
	@Override
    public void run(ApplicationArguments args) throws Exception {
		if (propertiesConfig.isHdmapAdd() || propertiesConfig.isHdmapDelete() || propertiesConfig.isHdmapDownload() || propertiesConfig.isHdmapUpdate()) {
			FtpClientUtils.open(ftpPropertiesconfig);	
		}
    }
}