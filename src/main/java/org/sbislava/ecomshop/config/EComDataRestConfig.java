package org.sbislava.ecomshop.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.sbislava.ecomshop.entity.Product;
import org.sbislava.ecomshop.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class EComDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;
    private final static HttpMethod[] THE_UNSUPPORTED_ACTIONS = {
            HttpMethod.PUT,
            HttpMethod.POST,
            HttpMethod.DELETE
    };

    public EComDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        //disable HTTP methods for Product: PUT, POST and DELETE
        disabledExposureConfiguration(config, Product.class);

        //disable HTTP methods for ProductCategory: PUT, POST and DELETE
        disabledExposureConfiguration(config, ProductCategory.class);

        //call internal helper method
        exposeIds(config);
    }

    private void disabledExposureConfiguration(RepositoryRestConfiguration config, Class<?> clazz) {
        config.getExposureConfiguration()
                .forDomainType(clazz)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(THE_UNSUPPORTED_ACTIONS))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(THE_UNSUPPORTED_ACTIONS));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class<?>> entityClasses = new ArrayList<>();
        entities.forEach(entity -> entityClasses.add(entity.getJavaType()));
        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
