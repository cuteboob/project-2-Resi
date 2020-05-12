package checkCodeJava;

public class main {
	public static void main(String[] args) {
		A ai = new A();
		B bi = new B();
		ai.b = bi;
		bi.a = ai;
		System.out.print(bi.GanBBang10 + "\n");
		System.out.print("helloWorld \n");
		ai.b.GanBBang10 = 1;
		System.out.print(ai.b.a.b.a.b.a.b.GanBBang10);
	}
}
