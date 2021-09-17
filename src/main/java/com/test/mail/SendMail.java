package com.test.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.test.entities.security.User;



public interface SendMail {
	void verify(User user ,String to) throws UnsupportedEncodingException, MessagingException;
	void resetPassword(User user,String to) throws UnsupportedEncodingException, MessagingException;
	void enabledOrder(String email,String to, String token) throws UnsupportedEncodingException, MessagingException;
}
