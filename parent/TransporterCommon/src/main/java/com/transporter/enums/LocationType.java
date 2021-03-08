package com.transporter.enums;

public enum LocationType {
	PICK_UP("pickup"),
	DROP("drop");
	private final String value;
	
	private LocationType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean equalsName(String otherName) {
        return value.equals(otherName);
    }

    public String toString() {
       return this.value;
    }
}
