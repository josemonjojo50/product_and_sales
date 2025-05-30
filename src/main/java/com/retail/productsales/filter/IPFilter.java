package com.retail.productsales.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class IPFilter implements Filter {
	private static final List<String> ALLOWED_IPS = List.of("127.0.0.1", "::1", "0:0:0:0:0:0:0:1");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String ipAddress = httpRequest.getRemoteAddr();
		System.out.println("Request from IP: " + ipAddress);

		if (ALLOWED_IPS.contains(ipAddress)) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "IP not allowed");
		}
	}
}
