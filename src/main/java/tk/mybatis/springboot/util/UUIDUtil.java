package tk.mybatis.springboot.util;

import java.util.UUID;

/**
 * Created by DELL-5490 on 2018/9/20.
 */
public class UUIDUtil {

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
