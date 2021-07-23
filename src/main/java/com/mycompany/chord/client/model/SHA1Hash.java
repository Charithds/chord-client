/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.model;

/**
 *
 * @author Charith.S
 */
public class SHA1Hash {
	private byte[] hashedBytes;
	private long longValue;
	private String hexValue;

	public byte[] getHashedBytes() {
		return hashedBytes;
	}

	public void setHashedBytes(byte[] hashedBytes) {
		this.hashedBytes = hashedBytes;
	}

	public long getLongValue() {
		return longValue;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}

	public String getHexValue() {
		return hexValue;
	}

	public void setHexValue(String hexValue) {
		this.hexValue = hexValue;
	}
}
