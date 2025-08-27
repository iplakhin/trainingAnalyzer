package com.iplakhin.flow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.iplakhin.flow.db.PolarUserDAO;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iplakhin.flow.models.PolarActivity;
import com.iplakhin.flow.models.PolarUser;

public class FlowConnector {
    private static PolarUser user;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36";
    private String csrfToken;
    private final CloseableHttpClient client;
    String cookies;

    private static final String LOGIN_URL = "https://auth.polar.com/login?response_type=code&scope=POLAR_SSO&client_id=flow&redirect_uri=https://flow.polar.com/flowSso/redirect&state=";
    private static final String DIARY_URL = "https://flow.polar.com/diary";
    private static final String EVENTS_URL = "https://flow.polar.com/training/getCalendarEvents?start=";
    private static final String ACTIVITY_URL = "https://flow.polar.com/api/export/training/tcx/";



    public FlowConnector() {
        this.client = HttpClients.createDefault();
        }


    private String getPageContent(String url) throws Exception {
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Host", "flow.polar.com");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Language", "ru-RU,ru;q=0.9,de-DE;q=0.8,de;q=0.7,en-US;q=0.6,en;q=0.5");
        request.setHeader("Connection", "Keep-Alive");

        // Add cookies if they exist
        if (cookies != null) {
            request.setHeader("Cookie", cookies);
        }

        StringBuilder result = new StringBuilder();
        try (ClassicHttpResponse response = client.execute(request);
             BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {

            // Extract CSRF token if not already set
            if (this.csrfToken == null) {
                Header cookieHeader = response.getFirstHeader("Set-Cookie");
                if (cookieHeader != null) {
                    String headerValue = cookieHeader.getValue();
                    if (headerValue.contains("XSRF-TOKEN=")) {
                        this.csrfToken = headerValue.substring(
                            headerValue.indexOf("XSRF-TOKEN=") + 11,
                            headerValue.indexOf(";")
                        );
                    }
                }
            }

            // Store cookies from response
            Header setCookieHeader = response.getFirstHeader("Set-Cookie");
            if (setCookieHeader != null) {
                this.cookies = setCookieHeader.getValue();
            }

            // Read response content
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }
        }

        return result.toString();
    }

    public boolean initializeSession() throws Exception {
        // Получаю страницу логина и вытаскиваю CSRF-токен
        String loginUrl = LOGIN_URL + UUID.randomUUID();
        getPageContent(loginUrl);

        // Логинюсь
        PolarUser polarUser = getUser();

        List<NameValuePair> loginForm = new ArrayList<>();

        if (polarUser != null) {
            loginForm.add(new BasicNameValuePair("username", user.getUsername()));
            loginForm.add(new BasicNameValuePair("password", user.getPassword()));
            loginForm.add(new BasicNameValuePair("_csrf", this.csrfToken));
            sendPostRequest("https://auth.polar.com/login", loginForm);
        } else {
            System.out.println("PolarUser is not found. Check that you entered credentials for Polar Flow");
            return false;
        }

        // Проверяю, что залогинился
        String diaryResponse = getPageContent(DIARY_URL);
        return !diaryResponse.contains("\"NotAuthenticated\"");
    }


    public List<PolarActivity> getListActivities(LocalDate start, LocalDate end) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.y", Locale.ENGLISH);
        
        // Формируем URL для запроса
        StringBuilder eventUrl = new StringBuilder(EVENTS_URL);
        eventUrl.append(start.format(formatter))
                .append("&end=")
                .append(end.format(formatter));
        
        // Получаем JSON ответ
        String response = getPageContent(eventUrl.toString());
        
        // Парсим JSON в список активностей
        return parseJsonListActivities(response);
    }

    

    private String sendPostRequest(String url, List<NameValuePair> listParams) throws Exception {
        HttpPost postRequest = new HttpPost(url);
        postRequest.setHeader("User-Agent", USER_AGENT);
        postRequest.setHeader("Accept", "*/*");
        postRequest.setHeader("Accept-Language", "en-US,en;q=0.5");
        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        
        // !!! Здесь как будто перезаписываются куки токеном?
        if (this.cookies != null) {
            postRequest.setHeader("Cookie", this.cookies);
        } else if (this.csrfToken != null) {
            postRequest.setHeader("Cookie", "XSRF-TOKEN=" + this.csrfToken);
        }
        
        postRequest.setEntity(new UrlEncodedFormEntity(listParams));

        StringBuilder result = new StringBuilder();
        try (ClassicHttpResponse response = client.execute(postRequest);
             BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            
            // Update cookies from response if present
            Header cookieHeader = response.getFirstHeader("Set-Cookie");
            if (cookieHeader != null) {
                String headerValue = cookieHeader.getValue();
                this.cookies = headerValue;  // Store complete cookie string
                
                // Also update CSRF token if present in cookies
                if (headerValue.contains("XSRF-TOKEN=")) {
                    this.csrfToken = headerValue.substring(
                        headerValue.indexOf("XSRF-TOKEN=") + 11,
                        headerValue.indexOf(";")
                    );
                }
            }

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }
        }
        
        return result.toString();
    }

    public void closeSession() throws Exception {
        this.getPageContent("https://flow.polar.com/flowSso/logout");
    }



    private static List<PolarActivity> parseJsonListActivities(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<PolarActivity>>() {}.getType();
        List<PolarActivity> activities = gson.fromJson(json, listType);
        
        // Сортируем по убыванию (самые новые первые)
        Collections.sort(activities, Collections.reverseOrder());
        
        return activities;
    }

    public void downloadActivities(List<PolarActivity> activities) throws Exception {
        // Создаем директорию если её нет
        File polarDir = new File("polar");
        if (!polarDir.exists()) {
            polarDir.mkdir();
        }

        for (PolarActivity activity : activities) {
            String activityUrl = ACTIVITY_URL + activity.getListItemId();
            String fileName = String.format("polar/%s.tcx", activity.getListItemId());
            
            // Пропускаем если файл уже существует
            File activityFile = new File(fileName);
            if (activityFile.exists()) {
                continue;
            }

            // Получаем содержимое файла
            String content = getPageContent(activityUrl);
            
            // Сохраняем в файл
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(content);
            }
            Thread.sleep(2000);
        }
    }

    private static PolarUser getUser() {
        return PolarUserDAO.getPolarUser();
    }

    public static void setUser(PolarUser user) {
        FlowConnector.user = user;
    }
}
