package com.example.cay.newsmovie.bean;

public class VersionUpdataBean {
private String URLaddress;
private int ForcedUpdate;
private float Version;
public String getURLaddress() {
	return URLaddress;
}
public void setURLaddress(String uRLaddress) {
	URLaddress = uRLaddress;
}
public int getForcedUpdate() {
	return ForcedUpdate;
}
public void setForcedUpdate(int forcedUpdate) {
	ForcedUpdate = forcedUpdate;
}
public float getVersion() {
	return Version;
}
public void setVersion(float version) {
	Version = version;
}

	@Override
	public String toString() {
		return "VersionUpdataBean{" +
				"URLaddress='" + URLaddress + '\'' +
				", ForcedUpdate=" + ForcedUpdate +
				", Version=" + Version +
				'}';
	}
}
