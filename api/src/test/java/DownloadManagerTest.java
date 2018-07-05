
import com.agoda.DownloadManager;
import com.agoda.protocols.AbstractFileHandler;
import com.agoda.protocols.FtpFileHandler;
import com.agoda.protocols.HttpFileHandler;
import java.net.MalformedURLException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DownloadManagerTest {
  private String httpUrl;
  private String httpsUrl;
  private String ftpUrl;

  @Before
  public void setUp() {
    this.httpUrl = "http://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg";
    this.httpsUrl = "https://archive.org/download/80MegapixelsCameraSampleImage/CF000891.jpg";
    ftpUrl = "ftp://speedtest.tele2.net/50MB.zip";
  }

  @Test(expected = MalformedURLException.class)
  public void whenBadUrl_thenHttpFileHandler() throws MalformedURLException {
    String badUrl = "hts://www.ss.com/abc.jpeg";
    DownloadManager.getInstance(badUrl);
  }

  @Test
  public void whenHttpUrl_thenHttpFileHandler() throws MalformedURLException {
    AbstractFileHandler abstractFileHandler = DownloadManager.getInstance(httpUrl);
    Assert.assertEquals(abstractFileHandler.getClass(), HttpFileHandler.class);
  }

  @Test
  public void whenHttpsUrl_thenHttpFileHandler() throws MalformedURLException {
    AbstractFileHandler abstractFileHandler = DownloadManager.getInstance(httpsUrl);
    Assert.assertEquals(abstractFileHandler.getClass(), HttpFileHandler.class);
  }

  @Test
  public void whenFtpUrl_thenFtpFileHandler() throws MalformedURLException {
    AbstractFileHandler abstractFileHandler = DownloadManager.getInstance(ftpUrl);
    Assert.assertEquals(abstractFileHandler.getClass(), FtpFileHandler.class);
  }
}
