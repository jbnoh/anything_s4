package com.anything.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.anything.vo.DataMap;

import lombok.ToString;

@ToString
public class HttpHeaderUtil {

	public static Builder builder() {

		return new Builder();
	}

	/**
	 * Builder static class
	 * 
	 * @author rjb
	 *
	 */
	public static class Builder {

		private MediaType type;

		private DataMap dataMap;

		public Builder contentType(MediaType type) {
			this.type = type;
			return this;
		}

		public Builder add(DataMap dataMap) {
			this.dataMap = dataMap;
			return this;
		}

		public HttpHeaders build() {
			HttpHeaders headers = new HttpHeaders();

			if (dataMap != null) {
				dataMap.forEach((k,v) -> {
					headers.add(k.toString(), v.toString());
				});
			}

			if (type != null) {
				headers.setContentType(type);
			}

			return headers;
		}
	}
}
