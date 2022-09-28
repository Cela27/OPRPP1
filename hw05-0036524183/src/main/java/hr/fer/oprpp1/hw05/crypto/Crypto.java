package hr.fer.oprpp1.hw05.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class with encrypting, decrypting and check SHA-256 methods.
 * @author Antonio
 *
 */
public class Crypto {
	private String inputFile;
	private String outputFile;
	private String keyText;
	private String ivText;
	private String ocekivaniDigest;
	
	/**
	 * Basic constructor for {@link Crypto}.
	 * @param inputFile String path to the input file
	 * @param outputFile String path to the output file
	 * @param operation encrypt, decrypt or checksha
	 */
	public Crypto(String inputFile, String outputFile, String operation) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		
		if(operation.equals("checksha")) {
			Scanner s= new Scanner(System.in);
			System.out.println("Please provide expected sha-256 digest for "+inputFile+":");
			System.out.print(">");
			
			ocekivaniDigest=s.nextLine();
			s.close();
			checsha(inputFile);
		}
		else if(operation.equals("encrypt")) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print(">");
			Scanner s= new Scanner(System.in);
			keyText=s.next();
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print(">");
			ivText=s.next();
			s.close();
			encryptOrDecrypt(true);
		}else if(operation.equals("decrypt")) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print(">");
			Scanner s= new Scanner(System.in);
			keyText=s.next();
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print(">");
			ivText=s.next();
			s.close();
			encryptOrDecrypt(false);
		}else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Check SHA-256 for input file.
	 * @param inputFile String path to the input file
	 */
	public void checsha(String inputFile) {
		
		String shaChecksum = null;
		MessageDigest shaDigest;
		File file=new File(inputFile);
		try {
			shaDigest = MessageDigest.getInstance("SHA-256");
			shaChecksum = getFileChecksum(shaDigest, file);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(ocekivaniDigest.equals(shaChecksum)) {
			System.out.println("Digesting completed. Digest of "+file.toString()+ " matches expected digest.");
		}else {
			System.out.println("Digesting completed. Digest of "+file.toString() +" does not match the expected digest. Digest\r\n"
					+ "was: "+ shaChecksum);
		}
	}
	
	/**
	 * Encryption or decryption of input file into the output file.
	 * @param encrypt true if you want encryption
	 */
	public void encryptOrDecrypt(boolean encrypt) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			InputStream is = Files.newInputStream(Paths.get(inputFile));
			OutputStream os = Files.newOutputStream(Paths.get(outputFile));
			
			byte[] cleartext= new byte[4096];
			byte[] ciphertext;
			int r;
			
			while((r=is.read(cleartext))>0) {
				ciphertext=cipher.update(cleartext, 0, r);
				os.write(ciphertext);
			}
			
			ciphertext=cipher.doFinal();
			os.write(ciphertext);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
			System.exit(1);
		}
		
		if(encrypt) {
			System.out.println("Encryption completed. Generated file "+outputFile+" based on file "+ inputFile);			
		}else {
			System.out.println("Decryption completed. Generated file "+outputFile+" based on file "+ inputFile);
		}
	}
	/**
	 * Help function for checksha method. Returns checksum of file.
	 * @param digest
	 * @param file
	 * @return {@link String} checksum
	 * @throws IOException if error happens
	 */
	private String getFileChecksum(MessageDigest digest, File file) throws IOException {
		FileInputStream is = new FileInputStream(file);
		byte[] buff = new byte[4096];
		int bytesCount = 0;

		while ((bytesCount = is.read(buff)) != -1) {
			digest.update(buff, 0, bytesCount);
		}
		is.close();
		byte[] bytes = digest.digest();
		return Util.bytetohex(bytes);
	}
}
