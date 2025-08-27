<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Polar Authentication</title>
</head>
<body>
    <h2>Polar account credentials</h2>
    <form action="login" method="post">
        <label for="username">Имя пользователя:</label><br>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Пароль:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <button type="submit">Сохранить</button>
    </form>
</body>
</html>