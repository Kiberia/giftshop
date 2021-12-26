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
import ru.war.robots.giftshop.jooq.tables.pojos.Item;
import ru.war.robots.giftshop.model.dto.BuyDto;
import ru.war.robots.giftshop.model.dto.GiftDto;
import ru.war.robots.giftshop.model.pojo.UserPojo;
import ru.war.robots.giftshop.service.ReferenceService;
import ru.war.robots.giftshop.service.ShopService;

import java.util.List;

@RestController
@RequestMapping(
    value="/reference",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class ReferenceController {
    @Autowired
    ReferenceService referenceService;

    @Transactional(readOnly = true)
    @RequestMapping(value = "/list/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserPojo>> listUsers() {
        List<UserPojo> result = referenceService.getUserList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @Transactional(readOnly = true)
    @RequestMapping(value = "/list/items", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> listItems() {
        List<Item> result = referenceService.getItemList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
