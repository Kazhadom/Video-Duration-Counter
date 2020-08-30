import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.junit.Test;


import java.io.File;

public class JaveTests {

    @Test
    public void test1(){
        File f = new File("C:\\BaiduDisk\\架构师从0到1000\\阶段一：单体电商项目架构，开发与上线（1~5周）\\01.万丈高楼，地基首要\\1-1 课程导学_.mp4");
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(f);
        try {
            ff.start();
            duration = ff.getLengthInTime()/(1000*1000);
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        System.out.println(duration);
    }
}
