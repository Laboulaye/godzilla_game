package student.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SecureIOStream  extends IOStream{
	
	private PublicKey publicKey;
	private PrivateKey privateKey;

	public SecureIOStream(BufferedOutputStream out, BufferedInputStream in, PublicKey publicKey, PrivateKey privateKey) {
		super(out, in);
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	@Override
	public void send(int value) throws IOException{
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encryptedBytes = cipher.doFinal(new byte[] {(byte) value});
			super.send(value);
		} catch (IllegalBlockSizeException| BadPaddingException | InvalidKeyException | 
				NoSuchPaddingException| NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int receive() throws IOException {
		byte value = (byte) super.receive();
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] decryptedBytes = cipher.doFinal(new byte[] {(byte) value});
			return (int) decryptedBytes[0];
		} catch (BadPaddingException|IllegalBlockSizeException| InvalidKeyException | 
				NoSuchPaddingException| NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	

}
