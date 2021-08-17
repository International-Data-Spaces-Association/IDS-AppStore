package de.fraunhofer.fit.appstore.initializer;

import lombok.extern.log4j.Log4j2;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implements hibernate search functionality.
 */
@Log4j2
@Component
public class HibernateSearchInitializer implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * The entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        final var searchSession = Search.session(entityManager);
        final var indexer = searchSession.massIndexer();
        if (log.isInfoEnabled()) {
            log.info("Hibernate Search initialized");
        }

        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            if (log.isDebugEnabled()) {
                log.error("An error occurred when trying to build Hibernate Search indexes. "
                        + "[exception=({})]" + e.toString(), e);
            }
        }
    }
}
