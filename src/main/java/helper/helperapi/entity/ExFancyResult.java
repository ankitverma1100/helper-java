package helper.helperapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_fancyresult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExFancyResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "fancyid", unique = true)
	private String fancyid;

	@Column(name = "fancyname")
	private String fancyname;

	@Column(name = "sportid")
	private int sportid;

	@Column(name = "sportname")
	private String sportname;

	@Column(name = "matchid")
	private Integer matchid;

	@Column(name = "matchname")
	private String matchname;

	@Column(name = "seriesname")
	private String seriesname;

	@Column(name = "createdon")
	private String createdon;

	@Column(name = "result")
	private Integer result;

	@Column(name = "isresult")
	private Boolean isResult;

	@Column(name = "resultstatuscron")
	private Boolean resultStatusCron;
	
	@Column(name = "resultdeclareby")
	private String resultdeclareby;

	@Column(name = "isprofitlossclear")
	private Boolean isprofitlossclear;
	
	
	@Column(name = "fancytype")
	private String fancyType = "";
	
	@Column(name = "resultstatus", nullable = false, columnDefinition = "VARCHAR(40) default 'OPEN'")
	private String resultstatus = "OPEN";
	
	@Column(name = "updatedon", columnDefinition = " Timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedOn;
	
	@Transient
	private String resultDetail;

}
