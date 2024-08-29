package helper.helperapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_marketbetlock", 
indexes = {
		@Index(name = "matchid_index", columnList = "matchid"),
		@Index(name = "matchid_marketname_index", columnList = "matchid, marketname")
		})
public class MarketBetLock {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "marketname")
	private String marketName;
	
	@Column(name = "matchid")
	private Integer matchId;
	
	@Column(name = "userid")
	private String userId;
}
