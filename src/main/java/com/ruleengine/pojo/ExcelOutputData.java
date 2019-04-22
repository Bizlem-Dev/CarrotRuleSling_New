package com.ruleengine.pojo;

public class ExcelOutputData {

	private String agentName = null;
	private String agentId = null;
	private String teamName = null;
	private String netSales = null;
	private String saleDate = null;
	private String agentDirectC = null;
	private String dateoFregistrationC = null;
	private String buildingNameC = null;
	private String propertyNameC = null;
	private String commisionAmt = null;
	private String commissionRate = null;
	private String clubbingFlag = null;
	private String clubbingSales = "0";
	private String dealBaseOutput = null;
	private String baseRateOutput = null;
	private String nationalityMultiplier = null;
	private String nationalityIncrement = null;
	private String nationalityFlatRate = null;
	private String focusedProdMultiplier = null;
	private String focusedProdIncrement = null;
	private String focusedProdFlatRate = null;
	private String eidIncentiveMultiplier = null;
	private String eidIncentiveIncrement = null;
	private String eidIncentiveFlatRate = null;
	private String commissionMultiplier = null;
	private String commissionIncrement = null;
	private String commissionFlatRate = null;
    private String registrationId = null;
    private String month = null;
	private String depositReceived = null;
	private String docOk = null;
	private String unitName = null;
	private String heardOfDamac = null;
	private String bedRmTypeName = null;
	private String salePerson = null;
	private String managerName = null;
	private String hos = null;
	private String hod = null;
	private String customerNumber = null;
	private String status = null;
	private String netSplitSale = null;
	private String bookingType = null;
	private String docOkDt = null;
	private String splitDeals = null;
	private String splitPerc = null;
	private String saleYear = null;
	private String endOfMonthDate = null;
	private String noOfDays = null;
	private String docOkDays = null;
	private String dpReceiptDays = null;
	private String salesPerMonth = null;
	private String agentSales = null;
	private String directSales = null;
	private String pcComm = null;
	private String nationality = null;
	private String collPerc = null;
	private String depRecDt = null;
	private String depRecMnth = null;
	private String saleOffice = null;
	private String pcCommissionNetSale= null;
	private String pcCommissionOrg = null;
	
	private String cdpFlag = null;
	private String eidFlag = null;
	private String specialCommFlag = null;
	private String collection = null;
	private String nationalityFlag = null;
	/*								
	{"price":"1700000","district":"AL YUFRAH 2","product":"Dubai","office":"UAE",
	"bedroom":"3 BR","sqft":"2829","pricepersqft":"600.9190526688","pricerange":"<=Dhs 1000",
	"dealmoder":"Regular","remarks":" ","leaseoption":"N","transfercategory":" ","transferremarks":" ",
	"versionnumber":"TYPE 3 TYPE 3C TYPE 3","complainceflag":" ","unitbalwithoutrebate":" ","partyid":"1550880",
	"dp__c":"0.24","collection__c":"0.34","cdp_flag":"Y","mgrcomm":" ","thcomm":" ","vpcomm":" ","totalcomm":" ",
    "paigflag":" ","ratepaid":" ","pccommpaid":" ","mgrcommpaid":" ","thcommpaid":" ","vpcommpaid":" ",
    "totalcommpaid":" ","pccommbtp":" ","mgrcommbtp":" ","thcommbtp":" ","vpcommbtp":" ","totalcommbtp":" ",
    "commission19thjul-31staug17":" ","totalsalescomm19jul-31aug":" ","eidincentive":" ",
	"oldpolicycommrate":" ","agentclass":" ","1.5xflag":" ",
	"1.5x-sale":" ","ao50%reductionvillas":" ","eligiblectcases":" ","recovery":" "}				
					*/
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getNetSales() {
		return netSales;
	}
	public void setNetSales(String netSales) {
		this.netSales = netSales;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getAgentDirectC() {
		return agentDirectC;
	}
	public void setAgentDirectC(String agentDirectC) {
		this.agentDirectC = agentDirectC;
	}
	public String getDateoFregistrationC() {
		return dateoFregistrationC;
	}
	public void setDateoFregistrationC(String dateoFregistrationC) {
		this.dateoFregistrationC = dateoFregistrationC;
	}
	public String getBuildingNameC() {
		return buildingNameC;
	}
	public void setBuildingNameC(String buildingNameC) {
		this.buildingNameC = buildingNameC;
	}
	public String getPropertyNameC() {
		return propertyNameC;
	}
	public void setPropertyNameC(String propertyNameC) {
		this.propertyNameC = propertyNameC;
	}
	public String getCommisionAmt() {
		return commisionAmt;
	}
	public void setCommisionAmt(String commisionAmt) {
		this.commisionAmt = commisionAmt;
	}
	
	public String getClubbingFlag() {
		return clubbingFlag;
	}
	public void setClubbingFlag(String clubbingFlag) {
		this.clubbingFlag = clubbingFlag;
	}
	public String getClubbingSales() {
		return clubbingSales;
	}
	public void setClubbingSales(String clubbingSales) {
		this.clubbingSales = clubbingSales;
	}
	public String getDealBaseOutput() {
		return dealBaseOutput;
	}
	public void setDealBaseOutput(String dealBaseOutput) {
		this.dealBaseOutput = dealBaseOutput;
	}
	public String getBaseRateOutput() {
		return baseRateOutput;
	}
	public void setBaseRateOutput(String baseRateOutput) {
		this.baseRateOutput = baseRateOutput;
	}
	public String getNationalityMultiplier() {
		return nationalityMultiplier;
	}
	public void setNationalityMultiplier(String nationalityMultiplier) {
		this.nationalityMultiplier = nationalityMultiplier;
	}
	public String getNationalityIncrement() {
		return nationalityIncrement;
	}
	public void setNationalityIncrement(String nationalityIncrement) {
		this.nationalityIncrement = nationalityIncrement;
	}
	public String getNationalityFlatRate() {
		return nationalityFlatRate;
	}
	public void setNationalityFlatRate(String nationalityFlatRate) {
		this.nationalityFlatRate = nationalityFlatRate;
	}
	public String getFocusedProdMultiplier() {
		return focusedProdMultiplier;
	}
	public void setFocusedProdMultiplier(String focusedProdMultiplier) {
		this.focusedProdMultiplier = focusedProdMultiplier;
	}
	public String getFocusedProdIncrement() {
		return focusedProdIncrement;
	}
	public void setFocusedProdIncrement(String focusedProdIncrement) {
		this.focusedProdIncrement = focusedProdIncrement;
	}
	public String getFocusedProdFlatRate() {
		return focusedProdFlatRate;
	}
	public void setFocusedProdFlatRate(String focusedProdFlatRate) {
		this.focusedProdFlatRate = focusedProdFlatRate;
	}
	public String getEidIncentiveMultiplier() {
		return eidIncentiveMultiplier;
	}
	public void setEidIncentiveMultiplier(String eidIncentiveMultiplier) {
		this.eidIncentiveMultiplier = eidIncentiveMultiplier;
	}
	public String getEidIncentiveIncrement() {
		return eidIncentiveIncrement;
	}
	public void setEidIncentiveIncrement(String eidIncentiveIncrement) {
		this.eidIncentiveIncrement = eidIncentiveIncrement;
	}
	public String getEidIncentiveFlatRate() {
		return eidIncentiveFlatRate;
	}
	public void setEidIncentiveFlatRate(String eidIncentiveFlatRate) {
		this.eidIncentiveFlatRate = eidIncentiveFlatRate;
	}
	public String getCommissionMultiplier() {
		return commissionMultiplier;
	}
	public void setCommissionMultiplier(String commissionMultiplier) {
		this.commissionMultiplier = commissionMultiplier;
	}
	public String getCommissionIncrement() {
		return commissionIncrement;
	}
	public void setCommissionIncrement(String commissionIncrement) {
		this.commissionIncrement = commissionIncrement;
	}
	public String getCommissionFlatRate() {
		return commissionFlatRate;
	}
	public void setCommissionFlatRate(String commissionFlatRate) {
		this.commissionFlatRate = commissionFlatRate;
	}
	public String getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDepositReceived() {
		return depositReceived;
	}
	public void setDepositReceived(String depositReceived) {
		this.depositReceived = depositReceived;
	}
	public String getDocOk() {
		return docOk;
	}
	public void setDocOk(String docOk) {
		this.docOk = docOk;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getHeardOfDamac() {
		return heardOfDamac;
	}
	public void setHeardOfDamac(String heardOfDamac) {
		this.heardOfDamac = heardOfDamac;
	}
	public String getBedRmTypeName() {
		return bedRmTypeName;
	}
	public void setBedRmTypeName(String bedRmTypeName) {
		this.bedRmTypeName = bedRmTypeName;
	}
	public String getSalePerson() {
		return salePerson;
	}
	public void setSalePerson(String salePerson) {
		this.salePerson = salePerson;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getHos() {
		return hos;
	}
	public void setHos(String hos) {
		this.hos = hos;
	}
	public String getHod() {
		return hod;
	}
	public void setHod(String hod) {
		this.hod = hod;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNetSplitSale() {
		return netSplitSale;
	}
	public void setNetSplitSale(String netSplitSale) {
		this.netSplitSale = netSplitSale;
	}
	public String getBookingType() {
		return bookingType;
	}
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	public String getDocOkDt() {
		return docOkDt;
	}
	public void setDocOkDt(String docOkDt) {
		this.docOkDt = docOkDt;
	}
	public String getSplitDeals() {
		return splitDeals;
	}
	public void setSplitDeals(String splitDeals) {
		this.splitDeals = splitDeals;
	}
	public String getSplitPerc() {
		return splitPerc;
	}
	public void setSplitPerc(String splitPerc) {
		this.splitPerc = splitPerc;
	}
	public String getSaleYear() {
		return saleYear;
	}
	public void setSaleYear(String saleYear) {
		this.saleYear = saleYear;
	}
	public String getEndOfMonthDate() {
		return endOfMonthDate;
	}
	public void setEndOfMonthDate(String endOfMonthDate) {
		this.endOfMonthDate = endOfMonthDate;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getDocOkDays() {
		return docOkDays;
	}
	public void setDocOkDays(String docOkDays) {
		this.docOkDays = docOkDays;
	}
	public String getDpReceiptDays() {
		return dpReceiptDays;
	}
	public void setDpReceiptDays(String dpReceiptDays) {
		this.dpReceiptDays = dpReceiptDays;
	}
	public String getSalesPerMonth() {
		return salesPerMonth;
	}
	public void setSalesPerMonth(String salesPerMonth) {
		this.salesPerMonth = salesPerMonth;
	}
	public String getAgentSales() {
		return agentSales;
	}
	public void setAgentSales(String agentSales) {
		this.agentSales = agentSales;
	}
	public String getDirectSales() {
		return directSales;
	}
	public void setDirectSales(String directSales) {
		this.directSales = directSales;
	}
	public String getPcComm() {
		return pcComm;
	}
	public void setPcComm(String pcComm) {
		this.pcComm = pcComm;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCollPerc() {
		return collPerc;
	}
	public void setCollPerc(String collPerc) {
		this.collPerc = collPerc;
	}
	public String getDepRecDt() {
		return depRecDt;
	}
	public void setDepRecDt(String depRecDt) {
		this.depRecDt = depRecDt;
	}
	public String getDepRecMnth() {
		return depRecMnth;
	}
	public void setDepRecMnth(String depRecMnth) {
		this.depRecMnth = depRecMnth;
	}
	public String getSaleOffice() {
		return saleOffice;
	}
	public void setSaleOffice(String saleOffice) {
		this.saleOffice = saleOffice;
	}
	public String getPcCommissionNetSale() {
		return pcCommissionNetSale;
	}
	public void setPcCommissionNetSale(String pcCommissionNetSale) {
		this.pcCommissionNetSale = pcCommissionNetSale;
	}
	public String getPcCommissionOrg() {
		return pcCommissionOrg;
	}
	public void setPcCommissionOrg(String pcCommissionOrg) {
		this.pcCommissionOrg = pcCommissionOrg;
	}
	public String getCdpFlag() {
		return cdpFlag;
	}
	public void setCdpFlag(String cdpFlag) {
		this.cdpFlag = cdpFlag;
	}
	public String getEidFlag() {
		return eidFlag;
	}
	public void setEidFlag(String eidFlag) {
		this.eidFlag = eidFlag;
	}
	public String getSpecialCommFlag() {
		return specialCommFlag;
	}
	public void setSpecialCommFlag(String specialCommFlag) {
		this.specialCommFlag = specialCommFlag;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getNationalityFlag() {
		return nationalityFlag;
	}
	public void setNationalityFlag(String nationalityFlag) {
		this.nationalityFlag = nationalityFlag;
	}
	@Override
	public String toString() {
		return String.format(
				"ExcelOutputData [agentName=%s, agentId=%s, teamName=%s, netSales=%s, saleDate=%s, agentDirectC=%s, dateoFregistrationC=%s, buildingNameC=%s, propertyNameC=%s, commisionAmt=%s, commissionRate=%s, clubbingFlag=%s, clubbingSales=%s, dealBaseOutput=%s, baseRateOutput=%s, nationalityMultiplier=%s, nationalityIncrement=%s, nationalityFlatRate=%s, focusedProdMultiplier=%s, focusedProdIncrement=%s, focusedProdFlatRate=%s, eidIncentiveMultiplier=%s, eidIncentiveIncrement=%s, eidIncentiveFlatRate=%s, commissionMultiplier=%s, commissionIncrement=%s, commissionFlatRate=%s, registrationId=%s, month=%s, depositReceived=%s, docOk=%s, unitName=%s, heardOfDamac=%s, bedRmTypeName=%s, salePerson=%s, managerName=%s, hos=%s, hod=%s, customerNumber=%s, status=%s, netSplitSale=%s, bookingType=%s, docOkDt=%s, splitDeals=%s, splitPerc=%s, saleYear=%s, endOfMonthDate=%s, noOfDays=%s, docOkDays=%s, dpReceiptDays=%s, salesPerMonth=%s, agentSales=%s, directSales=%s, pcComm=%s, nationality=%s, collPerc=%s, depRecDt=%s, depRecMnth=%s, saleOffice=%s, pcCommissionNetSale=%s, pcCommissionOrg=%s, cdpFlag=%s, eidFlag=%s, specialCommFlag=%s, collection=%s, nationalityFlag=%s]",
				agentName, agentId, teamName, netSales, saleDate, agentDirectC, dateoFregistrationC, buildingNameC,
				propertyNameC, commisionAmt, commissionRate, clubbingFlag, clubbingSales, dealBaseOutput,
				baseRateOutput, nationalityMultiplier, nationalityIncrement, nationalityFlatRate, focusedProdMultiplier,
				focusedProdIncrement, focusedProdFlatRate, eidIncentiveMultiplier, eidIncentiveIncrement,
				eidIncentiveFlatRate, commissionMultiplier, commissionIncrement, commissionFlatRate, registrationId,
				month, depositReceived, docOk, unitName, heardOfDamac, bedRmTypeName, salePerson, managerName, hos, hod,
				customerNumber, status, netSplitSale, bookingType, docOkDt, splitDeals, splitPerc, saleYear,
				endOfMonthDate, noOfDays, docOkDays, dpReceiptDays, salesPerMonth, agentSales, directSales, pcComm,
				nationality, collPerc, depRecDt, depRecMnth, saleOffice, pcCommissionNetSale, pcCommissionOrg, cdpFlag,
				eidFlag, specialCommFlag, collection, nationalityFlag);
	}
}
	
	