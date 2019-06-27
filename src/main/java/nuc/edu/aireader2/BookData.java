package nuc.edu.aireader2;

import android.media.Image;

public class BookData {
    Image img;
    String name;
    int img1;
    public BookData(Image img, String name) {
        this.img = img;
        this.name = name;
    }
    public BookData( String name) {
        this.name = name;
    }

    public BookData(int img, String name) {
        this.img1 = img;
        this.name = name;
    }

    public Image getAppImg() {
        return img;
    }
    public int getAppImg1() {
        return img1;
    }

    public String getAppName() {
        return name;
    }
}
