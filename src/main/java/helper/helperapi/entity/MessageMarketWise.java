package helper.helperapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "message_market_wise", uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "market_id"}))
public class MessageMarketWise {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "match_id")
	private Integer matchId;
	
	@Column(name = "market_id")
	private String marketId;
	
	@Column(name = "market_name")
	private String marketName;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "is_display_message")
	private Boolean isDisplayMessage;
}
