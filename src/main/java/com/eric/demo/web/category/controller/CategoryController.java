package com.eric.demo.web.category.controller;

import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.ResponseVo;
import com.eric.demo.web.category.domain.Category;
import com.eric.demo.web.category.domain.CategoryCriteria;
import com.eric.demo.web.category.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("/getCategory")
    @ResponseBody
    public ResponseVo getCategory(String categoryId) {
        if (Check.NuNObj(categoryId)) {
            return ResponseVo.responseError("获取大类id为空");
        }
        CategoryCriteria categoryCriteria = new CategoryCriteria();
        categoryCriteria.or().andParentCategoryIdEqualTo(categoryId);
        List<Category> categoryList = categoryService.search(categoryCriteria);
        return ResponseVo.responseOk(categoryList);
    }


}
