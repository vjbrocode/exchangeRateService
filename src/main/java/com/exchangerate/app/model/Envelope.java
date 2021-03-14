package com.exchangerate.app.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gesmes:Envelope")
public class Envelope {
	
	public String subject;
	public Sender Sender;
	public Cube Cube;
	public String gesmes;
	public String xmlns;
	public String text;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Sender getSender() {
		return Sender;
	}
	public void setSender(Sender sender) {
		Sender = sender;
	}
	public Cube getCube() {
		return Cube;
	}
	public void setCube(Cube cube) {
		Cube = cube;
	}
	public String getGesmes() {
		return gesmes;
	}
	public void setGesmes(String gesmes) {
		this.gesmes = gesmes;
	}
	public String getXmlns() {
		return xmlns;
	}
	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Envelope [subject=" + subject + ", Sender=" + Sender + ", Cube=" + Cube + ", gesmes=" + gesmes
				+ ", xmlns=" + xmlns + ", text=" + text + "]";
	}
	
}
