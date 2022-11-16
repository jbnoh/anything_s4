package com.anything.swagger.rule;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {

	private Integer page;
	private Integer size;
	private List<String> sort;
}
