package helper.helperapi.mysqlRepo;
import helper.helperapi.sqlModels.FancyResultsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FancyResultsRepository extends JpaRepository<FancyResultsModel,Integer> {

    @Query(value = "SELECT \n" +
            "  fr.fancyid,\n" +
            "  fr.fancyname,\n" +
            "  fr.result,\n" +
            "  fr.resultdeclareby,\n" +
            "  fr.createdon,\n" +
            "  rs.result AS rsfancy_result,\n" +
            "  rs.fancyid AS rsfancy_fancyid,\n" +
            "  rb.marketid,\n" +
            "  rb.donby\n" +
            "FROM t_fancyresult fr\n" +
            "LEFT JOIN t_rsfancy_updated_result rs ON fr.fancyid = rs.fancyid\n" +
            "LEFT JOIN t_fancy_rollback_detail rb ON fr.fancyid = rb.marketid\n" +
            "WHERE fr.matchid =:matchId\n" +
            "ORDER BY fr.createdon DESC",nativeQuery = true)
    public List<Map<String,Object>> getFancyResult(int matchId);

}
