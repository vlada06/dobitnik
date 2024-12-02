package com.vld.dobitnik.api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vld.dobitnik.cqrs.Draw;
import com.vld.dobitnik.cqrs.DrawRepository;
import com.vld.dobitnik.exception.NotFoundException;
import com.vld.dobitnik.exception.NullParameterException;
import com.vld.dobitnik.process.WheelingSystemBuilder;
import com.vld.dobitnik.utils.CommonConstants;
import com.vld.dobitnik.utils.PageExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vladimir Davidovic
 * date: 07/12/2019
 * time: 18:43
 */

@RestController
public class SystemController implements SystemControllerAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
    private static final String EXCEPTION_MESSAGE = "The draw number %s could not be fetched from the database";


    @Autowired
    private final DrawRepository drawRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private WheelingSystemBuilder wheelingSystemBuilder;

    @Autowired
    private PageExtractor pageExtractor;

    public SystemController(DrawRepository drawRepository) {
        this.drawRepository = drawRepository;
    }

    @Override
    public String home() {
        return "through API swagger";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode getWheelingSystem(JsonNode requestData) {
        LOGGER.debug("EuroWinnersControllerImpl.getWheelingSystem()");
        if (requestData.isEmpty()) {
            LOGGER.error(CommonConstants.EMPTY_JSON_ERROR);
            throw new NullParameterException(CommonConstants.EMPTY_JSON_ERROR);
        }
        JsonNode response = wheelingSystemBuilder.buildWheelingSystem(requestData);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("EuroWinnersControllerImpl.getWheelingSystem returned system: %s",
                    response.toPrettyString()));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode getRandomWheelingSystem(JsonNode requestData) {
        LOGGER.debug("EuroWinnersControllerImpl.getRandomWheelingSystem()");

        return wheelingSystemBuilder.buildRandomWheelingSystem(requestData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Draw addDraw(JsonNode addRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Adding draw %s", addRequest.toString()));
        }
        ObjectMapper mapper = new ObjectMapper();
        Draw draw = mapper.convertValue(addRequest, Draw.class);

        return drawRepository.save(draw);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Draw getDraw(@PathVariable String drawNumber) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching draw %s", drawNumber));
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("drawNumber").is(drawNumber));
        Draw draw = mongoTemplate.findOne(query, Draw.class);

        if (null != draw) {
            return draw;
        } else {
            throw new NotFoundException(String.format(EXCEPTION_MESSAGE, drawNumber));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
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


    /**
     * {@inheritDoc}
     */
    @Override
    public JsonNode getSievedWheelingSystem(JsonNode requestData) {
        if (requestData.isEmpty()) {
            LOGGER.error(CommonConstants.EMPTY_JSON_ERROR);
            throw new NullParameterException(CommonConstants.EMPTY_JSON_ERROR);
        }

        JsonNode response = wheelingSystemBuilder.getSievedWheelingSystem(requestData);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("EuroWinnersControllerImpl.getWheelingSystem returned system: %s",
                    response.toPrettyString()));
        }
        return response;
    }
}

