package com.onlineMIS.ORM.entity.headQ.user;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.onlineMIS.ORM.entity.headQ.HR.MagerEmployeeRelationship;



public class UserInfor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5079784179894810789L;
	public static final String ACCOUNTANT_CODE = "01";
	public static final String SALES_CODE = "02";
	public static final String LOGISTIC_CODE = "03";
	public static final String ADMIN_CODE = "00";
	
	public static final String SWITCH_CHAIN_FUNCTION = "userJSP!swithToChain";

	public static final int SUPER_ADMIN = 99;
	
	public static final int NORMAL_ACCOUNT = 0;
	public static final int RESIGNED = 1;
	
	private int user_id;
	private String user_name;
	private String pinyin;
	private String name;
	private String password;
	private int roleType;
	private Date onBoardDate = new Date();
	private String department;
	private Date birthday = new Date();
	private String mobilePhone;
	private String homePhone;
	private String idNumber;
	private double baseSalary;
	private double baseVacation;
	private String jobTitle;

	
	//key: department code
	//value: department name
	private String departmentName;
	private static Map<String, String> departmentMap = new HashMap<String, String>();

	static {
		departmentMap.put("01", "会计");
		departmentMap.put("02", "销售");
		departmentMap.put("03", "物流");
		departmentMap.put("04", "连锁");
	}
	
	/**
	 * 0 means normal 
	 * 1 means resigned/retired, the account is disabled
	 */
	private int resign = 0;
	
	/**
	 * The functions the user has
	 */
	private Set<UserFunctionality> userFunction_Set =new HashSet<UserFunctionality>();
	private List<String> functions = new ArrayList<String>();
	
	/**
	 * the employees under this user
	 * to avoid recursive select in jason
	 */

	private Set<MagerEmployeeRelationship> employeeUnder_Set = new HashSet<MagerEmployeeRelationship>();


	public Set<MagerEmployeeRelationship> getEmployeeUnder_Set() {
		return employeeUnder_Set;
	}
	public void setEmployeeUnder_Set(Set<MagerEmployeeRelationship> employeeUnder_Set) {
		this.employeeUnder_Set = employeeUnder_Set;
	}


	public Set<UserFunctionality> getUserFunction_Set() {
		return userFunction_Set;
	}
	public void setUserFunction_Set(Set<UserFunctionality> userFunction_Set) {
		this.userFunction_Set = userFunction_Set;

	}

	public Date getOnBoardDate() {
		return onBoardDate;
	}
	public void setOnBoardDate(Date onBoardDate) {
		this.onBoardDate = onBoardDate;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public double getBaseSalary() {
		return baseSalary;
	}
	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}
	public double getBaseVacation() {
		return baseVacation;
	}
	public void setBaseVacation(double baseVacation) {
		this.baseVacation = baseVacation;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public int getResign() {
		return resign;
	}
	public void setResign(int resign) {
		this.resign = resign;
	}

	public String getDepartmentName() {
		return departmentMap.get(department);
	}
	public List<String> getFunctions() {
		return functions;
	}
	public void setFunctions(List<String> functions) {
		this.functions = functions;
	}
	
	public boolean containFunction(String url){
		return functions.contains(url);
	}
	
	public boolean containFunction(int functionId){
		UserFunctionality functionality = new UserFunctionality(this.getUser_id(), functionId);
		boolean isContain =  userFunction_Set.contains(functionality);
		
		return isContain;
	}
	
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + user_id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfor other = (UserInfor) obj;
		if (user_id != other.user_id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UserInfor [user_id=" + user_id + ", user_name=" + user_name
				+ ", name=" + name + ", password=" + password + ", roleType="
				+ roleType + ", onBoardDate=" + onBoardDate + ", department="
				+ department + ", birthday=" + birthday + ", mobilePhone="
				+ mobilePhone + ", homePhone=" + homePhone + ", idNumber="
				+ idNumber + ", baseSalary=" + baseSalary + ", baseVacation="
				+ baseVacation + ", jobTitle=" + jobTitle + ", jinsuanID="
			    + ", resign=" + resign + ", functions=" 
				+ "]";
	}

}
