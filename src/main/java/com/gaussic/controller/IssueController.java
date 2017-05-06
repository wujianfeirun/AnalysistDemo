package com.gaussic.controller;

import com.gaussic.model.IssueEntity;
import com.gaussic.model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2017/4/23.
 */

@Controller
public class IssueController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/admin/issues", method = RequestMethod.GET)
    public String getIssues(ModelMap modelMap) {
        List<IssueEntity> issueList = new ArrayList<IssueEntity>();
        for (int i = 0; i <5 ; i++) {
            IssueEntity issue=new IssueEntity("150699","tps","system","aa","bbb","aaaa","aaa",new Date(),"jw18233@com"+i,"add");
            issueList.add(issue);
        }

        modelMap.addAttribute("issueList", issueList);
        return "admin/issues";
    }

    @RequestMapping(value = "/admin/issues/add", method = RequestMethod.GET)
    public String addIssue() {
        return "admin/addIssue";
    }

    @RequestMapping(value = "/admin/issues/addP", method = RequestMethod.GET)
    public String addUserPost(@ModelAttribute("user") IssueEntity issueEntity) {
        // 重定向到用户管理页面，方法为 redirect:url
        return "redirect:/admin/issues";
    }

    @RequestMapping(value = "/admin/issues/show/{id}", method = RequestMethod.GET)
    public String showIssues(@PathVariable("id") Integer userId, ModelMap modelMap) {
        return "admin/userDetail";
    }

    // 更新用户信息 页面
    @RequestMapping(value = "/admin/issues/update/{id}", method = RequestMethod.GET)
    public String updateIssue(@PathVariable("id") Integer userId, ModelMap modelMap) {
        return "admin/updateUser";
    }

    // 更新用户信息 操作
    @RequestMapping(value = "/admin/issues/updateP", method = RequestMethod.GET)
    public String updateIssuePost(@ModelAttribute("userP") UserEntity user) {
        return "redirect:/admin/issues";
    }

    // 删除用户
    @RequestMapping(value = "/admin/issues/delete/{id}", method = RequestMethod.GET)
    public String deleteIssue(@PathVariable("id") Integer userId) {
        return "redirect:/admin/issues";
    }
}
