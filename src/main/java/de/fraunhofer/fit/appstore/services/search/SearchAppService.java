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
package de.fraunhofer.fit.appstore.services.search;

import de.fraunhofer.fit.appstore.repositories.SearchAppRepository;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.resource.Resource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for searching apps.
 */
@Service
@RequiredArgsConstructor
public class SearchAppService {

    /**
     * Repository for searching apps.
     */
    private final @NonNull SearchAppRepository searchAppRepository;

    /**
     * Gets the total result count for a specific search text.
     *
     * @param searchText Text to search for.
     * @return Total search count.
     */
    public long searchAppsResultCount(final String searchText) {
        return searchAppRepository.searchResourceTotalCount(searchText);
    }

    /**
     * Gets the total number of pages for a specific search text and results per page.
     *
     * @param searchText     The text to search for.
     * @param resultsPerPage The number of search results per page.
     * @return The number of pages needed to display all the search results.
     */
    public int searchAppsPagesCount(final String searchText, final int resultsPerPage) {
        final var appsTotalCount = searchAppRepository.searchResourceTotalCount(searchText);

        return (int) Math.floorDiv(appsTotalCount, resultsPerPage) + 1;
    }

    /**
     * Search for apps by a specific search text, the actual page number, and results per page.
     *
     * @param input          The text to search for.
     * @param pageNo         The current page number.
     * @param resultsPerPage The number of search results per page.
     * @return A list of apps to match the search criteria.
     */
    public List<App> searchApps(final String input, final int pageNo, final int resultsPerPage) {
        return searchAppRepository.searchApps(input, pageNo, resultsPerPage);
    }

    /**
     * Search for apps by a description, the actual page number, and results per page.
     *
     * @param description    The description to search for.
     * @param pageNo         The current page number.
     * @param resultsPerPage The number of search results per page.
     * @return A list of apps to match the search criteria.
     */
    public List<Resource> searchAppsByDescription(final String description, final int pageNo,
                                             final int resultsPerPage) {
        return searchAppRepository.searchResourceByDescription(description, pageNo, resultsPerPage);
    }

    /**
     * Search for apps by category, the actual page number, and results per page.
     *
     * @param category       The category to search for.
     * @param pageNo         The current page number.
     * @param resultsPerPage The number of search results per page.
     * @return A list of apps to match the search criteria.
     */
    public List<Resource> searchResourcesByCategory(final String category, final int pageNo,
                                                    final int resultsPerPage) {
        return searchAppRepository.searchResourcesByCategory(category, pageNo, resultsPerPage);
    }

    /**
     * Search for app resource categories.
     *
     * @return A list of app resources to match the search criteria.
     */
    public List<Resource> getAppCategories() {
        return searchAppRepository.searchResourceCategories();
    }

}
