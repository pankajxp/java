package call.center.design;

public class Main {

	public static void main(String[] args) {
		System.out.println("in main");
		Employee pm = new PM("Mr. PM",null);
		Employee tl = new TL("Mr. TL", pm);
		Employee emp1 = new Fresher("Fresher1",tl);
		Employee emp2 = new Fresher("Fresher2",tl);
		Call call = new Call(Level.One);
		emp1.handleCall(call);
		emp2.handleCall(new Call(Level.Three));
		System.out.println("exit main");
	}

}
