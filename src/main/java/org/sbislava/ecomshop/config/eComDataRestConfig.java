package org.sbislava.ecomshop.config;

import org.sbislava.ecomshop.entity.Product;
import org.sbislava.ecomshop.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class eComDataRestConfig implements RepositoryRestConfigurer {
    private final static HttpMethod[] THE_UNSUPPORTED_ACTIONS = {
            HttpMethod.PUT,
            HttpMethod.POST,
            HttpMethod.DELETE
    };
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        //disable HTTP methods for Product: PUT, POST and DELETE
        disabledExposureConfiguration(config, Product.class);

        //disable HTTP methods for ProductCategory: PUT, POST and DELETE
        disabledExposureConfiguration(config, ProductCategory.class);
    }

    private void disabledExposureConfiguration(RepositoryRestConfiguration config, Class<?> clazz) {
        config.getExposureConfiguration()
                .forDomainType(clazz)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(THE_UNSUPPORTED_ACTIONS))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(THE_UNSUPPORTED_ACTIONS));
    }
}
