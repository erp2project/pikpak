package kr.co.pikpak.device;

public enum UserType {
	OPERATOR("[operator]"), SUPPLIER("[supplier]"), VENDOR("[vendor]");
	private final String type;

	UserType(String string) {
		type = string;
	}

	@Override
	public String toString() {
		return type;
	}
}
