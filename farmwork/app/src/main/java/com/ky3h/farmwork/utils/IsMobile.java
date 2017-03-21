package com.ky3h.farmwork.utils;

import android.text.TextUtils;

/**
 * Created by l on 2016/7/7.
 * 验证是否为手机号
 */
public class IsMobile {
    /**
     * 判断手机格式是否正确
     * @param mobiles
     * @return
     * 　手机号码开头前三位数字叫号段，是由国家分配给各个营运商的，每个运营商的号段不同。
    　　三大运营商号段：
     * 中国移动号段：134、135、136、137、138、139、150、151、152、157、158、159、147、182、183、184、187、188、178；
     * 中国联通号段：130、131、132、145（145属于联通无线上网卡号段）、155、156、185、186 、176、176；
     * 　中国电信号段：133 、153 、180 、181 、189 、177。
     * 总结起来就是第一位必定为1，第二位必定为3或4或5或或8，其他位置的可以为0-9
     */
    public static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3，4，5，7，8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}" ;
        if (TextUtils.isEmpty(mobiles))
        {
            return false ;
        }
        else
        {
            return mobiles.matches( telRegex ) ;
        }

    }
}
