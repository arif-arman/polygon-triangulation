import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class Test {
	public static void main(String[] args) {
		TreeMap<Double, String> bst = new TreeMap<>();
		Random rand = new Random();
		for (int i=0;i<5;i++) {
			bst.put(rand.nextDouble()%200, "Hello" + i);
		}
		SortedMap<Double, String> map = bst.headMap(new Double(0.5)); 
		System.out.println(map);
		System.out.println(map.get(map.lastKey()));
	}
}
