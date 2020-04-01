package cdinit.netty.Client;

import io.netty.util.CharsetUtil;

import java.io.*;
import java.net.Socket;

public class SimpleSocketClient {
    public static void main(String[] args) throws Exception {
        Socket socket = null;
        DataInputStream dis = null;
        PrintWriter pw = null;
        String msg = "";

        File file = new File("C:\\Users\\admin\\Desktop\\xml.txt");

        FileInputStream is = null;
        StringBuilder stringBuilder = null;
        try {
            /**
             * 文件有内容才去读文件
             */
            is = new FileInputStream(file);
            InputStreamReader streamReader = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line);
            }
            msg = String.valueOf(stringBuilder);
            reader.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] resBytes;
        try {
            socket = new Socket("127.0.0.1", 9999);
            pw = new PrintWriter(socket.getOutputStream());
            byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
            int length = bytes.length;
            String res  = "00"+String.valueOf(length) + msg; // 模拟前8位
            pw.write(res);
            pw.flush();

            // 接收
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            byte[] resLenBytes = new byte[8];
            dis.read(resLenBytes);
            Integer resLen = Integer.parseInt(new String(resLenBytes,CharsetUtil.UTF_8));
            resBytes = new byte[resLen];
            dis.read(resBytes);
            System.out.println(new String(resBytes,CharsetUtil.UTF_8));

        } finally {
            if(pw != null){
                pw.close();
            }
            if(dis != null){
                dis.close();
            }
            if(socket != null){
                socket.close();
            }
        }

//        System.out.println(new String(resBytes,CharsetUtil.UTF_8));

    }
}
