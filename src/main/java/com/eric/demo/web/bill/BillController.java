package com.eric.demo.web.bill;

import com.eric.demo.commons.annotation.CommonLog;
import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.ResponseVo;
import com.eric.demo.commons.util.SOAResParseUtil;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.commons.validator.DataTransferObject;
import com.eric.demo.commons.validator.ParamCheckLogic;
import com.eric.demo.web.bill.domain.Bill;
import com.eric.demo.web.bill.dto.BillSaveDto;
import com.eric.demo.web.bill.service.IBillService;
import com.eric.demo.web.users.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "bill")
public class BillController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private ParamCheckLogic paramCheckLogic;

    @Autowired
    private IBillService billService;

    @RequestMapping(value = "create")
    @CommonLog
    @ResponseBody
    public ResponseVo create(BillSaveDto billSaveDto, HttpSession session) {
        User user = (User) session.getAttribute(BaseConst.USER_SESSION_KEY);
        try {
            if (Check.NuNObj(user)) {
                return ResponseVo.responseError("登录过期请重新登录");
            }
            DataTransferObject dto = paramCheckLogic.checkObjParamValidate(billSaveDto);
            if (!SOAResParseUtil.checkDTO(dto)) {
                return ResponseVo.responseError(dto.getMsg());
            }
            Bill bill = new Bill();
            BeanUtils.copyProperties(billSaveDto, bill);
            bill.setUid(user.getId());
            bill.setCreatedBy(user.getNickName());

            billService.create(bill);
        } catch (Exception e) {
            LOGGER.error("记录创建失败", e);
            return ResponseVo.responseError("创建记录失败");
        }
        return ResponseVo.responseOk(null);
    }

}
