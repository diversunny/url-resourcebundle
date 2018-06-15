package at.gerson.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class URLResourceBundleControl extends ResourceBundle.Control {	
	public static final String BASE_URL_PROPERTY = "at.gerson.util.resourcebundle_baseurl";
	
	@Override
	public List<String> getFormats(String baseName) {
		//only java.properties
		return ResourceBundle.Control.FORMAT_PROPERTIES;
	}
	public boolean isFormatSupported(String baseName,String format){
		return getFormats(baseName).contains(format);
	}
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		if (baseName == null || locale == null || format == null || loader == null) {
			throw new NullPointerException();
		}
		String urlConfig = System.getProperty(BASE_URL_PROPERTY);
		// no url base configured bundle cannot be provided
		if (urlConfig == null)
			return null;

		ResourceBundle bundle = null;
		if (isFormatSupported(baseName,format)) {
			//throws MalformedURLException
			URL[] urls = new URL[] { new URL(urlConfig) };
			URLClassLoader urlClassloader = new URLClassLoader(urls, loader);
			bundle = super.newBundle(baseName, locale, format, urlClassloader, reload);
		}
		else {
			bundle = super.newBundle(baseName, locale, format, loader, reload);
		}
		return bundle;
	}
}
