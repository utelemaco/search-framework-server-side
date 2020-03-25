package com.owse.searchFramework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for generic search.
 */
@RestController
@RequestMapping("/api")
public class SearchController {

    private final Logger log = LoggerFactory.getLogger(SearchController.class);

    private final BeanFactory beanFactory;

    public SearchController(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     */
    @PostMapping("/search/{beanName}")
    public PageableSearchResult<?> search(@PathVariable String beanName, @RequestBody PageableSearchRequest searchRequest) {
        log.debug("REST request to search using bean ", beanName);
        return getDAO(beanName).search(searchRequest);
    }

    /**
     */
    @GetMapping("/search/{beanName}/search-config")
    public SearchConfig getSearchConfig(@PathVariable String beanName) {
        log.debug("REST request to get SearchConfig using bean ", beanName);
        return getDAO(beanName).getSearchConfig();
    }

    private AbstractDAO<?, ?> getDAO(String beanName) {
        return (AbstractDAO<?,?>) beanFactory.getBean(beanName);
    }

}
