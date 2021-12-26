package ru.war.robots.giftshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.war.robots.giftshop.config.InvalidDataException;
import ru.war.robots.giftshop.model.dto.BuyDto;
import ru.war.robots.giftshop.model.dto.GiftDto;
import ru.war.robots.giftshop.service.ShopService;

@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class MainController {
    @Autowired
    ShopService shopService;

    @Transactional
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ResponseEntity<Object> buy(@RequestBody BuyDto dto) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String result = null;
        try {
            shopService.buyItem(dto);
            httpStatus = HttpStatus.OK;
        } catch (InvalidDataException e) {
            result = e.getMessage();
        }
        return new ResponseEntity<>(result, httpStatus);
    }

    @Transactional
    @RequestMapping(value = "/gift", method = RequestMethod.POST)
    public ResponseEntity<Object> gift(@RequestBody GiftDto dto) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String result = null;
        try {
            shopService.gift(dto);
            httpStatus = HttpStatus.OK;
        } catch (InvalidDataException e) {
            result = e.getMessage();
        }
        return new ResponseEntity<>(result, httpStatus);
    }
}
