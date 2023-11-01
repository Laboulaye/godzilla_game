package student.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class IOStream {

	private BufferedInputStream in;
	private BufferedOutputStream out;

	public IOStream(BufferedOutputStream out, BufferedInputStream in) {
		super();
		this.in = in;
		this.out = out;
	}

	public void send(int value) throws IOException {
		out.write(value);
		out.flush();
	}

	public int receive() throws IOException {
		if (in.available() > 0) {
			return in.read();
		} 
		return -1;
	}

}
