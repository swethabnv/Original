package com.wsndata.data;

import java.io.Serializable;

public class BankBranch implements Serializable 
{	
	private static final long serialVersionUID = -5738588613929516447L;
	private long branchCode;
	private long bankId;
	
	public long getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	
}
