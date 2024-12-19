package com.vld.dobitnik.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vld.dobitnik.cqrs.Draw;
import com.vld.dobitnik.cqrs.DrawRepository;
import com.vld.dobitnik.utils.PageExtractor;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/draws")
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

}
