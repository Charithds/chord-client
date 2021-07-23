/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.util;

import com.mycompany.chord.client.model.Address;
import com.mycompany.chord.client.model.SHA1Hash;
import com.mycompany.chord.client.state.ChordConfig;
import jakarta.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 *
 * @author Charith.S
 */
public class SHA1Hasher {
	private MessageDigest md;

	private static SHA1Hasher single_instance = null;
	public static SHA1Hasher getInstance() {
		if (single_instance == null)
			single_instance = new SHA1Hasher();

		return single_instance;
	}

	private SHA1Hasher() {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SHA1Hash hash(Address address) {
		String addressSt = address.getUsername() + address.getIp() + address.getPort();
                return hash(addressSt);
	}
        
        public SHA1Hash hash(String fileName) {
		String addressSt = fileName;
		// Hash address
		md.reset();
		byte[] addressBytes = md.digest(addressSt.getBytes());
		byte[] hashedBytes = new byte[8];

		// Create 4-byte segments from 20-byte hash then XOR them together to get final 4-byte hash
		for (int i = 0; i < 4; i++) {
			hashedBytes[i + 4] = (byte) (addressBytes[i] ^ addressBytes[i + 4] ^ addressBytes[i + 8] ^ addressBytes[i + 12] ^ addressBytes[i + 16]);
		}

		          SHA1Hash sha1Hash = new SHA1Hash();
		sha1Hash.setHashedBytes(hashedBytes);
		sha1Hash.setLongValue(getLong(hashedBytes));
		sha1Hash.setHexValue(getHex(hashedBytes));
		return sha1Hash;
	}

	private String getHex(byte[] hashedBytes) {
		return DatatypeConverter.printHexBinary(Arrays.copyOfRange(hashedBytes, 4, 8));
	}

	private long getLong(byte[] hashedBytes) {
		return java.nio.ByteBuffer.wrap(hashedBytes).getLong() % ChordConfig.HASH_BITS;
	}
}
