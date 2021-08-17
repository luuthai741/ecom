package com.test.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.test.entities.security.User;

@Service
public class SendMailImpl implements SendMail {
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void verify(User user, String to) throws UnsupportedEncodingException, MessagingException {
		String subject = "Hãy xác nhận để đăng ký";
		String senderName = "OGANI - Book Shop";

		String verifyURL = to + "/verify?verifyToken=" + user.getVerifyToken();

		StringBuilder builder = new StringBuilder();
		builder.append("<p><Dear> " + user.getUsername() + " </p>");
		builder.append("<p> Bạn đã đăng ký tài khoản thành công ! </p>");
		builder.append("<p> Hãy xác nhận email để hoàn thiện bước cuối cùng ! </p>");
		builder.append("<h3><a href=\"" + verifyURL + "\">Xác nhận</a><h3>");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("luuthai555@gmail.com", senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(builder.toString(), true);

		mailSender.send(message);

	}

	@Override
	public void resetPassword(User user, String to)
			throws UnsupportedEncodingException, MessagingException {
		String subject = "Nhấn vào link để thay đổi mật khẩu";
		String senderName = "OGANI - Book Shop";

		String resetURL = to + "/reset?resetToken=" + user.getResetToken();

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String content = "<p>Xin chào,</p>";
		content += "<p> Bạn vừa gửi yêu cầu thay đổi mật khẩu</p>";
		content += "<p>Nhấn vào link bên dưới để thay đổi mật khẩu</p>";
		content += "<p><a href=\"" + resetURL + "\">	Thay đổi mật khẩu</a></p>";

		helper.setFrom("luuthai555@gmail.com", senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);

	}

	@Override
	public void checkMail(User user, String to, String token) throws UnsupportedEncodingException, MessagingException {
		String subject = "Hãy xác nhận thanh toán đơn hàng";
		String senderName = "OGANI - Book Shop";

		String verifyURL = to + "/cart/check-out?token=" + token;

		StringBuilder builder = new StringBuilder();
		builder.append("<p>" + "Xin chào, " + user.getUsername() + " </p>");
		builder.append("<p>" + " Đây là tin nhắn xác nhận để thanh toán" + "</p>");
		builder.append("<p>" + " Vui lòng nhấn vào đường dẫn để thanh toán" + "</p>");
		builder.append("<h3><a href=\"" + verifyURL + "\">Xác nhận</a><h3>");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("luuthai555@gmail.com", senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(builder.toString(), true);

		mailSender.send(message);
	}

}
