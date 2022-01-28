package miniJava;

import java.io.IOException;

public class FileFormatException extends IOException {
	FileFormatException() {
		super();
	};
	
	FileFormatException(String s) {
		super(s);
	}
}