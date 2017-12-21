package com.eric.demo.api.user.cache;

import com.eric.demo.api.user.dto.UserCacheDto;
import com.eric.demo.api.user.service.UserService;
import com.eric.demo.cache.CommonCache;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCache extends CommonCache<String, UserCacheDto> {

    @Autowired
    private UserService userService;

    @Override
    public UserCacheDto execute(String key) throws Exception {
        setTime(10);
        return get("uuu-", key, UserCacheDto.class, new Fun<String, UserCacheDto>() {
            @Override
            public UserCacheDto get(String key) throws Exception {
                UserCacheDto cache = new UserCacheDto().setUserList(userService.findList(Maps.newHashMap()));
                return cache;
            }
        });
    }


}
