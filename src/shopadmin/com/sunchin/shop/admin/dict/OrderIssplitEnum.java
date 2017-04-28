package com.sunchin.shop.admin.dict;

public enum OrderIssplitEnum {

	YES("1", "已拆分"),
	
	NO("0", "待拆分");

	private String code;

	private String name;

	private OrderIssplitEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String desc) {
		this.name = desc;
	}

}