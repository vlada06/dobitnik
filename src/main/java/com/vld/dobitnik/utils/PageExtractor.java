package com.vld.dobitnik.utils;


 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageImpl;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
 import org.springframework.stereotype.Component;
 import java.util.List;

 import static java.util.Collections.EMPTY_LIST;

@Component
public class PageExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageExtractor.class);

    public <T> Page<T> extractPageFromList(List<T> list, Pageable requestedPage) {
        int listSize = list.size();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("PageExtractor.extractPageFromList() list size = %s", listSize));
        }

        if (requestedPage.isUnpaged()) {
            return new PageImpl<>(list, PageRequest.of(0, 1), listSize);
        }

        boolean firstPageElementOutsideList =
                ((requestedPage.getPageNumber() * requestedPage.getPageSize()) + 1) > listSize;

        if (firstPageElementOutsideList) {
            return new PageImpl<>(EMPTY_LIST, requestedPage, listSize);
        }

        long start = requestedPage.getOffset();

        long end = 0;
        if ((start + requestedPage.getPageSize()) > listSize) {
            end = listSize;
        } else {
            end = start + requestedPage.getPageSize();
        }
        List<T> subList = list.subList((int) start, (int) end);

        return new PageImpl<>(subList, requestedPage, listSize);
    }
}
