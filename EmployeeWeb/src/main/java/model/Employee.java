package model;

public class Employee {
	private String employeeName;
	private int employeeId;
	private String employeeDob;
	private String employeePassword;
	public Employee() {
		super();
	}
	public Employee(String employeeName, int employeeId, String employeeDob, String employeePassword) {
		super();
		this.employeeName = employeeName;
		this.employeeId = employeeId;
		this.employeeDob = employeeDob;
		this.employeePassword = employeePassword;
	}
	public Employee(int employeeId, String employeePassword) {
		super();
		this.employeeId = employeeId;
		this.employeePassword = employeePassword;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeDob() {
		return employeeDob;
	}
	public void setEmployeeDob(String employeeDob) {
		this.employeeDob = employeeDob;
	}
	public String getEmployeePassword() {
		return employeePassword;
	}
	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}
	
	
}
