package ru.itis.semestr.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itis.semestr.config.AppConfig;
import ru.itis.semestr.dto.response.GoogleSheetResponse;
import ru.itis.semestr.entity.News;
import ru.itis.semestr.service.NewsService;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final RestTemplate restTemplate;

    private final String SHEET_ID;
    private final String API_KEY;
    private static final String RANGE = "Новости!A2:C";

    public NewsServiceImpl(
            RestTemplate restTemplate,
            AppConfig appConfig) {
        this.SHEET_ID = appConfig.googleConfig().getSheetsId();
        this.API_KEY = appConfig.googleConfig().getApiKey();
        this.restTemplate = restTemplate;
    }

    public List<News> fetchNewsFromGoogleSheets() {
        String url = String.format(
                "https://sheets.googleapis.com/v4/spreadsheets/%s/values/%s?key=%s",
                SHEET_ID, RANGE, API_KEY
        );

        ResponseEntity<GoogleSheetResponse> response =
                restTemplate.getForEntity(url, GoogleSheetResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<List<String>> values = response.getBody().getValues();

            if (values == null || values.isEmpty()) {
                return List.of(
                        News.builder()
                                .title("Нет новостей")
                                .summary("Скоро!")
                                .details("Скоро здесь будут новости!")
                                .build()
                );
            }

            return values.stream()
                    .map(row -> {
                        String title = !row.isEmpty() ? row.get(0) : "";
                        String summary = row.size() > 1 ? row.get(1) : "";
                        String details = row.size() > 2 ? row.get(2) : "";
                        return News.builder()
                                .title(title)
                                .summary(summary)
                                .details(details)
                                .build();
                    })
                    .toList();
        }

        return List.of();
    }
}