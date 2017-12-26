package com.eric.demo.web.users.controller;

import com.alibaba.fastjson.JSONObject;
import com.eric.demo.commons.annotation.CommonLog;
import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.MD5Util;
import com.eric.demo.commons.util.ResponseVo;
import com.eric.demo.commons.util.SOAResParseUtil;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.commons.validator.DataTransferObject;
import com.eric.demo.commons.validator.ParamCheckLogic;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.domain.UserCriteria;
import com.eric.demo.web.users.dto.RegisterDto;
import com.eric.demo.web.users.dto.UserDto;
import com.eric.demo.web.users.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户控制器
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ParamCheckLogic paramCheckLogic;

    @Value("${register.callBack.url}")
    private String registerCallBackUrl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.address}")
    private String mailFrom;

    @RequestMapping("login")
    @ResponseBody
    public ResponseVo login(HttpSession session, UserDto userDto) {
        //参数校验
        DataTransferObject dataTransferObject = paramCheckLogic.checkObjParamValidate(userDto);
        if (!SOAResParseUtil.checkDTO(dataTransferObject)) {
            return ResponseVo.responseError(dataTransferObject.getMsg());
        }
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(MD5Util.string2MD5(userDto.getPassword()));
        user.setIsDel(BaseConst.isDel.no_Del.getCode());
        List<User> userList = userService.findByVo(user);
        if (Check.NuNCollection(userList)) {
            return ResponseVo.responseError("用户信息不存在");
        }
        session.setAttribute(BaseConst.USER_SESSION_KEY, userList.get(0));

        return ResponseVo.responseOk(null);
    }

    @RequestMapping("register")
    public String register(String token, Model model) {
        if (Check.NuNObj(token)) {
            model.addAttribute("message", "未获取到有效token");
            return "error/error";
        }
        String vo = redisTemplate.opsForValue().get(token);
        if (Check.NuNObj(vo)) {
            model.addAttribute("message", "验证邮件已过期");
            return "error/error";
        }
        RegisterDto registerDto = JSONObject.parseObject(vo, RegisterDto.class);
        //参数校验
        DataTransferObject dataTransferObject = paramCheckLogic.checkObjParamValidate(registerDto);
        if (!SOAResParseUtil.checkDTO(dataTransferObject)) {
            model.addAttribute("message", dataTransferObject.getMsg());
            return "error/error";
        }
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or(new UserCriteria().or().andEmailEqualTo(registerDto.getEmail()));
        userCriteria.or(new UserCriteria().or().andUserNameEqualTo(registerDto.getUserName()));
        List<User> userList = userService.search(userCriteria);
        userList = userList.stream().filter(i -> i.getIsDel() == BaseConst.isDel.no_Del.getCode()).collect(Collectors.toList());
        if (!Check.NuNCollection(userList)) {
            model.addAttribute("message", "该有户名或者邮箱在注册期间被其他用户激活，注册失败！");
            return "error/error";
        }
        //插入信息
        User insertUser = new User();
        BeanUtils.copyProperties(registerDto, insertUser);
        insertUser.setPassword(MD5Util.string2MD5(registerDto.getPassword()));
        insertUser = userService.create(insertUser);
        model.addAttribute("data", insertUser);
        return "";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    @CommonLog
    public ResponseVo sendEmail(RegisterDto registerDto) {
        DataTransferObject dataTransferObject = paramCheckLogic.checkObjParamValidate(registerDto);
        try {
            if (!SOAResParseUtil.checkDTO(dataTransferObject)) {
                return ResponseVo.responseError("发送失败：" + dataTransferObject.getMsg());
            }
            UserCriteria userCriteria = new UserCriteria();
            userCriteria.or(new UserCriteria().or().andEmailEqualTo(registerDto.getEmail()));
            userCriteria.or(new UserCriteria().or().andUserNameEqualTo(registerDto.getUserName()));
            List<User> userList = userService.search(userCriteria);
            userList = userList.stream().filter(i -> i.getIsDel() == BaseConst.isDel.no_Del.getCode()).collect(Collectors.toList());
            if (!Check.NuNCollection(userList)) {
                return ResponseVo.responseError("用户名或者邮箱已存在");
            }
            String uuid = UUID.randomUUID().toString();
            String url = registerCallBackUrl + "token=" + uuid;
            //redis缓存五分钟
            redisTemplate.opsForValue().set(uuid, JSONObject.toJSONString(registerDto), 5, TimeUnit.MINUTES);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(registerDto.getEmail());
            helper.setSubject("激活邮件");
            //html 加如参数 true
            helper.setText("点击url激活：" + url, true);
            mailSender.send(message);
        } catch (Exception e) {
            LOGGER.error("发送激活邮件错误", e);
            return ResponseVo.responseError("发送激活邮件错误");
        }
        return ResponseVo.responseOk(null);

    }

}
