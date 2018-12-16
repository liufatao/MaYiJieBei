package com.mayi.mayijiebei;

import android.content.Context;
import java.text.DecimalFormat;

/**
 * File description.
 *
 * @author liufatao
 * @date 2018/12/15
 */
public class Utils {
  /**
   * dp转换成px
   */
  public static int dp2px(Context context,float dpValue){
    float scale=context.getResources().getDisplayMetrics().density;
    return (int)(dpValue*scale+0.5f);
  }

  /**
   * px转换成dp
   */
  public static int px2dp(Context context,float pxValue){
    float scale=context.getResources().getDisplayMetrics().density;
    return (int)(pxValue/scale+0.5f);
  }
  /**
   * sp转换成px
   */
  public static int sp2px(Context context,float spValue){
    float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue*fontScale+0.5f);
  }
  /**
   * px转换成sp
   */
  public int px2sp(Context context,float pxValue){
    float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue/fontScale+0.5f);
  }
  /**
   * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
   *
   * @param str 需要处理的字符串
   * @return 处理完之后的字符串
   */
  public static String addComma(String str) {
    DecimalFormat decimalFormat = new DecimalFormat(",###");
    return decimalFormat.format(Double.parseDouble(str));
  }


}
