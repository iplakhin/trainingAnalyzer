package com.iplakhin.spring.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/sync")
public class PolarSyncController {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> fileIds = new ArrayList<>();
        String report;
        String error = null;
        try {
            // Пример имитации загрузки
            fileIds.add("file-123");
            fileIds.add("file-456");
            fileIds.add("file-789");
            report = "Синхронизация завершена успешно.";
        } catch (Exception e) {
            report = "Синхронизация завершена с ошибками.";
            error = e.getMessage();
        }
        request.setAttribute("report", report);
        request.setAttribute("fileIds", fileIds);
        request.setAttribute("error", error);
        request.getRequestDispatcher("sync.jsp").forward(request, response);
    }
}

