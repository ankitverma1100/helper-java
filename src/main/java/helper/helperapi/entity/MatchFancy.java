package helper.helperapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "t_matchfancy", 
		indexes = {
		@Index(name = "eventid_isactive_index", columnList = "eventid, isactive")
		})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchFancy implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name = "fancyid", unique = true)
	private String fancyid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "sportid")
	private Integer sportId;
	
	@Column(name = "matchname")
	private String matchname;
	
	@Column(name = "oddstype")
	private String oddstype;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "eventid")
	private Integer eventid;
	
	@Column(name = "runnerid")
	private String runnerid;
	
	@Column(name = "isactive")
	private Boolean isActive;
	
	@Column(name = "mtype")
	private String mtype;
	
	@Column(name = "addby")
	private String addby;
	
	@Column(name = "suspendedby")
	private String suspendedBy;
	
	@Column(name = "provider")
	private String provider;
	
	@Column(name = "createdon")
	private Date createdon;
	
    @Column(name = "remarks")
	private String remarks;
	
	@Column(name = "minbet")
	private int minBet;
	
	@Column(name = "maxbet")
	private int maxBet;
	
	@Column(name = "betdelay")
	private int betDelay;
	
	@Column(name = "updatedon", columnDefinition = " Timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedOn;
	
	@Column(name = "is_rolled_back")
	private boolean isRolledBack;
	
	@Column(name = "is_show")
	private boolean isShow;
}
