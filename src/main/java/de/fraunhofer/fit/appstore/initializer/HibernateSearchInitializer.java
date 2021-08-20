/*
 * Copyright 2021 Fraunhofer Institute for Applied Information Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
