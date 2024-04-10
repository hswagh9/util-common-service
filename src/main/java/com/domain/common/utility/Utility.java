package com.domain.common.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.criteria.Path;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.domain.common.constant.GlobalConstants;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Utility {
	/**
	 * Implementation of pattern for searching specific text
	 * 
	 * @param searchTerm
	 * @return
	 */
	public static String getContainsLikePattern(String searchTerm) {
		if (StringUtils.isBlank(searchTerm)) {
			return "%";
		} else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}

	/**
	 * Implementation for return string for filter data
	 * 
	 * @param filterBy
	 * @return
	 */
	public static Map<String, String> prepareFilterByMap(String filterBy) {
		Map<String, String> filterByMaps = new HashMap<>();
		String[] arr = filterBy.split(";");
		for (String key : arr) {
			String[] tempArr = key.split(":", 2);
			filterByMaps.put(tempArr[0], tempArr[1]);
		}
		return filterByMaps;
	}

	public static Date format(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			sdf.setTimeZone(getZone());
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	private static TimeZone getZone() {
		ZoneId arizonaTimeZone = ZoneId.of("America/Phoenix");
		return TimeZone.getTimeZone(arizonaTimeZone);
	}

	/**
	 * Implementation of sort record by ASC or DESC
	 * 
	 * @param sortBy
	 * @return
	 */
	public static List<Order> sortByValues(String sortBy) {
		List<Order> filterByMaps = new ArrayList<>();
		String[] arr = sortBy.split(";");
		for (String key : arr) {
			System.out.println(key);
			String[] tempArr = key.split(":", 2);
			final Direction direction = tempArr[1].equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
			filterByMaps.add(new Order(direction, tempArr[0]));
		}
		return filterByMaps;
	}

	/**
	 * 
	 * 
	 * @param 
	 * @return
	 */
	public static <T> Specification<T> createDateRangeSpecification(String startDate, String endDate,
			Path<Date> dateField) {
		return (root, query, cb) -> {
			Date start;
			Date end;
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				start = Utility.format(startDate, GlobalConstants.SIMPLE_DATE_FORMAT);
				end = Utility.format(endDate, GlobalConstants.SIMPLE_DATE_FORMAT);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				end = calendar.getTime();
				return cb.and(cb.greaterThanOrEqualTo(dateField, start), cb.lessThan(dateField, end));
			} else if (StringUtils.isNotBlank(startDate)) {
				start = Utility.format(startDate, GlobalConstants.SIMPLE_DATE_FORMAT);
				return cb.greaterThanOrEqualTo(dateField, start);
			} else if (StringUtils.isNotBlank(endDate)) {
				end = Utility.format(endDate, GlobalConstants.SIMPLE_DATE_FORMAT);
				return cb.lessThanOrEqualTo(dateField, end);
			}
			return null;
		};
	}

	public static Boolean validateDate(String sourceDate, String format, String targetDate, String requiredFormat) {
		Date start = format(sourceDate, format);
		Date end = format(targetDate, requiredFormat);
		return start.compareTo(end) > 0 ? false : true;
	}

}