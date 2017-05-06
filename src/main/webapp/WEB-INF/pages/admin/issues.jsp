<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>support Issue 管理</title>

    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <h1>SpringMVC 博客系统-用户管理</h1>
    <hr/>

    <h3>所有用户 <a href="/admin/issues/add" type="button" class="btn btn-primary btn-sm">添加</a></h3>

    <c:if test="${empty issueList}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>User表为空，请<a href="/admin/issues/add" type="button" class="btn btn-primary btn-sm">添加</a>
        </div>
    </c:if>

    <c:if test="${!empty issueList}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>系统</th>
                <th>类型</th>
                <th>issueName</th>
                <th>操作</th>
                <th>时间</th>
                <th>附加</th>
            </tr>

            <c:forEach items="${issueList}" var="issue">
                <tr>
                    <td>${issue.sys_scope}-${issue.sub_sys_scope}</td>
                    <td>${issue.sys_type}-${issue.sub_sys_type}</td>
                    <td>${issue.issue}-${issue.sub_issue}</td>
                    <td>${issue.solution}</td>
                    <td>${issue.last_occ_time}</td>
                    <td>${issue.additional}</td>
                    <td>
                        <a href="/admin/issues/show/${issue.id}" type="button" class="btn btn-sm btn-success">详情</a>
                        <a href="/admin/issues/update/${issue.id}" type="button" class="btn btn-sm btn-warning">修改</a>
                        <a href="/admin/issues/delete/${issue.id}" type="button" class="btn btn-sm btn-danger">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>