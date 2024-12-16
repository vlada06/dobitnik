package com.vld.dobitnik.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vld.dobitnik.cqrs.Draw;
import com.vld.dobitnik.cqrs.DrawRepository;
import com.vld.dobitnik.exception.NotFoundException;
import com.vld.dobitnik.utils.PageExtractor;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class DataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    private static final String EXCEPTION_MESSAGE = "The draw number %s could not be fetched from the database";

//    @Autowired
    private final DrawRepository drawRepository;

    private PageExtractor pageExtractor;

    public DataController(DrawRepository drawRepository) {
        this.drawRepository = drawRepository;
    }

    /**
     *
     * Add a past draw to the repository.
     * @param addRequest JSON containing draw data to be saved
     * @return  JSON verifying the persisted data
     */
    @ApiOperation(value = "JsonNode", response = JsonNode.class)
    @PostMapping(value = "/draws")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Draw addDraw(Draw addRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Adding draw %s", addRequest.toString()));
        }
        String ticketCode = UUID.randomUUID().toString();

        ObjectMapper mapper = new ObjectMapper();
        Draw draw = mapper.convertValue(addRequest, Draw.class);

        return drawRepository.save(draw);
    }


    /**
     * Fetch a single draw record from the repository.
     *
     * @param drawNumber The number of the draw to be fetched.
     * @return A single draw record.
     */
    @GetMapping(value = "/draws/{drawNumber}")
    @ResponseStatus(HttpStatus.OK)

//    @Override
    public Draw getDraw(@PathVariable String drawNumber) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching draw %s", drawNumber));
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("drawNumber").is(drawNumber));
//        Draw draw = mongoTemplate.findOne(query, Draw.class);
        Draw draw = null;

        if (null != draw) {
            return draw;
        } else {
            throw new NotFoundException(String.format(EXCEPTION_MESSAGE, drawNumber));
        }
    }

    /**
     * Fetch draw records from the repository.
     *
     * @param pageable
     * @return A pageful of past draws
     */
    @GetMapping(value = "/draws/{drawNumber}")
    @ResponseStatus(HttpStatus.OK)

    public Page<Draw> getDraws(Pageable pageable) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching all draws");
        }
        List<Draw> draws = drawRepository.findAll();

        PageRequest sortedPage = PageRequest.of(
            pageable.isUnpaged() ? 0 : pageable.getPageNumber(),
            pageable.isUnpaged() ? Integer.MAX_VALUE : pageable.getPageSize(),
            Sort.by(Sort.Direction.ASC, "id"));

        if (!draws.isEmpty()) {
            return pageExtractor.extractPageFromList(draws, sortedPage);
        } else {
            throw new NotFoundException("No draws fetched");
        }
    }


}
