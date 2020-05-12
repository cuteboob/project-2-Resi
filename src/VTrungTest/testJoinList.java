package VTrungTest;

import java.util.ArrayList;
import java.util.List;

public class testJoinList {
	public static void main(String[] args) {
		String a = "1";
		String b = "2";
		String c = "3";
		List<String> listOne = new ArrayList<String>();
		List<String> listTwo = new ArrayList<String>();
		
		listOne.add(a);
		listOne.add(b);
		
		listOne.add(b);
		listOne.add(c);
		
		List<String> newList = new ArrayList<String>(listOne);
		newList.addAll(listTwo);
		System.out.println(newList.toString());
	}
}
