package part_four.task_one.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Helper interface for easy get properties by file name. Create for correct get
 * properties from package.
 */

public interface PropertiesGetter {

	/**
	 * Get properties from file and add its to props.
	 * 
	 * @param sourceName file name.
	 * @param props      properties for fill
	 * @throws IOException {@inheritDoc}
	 */
	default public void getProperties(String sourceName, Properties props) throws IOException {
		InputStream in = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(sourceName);
			if (in == null) {
				in = Files.newInputStream(Paths.get(sourceName));
			}
			props.load(in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}
