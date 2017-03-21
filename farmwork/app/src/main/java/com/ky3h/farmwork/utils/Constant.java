package com.ky3h.farmwork.utils;

import android.os.Environment;

import com.ky3h.farmwork.bean.YueYaoItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipengcheng on 2016/8/16.
 * you are sb
 */
public class Constant {
    public static final String BASE_URL = "http://119.254.24.4:7006";
    public static final String LOGIN_URL = BASE_URL
            + "/login/commit.jhtml";
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/farmwork";
    public static final String UPLOAD_PATH = BASE_PATH + "/" + "upload";

    public static int sourceJump=0;

    public static final boolean DEBUG = false;
    public static final String PATH = "yanhuang";

    public static final String DOWNLOAD_YUEYAO_PATH = PATH + "/" + "yueyao";
    public static final String DOWNLOAD_SHIFANYIN_PATH = PATH + "/" + "shifanyin";
    public final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getPath()
            + "/yanhuang/cache";
    //头像保存路径
    public static final String FILE_SAVEPATH_ICON=Constant.FILE_SAVEPATH +"/"+ System.currentTimeMillis() + ".jpg";

    public static final String CURRENT_TAB = "current_tab";

    public static final String PREFERENCE = "yanhuang";
    public static final String LAST_DIAGNOSIS_TIME = "lastDiagnosisTime";
    public static final String USED_COUNT = "usedCount";

    public static List<YueYaoItem> LIST = new ArrayList<YueYaoItem>();//保存加入购物车里面的乐药
    public static final List<YueYaoItem> LIST_DELETE_YUEYAO=new ArrayList<>();//保存在乐药结算那一步删除的乐药


    public static final int API_SUCCESS_CODE = 100;
    public static final int API_UNLOGIN = 44;
    public static final int CARD_ISBIND = 46;
    public static final int UNDEFINED = -1;

    public static final String UPLOAD_SERVER = "116.255.142.98";
    public static final int UPLOAD_SERVER_PORT = 7878;

    public static final String MUSIC_FORMAT_NAME = ".mp3";
    public static final String MUSIC_FORMAT_NAME_Downing = ".mp3.downing";
    public static final String DOWNLOAD_URL_TEST = "http://116.255.142.98/dx/sga.mp3";

    /** 友盟分享 */
    public static final String DESCRIPTOR = "com.umeng.share";

    private static final String TIPS = "请移步官方网站 ";
    private static final String END_TIPS = ", 查看相关说明.";
    public static final String TENCENT_OPEN_URL = TIPS
            + "http://wiki.connect.qq.com/android_sdk使用说明" + END_TIPS;
    public static final String PERMISSION_URL = TIPS
            + "http://wiki.connect.qq.com/openapi权限申请" + END_TIPS;

    public static final String SOCIAL_LINK = "http://www.umeng.com/social";
    public static final String SOCIAL_TITLE = "友盟社会化组件帮助应用快速整合分享功能";
    public static final String SOCIAL_IMAGE = "http://www.umeng.com/images/pic/banner_module_social.png";

    public static final String SOCIAL_CONTENT = "友盟社会化组件（SDK）让移动应用快速整合社交分享功能，我们简化了社交平台的接入，为开发者提供坚实的基础服务：（一）支持各大主流社交平台，"
            + "（二）支持图片、文字、gif动图、音频、视频；@好友，关注官方微博等功能"
            + "（三）提供详尽的后台用户社交行为分析。http://www.umeng.com/social";

    // public static final String URL_PRE =
    // "http://www.happyto.net/VoiceDiagnosis/voice/";
    // public static final String URL_PRE =
    // "http://www.happyto.net/Diagnosis/voice";
    // 炎黄官网
    // public static final String URL_PRE =
    // "http://119.254.24.4:8001/VoiceDiagnosis/voice";
    // public static final String URL_PRE =
    // "http://192.168.1.216:8080/healthlm";
    // public static final String URL_PRE =
    // "http://115.28.32.36:10088/healthlm";
    public static final String PRODUCTION_SYSTEM_URL = "http://eky3h.com/healthlm/";//正式版
    public static final String TEST_SYSTEM_URL = "http://119.254.24.4:7006/";//测试环境


    public static final String TEST_SYSTEM_COOKIE = "http://119.254.24.4:7006";//测试版的Cookie
    public static final String PRODUCTION_SYSTEM_COOKIE = "http://eky3h.com";//正式版的Cookie

    public static final String PRODUCTION_SYSTEM_HEADER = "android_wybs_yyb-yh-";//正式版
    public static final String TEST_SYSTEM_HEADER = "android_wybs-yh-";//测试版


    public final static DecimalFormat decimalFormat = new DecimalFormat("#.##");//保留小数点后两位


    public static final String H5_URL = "http://115.28.32.36:10088/healthlm/";
    // public static final String URL_PRE =
    // "http://app.ky3h.com:10088/healthlm/";
    // 测试地址
    // public static final String URL_PRE =
    // "http://192.168.1.210:8080/yhwy/voice";
    // public static final String URL_PRE =
    // "http://192.168.1.210:7001/taiping/voice";
    // public static final String URL_PRE =
    // "http://liuzyx.vicp.cc:17001/taiping/voice";
    // 以下为功法图片文案相关
    // 预备动作
    public static final String yubeidongzuo = "首先，将和畅行调整到适宜坡度，坡度选择需循序渐进，以脚踝、小腿、膝盖、大腿后侧的肌肉略有拉抻感为宜。";
    // 起式
    public static final String qishi = "沉肩、坠肘、松胯，两手臂自然垂直于身体两侧，尽量放松身体，保持身体与地面垂直。";

    // 第一式 箭指长天
    public static final String diyishi_jianzhichangtian_1 = "脚尖并拢，脚跟分开45度，呈内八字型；舌抵上腭，鼻息调匀。";
    public static final String diyishi_jianzhichangtian_2 = "自然呼吸，两臂自体侧缓缓向上抬起，自然伸直紧贴双耳，掌心相对，两手相握，食指伸直，成“剑指”。";
    public static final String diyishi_jianzhichangtian_3 = "自双臂、头、颈、背、腰依次缓缓向后弯曲至极限，随着呼吸，身体不断向极限拉抻，完成10个呼吸即可。";

    // 第二式 海底捞月
    public static final String diershi_haidianlaoyue_1 = "双脚保持原有姿势不变，舌抵上腭，鼻息调匀，双腿保持直立。";
    public static final String diershi_haidianlaoyue_2 = "吸气，随之身体慢慢向下弯曲，双臂从体侧自然下垂至身前，头、颈、肩、臀依次松弛下来。轻轻左右依次摆动臀部、背部、肩部、颈部和头部，保持自然的呼吸。";
    public static final String diershi_haidianlaoyue_3 = "于极限处保持3分钟，此时腘窝会出现紧绷感，可拉抻足少阴肾经，有助于疏通此处的 “筋结”，能明显缓解腰背痛。";
    // public static final String diershi_haidianlaoyue_4 =
    // "于极限处保持3分钟，此时腘窝会出现紧绷感，可拉抻足少阴肾经，有助于疏通此处的 “筋结”，能明显缓解腰背痛。";
    // public static final String diershi_haidianlaoyue_5 =
    // "身体缓缓归于正位，双臂放松，回归体侧。";

    // 第三式 太极云手
    public static final String disanshi_taijiyunshou_1 = "双脚打开，与肩同宽，脚尖内扣，脚跟呈45度内八字形，舌抵上腭，鼻息调匀。";
    public static final String disanshi_taijiyunshou_2 = "左手缓缓抬起至额前，目视手心。右侧手臂抬起至小腹肚脐处左手臂带动上半身缓缓左转，至极限处，稍停顿3-5秒（注意髋关节保持不动）。";
    public static final String disanshi_taijiyunshou_3 = "缓缓回归正位的同时，右手臂抬起至额前，目视右手心。左侧手臂抬起至小腹肚脐处；右手臂带动上半身缓缓右转，至极限处，稍停顿3-5秒（注意髋关节保持不动）。如此左右往复10次。";

    // 第四式 高山流水
    public static final String disishi_gaoshanliushui_1 = "双脚打开，与肩同宽，脚尖内扣，脚跟呈45度内八字形，舌抵上腭，鼻息调匀。";
    public static final String disishi_gaoshanliushui_2 = "身体缓缓左倾下拉，左手沿裤线下探至极限，与此同时，右手上行至腋下，头部自然左倾，靠近肩部，稍作停顿。";
    public static final String disishi_gaoshanliushui_3 = "身体缓缓右倾下拉，右手沿裤线下探至极限，与此同时，左手上行至腋下，头部自然左倾，靠近肩部，稍作停顿。";
    // public static final String disishi_gaoshanliushui_4 = "身体缓缓归于正位，调整呼吸。";

    // 第五式 俯身探海
    public static final String diwushi_wushentanhai_1 = "保持姿势不变，舌抵上腭，鼻息调匀。";
    public static final String diwushi_wushentanhai_2 = "双腿保持直立，身体慢慢向下弯曲，双臂从体侧尽量下垂至身前，臀、背、肩、颈、头依次松弛下来，保持呼吸均匀。";
    public static final String diwushi_wushentanhai_3 = "轻轻的左右依次摆动头部、颈部、肩部和臀部，有助于不断向下拉伸至极限，于极限处保持3分钟，可加强对足少阴肾经、足厥阴肝经、足太阴脾经的拉抻。";

    // 第六式 俯身抱月
    public static final String diliushi_fushenbaoyue_1 = "身体后转，双脚并拢直立于和畅行上；双手自然垂于体侧，下颏微收，唇齿合拢，舌自然平贴于上腭。";
    public static final String diliushi_fushenbaoyue_2 = "身体前俯经骶椎、腰椎、胸椎、颈椎，依次放松，逐节缓慢牵引前屈。两腿缓缓下蹲，双手合抱于膝上，头部贴于膝上1分钟。";

    // 收式
    public static final String shoushi = "慢慢站起，从和畅行上走下，轻轻拍打四肢。";

}
