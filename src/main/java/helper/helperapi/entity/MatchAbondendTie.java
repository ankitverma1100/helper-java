package helper.helperapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_matchabondendtie")
public class MatchAbondendTie {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "marketid", unique = true)
	private String marketid;
	
	@Column(name = "marketname")
	private String marketname;
	
	@Column(name = "matchid")
	private int matchid;
	
	@Column(name = "matchname")
	private String matchname;
	
	@Column(name = "result")
	private String result;
	
	@Column(name = "sportid")
	private int sportid;
	
	@Column(name = "sportname")
	private String sportname;
	
	@Column(name = "status")
	private boolean status;
	
	@Column(name = "declared_by")
	private String declaredBy;
}
