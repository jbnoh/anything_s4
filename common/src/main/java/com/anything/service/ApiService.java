package com.anything.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiService<T> {

	private final RestTemplate restTemplate;

	public ResponseEntity<T> get(String url, HttpHeaders headers) {

		return get(url, headers, (Class<T>) Object.class);
	}

	private ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> clazz) {

		return callApiEndpoint(url, HttpMethod.GET, headers, null, clazz);
	}

	public ResponseEntity<T> post(String url, HttpHeaders headers, Object body) {

		return post(url, headers, body, (Class<T>) Object.class);
	}

	private ResponseEntity<T> post(String url, HttpHeaders headers, Object body, Class<T> clazz) {

		return callApiEndpoint(url, HttpMethod.POST, headers, body, clazz);
	}

	private ResponseEntity<T> callApiEndpoint(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> clazz) {

		return restTemplate.exchange(url, method, new HttpEntity<>(body, headers), clazz);
	}
}
