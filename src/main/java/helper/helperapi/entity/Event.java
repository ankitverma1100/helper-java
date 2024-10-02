package helper.helperapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "t_event", 
		indexes = {
		@Index(name = "eventid_index", columnList = "eventid")
		})
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "seriesid")
	private Integer seriesid;

	@Column(name = "sportid")
	private Integer sportid;

	@Column(name = "eventid", unique = true)
	private long eventid;

	@Column(name = "eventname")
	private String eventname;

	@Column(name = "open_date")
	private String openDate;

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
	
	@Column(name = "matchstartdate")
	private Date matchstartdate;
	
	@Column(name = "fancypause")
	private Boolean fancypause;

	@Column(name = "livetv")
	private Boolean liveTv;

	@Column(name = "betlock")
	private Boolean betLock;
	
	@Column(name = "is_redis_updated")
	private boolean isRedisUpdated;
	
	@Column(name = "in_play")
	private boolean inPlay;
	
	@Column(name = "fancylock")
	private boolean fancyLock;
	
	@Column(name = "bookmaker")
	private boolean bookmaker;
	
	@Column(name = "fancy")
	private boolean fancy;
	
	@Column(name = "channel_id")
	private String channelId;
	
	@javax.persistence.Transient
	private Integer backdiff;
	
	@javax.persistence.Transient
	private Integer laydiff;
	
}