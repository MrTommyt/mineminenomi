package xyz.pixelatedw.mineminenomi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.proxy.IModProxy;
import xyz.pixelatedw.mineminenomi.proxy.ModClientProxy;
import xyz.pixelatedw.mineminenomi.proxy.ModServerProxy;
import xyz.pixelatedw.wypi.APIConfig;

@Mod(APIConfig.PROJECT_ID)
public class ModMain
{
	public static ModMain instance;
	public static final IModProxy PROXY = DistExecutor.runForDist(() -> () -> new ModClientProxy(), () -> () -> new ModServerProxy());;
	public static final Logger LOGGER = LogManager.getLogger(APIConfig.PROJECT_ID);

	public ModMain()
	{	
		APIConfig.setupResourceFolderPath();

		instance = this;
		
		CommonConfig.init();
	}
}
