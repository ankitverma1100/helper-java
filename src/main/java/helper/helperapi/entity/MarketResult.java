package helper.helperapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "t_matchresult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarketResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@NotNull
	@Column(name = "marketid", unique = true)
	private String marketid;

	@NotNull
	@Column(name = "marketname")
	private String marketname;

	@NotNull
	@Column(name = "sportid")
	private Integer sportid;

//	@NotNull
//	@Column(name = "sportname")
//	private String sportname;

	@NotNull
	@Column(name = "matchid")
	private Integer matchid;

	@NotNull
	@Column(name = "matchname")
	private String matchname;

	@Column(name = "date")
	private Date date;

	@NotNull
	@Column(name = "selectionname")
	private String selectionname;

	@NotNull
	@Column(name = "selectionid")
	private Integer selectionid;

	@NotNull
	@Column(name = "markettype")
	private String markettype;

	@NotNull
	@Column(name = "result")
	private Integer result;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "isresult")
	private Boolean isResult;

	@Column(name = "type")
	private String type;

	@Column(name = "resultstatuscron")
	private Boolean resultStatusCron;
	
	@Column(name = "ismysqlupdated")
	Boolean ismysqlupdated;
	
	@Column(name = "resultstatus", nullable = false, columnDefinition = "VARCHAR(40) default 'OPEN'")
	private String resultstatus = "OPEN";

	@Column(name = "declared_by")
	private String declaredBy;

}
