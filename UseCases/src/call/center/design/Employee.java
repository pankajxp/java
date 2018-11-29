package call.center.design;

public abstract class Employee {
	protected String name;
	protected Designation designation;
	protected Employee senior;// escalate to senior
	protected boolean isFree;// if not free
	protected boolean canHandleCall;

	public Employee() {
		super();
	}

	public Employee(String name, Designation designation, Employee senior, boolean isFree, boolean canHandleCall) {
		super();
		this.name = name;
		this.designation = designation;
		this.senior = senior;
		this.isFree = isFree;
		this.canHandleCall = canHandleCall;
	}

	protected abstract void handleCall(Call call);

	public Employee getSenior() {
		return senior;
	}

	public void setSenior(Employee senior) {
		this.senior = senior;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public boolean isCanHandleCall() {
		return canHandleCall;
	}

	public void setCanHandleCall(boolean canHandleCall) {
		this.canHandleCall = canHandleCall;
	}
}

class Fresher extends Employee {

	public Fresher(String name, Employee senior) {
		super(name, Designation.FRESHER, senior, true, true);
	}

	public void handleCall(Call call) {// if can't handl
		canHandleCall = this.designation == Designation.FRESHER && call.getLevel() == Level.One;
		if (!this.isFree || !canHandleCall) {
			System.out.println("Forwarding call to TL.");
			this.getSenior().handleCall(call);
		} else { 
			System.out.println("Fresher handling the call.");
			//steps to handle the call
			try{
				this.isFree = false;
				//....
			} finally {
				this.isFree = true;
			}
		}
	}
}

class TL extends Employee {

	public TL(String name, Employee senior) {
		super(name, Designation.TL, senior, true, true);
	}
	
	public void handleCall(Call call) {// if can't handle
		canHandleCall = this.designation == Designation.TL && call.getLevel() == Level.Two;
		if (!this.isFree || !canHandleCall) {
			System.out.println("Forwarding call to PM.");
			this.getSenior().handleCall(call);
		} else { 
			//steps to handle the call
			System.out.println("TL handling the call.");
			try{
				this.isFree = false;
				//....
			} finally {
				this.isFree = true;
			}
		}
	}
}

class PM extends Employee {

	public PM(String name, Employee senior) {
		super(name, Designation.PM, senior, true, true);
	}
	
	public void handleCall(Call call) {// if can't handle
		canHandleCall = this.designation == Designation.PM && call.getLevel() == Level.Three;
		if (!this.isFree || !canHandleCall) {
			throw new RuntimeException("PM could not handle the call.");
//			this.getSenior().handleCall(call);
		} else { 
			//steps to handle the call
			System.out.println("PM handling the call.");
			try{
				this.isFree = false;
				//....
			} finally {
				this.isFree = true;
			}
		}
	}
}
