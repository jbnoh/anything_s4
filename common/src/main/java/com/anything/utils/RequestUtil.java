package com.anything.utils;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.http.MediaType;

import com.anything.vo.DataMap;

public class RequestUtil {

	public static DataMap getParams(HttpServletRequest request) {

		DataMap result = new DataMap();

		String contentType = request.getContentType();

		if (StringUtils.isNotBlank(contentType) && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {

			try {
				result.putJsonString(IOUtils.toString(request.getReader()));

				/**
				 * 이슈: No serializer found for class org.json.JSONArray and no properties discovered to create BeanSerializer
				 * 해결: JSONArray to String Array
				 */
				for (Object key : result.keySet()) {
					Object obj = result.get(key);
					if (obj instanceof JSONArray) {
						String[] strArray = ((JSONArray) obj).join(",").split(",");
						result.put(key, strArray);
					}
				}
			} catch (Exception e) {}
		} else {
			Enumeration<String> keys = request.getParameterNames();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String[] values = request.getParameterValues(key);

				if (values == null) {
					continue;
				}

				if (values.length > 1) {
					result.put(key, Arrays.asList(values));
				} else {
					String value = values[0];

					if (StringUtils.isNumeric(value)) {
						result.put(key, Integer.parseInt(value));
					} else {
						result.put(key, value);
					}
				}
			}
		}

		return result;
	}
}
