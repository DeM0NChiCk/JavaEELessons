package ru.itis.semestr.service;

import ru.itis.semestr.entity.News;

import java.util.List;

public interface NewsService {

    List<News> fetchNewsFromGoogleSheets();

}
