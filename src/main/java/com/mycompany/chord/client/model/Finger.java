/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.model;

public class Finger {
	private Address address;
	private Long node;
        private int hopCount;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getNode() {
		return node;
	}

	public void setNode(Long node) {
		this.node = node;
	}

    /**
     * @return the hopCount
     */
    public int getHopCount() {
        return hopCount;
    }

    /**
     * @param hopCount the hopCount to set
     */
    public void setHopCount(int hopCount) {
        this.hopCount = hopCount;
    }
}