package com.javalego.store.items.impl;

import java.io.Serializable;

import javax.persistence.Entity;

import com.javalego.store.items.ISocial;

/**
 * Redes sociales
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
public class Social extends BaseModel implements ISocial, Serializable {

	private static final long serialVersionUID = -3227925725482321065L;
	
	private String twitter;
	private String linkedin;
	private String facebook;
	private String googleplus;
	private String email;
	private String web;
	private String forum;
	private String blog;

	@Override
	public String getTwitter() {
		return twitter;
	}

	@Override
	public String getLinkedin() {
		return linkedin;
	}

	@Override
	public String getFacebook() {
		return facebook;
	}

	@Override
	public String getGooglePlus() {
		return googleplus;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public void setGooglePlus(String googleplus) {
		this.googleplus = googleplus;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Override
	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	@Override
	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getGoogleplus() {
		return googleplus;
	}

	public void setGoogleplus(String googleplus) {
		this.googleplus = googleplus;
	}

}
