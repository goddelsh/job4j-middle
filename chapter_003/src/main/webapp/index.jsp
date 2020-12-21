<%@ page import="models.User" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <script src="<%=request.getContextPath()%>/script.js"></script>
    <title>TODO List</title>
</head>
<body>
<div class="container pt-3">
    <a class="nav-link" href="<%=request.getContextPath()%>/login.do">
        <%
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) { %>
        <%=user.getName()%>
        | Выйти <% } %> </a>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Новая задача
            </div>
            <div class="card-body">
                <div class="form-group">
                    <p><label>Описание</label></p>
                    <p><textarea id="description" rows="10" cols="45" name="descr"></textarea></p>
                    <p><button onclick="createTask()" class="btn btn-default">Добавить</button></p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <p>Список задач</p>
                <p><button id="filterBtn" class="btn btn-default">...</button></p>
            </div>

            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">Описание</th>
                        <th scope="col">Дата создания</th>
                        <th scope="col">Статус</th>
                    </tr>
                    </thead>
                    <tbody id="itemsList">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
