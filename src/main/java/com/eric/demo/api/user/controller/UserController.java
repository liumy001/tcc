package com.eric.demo.api.user.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.eric.demo.api.user.cache.UserCache;
import com.eric.demo.commons.annotation.CommonLog;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserCache userCache;

    @Autowired
    private UserService userService;

    /**
     * consumes= MediaType.MULTIPART_FORM_DATA_VALUE
     * GET /users -> get all the users
     */
    @RequestMapping(value = "/users")
    public ResponseEntity<List<User>> getAll() throws Exception {
        PageHelper.startPage(1,5);
        Map<String, Object> map = Maps.newHashMap();
        return new ResponseEntity<>(userCache.execute("findAllUser").getUserList(), HttpStatus.OK);
    }


    /**
     * POST /users -> create a new user
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create() {
        User userDto=new User();
        userDto.setUsername("1");
        User user = userService.findOneByUsername(userDto.getUsername());
        if (user != null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("username already in use");
        }
        userDto.setPassword("1");
        userDto.setNickname("2");
        userDto.setCreatedDate(new Date());
        userService.create(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
