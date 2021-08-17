package de.fraunhofer.fit.appstore.model.search;

import io.dataspaceconnector.model.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity containing search result details.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchResult {

    /**
     * The results count.
     */
    private long resultsCount;

    /**
     * The page number.
     */
    private int pageNo;

    /**
     * The page count.
     */
    private int pageCount;

    /**
     * The app list.
     */
    private List<Resource> appList;

}
