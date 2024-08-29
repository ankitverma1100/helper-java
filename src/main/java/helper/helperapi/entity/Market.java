package helper.helperapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "t_market", 
		indexes = {
		@Index(name = "eventid_marketid_index", columnList = "eventid, marketid")
		})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Market {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name = "marketid", unique = true)
	private String marketid;
	
	@Column(name = "seriesid")
	private Integer seriesid;
	
	@Column(name = "sportid")
	private Integer sportid;
	
	@Column(name = "eventid")
	private Integer eventid;
	
	@Column(name = "marketname")
	private String marketname;
	
	@Column(name = "matchname")
	private String matchname;
	
	@Column(name = "status")
	private Boolean status;
	
	@Column(name = "isactive")
	private Boolean isactive;
	
	@Column(name = "createdon")
	private String createdon;
	
//	@Column(name = "updatedon")
//	private String updatedon;
	
	@Column(name = "updatedon", columnDefinition = " Timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedOn;
	
	@Column(name = "opendate")
	private Date opendate;
	
	@Column(name = "startdate")
	private String startdate;
	
	@Transient
	private String date;
		
	
	@Transient
	private String Ids;
	
	@Transient
	private String selectionids;
	
	@Column(name = "minbet")
	private Integer minbet;
	
	@Column(name = "maxbet")
	private Integer maxbet;
	
	@Column(name = "betdelay")
	private Integer betDelay;
	
	@Transient
	private String runnername;
	
	@Transient
	private String fancyprovider;
	
	@Transient
	private Boolean fancypause;
	
	@Transient
	private String sportName;
	
	@Transient
	private String seriesName;
	
	@Column(name = "inplay")
	private Boolean inPlay;

	@Transient
	private String marketIdWithOutDecimal;
	
	@Column(name = "min_bet_rate")
	private Double minBetRate = 0.0d;
	
	@Column(name = "max_bet_rate")
	private Double maxBetRate = 0.0d;
	
	@Column(name = "is_redis_updated")
	private boolean isRedisUpdated;

	@Column(name="display_message")
	private String displayMessage;
	
	@Column(name = "issuspended")
	private boolean isSuspended;
	
	@Column(name = "is_rolled_back")
	private boolean isRolledBack;
	
	
}