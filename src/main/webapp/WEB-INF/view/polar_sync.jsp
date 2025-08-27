<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%
    String report = (String) request.getAttribute("report");
    List<String> fileIds = (List<String>) request.getAttribute("fileIds");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Синхронизация с Flow</title>
</head>
<body>
    <h2>Синхронизация данных</h2>

    <form action="sync" method="post">
        <button type="submit">Загрузить из Flow</button>
    </form>

    <hr>

    <% if (report != null) { %>
        <h3>Отчет о синхронизации:</h3>
        <p><%= report %></p>
        <% if (fileIds != null && !fileIds.isEmpty()) { %>
            <ul>
                <% for (String id : fileIds) { %>
                    <li>ID файла: <%= id %></li>
                <% } %>
            </ul>
        <% } %>
    <% } %>

    <% if (error != null) { %>
        <h3 style="color:red;">Ошибка синхронизации:</h3>
        <p><%= error %></p>
    <% } %>
</body>
</html>