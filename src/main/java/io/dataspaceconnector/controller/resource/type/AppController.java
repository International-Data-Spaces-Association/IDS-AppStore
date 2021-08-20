/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
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
package io.dataspaceconnector.controller.resource.type;

import de.fraunhofer.fit.appstore.model.search.SearchResult;
import de.fraunhofer.fit.appstore.services.search.SearchAppService;
import io.dataspaceconnector.controller.resource.base.BaseResourceController;
import io.dataspaceconnector.controller.resource.base.tag.ResourceDescription;
import io.dataspaceconnector.controller.resource.base.tag.ResourceName;
import io.dataspaceconnector.controller.resource.view.app.AppView;
import io.dataspaceconnector.controller.util.ResponseCode;
import io.dataspaceconnector.controller.util.ResponseDescription;
import io.dataspaceconnector.model.app.App;
import io.dataspaceconnector.model.app.AppDesc;
import io.dataspaceconnector.service.resource.type.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Offers the endpoints for managing apps.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apps")
@Tag(name = ResourceName.APPS, description = ResourceDescription.APPS)
public class AppController extends BaseResourceController<App, AppDesc, AppView, AppService> {
// MOVED TO GUI CONTROLLER LOGIC
    //    /**
//     * The service managing apps.
//     */
//    private final @NonNull SearchAppService appSearchService;
//
//    /**
//     * Search for apps.
//     *
//     * @param searchText The search text.
//     * @param page       The number of pages.
//     * @param size       The number of results per page.
//     * @return Response with code 200 (Ok) and the search result.
//     */
//    @Tag(name = "UI", description = ResourceDescription.APPS)
//    @GetMapping(value = "/search")
//    @Operation(summary = "Search all Apps with value in description")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)})
//    public ResponseEntity<Object> searchApps(
//            @RequestParam(value = "search") final String searchText,
//            @RequestParam(value = "page", required = false,
//                    defaultValue = "0") final Integer page,
//            @RequestParam(value = "size", required = false,
//                    defaultValue = "30") final Integer size) {
//
//        final var resultsCount = appSearchService.searchAppsResultCount(searchText);
//        final var pageCount = appSearchService.searchAppsPagesCount(searchText, size);
//        final var appList = appSearchService.searchAppsByDescription(searchText, page, size);
//
//        final var result = new SearchResult();
//        result.setPageNo(page);
//        result.setResultsCount(resultsCount);
//        result.setPageCount(pageCount);
//        result.setAppList(appList);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
