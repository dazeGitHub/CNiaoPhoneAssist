<<<<<<< HEAD
package zlc.season.rxdownload2.entity;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/10/21
 * Time: 15:28
 * Download Range
 */
public class DownloadRange {
    public long start;
    public long end;
    public long size;

    public DownloadRange(long start, long end) {
        this.start = start;
        this.end = end;
        this.size = end - start + 1;
    }

    public boolean legal() {
        return start <= end;
    }
}
=======
package zlc.season.rxdownload2.entity;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/10/21
 * Time: 15:28
 * Download Range
 */
public class DownloadRange {
    public long start;
    public long end;
    public long size;

    public DownloadRange(long start, long end) {
        this.start = start;
        this.end = end;
        this.size = end - start + 1;
    }

    public boolean legal() {
        return start <= end;
    }
}
>>>>>>> 32674bc4d54d9e98a16c6edff476a379d3872a4c
