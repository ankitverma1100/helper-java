package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.MinMaxBetAmountSql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinMaxBetAmountRepo extends JpaRepository<MinMaxBetAmountSql,Integer> {

    List<MinMaxBetAmountSql> findByTypeIn(List<String> types);
}
