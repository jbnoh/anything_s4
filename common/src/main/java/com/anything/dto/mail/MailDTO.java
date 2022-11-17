package com.anything.dto.mail;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO {

	private String[] recipients;

	private String subject;

	private String content;

	private boolean isHtml;

	private File attach;
}
