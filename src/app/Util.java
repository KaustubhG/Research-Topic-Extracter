package app;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class Util {
	public static <K, V extends Comparable<? super V>> List<Entry<K, V>>

	findGreatest(Map<K, V> map, int n) {

		Comparator<? super Entry<K, V>> comparator = new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e0, Entry<K, V> e1) {
				V v0 = e0.getValue();
				V v1 = e1.getValue();
				return v0.compareTo(v1);
			}
		};
		PriorityQueue<Entry<K, V>> highest = new PriorityQueue<Entry<K, V>>(n, comparator);
		for (Entry<K, V> entry : map.entrySet()) {
			highest.offer(entry);
			while (highest.size() > n) {
				highest.poll();
			}
		}

		List<Entry<K, V>> result = new ArrayList<Map.Entry<K, V>>();
		while (highest.size() > 0) {
			result.add(highest.poll());
		}
		return result;
	}

	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> findLowest(Map<K, V> map, int n) {

		Comparator<? super Entry<K, V>> comparator = new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e0, Entry<K, V> e1) {
				V v0 = e0.getValue();
				V v1 = e1.getValue();
				return v1.compareTo(v0);
			}
		};

		PriorityQueue<Entry<K, V>> highest = new PriorityQueue<Entry<K, V>>(n, comparator);
		for (Entry<K, V> entry : map.entrySet()) {
			highest.offer(entry);
			while (highest.size() > n) {
				highest.poll();
			}
		}

		List<Entry<K, V>> result = new ArrayList<Map.Entry<K, V>>();
		while (highest.size() > 0) {
			result.add(highest.poll());
		}
		return result;
	}

	public static ArrayList<String> listFilesForFolder(final File folder) {

		ArrayList<String> list = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {

			if (fileEntry.isDirectory()) {
				list.addAll(listFilesForFolder(fileEntry));
			} else {
				String abs_filepath = fileEntry.getAbsolutePath();
				list.add(abs_filepath);
			}
		}
		return list;
	}

}
