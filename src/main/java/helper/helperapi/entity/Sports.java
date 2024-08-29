package helper.helperapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_sport")
public class Sports {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "name")
	String name;

	@Column(name = "sportid", unique = true)
	Integer sportid;

	@Column(name = "marketcount")
	String marketCount;

	@Column(name = "createdon")
	String createdOn;

	@Column(name = "updatedon")
	String updatedOn;

	@Column(name = "status")
	Boolean status;

	@Column(name = "adminid")
	String adminid;

	@Column(name = "isnew")
	Boolean isNew;

	@Column(name = "type")
	String type;
	
	@Column(name = "betlock")
	String betlock;
	
	@Column(name = "ismysqlupdated")
	Boolean ismysqlupdated;
	
//	@Column(name = "sport_image")
	@Transient
	private String sportUrl = "";
}
