package com.onlineMIS.ORM.entity.headQ.qxbabydb;

import java.io.Serializable;

public class NumPerHand2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4420317812621918098L;
	private int id;
    private int numPerHand;
    
    public NumPerHand2(){
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumPerHand() {
		return numPerHand;
	}

	public void setNumPerHand(int numPerHand) {
		this.numPerHand = numPerHand;
	}


  
}
