package crud.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Указываем фреймворку Spring, что использовать в качестве корневого
    // (root) контекста: ApplicationContext — «корневая» конфигурация
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class <?>[]{AppConfig.class};
    }

    // Указываем фреймворку Spring, что использовать в качестве
    // контекста DispatcherServlet: WebApplicationContext — MVC configuration
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ AppConfig.class};
    }

    // Отображение DispatcherServlet, данный метод отвечает за шаблон URL
    // аналогично элементу <url-pattern>/</url-pattern> в файле web.xml
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // Два метода ниже - это для создания фильтра (html +PATCH +DELETE)
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        registerHiddenFilter(servletContext);
    }

    private void registerHiddenFilter(ServletContext servletContext) {
        servletContext.addFilter("hiddenMethodFilter", new HiddenHttpMethodFilter())
                .addMappingForUrlPatterns(null, true, "/*");
    }
}