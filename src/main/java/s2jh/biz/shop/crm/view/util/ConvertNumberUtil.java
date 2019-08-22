package s2jh.biz.shop.crm.view.util;
import java.util.Stack;

/**
 * 数字转换工具类
 * @author wy
 *
 */
public class ConvertNumberUtil {
    private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    
 /** 
     * 将10进制转化为62进制  
     * @param number  
     * @param length 转化成的62进制长度，不足length长度的话高位补0，否则不改变什么 
     * @return 
     */  
    public static String convertBase10ToBase62(long number, int length){  
         Long rest=number;  
         Stack<Character> stack=new Stack<Character>();  
         StringBuilder result=new StringBuilder(0);  
         while(rest!=0){  
             stack.add(charSet[new Long((rest-(rest/62)*62)).intValue()]);  
             rest=rest/62;  
         }  
         for(;!stack.isEmpty();){  
             result.append(stack.pop());  
         }  
         int resultLength = result.length();  
         StringBuilder temp0 = new StringBuilder();  
         for(int i = 0; i < length - resultLength; i++){  
             temp0.append('0');  
         }  
           
         return temp0.toString() + result.toString();  
  
    }  
      
    /** 
     * 将62进制转换成10进制数 ，我重新写了这个函数，原先版本有问题
     *  
     * @param ident62 
     * @return 
     */  
    public static String convertBase62ToBase10( String ident62 ) {  
        Long dst = 0L;
        for(int i=0; i<ident62.length(); i++)
        {
            char c = ident62.charAt(i);
            for(int j=0; j<charSet.length; j++)
            {
                if(c == charSet[j])
                {
                    dst = (dst * 62) + j;
                    break;
                }
            }
        }
        String str = String.format( "%08d", dst);
        return str;
    }
    
    /** 
     * 测试
     * @param args 
     */  
    public static void main(String[] args) {  
        
        System.out.println("62System=" +convertBase10ToBase62(Long.parseLong("1710000000001"), 0));  
        
        System.out.println("10System=" +convertBase62ToBase10("U6XZjVJ"));   //120160524121052485
    }  
    
}