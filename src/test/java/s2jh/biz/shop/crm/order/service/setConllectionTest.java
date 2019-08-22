package s2jh.biz.shop.crm.order.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class setConllectionTest {

	public static void main(String[] args) {
		/*Set<Integer> intSet = new LinkedHashSet<Integer>();
		intSet.add(1);
		intSet.add(3);
		intSet.add(2);
		intSet.add(1);
		intSet.add(1);
		intSet.add(3);
		System.out.println(intSet);*/
		/*Set<Map> mapSet = new LinkedHashSet<Map>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		Map<String,Object> map2 = new HashMap<String, Object>();
		map1.put("1", "2");
		map2.put("1","2");
		mapSet.add(map1);
		mapSet.add(map2);
		System.out.println(mapSet);*/
		Set<person> personSet = new LinkedHashSet<person>();
		person person1 = new person("小明", 20);
		person person2  = new person("小明", 20);
		personSet.add(person1);
		personSet.add(person2);
		System.out.println(personSet);
	}
}

