import java.io.File;

class Test{

    public static void main(String[]args){
        String filePath="D:\\PG\\资料\\R\\高数\\高等数学强化";
        VideoCounter videoCounter = new VideoCounter(filePath);
        videoCounter.start();
        videoCounter.result();
    }
}