package com.javalego.demo.lazy;

import java.util.ArrayList;
import java.util.List;

public class ServiceDemo {

	private List<DemoDomain> dbFake = new ArrayList<DemoDomain>();

	public ServiceDemo() {
		boolean state = true;
		for (long i = 0; i < 100; i++) {
			DemoDomain item = new DemoDomain();
			item.setId(i);
			item.setName("Item " + i);
			state = !state;
			item.setEnabled(state);
			dbFake.add(item);
		}
	}

	public int count(boolean enab) {
		if (!enab) {
			return dbFake.size();
		}
		List<DemoDomain> filters = new ArrayList<DemoDomain>();
		for (DemoDomain item : dbFake) {
			if (item.getEnabled()) {
				filters.add(item);
			}
		}
		return filters.size();
	}

	public List<DemoDomain> getItemsFromTo(boolean enab, int startIndex,
			int numberOfIds) {
		if (!enab) {
			if (dbFake.size() - startIndex < 1) {
				return null;
			}
			return dbFake.subList(startIndex, (startIndex + numberOfIds) > dbFake.size() ? startIndex + (dbFake.size() - startIndex) : (startIndex + numberOfIds));
		}
		List<DemoDomain> filters = new ArrayList<DemoDomain>();
		for (DemoDomain item : dbFake) {
			if (item.getEnabled()) {
				filters.add(item);
			}
		}
		return filters.subList(startIndex, (startIndex + numberOfIds));
	}

	public int countFilter(boolean enab, String filterName) {
		List<DemoDomain> filters = new ArrayList<DemoDomain>();
		if (enab) {
			for (DemoDomain item : dbFake) {
				if (item.getName().contains(filterName)) {
					filters.add(item);
				}
			}
			return filters.size();
		}
		for (DemoDomain item : dbFake) {
			if (item.getName().contains(filterName) && item.getEnabled()) {
				filters.add(item);
			}
		}
		return filters.size();
	}

	public List<DemoDomain> getFilterItemsFromTo(boolean enab,
			String filterName, int startIndex, int numberOfIds) {
		List<DemoDomain> filters = new ArrayList<DemoDomain>();
		if (enab) {
			for (DemoDomain item : dbFake) {
				if (item.getName().contains(filterName)) {
					filters.add(item);
				}
			}
			return filters.subList(startIndex, (startIndex + numberOfIds));
		}
		for (DemoDomain item : dbFake) {
			if (item.getName().contains(filterName) && item.getEnabled()) {
				filters.add(item);
			}
		}
		return filters.subList(startIndex, (startIndex + numberOfIds));
	}

}
