package part_three.task_one;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDownloader {
	DataInputStream is;
	private final String fileName = "clientFiles/file.pdf";
	private File file;
	private boolean download = false;
	public FileDownloader(DataInputStream in) {
		this.is = in;
		File folder = new File("clientFiles");
		if(!folder.exists()){
			folder.mkdir();
		}
		file = new File(fileName);
	}
	
	private void checkFile() {
		if(file.exists()) {
			file.delete();
		}
	}

	public void downloadFile() throws IOException {
		System.out.println("downloadFile progress..");
		checkFile();
		try (BufferedInputStream bis = new BufferedInputStream(is);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file,true));) {
			int in = -1;
			byte[] byteArray = new byte[bis.available()];
			long downloaded = 0;
			while((in = bis.read(byteArray)) != -1) {
				if(in == 1 && byteArray[0] == -1) {
					break;
				}
				bos.write(byteArray, 0 , in);
				bos.flush();
				downloaded += in;
				
			}		
			System.out.println("downloaded "+ downloaded);
			bos.close();
			download = true;
		}
	}
	
	public boolean isDownload() {
		return download;
	}
}
