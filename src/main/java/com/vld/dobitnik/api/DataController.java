package com.vld.dobitnik.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vld.dobitnik.cqrs.Draw;
import com.vld.dobitnik.cqrs.DrawRepository;
import com.vld.dobitnik.utils.PageExtractor;
import com.vld.dobitnik.validate.CombinationValidation;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/draws")
public class DataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    private static final String EXCEPTION_MESSAGE = "The draw number %s could not be fetched from the database";

    @Autowired
    private CombinationValidation combinationValidation;
    //    @Autowired
    private final DrawRepository drawRepository;

    private PageExtractor pageExtractor;

    public DataController(DrawRepository drawRepository, CombinationValidation combinationValidation) {
        this.drawRepository = drawRepository;
        this.combinationValidation = combinationValidation;
    }

    /**
     * Add a past draw to the repository.
     *
     * @param addRequest JSON containing draw data to be saved
     * @return JSON verifying the persisted data
     */
    @ApiOperation(value = "JsonNode", response = JsonNode.class)
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Draw addDraw(@RequestBody Draw addRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Adding draw %s", addRequest.toString()));
        }
// TODO Validation(s) to be introduced
        if (invalidRequest(addRequest)) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
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
    @GetMapping("/{drawNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Draw getDraw(@PathVariable String drawNumber) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Fetching draw %s", drawNumber));
        }
        return drawRepository.findByDrawNumber(drawNumber).orElseThrow(() -> new NoSuchElementException(EXCEPTION_MESSAGE));
    }


    /**
     * Fetch all the available draws from the dataabase
     * TODO: implement Pageable, fetch a page at a time
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Draw> getAllDraws() {
        return drawRepository.findAll();
    }

//    public Page<Draw> getDraws(Pageable pageable) {
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("Fetching all draws");
//        }
//        List<Draw> draws = drawRepository.findAll();
//
//        PageRequest sortedPage = PageRequest.of(
//            pageable.isUnpaged() ? 0 : pageable.getPageNumber(),
//            pageable.isUnpaged() ? Integer.MAX_VALUE : pageable.getPageSize(),
//            Sort.by(Sort.Direction.ASC, "id"));
//
//        if (!draws.isEmpty()) {
//            return pageExtractor.extractPageFromList(draws, sortedPage);
//        } else {
//            throw new NotFoundException("No draws fetched");
//        }
//    }

    /**
     * TRUE if the draw is invalid.
     * @param draw
     * @return
     */
    private boolean invalidRequest(Draw draw) {
        // Split the request into main and bonus part
        List<Integer> main = draw.drawNumbers().mainNumbers();
        List<Integer> bonus = draw.drawNumbers().bonusNumbers();

        // invoke range and size validation for each
        Boolean isValidRange = combinationValidation.invalidRange(main, 50).isEmpty();  //TODO -rethink
        Boolean isValidSize = combinationValidation.invalidSize(main, main.size()).isEmpty();
        System.out.println(draw.toString());

        return isValidRange && isValidSize;
    }
}
