package helper.helperapi.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_betlist", 
		indexes = {
			@Index(name = "userid_marketid_matchid_isactive_index", columnList = "userid, marketid, matchid, isactive"),
			@Index(name = "marketid_isactive_index", columnList = "marketid, isactive")
			})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXMatchBet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private String userId;

	@Column(name = "matchid")
	private Integer matchId;

	@Column(name = "isback")
	private Boolean isBack;


	@Column(name = "islay")
	private Boolean isLay;

	@Column(name = "pricevalue")
	private Double priceValue;

	@Column(name = "odds")
	private Double odds;

	@Column(name = "placetime")
	private String placeTime;

	@Column(name = "matchedtime")
	private String matchedtime;

	@Column(name = "marketid")
	private String marketId;

	@Column(name = "marketname")
	private String marketName;

	@Column(name = "status")
	private String status;

	@Column(name = "selectionid")
	private int selectionId;

	@Column(name = "selectionname")
	private String selectionName;

	@Column(name = "updatedon")
	private Date updatedOn;

	@Column(name = "result")
	private Integer result;

	@Column(name = "resultid")
	private Integer resultId=0;;

	@Column(name = "date")
	private String date;

	@Column(name = "parentid")
	private String parentId;

	@Column(name = "adminid")
	private String adminId;

	@Column(name = "subadminid")
	private String subadminId;

	@Column(name = "supermasterid")
	private String supermasterId;

	@Column(name = "masterid")
	private String masterId;

	@Column(name = "dealerid")
	private String dealerId;


	@Column(name = "createdon")
	private Date createdOn;

	@Column(name = "stake")
	private int stake;

	@Column(name = "matchname")
	private String matchName;

	@Column(name = "sportid")
	private int sportId;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "pnl")
	private Double pnl;

	@Column(name = "liability")
	private Double liability;

	@Column(name = "netpnl")
	private Double netPnl;

	@Column(name = "isdeleted")
	private Boolean isDeleted;

	@Column(name = "userchain")
	private String userChain;

	@Column(name = "oldpnl")
	private Double oldpnl=0.0;

	@Column(name = "ismongodbupdated")
	private Boolean ismongodbupdated = false;

	@Column(name = "deviceinfo", length = 600)
	private String deviceinfo;

//	@javax.persistence.Transient
//	private DeviceInfo deviceInfo;




	@Column(name = "adminp")
	private Double adminp;

	@Column(name = "subadminp")
	private Double subadminp;

	@Column(name = "supermasterp")
	private Double supermasterp;

	@Column(name = "masterp")
	private Double masterp;

	@Column(name = "dealerp")
	private Double dealerp;


	@Column(name = "admintosbacomm")
	private Double admintosbacomm;

	@Column(name = "subadmintosumcomm")
	private Double subadmintosumcomm;

	@Column(name = "subadmintousercomm")
	private Double subadmintousercomm;


	@Column(name = "supermastertomcomm")
	private Double supermastertomcomm;

	@Column(name = "supermastertousercomm")
	private Double supermastertousercomm;

	@Column(name = "mastertodcomm")
	private Double mastertodcomm;


	@Column(name = "mastertousercomm")
	private Double mastertousercomm;

	@Column(name = "dealertousercomm")
	private Double dealertousercomm;

	@Column(name = "markettype")
	private String markettype;

	@Column(name = "userip")
	private String userip;

	@Column(name = "order_id_casino")
	private String orderIdCasino = "";

	@Column(name = "casino_round_id")
	private String casinoRoundId = "";

//	@Column(name = "isLedgerCalculated")
//	private Boolean isLedgerCalculated;

//	@Column(name = "ledgerCreatedAt")
//	private String ledgerCreatedAt ;

	@Column(name = "is_ledger_calculated")
	private Boolean isLedgerCalculated;

	@Column(name = "ledger_created_at")
	private String ledgerCreatedAt ;

}