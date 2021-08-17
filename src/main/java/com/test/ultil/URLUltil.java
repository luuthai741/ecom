package com.test.ultil;

import javax.servlet.http.HttpServletRequest;

public class URLUltil {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
