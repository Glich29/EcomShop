package org.sbislava.ecomshop.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.sbislava.ecomshop.entity.Country;
import org.sbislava.ecomshop.entity.Product;
import org.sbislava.ecomshop.entity.ProductCategory;
import org.sbislava.ecomshop.entity.State;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static final Class<?>[] TARGET_CLASSES = {
            Product.class,
            ProductCategory.class,
            Country.class,
            State.class
    };

    public EComDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        //disable HTTP methods for target classes: PUT, POST and DELETE
        disabledExposureClassesConfiguration(config, TARGET_CLASSES);

        //call internal helper method
        exposeIds(config);
    }

    private void disabledExposureClassesConfiguration(RepositoryRestConfiguration config, Class<?>[] classes) {
        Arrays.stream(classes).forEach(clazz -> disabledExposureClassConfiguration(config, clazz));
    }
    private void disabledExposureClassConfiguration(RepositoryRestConfiguration config, Class<?> clazz) {
        config.getExposureConfiguration()
                .forDomainType(clazz)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(THE_UNSUPPORTED_ACTIONS))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(THE_UNSUPPORTED_ACTIONS));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class<?>> entityClasses = new ArrayList<>();
        entities.forEach(entity -> entityClasses.add(entity.getJavaType()));
        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
