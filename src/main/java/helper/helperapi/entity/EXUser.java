package helper.helperapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

@Data
@Entity
@Table(name = "t_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXUser implements Serializable {

	private static final long serialVersionUID = -5370361549424940171L;

	@Id
	@GeneratedValue
	Integer id;

	@NotNull
	@Column(name = "username")
	private String username;

	@NotNull
	@Column(name = "userid", unique = true)
	private String userid;

	@NotNull
	@Column(name = "password")
	private String password;

//	@NotNull
//	@Column(name = "contact", unique = true, nullable = true)
//	private String contact;
	
	@Column(name = "mobile", nullable = true)
	private String mobile = "";

	@Column(name = "betlock")
	private Boolean betlock;

	@Column(name = "usertype")
	private Integer usertype;

	@Column(name = "accountlock")
	private Boolean accountlock;

    @NotNull
	@Column(name = "parentid")
	private String parentid;

	@Column(name = "adminid")
	private String adminid;

	@Column(name = "subadminid")
	private String subadminid;

	@Column(name = "supermasterid")
	private String supermasterid;

	@Column(name = "masterid")
	private String masterid;

	@Column(name = "dealerid")
	private String dealerid;

	@NotNull
	@Column(name = "adminpartership")
	private BigDecimal adminpartership;

	@Column(name = "subadminpartnership")
	private BigDecimal subadminpartnership;

	@Column(name = "supermastepartnership")
	private BigDecimal supermastepartnership;

	@Column(name = "masterpartership")
	private BigDecimal masterpartership;

	@Column(name = "delearpartership")
	private BigDecimal delearpartership;

	@Column(name = "netexposure")
	private Integer netexposure;

	@Column(name = "createdon")
	private String createdon;

	@Column(name = "updaedon")
	private String updaedon;
	
	@Column(name = "appid")
	private Integer appid;

	@Column(name = "passwordtype")
	private String passwordtype;

	@Column(name = "userchain")
	private String userchain;

	@Transient
	private BigDecimal partnershipratio;
	
	@Column(name = "active")
	private  Boolean active;
	
	@NotNull
	@Column(name = "oddsloss")
	private BigDecimal oddsloss;
	
	@NotNull
	@Column(name = "casinocommssion")
	private Double casinocommssion;
	
	@NotNull
	@Column(name = "fancyloss")
	private BigDecimal fancyloss;

	@Column(name = "availablebalance")
	private BigDecimal availableBalance;
	
	@Column(name = "betcount")
	private Boolean betCount; 
	
	@Column(name = "selfpartnership")
	private Integer selfpartnership;
	
	@Transient
	private String partnership;
	
	@Transient
	private String partnershipc;

	@Transient
	private BigDecimal pnlamount;

	@Column(name = "uplineamount")
	private Double uplineAmount;

	@Column(name = "creditref")
	private BigDecimal creditRef;

	@Transient
	private Double totalMasterBalance;

	@Transient
	private BigDecimal downlinecreditRef;

	@Transient
	private BigDecimal downlineavailableBalance;

	@Transient
	private Double downlineWithProfitLoss;

	@Transient
	private Double myProfitLoss;

	@Column(name = "availablebalancewithpnl")
	private Double availableBalanceWithPnl;
	
	@Transient
	private Double currentExpo;

	@Transient
	private String parentPassword;

	@Column(name = "adminpartershipc")
	private BigDecimal adminpartershipc;

	@Column(name = "subadminpartnershipc")
	private BigDecimal subadminpartnershipc;

	@Column(name = "supermastepartnershipc")
	private BigDecimal supermastepartnershipc;

	@Column(name = "masterpartershipc")
	private BigDecimal masterpartershipc;

	@Column(name = "delearpartershipc")
	private BigDecimal delearpartershipc;

	@Transient
	private BigDecimal partnershipratioc;

	@Transient
	private String userRoll;

	@Transient
	private String appurl;

	@Transient
	private String appname;
	
	@Transient
	private Double balance;

	@Column(name = "betlockby")
	private Integer betlockBy;
	
	@Column(name = "lcasinolock")
	private Boolean lcasinolock;
	
	@Column(name = "vcasinolock")
	private Boolean vcasinolock;
	
	
	@Column(name = "city")
	private String city = "";
	
//	@Column(name = "favmaster")
//	private boolean favmaster = false;
	
	@Column(name = "themecolor")
	private String themecolor = "";
	
	@Column(name = "withdraw_point")
	private double withdrawPoint;

	@Column(name = "reference")
	private String reference;

	@Column(name = "casinopnl" , columnDefinition = "double default 0.00")
	private double casinopnl;

	
	@Transient
	private HashMap<String, HashMap<String, String>> betdelaymap;
	
	@Transient
	private String netexposurefromlibility;

}
