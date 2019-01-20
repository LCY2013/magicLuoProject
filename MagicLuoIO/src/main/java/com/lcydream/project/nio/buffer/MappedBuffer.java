package com.lcydream.project.nio.buffer;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.*;
import java.nio.channels.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class MappedBuffer {  
    static private final int start = 0;
    static private final int size = 1024;  
      
    static public void main( String args[] ) throws Exception {  
        RandomAccessFile raf = new RandomAccessFile( "D:\\test.txt", "rw" );
        FileChannel fc = raf.getChannel();  
        
        //把缓冲区跟文件系统进行一个映射关联
        //只要操作缓冲区里面的内容，文件内容也会跟着改变
        MappedByteBuffer mbb = fc.map( FileChannel.MapMode.READ_WRITE,start, size );  
          
        mbb.put( 0, (byte)97 );  
        mbb.put( 1023, (byte)122 );

        clean(mbb);

        raf.close();
    }

    /**
     * 直接缓冲区释放解决方案
     * @param buffer 释放的参数
     * @throws Exception 异常
     */
    public static void clean(final Object buffer) throws Exception {
        AccessController.doPrivileged(new PrivilegedAction() {
            @Override
            public Object run() {
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
                    cleaner.clean();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}