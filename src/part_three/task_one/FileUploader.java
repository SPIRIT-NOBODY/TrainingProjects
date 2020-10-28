package part_three.task_one;

import java.io.*;

public class FileUploader {

	private byte[] fileBytes = new byte[8192];
	private final String fileName = "serverFiles/file.pdf";
	DataOutputStream os;
	File file;

	public FileUploader(DataOutputStream os) {
		this.os = os;
		file = new File(fileName);
	}
	
	public void uploadFile() throws FileNotFoundException, IOException {

		try (FileInputStream fis = new FileInputStream(file);
				BufferedInputStream buffFis = new BufferedInputStream(fis);) {			
			int in = -1;
			long uploadProgress = 0;
			long fileSize = buffFis.available();
			while((in = buffFis.read(fileBytes)) != -1) {
				os.write(fileBytes, 0 , in);
				uploadProgress += in;				
			}
			System.out.println("upload "+ uploadProgress + " from " +  fileSize);		
			os.flush();
			fis.close();
		}
	}
}
