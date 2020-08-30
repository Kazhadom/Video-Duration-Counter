import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import java.io.File;

public class VideoCounter {

    // Root directory
    File file;
    // The number of other files
    Integer otherFileCount = 0;
    // The number of files
    Integer fileCount = 0;
    // The total duration of videos
    Long durationSum = 0L; // unit: ms
    // Video types
    String[] types = {"mp4", "rmvb", "flv"};

    /**
     * Construction1
     * @param filePath
     */
    public VideoCounter(String filePath){
        File f = new File(filePath);
        this.file = f;
    }

    /**
     * Construction2
     * Appoint the types of video that will be counted.
     * @param filePath
     * @param types
     */
    public VideoCounter(String filePath, String[] types){
        File f = new File(filePath);
        this.file = f;
        this.types = types;
    }

    public void start(){
        if(file==null){
            System.out.println("文件为null");
            return;
        }
        if(!file.isDirectory()){
            System.out.println("The specified path is not a directory");
            return;
        }
        traverseDir(file.listFiles());
    }

    public void result(){
        System.out.println("视频文件数量："+fileCount);
        System.out.println("其他文件数量："+otherFileCount);
        System.out.println("文件总时间："+durationSum+"秒");
    }

    private void traverseDir(File[] f){
        if(f==null){// 这里要判下空？ TODO
            System.out.println("找不到文件");
            return;
        }
        if(f.length==0){
            System.out.println("文件夹下没有任何文件");
            return;
        }
        for(File e:f){
            if(!e.isDirectory()){// 子文件不是文件夹
                // 视频文件时长+数量统计
                calDurationAndCount(e);
                continue;// 别忘了
            }
            // 子文件是文件夹
            traverseDir(e.listFiles());
        }
    }

    private void calDurationAndCount(File f) {
        String[] fileName = f.toString().split("\\.");// 注意
        if(fileName.length==0){
            System.out.println(f.toString()+" 文件名不符合规范");
            return;
        }
        String suffix = fileName[fileName.length-1];
        // 找类型数组中是否在数组中存在当前文件的类型 如果存在就算时长，不存在就是其他文件，不算时长
        Boolean flag = false;// 是否符合指定的视频类型
        for(String type:types){
            if(type.equals(suffix)){
                flag = true;
            }
        }
        if(!flag){// 不符合的类型
            System.out.println(f.toString()+" 文件不符合格式");
            otherFileCount++;
            return;
        }
        // 符合的类型
        // 开始计算时长并积累
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(f);
        try {
            ff.start();
            duration = ff.getLengthInTime()/(1000*1000);
            ff.stop();
        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
        }
        // 统计时长
        durationSum += duration;
        // 统计视频文件数量
        fileCount++;
    }


}
