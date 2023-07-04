package com.learn.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userdetailservice;

	@Autowired
	private JwtHelper jwthelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get token
		String requestTokenHeader = request.getHeader("Authorization");
		
		System.out.print("token bearer msg" + requestTokenHeader);

		// bearer 6327863287368sghkg

		String username = null;
		String token = null;

		if (requestTokenHeader!= null && requestTokenHeader.startsWith("Bearer")) {

			token = requestTokenHeader.substring(7);

			try {
				username = this.jwthelper.extractUsername(token);

			} catch (IllegalArgumentException e) {
				System.out.print("Unable to generate token");
			} catch (ExpiredJwtException e) {

				System.out.print("Expired token");
			}

			catch (

			MalformedJwtException e) {

				System.out.print("invalid jwt");
			}
		}

		else {
			System.out.print("Jwt token doest start with bearer");
		}
		// we have got token and now we have to validate it

		// validATE
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userdetailservice.loadUserByUsername(username);

			if (this.jwthelper.validateToken(token, userDetails)) {

				// all are going well
				UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				t.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(t);

			} else {
				System.out.print("invalid token because it has not been validated");

			}
		} else {
			System.out.print("username or context is null");

		}

		filterChain.doFilter(request, response);
	}
}
