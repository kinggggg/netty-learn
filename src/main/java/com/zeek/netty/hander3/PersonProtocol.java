package com.zeek.netty.hander3;

/**
 * @ClassName PersonProtocol
 * @Description
 * @Author liweibo
 * @Date 2020/1/6 4:16 PM
 * @Version v1.0
 **/
public class PersonProtocol {

    private int length;

    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
