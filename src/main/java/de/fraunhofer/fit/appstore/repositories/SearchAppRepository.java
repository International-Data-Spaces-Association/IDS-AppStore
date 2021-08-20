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
package de.fraunhofer.fit.appstore.repositories;

import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.resource.Resource;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository for searching apps.
 */
@Repository
public class SearchAppRepository {

    /**
     * The entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Search the number of apps for a given search input.
     *
     * @param searchText The search text.
     * @return The number of matching apps.
     */
    @Transactional
    public long searchResourceTotalCount(final String searchText) {
        final var searchSession = Search.session(entityManager);
        final var scope = searchSession.scope(Resource.class);

        return searchSession.search(Resource.class)
                .where(scope.predicate().match()
                        .fields("description")
                        .matching(searchText).toPredicate())
                .fetchTotalHitCount();
    }

    /**
     * Search apps for a given search input, the page number, and the number of results per page.
     *
     * @param text           The search text.
     * @param pageNo         The page number.
     * @param resultsPerPage The number of results per page.
     * @return A list of matching apps.
     */
    @Transactional
    public List<App> searchApps(final String text, final int pageNo, final int resultsPerPage) {
        final var searchSession = Search.session(entityManager);
        final var scope = searchSession.scope(App.class);

        // For Pagination Skip Results --> offset = pageNo * resultsPerPage
        final int offset = pageNo * resultsPerPage;
        final var result = searchSession.search(scope)
                .where(scope.predicate()
                        .match()
                        .fields("description")
                        .matching(text)
                        .toPredicate())
                .fetch(offset, resultsPerPage);

        long totalHitCount = result.total().hitCount(); // TODO not used?

        return result.hits();
    }

    /**
     * Search apps categories.
     *
     * @return A list of matching app resources.
     */
    public List<Resource> searchResourceCategories() {
        final var searchSession = Search.session(entityManager);
        final var scope = searchSession.scope(Resource.class);

        return searchSession.search(scope).where(f ->
                f.exists().field("keywords")).fetchAll().hits();
    }

    /**
     * Search app descriptions.
     *
     * @return A list of matching apps.
     */
    public List<App> searchAppDescription() {
        final var searchSession = Search.session(entityManager);
        final var scope = searchSession.scope(App.class);

        return searchSession.search(scope).where(f ->
                f.exists().field("description")).fetchAll().hits();
    }

    /**
     * Search app resources by category.
     *
     * @param category       The category.
     * @param pageNo         The page number.
     * @param resultsPerPage The number of results per page.
     * @return A list of matching resources.
     */
    @Transactional
    public List<Resource> searchResourcesByCategory(final String category, final int pageNo,
                                                    final int resultsPerPage) {
        final var searchSession = Search.session(entityManager);
        final var scope = searchSession.scope(Resource.class);

        // For Pagination Skip Results --> offset = pageNo * resultsPerPage
        final int offset = pageNo * resultsPerPage;
        final var result = searchSession.search(scope)
                .where(scope.predicate()
                        .match()
                        .field("keywords")
                        .matching(category)
                        .toPredicate())
                .fetch(offset, resultsPerPage);

        long totalHitCount = result.total().hitCount();

        return result.hits();
    }

    /**
     * Search apps by category.
     *
     * @param category       The category.
     * @param pageNo         The page number.
     * @param resultsPerPage The number of results per page.
     * @return A list of matching apps.
     */
    @Transactional
    public List<Resource> searchResourceByDescription(final String category, final int pageNo,
                                                 final int resultsPerPage) {
        final var searchSession = Search.session(entityManager);
        final var scope = searchSession.scope(Resource.class);

        // For Pagination Skip Results --> offset = pageNo * resultsPerPage
        final int offset = pageNo * resultsPerPage;
        final var result = searchSession.search(scope)
                .where(scope.predicate()
                        .match()
                        .field("description")
                        .matching(category)
                        .toPredicate())
                .fetch(offset, resultsPerPage);

        long totalHitCount = result.total().hitCount();

        return result.hits();
    }

    /**
     * Search app resources by category total count.
     *
     * @param category The category.
     * @return The total count.
     */
    @Transactional
    public long searchResourcesByCategoryTotalCount(final String category) {
        final var searchSession = Search.session(entityManager);
        final var scope = searchSession.scope(Resource.class);

        return searchSession.search(Resource.class)
                .where(scope.predicate()
                        .match()
                        .fields("keywords")
                        .matching(category)
                        .toPredicate())
                .fetchTotalHitCount();
    }

}
