package by.pvt.pintusov.courses.managers;

import by.pvt.pintusov.courses.constants.PagePath;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * Page path manager
 * @author dpintusov
 * @version 1.2
 */
@Component
public class PagePathManager {
	private final ResourceBundle bundle = ResourceBundle.getBundle(PagePath.CONFIGS_SOURCE);

	private PagePathManager () {}

	public String getProperty (String key) { return bundle.getString(key); }
}
