package org.shaw.advance.io.bytes;

/**
 * Created by joy on 17-2-2.
 */
public class EncodeDemo {
    public static void main(String[] args) throws Exception {

    }


    /**
     * String编码转换
     * @param str
     * @throws Exception
     */
    public static void StringToEncode(String str) throws Exception {
        byte[] bytes = str.getBytes();

        /*       String编码还原          */
        String string = new String(bytes); //默认使用项目编码
        System.out.println(string);

        String string1 = new String(bytes,"utf-16be");  //使用原来编码还原
        System.out.println("utf-16be"+":"+string1);

        String string2 = new String(bytes,"utf-8");
        System.out.println("utf-8"+":"+string2);
    }

    /**
     * 16进制编码
     * @throws Exception
     */
    public static void Encode() throws Exception {
        String str = "学习JAVA";
        /*       项目编码          */
        byte[] bytes = str.getBytes();  // 默认使用项目编码
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(Integer.toHexString(bytes[i] & 0xff) + " ");
            if (i == bytes.length - 1) {
                System.out.println();
            }
        }

        /*       JAVA编码          */
        byte[] bytes1 = str.getBytes("utf-16be");  //指定编码
        for (int i = 0; i < bytes1.length; i++) {
            System.out.print(Integer.toHexString(bytes1[i] & 0xff) + " ");
            if (i == bytes1.length - 1) {
                System.out.println();
            }
        }
    }

}
