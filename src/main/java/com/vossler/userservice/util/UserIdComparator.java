package com.vossler.userservice.util;

import java.util.Comparator;

import com.vossler.userservice.dto.User;

/**
 * The User.id field is defined as a String, because the JSON returned by the external API marshalls them as Strings.  It appears that the id should always be a String representation
 * of an integer, and I would like to sort it numerically rather than alphabetically.  However since I have no documenation or contract with the external API to guarantee that there
 * WON'T be user results without an id or with a non-numeric id, I will code my Comparator defensively. 
 */
public class UserIdComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		if (o1.getId() == null && o2.getId() == null) return 0;
		if (o1.getId() == null && o2.getId() != null) return -1;
		if (o1.getId() != null && o2.getId() == null) return 1;
		
		try {
			return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
		} catch (NumberFormatException e) {
			return o1.getId().compareTo(o2.getId());
		}
	}

}
