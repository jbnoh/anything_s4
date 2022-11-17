package com.anything.smtp;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.anything.dto.mail.MailDTO;
import com.anything.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailTransport {

	private final static String DEFAULT_CHARSET = "UTF-8";

	// B == BASE64
	private final static String DEFAULT_ENCODING = "B";

//	@Autowired
	private JavaMailSenderImpl javaMailSender;

	public void send(MailDTO mailDto) throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		Object objFrom = javaMailSender.getJavaMailProperties().get("username");
		if (objFrom instanceof String == false) {
			throw new CustomException("properties userName is null");
		}

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, DEFAULT_CHARSET);
			mimeMessageHelper.setFrom(new InternetAddress(objFrom.toString()));
			mimeMessageHelper.setTo(parseAddresses(mailDto.getRecipients()));
			mimeMessageHelper.setSentDate(new Date());

			// subject
			mimeMessageHelper.setSubject(MimeUtility.encodeText(mailDto.getSubject(), DEFAULT_CHARSET, DEFAULT_ENCODING));

			// content
			mimeMessageHelper.setText(mailDto.getContent(), mailDto.isHtml());

			// attach
			File attach = mailDto.getAttach();
			if (attach != null && attach.isFile()) {
				FileSystemResource fileSystemResource = new FileSystemResource(attach);
				mimeMessageHelper.addAttachment(MimeUtility.encodeText(fileSystemResource.getFilename(), DEFAULT_CHARSET, DEFAULT_ENCODING), fileSystemResource);
			}

			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			throw e;
		}
	}

	private InternetAddress[] parseAddresses(String[] recipients) throws Exception {

		List<String> list = Arrays.asList(recipients);
		return (InternetAddress[]) list.toArray(new InternetAddress[list.size()]);
	}
}
