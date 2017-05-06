package com.gaussic.model;

import java.util.Date;

/**
 * Created by asus on 2017/4/23.
 */
public class IssueEntity {
    private String sys_scope;
    private String sub_sys_scope;
    private String sys_type;
    private String sub_sys_type;
    private String issue;
    private String sub_issue;
    private String solution;
    private Date last_occ_time;
    private String emaillist;
    private String additional;



    public IssueEntity(String sys_scope, String sub_sys_scope, String sys_type, String sub_sys_type, String issue, String sub_issue, String solution, Date last_occ_time, String emaillist, String additional) {
        this.sys_scope = sys_scope;
        this.sub_sys_scope = sub_sys_scope;
        this.sys_type = sys_type;
        this.sub_sys_type = sub_sys_type;
        this.issue = issue;
        this.sub_issue = sub_issue;
        this.solution = solution;
        this.last_occ_time = last_occ_time;
        this.emaillist = emaillist;
        this.additional = additional;
    }

    public String getSys_scope() {
        return sys_scope;
    }

    public void setSys_scope(String sys_scope) {
        this.sys_scope = sys_scope;
    }

    public String getSub_sys_scope() {
        return sub_sys_scope;
    }

    public void setSub_sys_scope(String sub_sys_scope) {
        this.sub_sys_scope = sub_sys_scope;
    }

    public String getSys_type() {
        return sys_type;
    }

    public void setSys_type(String sys_type) {
        this.sys_type = sys_type;
    }

    public String getSub_sys_type() {
        return sub_sys_type;
    }

    public void setSub_sys_type(String sub_sys_type) {
        this.sub_sys_type = sub_sys_type;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSub_issue() {
        return sub_issue;
    }

    public void setSub_issue(String sub_issue) {
        this.sub_issue = sub_issue;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Date getLast_occ_time() {
        return last_occ_time;
    }

    public void setLast_occ_time(Date last_occ_time) {
        this.last_occ_time = last_occ_time;
    }

    public String getEmaillist() {
        return emaillist;
    }

    public void setEmaillist(String emaillist) {
        this.emaillist = emaillist;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

}
