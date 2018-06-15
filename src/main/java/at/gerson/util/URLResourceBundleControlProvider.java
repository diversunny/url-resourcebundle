package at.gerson.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.spi.ResourceBundleControlProvider;

public class URLResourceBundleControlProvider
    implements ResourceBundleControlProvider {
    static final ResourceBundle.Control PROPERTIESCONTROL =
        new URLResourceBundleControl();
    public static final String BASE_NAME_SEPERATOR = ",";
	public static final String BASE_NAMES_PROPERTY = "at.gerson.util.resourcebundle_basenames";
    protected static Set<String> getBaseNamesConfig() {
		String baseNameConfig = System.getProperty(BASE_NAMES_PROPERTY);
		if (baseNameConfig == null) {
			return null;//no basename
		}
		 String[] baseNamesConfig = baseNameConfig.split(BASE_NAME_SEPERATOR);		 
		 Set<String> result= new HashSet<String>(baseNamesConfig.length);
		 result.addAll(Arrays.asList(baseNamesConfig));
		 return result;
	}
	protected static boolean isBaseNameSupported(String baseName){
		Set<String> baseNamesConfig = getBaseNamesConfig();		 
		if(baseNamesConfig==null||baseNamesConfig.isEmpty())return false;
		return baseNamesConfig.contains(baseName);		
	}
    public ResourceBundle.Control getControl(String baseName) {
        if(isBaseNameSupported(baseName)){
            return PROPERTIESCONTROL;
        }
        return null;
    }
}
