package com.flightreservation.business.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.flightreservation.data.repository.Repository;
import com.flightreservation.model.MonthlyNews;

public class NewsService {
    private final Repository<MonthlyNews> newsRepository;
    private MonthlyNews cachedNews;

    public NewsService(Repository<MonthlyNews> newsRepository) {
        this.newsRepository = Objects.requireNonNull(newsRepository);
    }

    public MonthlyNews getMonthlyNews() {
        if (cachedNews == null) {
            cachedNews = fetchLatestNews();
        }
        return cachedNews;
    }

    public void refreshNews() {
        cachedNews = fetchLatestNews();
    }

    private MonthlyNews fetchLatestNews() {
        List<MonthlyNews> newsList = newsRepository.getAll();
        return newsList.stream()
                .max(Comparator.comparing(MonthlyNews::getPublishDate))
                .orElse(null);
    }
}
