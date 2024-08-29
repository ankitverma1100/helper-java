package helper.helperapi.repository;


import helper.helperapi.models.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface SeriesRepository extends MongoRepository <Series,String> {
    Optional<Series> findBySeriesid(String seriesid);
    Series findBySeriesname (String seriesname);
}
