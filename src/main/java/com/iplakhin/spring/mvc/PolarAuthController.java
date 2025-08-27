package com.iplakhin.spring.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;


@WebServlet("/polarlogin")
public class PolarAuthController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        if ("admin".equals(username) && "admin".equals(password)) {
            response.sendRedirect("sync.jsp");
        } else {
            request.setAttribute("error", "Неверное имя пользователя или пароль");
            request.getRequestDispatcher("auth.jsp").forward(request, response);
        }
    }
}
